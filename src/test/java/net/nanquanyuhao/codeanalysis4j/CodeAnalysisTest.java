package net.nanquanyuhao.codeanalysis4j;

import net.nanquanyuhao.codeanalysis4j.impl.CodeAnalysisManagerImpl;
import net.nanquanyuhao.codeanalysis4j.model.CodeAnalysisJob;

/**
 * Created by nanquanyuhao on 2018/11/20.
 */
public class CodeAnalysisTest {

    public static void main(String[] args) {

        // 项目中直接由 Spring 进行实例化，业务模块使用 @Autowired 注入即可
        CodeAnalysisManager cam = new CodeAnalysisManagerImpl("http://techjenkins:8080", "liyh1928", "1qaz@WSX", "http://192.168.41.67:10080/", "sonarqube-template");

        // 创建并构建
        CodeAnalysisJob caj = cam.createJob("liyeheng.paas","liyeheng/paas","liyeheng/paas.git");
        // 构建
        // cam.build("liyeheng.paas");
    }
}
