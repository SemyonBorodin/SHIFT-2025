package com.semyon.cli;

import org.apache.commons.cli.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class CliParserTest {
    @Test
    void shouldParseValidInputFile() throws ParseException {
        String[] args = {"input.txt"};

        CliParser cliParser = new CliParser(args);

        assertThat(cliParser.getInputFileName()).isEqualTo("input.txt");
    }

    @Test
    void shouldParseValidOutput() {
        String[] args = {"-o", "output.txt"};

        CliParser cliParser = new CliParser(args);

        assertThat(cliParser.hasOutputFile()).isTrue();
        assertThat(cliParser.getOutputFileName()).isEqualTo("output.txt");
    }

    @Test
    void shouldThrowExceptionIfInputFileIsNotSpecified() throws ParseException {
        String[] args = {"-o", "output.txt"};

        CliParser cliParser = new CliParser(args);

        assertThatThrownBy(() -> cliParser.getInputFileName())
                .isInstanceOf(ParseException.class)
                .hasMessageContaining("Не задан входной файл");
    }

    @Test
    void hasFileLoggerOptionTest() {
        String[] args = {"input.txt", "-f"};

        CliParser cliParser = new CliParser(args);

        assertThat(cliParser.hasFileLoggerOption()).isTrue();
    }
}
