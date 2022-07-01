package com.unipi.ipap.springselfsignedcertificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSelfSignedCertificateApplication {

    public static void main(String[] args) {
        // SpringApplication.run(SpringSelfSignedCertificateApplication.class, args);
        SpringApplication application = new SpringApplication(SpringSelfSignedCertificateApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);
    }

}
