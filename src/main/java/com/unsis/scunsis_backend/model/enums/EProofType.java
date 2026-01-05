package com.unsis.scunsis_backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EProofType {
    JORNADA("JDA", "jornada"),
    CONGRESO("CGO", "congreso"),
    PONENCIA("PCA", "ponencia"),
    RECONOCIMIENTO("RMTO", "reconocimiento"),
    OTRO("OTHER", "reconocimiento");
    // Para completar el folio hay que contar cuantas constancias hay de cada tipo
    // Y se concatena el número SERIAL con el AÑO ACTUAL.
    // Se tiene que hacer una consulta a la BD para contar constancias por tipo
    // y sacar el año actual.
    private final String description;
    private final String lowerType;
}
