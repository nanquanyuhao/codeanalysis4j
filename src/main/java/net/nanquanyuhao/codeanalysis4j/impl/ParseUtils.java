package net.nanquanyuhao.codeanalysis4j.impl;

import net.nanquanyuhao.codeanalysis4j.common.VariableConstant;
import net.nanquanyuhao.codeanalysis4j.model.CodeAnalysisProject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by nanquanyuhao on 2018/11/20.
 */
class ParseUtils {

    static CodeAnalysisProject parseProjectWithXml(final String xml) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element buildElement = document.getRootElement().element("builders").element("hudson.plugins.sonar.SonarRunnerBuilder");
        Element element = document.getRootElement().element("scm").element("userRemoteConfigs").element("hudson.plugins.git.UserRemoteConfig");

        String[] propertiesText = buildElement.elementText("properties").split("\n");
        String projectKey = null;
        String projectName = null;

        boolean projectKeyIsSet = false;
        boolean projectNameIsSet = false;
        for (String property : propertiesText) {
            if (!projectKeyIsSet && property.startsWith(VariableConstant.PROJECT_KEY)) {
                projectKey = property.replace(VariableConstant.PROJECT_KEY + "=", "").trim();
                projectKeyIsSet = true;
                continue;
            }
            if (!projectNameIsSet && property.startsWith(VariableConstant.PROJECT_NAME)) {
                projectName = property.replace(VariableConstant.PROJECT_NAME + "=", "").trim();
                projectNameIsSet = true;
                continue;
            }
        }
        String projectURL = element.elementText("url");
        CodeAnalysisProject cap = new CodeAnalysisProject(projectKey, projectName, projectURL);

        return cap;
    }
}
