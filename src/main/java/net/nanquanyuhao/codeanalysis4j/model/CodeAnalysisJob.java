package net.nanquanyuhao.codeanalysis4j.model;

import com.offbytwo.jenkins.model.JobWithDetails;

/**
 * Created by nanquanyuhao on 2018/11/20.
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
     * @param jwd
     * @param projectKey
     * @param projectName
     * @param projectURL
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
