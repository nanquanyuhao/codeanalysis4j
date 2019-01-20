package net.nanquanyuhao.codeanalysis4j;

import net.nanquanyuhao.codeanalysis4j.model.CodeAnalysisJob;

/**
 * @author nanquanyuhao
 */
public interface CodeAnalysisManager {

    /**
     * 创建一个 Jenkins 代码检测任务
     *
     * @param projectKey  项目主键
     * @param projectName 项目名称
     * @param projectURL  项目地址
     * @return 构建任务信息
     */
    CodeAnalysisJob createJob(String projectKey, String projectName, String projectURL);

    /**
     * 据项目唯一标识触发一次构建
     *
     * @param projectKey 项目主键
     */
    void build(String projectKey);
}
