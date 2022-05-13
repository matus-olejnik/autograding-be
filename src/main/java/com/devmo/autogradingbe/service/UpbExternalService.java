package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.repository.UpbExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

@Service
public class UpbExternalService implements ExternalSvc {

    private final UploadSvc uploadSvc;

    private final UpbExternalRepository upbExternalRepository;

    @Autowired
    public UpbExternalService(UploadSvc uploadSvc, UpbExternalRepository upbExternalRepository) {
        this.uploadSvc = uploadSvc;
        this.upbExternalRepository = upbExternalRepository;
    }

    @Override
    public void backupFile(File file, Long assigmentIdentifier, String studentIdentifier) {
        File zippedFile = new File(studentIdentifier + "-zippedFile.zip");
        ZipUtil.pack(file, zippedFile);

        String solutionUrl = uploadSvc.uploadSolution(zippedFile);
        upbExternalRepository.uploadSolution(solutionUrl, assigmentIdentifier, studentIdentifier);
    }
}
