package com.recarga.digitalsign.service;


import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.recarga.digitalsign.model.Contract;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ConvertService {

    @Value("${pdfFolder}")
    private String outputPdf;

    public void convertHtmlToPdf(Contract contract) throws IOException {

        Document doc = Jsoup.parse(contract.getHtml());
        String outputPdfFilename = outputPdf+contract.getFileName();

        try (OutputStream os = new FileOutputStream(outputPdfFilename)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(outputPdfFilename);
            builder.toStream(os);

            builder.withW3cDocument(new W3CDom().fromJsoup(doc), "/");
            builder.run();
        }
    }
}
