package com.unsis.scunsis_backend.service.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import java.util.stream.IntStream;

@Service
public class ExcelParseService {

    public List<List<String>> parseToArrays(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename != null && filename.toLowerCase().endsWith(".ods")) {
            return parseOdsToArrays(file);
        }
        return parseXlsxToArrays(file);
    }

    public List<Map<String, String>> parseToFolioEntries(MultipartFile file) throws IOException {
        List<List<String>> data = parseToArrays(file);
        return extractFolioEntries(data);
    }

    public List<Map<String, String>> extractFolioEntries(List<List<String>> data) {
        if (data.isEmpty()) return List.of();

        boolean hasHeader = isHeaderRow(data.getFirst());
        int startIndex = hasHeader ? 1 : 0;

        return IntStream.range(startIndex, data.size())
                .mapToObj(i -> toFolioEntry(data.get(i)))
                .toList();
    }

    private Map<String, String> toFolioEntry(List<String> row) {
        Map<String, String> entry = new LinkedHashMap<>();
        entry.put("nombre", !row.isEmpty() ? row.get(0) : "");
        entry.put("primer_apellido", row.size() > 1 ? row.get(1) : "");
        entry.put("segundo_apellido", row.size() > 2 ? row.get(2) : "");
        entry.put("grado_academico", row.size() > 3 ? row.get(3) : "");
        entry.put("grado", row.size() > 4 ? row.get(4) : "");
        return entry;
    }

    private boolean isHeaderRow(List<String> row) {
        if (row.isEmpty()) return false;
        String first = row.getFirst().toLowerCase();
        return first.equals("nombre") || first.equals("nombres") || first.equals("name");
    }

    private List<List<String>> parseXlsxToArrays(MultipartFile file) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    rowData.add(getCellValueAsString(row.getCell(i)));
                }
                data.add(rowData);
            }
        }
        return data;
    }

    private List<List<String>> parseOdsToArrays(MultipartFile file) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (InputStream is = file.getInputStream()) {
            SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(is);
            try {
                Table sheet = doc.getSheetByIndex(0);
                for (org.odftoolkit.simple.table.Row row : sheet.getRowList()) {
                    List<String> rowData = new ArrayList<>();
                    int lastCell = row.getCellCount();
                    for (int i = 0; i < lastCell; i++) {
                        org.odftoolkit.simple.table.Cell cell = row.getCellByIndex(i);
                        rowData.add(cell != null ? cell.getStringValue() : "");
                    }
                    data.add(rowData);
                }
            } finally {
                doc.close();
            }
        } catch (Exception e) {
            throw new IOException("Error al leer archivo ODS: " + e.getMessage(), e);
        }
        return data;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
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
