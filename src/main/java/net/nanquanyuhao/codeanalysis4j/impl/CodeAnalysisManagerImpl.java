package net.nanquanyuhao.codeanalysis4j.impl;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import net.nanquanyuhao.codeanalysis4j.CodeAnalysisManager;
import net.nanquanyuhao.codeanalysis4j.common.VariableConstant;
import net.nanquanyuhao.codeanalysis4j.exception.CodeAnalysisException;
import net.nanquanyuhao.codeanalysis4j.model.CodeAnalysisJob;
import net.nanquanyuhao.codeanalysis4j.model.CodeAnalysisProject;

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
    private final String templateProject;
    /**
     * 代码分析模板项目 Jenkins XML 文件
     */
    private String templateProjectXml;

    /**
     * 用于替换使用的代码分析模板项目的项目标识
     */
    private String templateRepositoryKey;
    /**
     * 用于替换使用的代码分析模板项目的项目名称
     */
    private String templateRepositoryName;
    /**
     * 用于替换使用的代码分析模板项目的代码仓库地址
     */
    private String templateRepositoryURL;

    /**
     * 实际服务组件构造器
     *
     * @param serverUri       Jenkins 服务地址
     * @param jenkinsUsername Jenkins 服务管理员用户名
     * @param jenkinsPassword Jenkins 服务管理员用户密码
     * @param repositoryURL   代码仓库地址
     * @param templateProject 代码模板项目名
     */
    public CodeAnalysisManagerImpl(String serverUri, String jenkinsUsername, String jenkinsPassword, String repositoryURL, String templateProject) {

        URI url = null;
        try {
            url = new URI(serverUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.jenkinsServer = new JenkinsServer(url, jenkinsUsername, jenkinsPassword);
        this.repositoryURL = repositoryURL;
        this.templateProject = templateProject;

        try {
            templateProjectXml = jenkinsServer.getJobXml(templateProject);
            CodeAnalysisProject cap = ParseUtils.parseProjectWithXml(templateProjectXml);

            templateRepositoryKey = VariableConstant.PROJECT_KEY + "=" + cap.getProjectKey();
            templateRepositoryName = VariableConstant.PROJECT_NAME + "=" + cap.getProjectName();
            templateRepositoryURL = cap.getProjectURL();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CodeAnalysisJob createJob(String projectKey, String projectName, String projectURL) {

        String projectXml = templateProjectXml.replace(templateRepositoryKey, VariableConstant.PROJECT_KEY + "=" + projectKey)
                .replace(templateRepositoryName, VariableConstant.PROJECT_NAME + "=" + projectName)
                .replace(templateRepositoryURL, repositoryURL + projectURL);

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
}
