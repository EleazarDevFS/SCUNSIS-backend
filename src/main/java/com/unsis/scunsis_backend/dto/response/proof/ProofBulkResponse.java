package com.unsis.scunsis_backend.dto.response.proof;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProofBulkResponse {
    private int totalRows;
    private int successCount;
    private int errorCount;
    private List<String> generatedFolios;
    private List<String> errors;
}
