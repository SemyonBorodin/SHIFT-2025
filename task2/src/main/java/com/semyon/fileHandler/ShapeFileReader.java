package com.semyon.fileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ShapeFileReader {

    public static ShapeReadResult readShapeData(String inputFileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String typeLine = reader.readLine();
            String paramsLine = reader.readLine();

            return new ShapeReadResult(typeLine.strip(), List.of(paramsLine.strip().split(" ")));
        } catch (IOException e) {
            throw new IOException("Ошибка чтения из входного файла " + inputFileName);
        }
    }
}