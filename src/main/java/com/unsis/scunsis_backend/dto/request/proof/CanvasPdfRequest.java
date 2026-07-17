package com.unsis.scunsis_backend.dto.request.proof;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanvasPdfRequest {
    private String canvasImage;
    private List<List<String>> data;
}
