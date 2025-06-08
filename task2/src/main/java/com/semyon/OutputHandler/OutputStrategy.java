package com.semyon.OutputHandler;
import com.semyon.exceptions.OutputStrategyValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface OutputStrategy {
    Logger logger = LoggerFactory.getLogger(OutputStrategy.class);

    void print(String result);

    static OutputStrategy fromOutputType(String outputType){
        return switch (outputType.toUpperCase()) {
            case "CONSOLE" -> new ConsoleOutput();
            case "FILE" -> new FileOutput();
            default -> throw new OutputStrategyValidationException("Ошибка валидации. Неизвестный тип вывода: " + outputType);
        };
    }
}