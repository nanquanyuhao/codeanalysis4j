package net.nanquanyuhao.codeanalysis4j.impl;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import net.nanquanyuhao.codeanalysis4j.CodeAnalysisManager;
import net.nanquanyuhao.codeanalysis4j.common.VariableConstant;
import net.nanquanyuhao.codeanalysis4j.exception.CodeAnalysisException;
import net.nanquanyuhao.codeanalysis4j.model.CodeAnalysisJob;
import net.nanquanyuhao.codeanalysis4j.model.CodeAnalysisProject;
import net.nanquanyuhao.codeanalysis4j.model.TemplateProject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by nanquanyuhao on 2018/11/20.
 */
public class CodeAnalysisManagerImpl implements CodeAnalysisManager {

    /**
     * 初始化 Jenkins 客户端
     */
    private final JenkinsServer jenkinsServer;

    /**
     * 代码拉取仓库地址
     */
    private final String repositoryURL;

    /**
     * 代码分析模板项目名称
     */
    private final String templateProjectName;

    /**
     * 模板项目（禁止指令重排，确保有序取内存数据保证最新）
     */
    private volatile TemplateProject templateProject;

    /**
     * 实际服务组件构造器
     *
     * @param serverUri           Jenkins 服务地址
     * @param jenkinsUsername     Jenkins 服务管理员用户名
     * @param jenkinsPassword     Jenkins 服务管理员用户密码
     * @param repositoryURL       代码仓库地址
     * @param templateProjectName 代码模板项目名
     */
    public CodeAnalysisManagerImpl(String serverUri, String jenkinsUsername, String jenkinsPassword, String repositoryURL, String templateProjectName) {


        URI url = null;
        try {
            url = new URI(serverUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.jenkinsServer = new JenkinsServer(url, jenkinsUsername, jenkinsPassword);
        this.repositoryURL = repositoryURL;
        this.templateProjectName = templateProjectName;
    }

    @Override
    public CodeAnalysisJob createJob(String projectKey, String projectName, String projectURL) {

        // 获取模板项目
        TemplateProject templateProject = refreshTemplateProject();
        String projectXml = templateProject.getTemplateProjectXml().replace(templateProject.getTemplateRepositoryKey(), VariableConstant.PROJECT_KEY + "=" + projectKey)
                .replace(templateProject.getTemplateRepositoryName(), VariableConstant.PROJECT_NAME + "=" + projectName)
                .replace(templateProject.getTemplateRepositoryURL(), repositoryURL + projectURL);

        JobWithDetails job = null;
        try {
            jenkinsServer.createJob(projectKey, projectXml);
            job = jenkinsServer.getJob(projectKey);
            job.build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CodeAnalysisException("网络异常！");
        }

        CodeAnalysisJob codeAnalysisJob = new CodeAnalysisJob(job, projectKey, projectName, projectURL);
        return codeAnalysisJob;
    }

    @Override
    public void build(String projectKey) {

        refreshTemplateProject();
        try {
            JobWithDetails job = jenkinsServer.getJob(projectKey);
            job.build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CodeAnalysisException("网络异常！");
        }
    }

    /**
     * 类内部获取模板项目
     *
     * @return
     */
    private TemplateProject refreshTemplateProject() {

        if (templateProject == null) {
            synchronized (CodeAnalysisManagerImpl.class) {
                if (templateProject == null) {

                    String templateProjectXml = null;
                    try {
                        templateProjectXml = jenkinsServer.getJobXml(templateProjectName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new CodeAnalysisException("网络异常！");
                    }

                    CodeAnalysisProject cap = ParseUtils.parseProjectWithXml(templateProjectXml);
                    templateProject = new TemplateProject(templateProjectName, templateProjectXml, cap);
                }
            }
        }

        return templateProject;
    }
}
