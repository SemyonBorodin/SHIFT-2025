package com.semyon;

public class Config {
    private static String outputFileName = null;
    private static String inputFileName = null;

    public static String getOutputFileName() {
        return outputFileName;
    }

    public static void setOutputFileName(String outputFile) {
        outputFileName = (outputFile == null || outputFile.isBlank()) ? null : outputFile;
    }

    public static String getInputFileName() {
        return inputFileName;
    }

    public static void setInputFileName(String inputFile) {
        inputFileName = (inputFile == null || inputFile.isBlank()) ? null : inputFile;
    }
}