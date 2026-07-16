package com.unsis.scunsis_backend.controller.file;

import com.unsis.scunsis_backend.service.excel.ExcelParseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
}
