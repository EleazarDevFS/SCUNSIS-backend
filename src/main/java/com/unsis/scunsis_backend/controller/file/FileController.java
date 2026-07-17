package com.unsis.scunsis_backend.controller.file;

import com.unsis.scunsis_backend.service.excel.ExcelParseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final ExcelParseService excelParseService;

    @PostMapping("/file")
    public ResponseEntity<Map<String, Object>> parseExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<List<String>> data = excelParseService.parseToArrays(file);
            return ResponseEntity.ok(Map.of("data", data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al procesar el archivo: " + e.getMessage()));
        }
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<Map<String, Object>> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<List<String>> data = excelParseService.parseToArrays(file);
            List<Map<String, String>> folios = new ArrayList<>();

            boolean hasHeader = !data.isEmpty();
            List<String> headers = hasHeader ? data.getFirst() : List.of();
            boolean skipFirst = hasHeader && isHeaderRow(headers);

            for (int i = skipFirst ? 1 : 0; i < data.size(); i++) {
                List<String> row = data.get(i);
                Map<String, String> folioEntry = new LinkedHashMap<>();
                folioEntry.put("nombre", row.size() > 0 ? row.get(0) : "");
                folioEntry.put("primer_apellido", row.size() > 1 ? row.get(1) : "");
                folioEntry.put("segundo_apellido", row.size() > 2 ? row.get(2) : "");
                folioEntry.put("grado_academico", row.size() > 3 ? row.get(3) : "");
                folioEntry.put("grado", row.size() > 4 ? row.get(4) : "");
                folios.add(folioEntry);
            }

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("folios", folios);
            response.put("data", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al procesar el archivo: " + e.getMessage()));
        }
    }

    private boolean isHeaderRow(List<String> row) {
        if (row.isEmpty()) return false;
        String first = row.get(0).toLowerCase();
        return first.equals("nombre") || first.equals("nombres") || first.equals("name");
    }
}
