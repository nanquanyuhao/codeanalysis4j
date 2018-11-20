package net.nanquanyuhao.codeanalysis4j;

import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.system.SystemInfo;

/**
 * Created by nanquanyuhao on 2018/10/16.
 */
public class JenkinsRestTest {

    public static void main(String[] args) {
        JenkinsClient client = JenkinsClient.builder()
                .endPoint("http://techjenkins:8080") // Optional. Defaults to http://127.0.0.1:8080
                .credentials("liyh1928:1qaz@WSX") // Optional.
                .build();

        SystemInfo systemInfo = client.api().systemApi().systemInfo();
    }
}
