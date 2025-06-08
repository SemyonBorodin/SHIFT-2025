package com.semyon.cli;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliParser {
    private static final Logger logger = LoggerFactory.getLogger(CliParser.class);

    private CommandLine cmd;

    public CliParser(String[] args) {
        Options options = new Options();
        options.addOption("o", "output", true, "Output file (optional)");
        options.addOption("f", "fileLogger", false, "Logs in file");

        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Ошибка парсинга параметров командной строки, введите: input.txt [-o output.txt] [-f for logging in file]");
        }
    }

    public String getOutputFileName() {
        return cmd.getOptionValue("o");
    }

    public String getInputFileName() throws ParseException {
        var args = cmd.getArgList();
        if (args.isEmpty()) {
            throw new ParseException("Не задан входной файл!");
        }
        return cmd.getArgList().get(0);
    }

    public boolean hasOutputFile() {
        return cmd.hasOption("o");
    }

    public boolean hasFileLoggerOption() {
        return cmd.hasOption("f");
    }

}
