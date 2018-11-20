package net.nanquanyuhao.codeanalysis4j.model;

/**
 * Created by nanquanyuhao on 2018/11/20.
 */
public class CodeAnalysisProject {

    /**
     * 项目唯一标识
     */
    private final String projectKey;

    /**
     * 项目唯一标识
     */
    private final String projectName;

    /**
     * 项目唯一标识
     */
    private final String projectURL;

    public CodeAnalysisProject(String projectKey, String projectName, String projectURL) {
        this.projectKey = projectKey;
        this.projectName = projectName;
        this.projectURL = projectURL;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectURL() {
        return projectURL;
    }
}
