package net.nanquanyuhao.codeanalysis4j.model;

/**
 * @author nanquanyuhao
 */
public class CodeAnalysisProject {

    /**
     * 项目唯一标识
     */
    private final String projectKey;

    /**
     * 项目名称
     */
    private final String projectName;

    /**
     * 项目仓库地址
     */
    private final String projectURL;

    /**
     * 代码检测项目构造器
     *
     * @param projectKey  项目唯一标识
     * @param projectName 项目名称
     * @param projectURL  项目仓库地址
     */
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
