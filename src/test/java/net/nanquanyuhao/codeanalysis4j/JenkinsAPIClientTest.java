package net.nanquanyuhao.codeanalysis4j;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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

    private static void createJob(JenkinsServer jenkins) {

        try {
            String str = jenkins.getJobXml("sonarqube-template");

            String newStr = str.replace("sonar.projectKey=sonarqube-template", "sonar.projectKey=dd574ca0a9b54aa2b542f9738fc62d29")
                    .replace("sonar.projectName=sonarqube-template", "sonar.projectName=liyeheng/paas")
                    // 代码仓库地址
                    .replace("http://192.168.41.67:10080/liyeheng/gs-spring-boot-docker.git", "http://192.168.41.67:10080/liyeheng/paas.git");

            // jenkins.createJob("paas", newStr);

            //JobWithDetails job = jenkins.getJob("paas");

            String xml = jenkins.getJobXml("sonarqube-template");

            Document document = null;
            try {
                document = DocumentHelper.parseText(xml);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Element buildElement = document.getRootElement().element("builders").element("hudson.plugins.sonar.SonarRunnerBuilder");

            String[] propertiesText = buildElement.elementText("properties").split("\n");

            System.out.println(buildElement.elementText("properties"));

            System.out.println("——————————————————————");
            int i = 0;
            for (String string : propertiesText) {
                System.out.println(++i);
                System.out.println(string);
            }

            //job.build();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jenkins != null) {
                jenkins.close();
            }
        }
    }
}
