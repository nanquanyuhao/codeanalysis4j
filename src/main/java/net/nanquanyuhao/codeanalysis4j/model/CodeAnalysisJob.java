package net.nanquanyuhao.codeanalysis4j.model;

import com.offbytwo.jenkins.model.JobWithDetails;

/**
 * @author nanquanyuhao
 */
public class CodeAnalysisJob {

    /**
     * Jenkins 任务原生属性
     */
    private final JobWithDetails jwd;

    /**
     * 用户传值构造的项目，包含项目标识、名称及仓库地址
     */
    private final CodeAnalysisProject cap;

    /**
     * 代码检测任务实体构造器
     *
     * @param jwd         Jenkins 任务信息实体
     * @param projectKey  项目主键
     * @param projectName 项目名称
     * @param projectURL  项目地址
     */
    public CodeAnalysisJob(JobWithDetails jwd, String projectKey, String projectName, String projectURL) {
        this.jwd = jwd;
        this.cap = new CodeAnalysisProject(projectKey, projectName, projectURL);
    }

    public JobWithDetails getJwd() {
        return jwd;
    }

    public String getProjectKey() {
        return cap.getProjectKey();
    }

    public String getProjectName() {
        return cap.getProjectName();
    }

    public String getProjectURL() {
        return cap.getProjectURL();
    }
}
