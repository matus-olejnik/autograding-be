package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.repository.UpbExternalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.math.BigDecimal;

@Service
public class UpbExternalService implements ExternalSvc {

    private static final Logger logger = LoggerFactory.getLogger(UpbExternalService.class);

    private final UploadSvc uploadSvc;

    private final UpbExternalRepository upbExternalRepository;

    @Autowired
    public UpbExternalService(UploadSvc uploadSvc, UpbExternalRepository upbExternalRepository) {
        this.uploadSvc = uploadSvc;
        this.upbExternalRepository = upbExternalRepository;
    }

    @Override
    public void backupFile(File file, Long assigmentIdentifier, String studentIdentifier) {
        File zippedFile = new File(
                studentIdentifier.replaceAll("@|\\.", "") +
                        "-" +
                        assigmentIdentifier +
                        "-zippedFile.zip"
        );
        ZipUtil.pack(file, zippedFile);

        String solutionUrl = uploadSvc.uploadSolution(zippedFile);
        logger.info("Amazon solution url=" + solutionUrl);

        upbExternalRepository.saveSolution(solutionUrl, assigmentIdentifier, studentIdentifier);

        zippedFile.delete();
    }

    @Override
    public void updateEvaluation(BigDecimal numberOfPoints, String message, Long assigmentIdentifier, String studentIdentifier) {
        upbExternalRepository.updateEvaluation(numberOfPoints, message, assigmentIdentifier, studentIdentifier);
    }
}
