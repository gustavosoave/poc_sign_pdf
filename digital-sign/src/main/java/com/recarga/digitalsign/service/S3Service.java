package com.recarga.digitalsign.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

@Service
public class S3Service {

    private final AmazonS3 amazonS3Client = null;

    public PDDocument getFile(String bucketName, String fileName){
        S3Object s3object = amazonS3Client.getObject(bucketName, fileName);
        //return s3object.getObjectContent();

        return new PDDocument();
    }
}
