package com.semyon.OutputHandler;

import com.semyon.Config;
import com.semyon.exceptions.FileValidationException;
import com.semyon.fileHandler.FileValidator;
import com.semyon.fileHandler.FileWriter;

public class FileOutput implements OutputStrategy{

    public FileOutput() {
    }

    @Override
    public void print(String result) {
        String outputFileName = Config.getOutputFileName();
        try {
            FileValidator.validateOutputFile();
            FileWriter.writeToFile(result);
            logger.info("Результат записан в файл: {}", outputFileName);
            } catch (FileValidationException e) {
                logger.error("Ошибка записи в файл: {}", e.getMessage());
            }
    }
}