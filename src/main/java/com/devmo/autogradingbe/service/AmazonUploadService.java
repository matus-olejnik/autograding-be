package com.devmo.autogradingbe.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class AmazonUploadService implements UploadSvc {

    @Value("${aws-access-key}")
    private String AWS_ACCESS_KEY;

    @Value("${aws-secret-key}")
    private String AWS_SECRET_KEY;

    @Value("${aws-bucket-name}")
    private String AWS_BUCKET_NAME;

    public static final String AWS_URL_FORMAT = "https://%s.s3.amazonaws.com/%s";

    @Override
    public String uploadSolution(File fileToUpload) {
        AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        String[] fileNameParts = fileToUpload.getName().split("\\.");
        String fileName = fileNameParts[0];
        String fileExtension = fileNameParts[fileNameParts.length - 1];

        String fileNameHash = UUID.nameUUIDFromBytes(fileName.getBytes(StandardCharsets.UTF_8))
                .toString()
                .replaceAll("-", "");

        String objectKey = fileNameHash + "." + fileExtension;

        s3client.putObject(
                new PutObjectRequest(AWS_BUCKET_NAME, objectKey, fileToUpload)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return String.format(AWS_URL_FORMAT, AWS_BUCKET_NAME, objectKey);
    }
}
