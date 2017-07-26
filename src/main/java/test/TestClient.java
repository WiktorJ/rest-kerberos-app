package test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.kerberos.client.KerberosRestTemplate;

/**
 * Created by wjurasz on 20.07.17.
 */
//@SpringBootApplication
//@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class TestClient
//        implements CommandLineRunner
{

    public static void main(String[] args) {
        KerberosRestTemplate restTemplate =
                new KerberosRestTemplate("/home/wjurasz/IdeaProjects/simple-rest-app-test/cern.keytab", "test@PCBE15493.DYNDNS.CERN.CH");
        restTemplate.getForObject("http://pcbe15493.dyndns.cern.ch:8080/admin", String.class);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        KerberosRestTemplate restTemplate =
//                new KerberosRestTemplate("/home/wjurasz/IdeaProjects/simple-rest-app-test/example.keytab", "test@EXAMPLE.COM");
//        restTemplate.getForObject("http://localhost:8080/test2", String.class);
//    }
}
