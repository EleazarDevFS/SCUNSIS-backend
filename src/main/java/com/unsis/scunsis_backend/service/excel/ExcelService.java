package com.unsis.scunsis_backend.service.excel;

import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final IReceiverRepository receiverRepository;

    public record ParticipantRow(Receiver receiver, EParticipationRole role, String error) {}

    public List<ParticipantRow> parseParticipants(MultipartFile file, EParticipationRole defaultRole) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename != null && filename.toLowerCase().endsWith(".ods")) {
            return parseParticipantsOds(file, defaultRole);
        }
        return parseParticipantsXlsx(file, defaultRole);
    }

    private List<ParticipantRow> parseParticipantsXlsx(MultipartFile file, EParticipationRole defaultRole) throws IOException {
        List<ParticipantRow> result = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (!rowIterator.hasNext()) return result;
            Map<String, Integer> colMap = buildColumnMap(rowIterator.next(), true);
            boolean hasRoleColumn = colMap.containsKey("ROL");
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                processRow(row, colMap, hasRoleColumn, defaultRole, result, true);
            }
        }
        return result;
    }

    private List<ParticipantRow> parseParticipantsOds(MultipartFile file, EParticipationRole defaultRole) throws IOException {
        List<ParticipantRow> result = new ArrayList<>();
        try (InputStream is = file.getInputStream()) {
            SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(is);
            try {
                Table sheet = doc.getSheetByIndex(0);
                List<org.odftoolkit.simple.table.Row> rows = sheet.getRowList();
                if (rows.isEmpty()) return result;
                Map<String, Integer> colMap = buildColumnMap(rows.get(0));
                boolean hasRoleColumn = colMap.containsKey("ROL");
                for (int i = 1; i < rows.size(); i++) {
                    org.odftoolkit.simple.table.Row row = rows.get(i);
                    processRow(row, colMap, hasRoleColumn, defaultRole, result, false);
                }
            } finally {
                doc.close();
            }
        } catch (Exception e) {
            throw new IOException("Error al leer archivo ODS: " + e.getMessage(), e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <T> void processRow(T row, Map<String, Integer> colMap,
                                 boolean hasRoleColumn, EParticipationRole defaultRole,
                                 List<ParticipantRow> result, boolean isXlsx) {
        Map<String, String> fields = new HashMap<>();
        for (Map.Entry<String, Integer> entry : colMap.entrySet()) {
            String val;
            int idx = entry.getValue();
            if (isXlsx) {
                Cell cell = ((Row) row).getCell(idx);
                val = cell != null ? getCellValueAsString(cell) : "";
            } else {
                org.odftoolkit.simple.table.Cell cell =
                        ((org.odftoolkit.simple.table.Row) row).getCellByIndex(idx);
                val = cell != null ? cell.getStringValue() : "";
            }
            fields.put(entry.getKey(), val != null ? val.trim() : "");
        }

        String name = fields.getOrDefault("NOMBRE", "");
        String lastName = fields.getOrDefault("PRIMERAPELLIDO", "");
        if (name.isBlank() || lastName.isBlank()) return;

        Receiver receiver = Receiver.builder()
                .name(name)
                .lastName(lastName)
                .twoLastName(blankToNull(fields.get("SEGUNDOAPELLIDO")))
                .email(blankToNull(fields.get("EMAIL")))
                .phone(blankToNull(fields.get("TELEFONO")))
                .academicGrade(blankToNull(fields.get("GRADOACADEMICO")))
                .build();

        EParticipationRole role = resolveRole(fields, hasRoleColumn, defaultRole);
        if (role == null) {
            result.add(new ParticipantRow(receiver, null,
                    "Sin rol especificado. Incluye columna ROL en el Excel "
                    + "o pasa el parametro 'role' en la URL."));
            return;
        }

        findOrCreateReceiver(receiver);
        result.add(new ParticipantRow(receiver, role, null));
    }

    private Map<String, Integer> buildColumnMap(Row headerRow, boolean isXlsx) {
        Map<String, Integer> colMap = new HashMap<>();
        if (isXlsx) {
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    String header = getCellValueAsString(cell);
                    if (header != null && !header.isBlank()) {
                        colMap.put(header.trim().toUpperCase(), i);
                    }
                }
            }
        }
        return colMap;
    }

    private Map<String, Integer> buildColumnMap(org.odftoolkit.simple.table.Row headerRow) {
        Map<String, Integer> colMap = new HashMap<>();
        int cellCount = headerRow.getCellCount();
        for (int i = 0; i < cellCount; i++) {
            org.odftoolkit.simple.table.Cell cell = headerRow.getCellByIndex(i);
            if (cell != null) {
                String val = cell.getStringValue();
                if (val != null && !val.isBlank()) {
                    colMap.put(val.trim().toUpperCase(), i);
                }
            }
        }
        return colMap;
    }

    private EParticipationRole resolveRole(Map<String, String> fields,
                                            boolean hasRoleColumn, EParticipationRole defaultRole) {
        if (hasRoleColumn) {
            String roleStr = fields.getOrDefault("ROL", "");
            if (!roleStr.isBlank()) {
                try {
                    return EParticipationRole.valueOf(roleStr.trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    return null;
                }
            }
        }
        return defaultRole;
    }

    private void findOrCreateReceiver(Receiver receiver) {
        if (receiver.getEmail() != null && !receiver.getEmail().isBlank()) {
            Optional<Receiver> existing = receiverRepository.findByEmail(receiver.getEmail());
            if (existing.isPresent()) {
                Receiver existingReceiver = existing.get();
                receiver.setReceiverId(existingReceiver.getReceiverId());
                receiver.setName(existingReceiver.getName());
                receiver.setLastName(existingReceiver.getLastName());
                receiver.setTwoLastName(existingReceiver.getTwoLastName());
                receiver.setPhone(existingReceiver.getPhone());
                receiver.setAcademicGrade(existingReceiver.getAcademicGrade());
                return;
            }
        }
        Receiver saved = receiverRepository.save(receiver);
        receiver.setReceiverId(saved.getReceiverId());
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                double val = cell.getNumericCellValue();
                if (val == Math.floor(val) && !Double.isInfinite(val)) {
                    yield String.valueOf((long) val);
                }
                yield String.valueOf(val);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> {
                try {
                    yield String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    try {
                        yield cell.getStringCellValue();
                    } catch (Exception e2) {
                        yield "";
                    }
                }
            }
            default -> "";
        };
    }

    private String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
