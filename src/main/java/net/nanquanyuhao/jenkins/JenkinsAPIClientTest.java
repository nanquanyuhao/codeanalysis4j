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

        JobWithDetails job = jobs.get("gs-spring-boot-docker").details();
    }
}
