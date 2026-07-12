package com.unsis.scunsis_backend.service.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unsis.scunsis_backend.model.proof.Proof;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;

@Service
public class PdfGenerationService {

    public byte[] generateCertificate(Proof proof) {
        Document document = new Document(PageSize.A4.rotate(), 40, 40, 40, 40);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setPageEvent(new BorderHelper());
            document.open();

            PdfPTable content = new PdfPTable(1);
            content.setWidthPercentage(90);
            content.setHorizontalAlignment(Element.ALIGN_CENTER);
            content.setSpacingBefore(50);

            Font titleFont = new Font(Font.HELVETICA, 28, Font.BOLD, new Color(0, 51, 102));
            Font subtitleFont = new Font(Font.HELVETICA, 14, Font.NORMAL, new Color(80, 80, 80));
            Font nameFont = new Font(Font.HELVETICA, 24, Font.BOLD, new Color(0, 0, 0));
            Font bodyFont = new Font(Font.HELVETICA, 14, Font.NORMAL, new Color(50, 50, 50));
            Font smallFont = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(120, 120, 120));

            String senderName = proof.getSender() != null ? proof.getSender().getName() : "";
            String fullName = proof.getReceiver().getName() + " " + proof.getReceiver().getLastName()
                    + (proof.getReceiver().getTwoLastName() != null ? " " + proof.getReceiver().getTwoLastName() : "");
            String roleDisplay = proof.getRole() != null ? proof.getRole().getDisplayName() : "";
            String activityName = proof.getActivity() != null ? proof.getActivity().getActivityName() : "";
            String eventName = proof.getEvent() != null ? proof.getEvent().getEventName() : "";

            addCenteredCell(content, new Paragraph(senderName, subtitleFont));
            addCenteredCell(content, new Paragraph("CONSTANCIA", titleFont));

            Paragraph otorga = new Paragraph("Otorga la presente constancia a:", bodyFont);
            otorga.setSpacingBefore(30);
            otorga.setSpacingAfter(5);
            addCenteredCell(content, otorga);

            Paragraph namePara = new Paragraph(fullName, nameFont);
            namePara.setSpacingBefore(20);
            namePara.setSpacingAfter(10);
            addCenteredCell(content, namePara);

            Paragraph rolePara = new Paragraph("Por su participacion como " + roleDisplay, bodyFont);
            rolePara.setSpacingBefore(5);
            addCenteredCell(content, rolePara);

            if (!activityName.isBlank()) {
                Paragraph activityPara = new Paragraph("en la actividad: " + activityName, bodyFont);
                addCenteredCell(content, activityPara);
            }

            if (!eventName.isBlank()) {
                Paragraph eventPara = new Paragraph("del evento: " + eventName, bodyFont);
                eventPara.setSpacingBefore(5);
                addCenteredCell(content, eventPara);
            }

            Paragraph datePara = new Paragraph(proof.getDate().toString(), bodyFont);
            datePara.setSpacingBefore(30);
            addCenteredCell(content, datePara);

            Paragraph folioPara = new Paragraph("Folio: " + proof.getFolio(), smallFont);
            folioPara.setSpacingBefore(40);
            addCenteredCell(content, folioPara);

            document.add(content);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar PDF", e);
        }

        return baos.toByteArray();
    }

    private void addCenteredCell(PdfPTable table, Paragraph paragraph) {
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private static class BorderHelper extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            com.lowagie.text.Rectangle rect = document.getPageSize();
            rect.setBorder(com.lowagie.text.Rectangle.BOX);
            rect.setBorderWidth(3);
            rect.setBorderColor(new Color(0, 51, 102));
            rect.setTop(rect.getTop() - 10);
            rect.setBottom(rect.getBottom() + 10);
            rect.setLeft(rect.getLeft() + 10);
            rect.setRight(rect.getRight() - 10);
            cb.rectangle(rect);
        }
    }
}
