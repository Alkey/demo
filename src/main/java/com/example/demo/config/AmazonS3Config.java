package com.example.demo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
    @Value("#{environment.AWS_S3_ACCESS_KEY}")
    private String accessKey;
    @Value("#{environment.AWS_S3_SECRET_KEY}")
    private String secretKey;

    @Bean
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey)))
                .withRegion(Regions.EU_NORTH_1)
                .build();
    }

}
