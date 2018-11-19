package net.nanquanyuhao.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by nanquanyuhao on 2018/10/16.
 */
public class JenkinsAPIClientTest {

    public static void main(String[] args) throws URISyntaxException, IOException {

        JenkinsServer jenkins = new JenkinsServer(new URI("http://techjenkins:8080"), "liyh1928", "1qaz@WSX");

        Map<String, Job> jobs = jenkins.getJobs();
        createJob(jenkins);
        //JobWithDetails job = jobs.get("gs-spring-boot-docker").details();
    }

    private static void createJob(JenkinsServer jenkins){

        try {
            String str = jenkins.getJobXml("sonarqube-template");

            String newStr = str.replace("sonar.projectKey=sonarqube-template", "sonar.projectKey=liyeheng.paas")
                    .replace("sonar.projectName=sonarqube-template", "sonar.projectName=liyeheng/paas")
                    // 代码仓库地址
                    .replace("http://192.168.41.67:10080/liyeheng/gs-spring-boot-docker.git", "http://192.168.41.67:10080/liyeheng/paas.git");

            jenkins.createJob("paas", newStr);

            JobWithDetails job = jenkins.getJob("paas");
            job.build();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jenkins != null) {
                jenkins.close();
            }
        }
    }
}
