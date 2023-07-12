package com.springproject.oficinaatos.controller;

import com.springproject.oficinaatos.model.Cliente;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;

public class PDFController {

    public static ResponseEntity<byte[]> generateClientePDF(Cliente cliente) {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("Informações do Cliente"));
            document.add(new Paragraph("Nome: " + cliente.getNome()));
            document.add(new Paragraph("Telefone: " + cliente.getTelefone()));
            document.add(new Paragraph("Email: " + cliente.getEmail()));
            document.add(new Paragraph("Descrição: " + cliente.getDescricao()));

            document.close();

            byte[] contents = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "cliente.pdf");

            return ResponseEntity.ok().headers(headers).body(contents);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }
}
