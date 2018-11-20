package net.nanquanyuhao.codeanalysis4j;

import net.nanquanyuhao.codeanalysis4j.model.CodeAnalysisJob;

/**
 * Created by nanquanyuhao on 2018/11/19.
 */
public interface CodeAnalysisManager {

    /**
     * 创建一个 Jenkins 代码检测任务
     *
     * @param projectKey
     * @param projectName
     * @param projectURL
     * @return
     */
    CodeAnalysisJob createJob(String projectKey, String projectName, String projectURL);

    /**
     * 据项目唯一标识触发一次构建
     *
     * @param projectKey
     */
    void build(String projectKey);
}
