package com.semyon.OutputHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleOutput implements OutputStrategy{
    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    @Override
    public void print(String result) {
        logger.info(result);
    }
}