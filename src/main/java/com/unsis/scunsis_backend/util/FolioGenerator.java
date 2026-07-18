package com.unsis.scunsis_backend.util;

import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.time.ZoneId;

@Component
public class FolioGenerator {

    public String generateFolio(EParticipationRole role, long sequentialNumber) {
        int currentYear = Year.now(ZoneId.systemDefault()).getValue();
        return String.format("%s-%d-%04d", role.getCode(), currentYear, sequentialNumber);
    }

    public String generateFolio(EParticipationRole role, long sequentialNumber, int year) {
        return String.format("%s-%d-%04d", role.getCode(), year, sequentialNumber);
    }
}
