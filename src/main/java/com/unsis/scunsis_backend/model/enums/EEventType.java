package com.unsis.scunsis_backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EEventType {
    FISICO("Físico"),
    VIRTUAL("Virtual");
    private String value;
}
