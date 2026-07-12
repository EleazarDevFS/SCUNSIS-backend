package com.unsis.scunsis_backend.util;

import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class FolioGenerator {

    public String generateFolio(EParticipationRole role, long sequentialNumber) {
        int currentYear = Year.now().getValue();
        return String.format("%s-%04d-%d", role.getCode(), sequentialNumber, currentYear);
    }

    public String generateFolio(EParticipationRole role, long sequentialNumber, int year) {
        return String.format("%s-%04d-%d", role.getCode(), sequentialNumber, year);
    }
}
