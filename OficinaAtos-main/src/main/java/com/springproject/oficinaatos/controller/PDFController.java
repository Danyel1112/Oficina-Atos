package com.springproject.oficinaatos.controller;

import com.itextpdf.text.pdf.PdfPCell;
import com.springproject.oficinaatos.model.Cliente;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
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

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            addTitle(document, "Informações do Cliente", titleFont);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            addRowToTable(table, "Nome:", cliente.getNome(), contentFont);
            addRowToTable(table, "Telefone:", cliente.getTelefone(), contentFont);
            addRowToTable(table, "Email:", cliente.getEmail(), contentFont);
            addRowToTable(table, "Descrição:", cliente.getDescricao(), contentFont);

            document.add(table);
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

    private static void addTitle(Document document, String title, Font font) throws DocumentException {
        Paragraph paragraph = new Paragraph(title, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(20);
        document.add(paragraph);
    }

    private static void addRowToTable(PdfPTable table, String label, String value, Font font) {
        table.addCell(createCell(label, font));
        table.addCell(createCell(value, font));
    }

    private static PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, font));
        cell.setPadding(5);
        return cell;
    }
}
