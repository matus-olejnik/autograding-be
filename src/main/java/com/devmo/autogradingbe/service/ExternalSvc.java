package com.devmo.autogradingbe.service;

import java.io.File;
import java.math.BigDecimal;

public interface ExternalSvc {

    void backupFile(File file, Long assigmentIdentifier, String studentIdentifier);

    void updateEvaluation(BigDecimal numberOfPoints, String message, Long assigmentIdentifier, String studentIdentifier);
}
