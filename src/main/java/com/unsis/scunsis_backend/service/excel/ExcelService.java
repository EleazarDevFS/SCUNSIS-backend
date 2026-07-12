package com.unsis.scunsis_backend.service.excel;

import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
        List<ParticipantRow> result = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                return result;
            }

            Row headerRow = rowIterator.next();
            Map<String, Integer> colMap = buildColumnMap(headerRow);
            boolean hasRoleColumn = colMap.containsKey("ROL");

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Receiver receiver = parseRow(row, colMap);

                if (receiver == null) {
                    continue;
                }

                EParticipationRole role = resolveRole(row, colMap, hasRoleColumn, defaultRole);

                if (role == null) {
                    result.add(new ParticipantRow(receiver, null,
                            "Sin rol especificado. Incluye columna ROL en el Excel "
                            + "o pasa el parametro 'role' en la URL."));
                    continue;
                }

                findOrCreateReceiver(receiver);
                result.add(new ParticipantRow(receiver, role, null));
            }
        }

        return result;
    }

    private Map<String, Integer> buildColumnMap(Row headerRow) {
        Map<String, Integer> colMap = new HashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                String header = getCellValueAsString(cell);
                if (header != null && !header.isBlank()) {
                    colMap.put(header.trim().toUpperCase(), i);
                }
            }
        }
        return colMap;
    }

    private Receiver parseRow(Row row, Map<String, Integer> colMap) {
        String name = getCellValue(colMap, row, "NOMBRE");
        String lastName = getCellValue(colMap, row, "PRIMERAPELLIDO");
        String twoLastName = getCellValue(colMap, row, "SEGUNDOAPELLIDO");
        String email = getCellValue(colMap, row, "EMAIL");
        String phone = getCellValue(colMap, row, "TELEFONO");
        String academicGrade = getCellValue(colMap, row, "GRADOACADEMICO");

        if (name == null || name.isBlank() || lastName == null || lastName.isBlank()) {
            return null;
        }

        return Receiver.builder()
                .name(name.trim())
                .lastName(lastName.trim())
                .twoLastName(twoLastName != null ? twoLastName.trim() : null)
                .email(email != null ? email.trim() : null)
                .phone(phone != null ? phone.trim() : null)
                .academicGrade(academicGrade != null ? academicGrade.trim() : null)
                .build();
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

    private EParticipationRole resolveRole(Row row, Map<String, Integer> colMap,
                                            boolean hasRoleColumn, EParticipationRole defaultRole) {
        if (hasRoleColumn) {
            String roleStr = getCellValue(colMap, row, "ROL");
            if (roleStr != null && !roleStr.isBlank()) {
                try {
                    return EParticipationRole.valueOf(roleStr.trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    return null;
                }
            }
        }
        return defaultRole;
    }

    private String getCellValue(Map<String, Integer> colMap, Row row, String columnName) {
        Integer colIndex = colMap.get(columnName.toUpperCase());
        if (colIndex == null) {
            return null;
        }
        Cell cell = row.getCell(colIndex);
        return getCellValueAsString(cell);
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
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
}
