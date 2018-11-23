package net.nanquanyuhao.codeanalysis4j.model;

import net.nanquanyuhao.codeanalysis4j.common.VariableConstant;

/**
 * Created by nanquanyuhao on 2018/11/23.
 * <p>
 * 模板项目
 */
public class TemplateProject {

    /**
     * 模板任务在 Jenkins 中任务名称，即系统中配置的模板任务名
     */
    private final String jenkinsJobName;

    /**
     * 模板任务在 Jenkins 中解析的项目 XML 文件
     */
    private final String templateProjectXml;

    /**
     * 模板项目的 Sonarqube 中的配置信息
     */
    private final CodeAnalysisProject cap;

    public TemplateProject(String jenkinsJobName, String templateProjectXml, CodeAnalysisProject cap) {
        this.jenkinsJobName = jenkinsJobName;
        this.templateProjectXml = templateProjectXml;
        this.cap = cap;
    }

    /**
     * 获取模板项目配置文件 XML
     *
     * @return
     */
    public String getTemplateProjectXml() {
        return templateProjectXml;
    }

    /**
     * 获取模板项目 Key 替换字符串
     *
     * @return
     */
    public String getTemplateRepositoryKey() {

        StringBuilder stringBuilder = new StringBuilder(VariableConstant.PROJECT_KEY);
        stringBuilder.append("=").append(cap.getProjectKey());

        return stringBuilder.toString();
    }

    /**
     * 获取模板项目 Name 替换字符串
     *
     * @return
     */
    public String getTemplateRepositoryName() {

        StringBuilder stringBuilder = new StringBuilder(VariableConstant.PROJECT_NAME);
        stringBuilder.append("=").append(cap.getProjectName());

        return stringBuilder.toString();
    }

    /**
     * 获取模板项目仓库地址替换字符串（地址全路径）
     *
     * @return
     */
    public String getTemplateRepositoryURL() {

        return cap.getProjectURL();
    }
}
