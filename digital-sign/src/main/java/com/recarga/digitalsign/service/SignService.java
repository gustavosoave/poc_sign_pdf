package com.recarga.digitalsign.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.ExternalSigningSupport;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class SignService {

    @Autowired
    private S3Service s3Service;

    @Value("bucketName")
    private String bucketName;

    public void sign(String fileName, OutputStream output){

        // Create a Document object.
        PDDocument pdDocument = s3Service.getFile(bucketName, fileName);

        // Create a Page object
        PDPage pdPage = new PDPage();
        // Add the page to the document and save the document to a desired file.
        pdDocument.addPage(pdPage);
        try {

            PDSignature pdSignature = new PDSignature();
            pdSignature.setFilter(PDSignature.FILTER_VERISIGN_PPKVS);
            pdSignature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_SHA1);
            pdSignature.setName("Recargapay sign Test");
            pdSignature.setLocation("RP");
            pdSignature.setReason("Recargapay Sign Dev");
            pdSignature.setSignDate(Calendar.getInstance());
            pdDocument.addSignature(pdSignature);

            ExternalSigningSupport externalSigning =
                    pdDocument.saveIncrementalForExternalSigning(output);
            // invoke external signature service
            byte[] cmsSignature = sign(externalSigning.getContent());
            // set signature bytes received from the service
            externalSigning.setSignature(cmsSignature);

            pdDocument.save("/home/soave/newPdfSigned.pdf");
            pdDocument.close();
            System.out.println("PDF saved to the location !!!");

        } catch (IOException ioe) {
            System.out.println("Error while saving pdf" + ioe.getMessage());
        } catch (Exception e){
            System.out.println("Error while saving pdf" + e.getMessage());
        }

    }

}

