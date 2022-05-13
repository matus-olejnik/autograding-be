package com.devmo.autogradingbe.service;

import java.io.File;

public interface ExternalSvc {

    void backupFile(File file, Long assigmentIdentifier, String studentIdentifier);
}
