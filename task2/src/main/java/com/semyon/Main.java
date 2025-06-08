package com.semyon;

import com.semyon.OutputHandler.OutputStrategy;
import com.semyon.exceptions.OutputStrategyValidationException;
import com.semyon.cli.CliParser;
import com.semyon.exceptions.FileValidationException;
import com.semyon.fileHandler.ShapeFileReader;
import com.semyon.exceptions.ShapeValidationException;
import com.semyon.fileHandler.ShapeReadResult;
import com.semyon.shapes.ShapeFactory;
import com.semyon.fileHandler.FileValidator;
import com.semyon.exceptions.TriangleInequalityException;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public enum OutputType {
        CONSOLE,
        FILE
    }

    public static void main(String[] args) {

        CliParser cliParser = new CliParser(args);
        try {
            String inputFileName = cliParser.getInputFileName();
            Config.setInputFileName(inputFileName);

            FileValidator.validateInputFile();

            String outputFileName = cliParser.getOutputFileName();
            Config.setOutputFileName(outputFileName);

            OutputType outputType = cliParser.hasOutputFile() ? OutputType.FILE : OutputType.CONSOLE;
            String outputTypeStr = outputType.name();

            ShapeReadResult shapeData = ShapeFileReader.readShapeData(Config.getInputFileName());
            String shapeTypeStr = shapeData.shapeType();
            List<String> paramsList = shapeData.params();

            ShapeFactory shapeFactory = new ShapeFactory();
            String result = shapeFactory.createShape(shapeTypeStr, paramsList).getCharacteristics();


            OutputStrategy outputStrategy = OutputStrategy.fromOutputType(outputTypeStr);
            outputStrategy.print(result);

            } catch (ShapeValidationException | TriangleInequalityException e){
                logger.error("Ошибка создания фигуры: {}", e.getMessage());
                System.exit(1);
            } catch (ParseException e) {
                logger.error("Ошибка парсинга аргументов коммандной строки : {}", e.getMessage());
                System.exit(1);
            } catch (FileValidationException e) {
                logger.error("Ошибка валидации! {}", e.getMessage());
            } catch (OutputStrategyValidationException | IOException e) {
                logger.error("{}", e.getMessage());
                System.exit(1);
            }
    }
}

