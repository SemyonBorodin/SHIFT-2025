package com.semyon.fileHandler;

import com.semyon.Config;
import com.semyon.exceptions.FileValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {
    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);

    public static void writeToFile(String result) throws FileValidationException {
        String outputFileName = Config.getOutputFileName();
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(outputFileName))){
            writer.write(result);
            logger.info("{} \nУспешно записано в файл: {}",
                    result, outputFileName);
        } catch (IOException e){logger.error("Ошибка при записи результата {} в файл: {}",
                result, e.getMessage());
            throw new FileValidationException("Ошибка при записи результата в файл" + e.getMessage());
        }
    }
}