    package com.semyon.fileHandler;

    import com.semyon.Config;
    import com.semyon.exceptions.FileValidationException;

    import java.io.File;

    public class FileValidator {

        public static void validateInputFile() throws FileValidationException {
            String inputFileName = Config.getInputFileName();
            File inputfile = new File(inputFileName);
            if (!inputfile.exists()) {
                throw new FileValidationException("Файл для чтения " + inputFileName + " не существует");
            }
            if (inputfile.exists() && !inputfile.canRead()) {
                throw new FileValidationException("Файл для чтения " + inputFileName + " существует, но недоступен для чтения");
            }
        }

        public static void validateOutputFile() throws FileValidationException {
            String outputFileName = Config.getOutputFileName();
            File outputFile = new File(outputFileName);
            File parentDir = outputFile.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                throw new FileValidationException("Родительская директория для выходного файла "  + outputFileName + " не существует");
            }
            if (!outputFile.canWrite()) {
                throw new FileValidationException("Файл выходной " + outputFileName + "недоступен для записи");
            }
        }
    }