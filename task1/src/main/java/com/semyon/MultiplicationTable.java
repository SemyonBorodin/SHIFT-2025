package com.semyon;

public class MultiplicationTable {
    private final int tableSize;
    private final int firstColumnWidth;
    private final int otherColumnWidth;
    private final String separator;
    private static final String CELL_FILLER = " ";
    private static final String CELL_SEPARATOR = "|";
    private static final String SEPARATOR_FILLER = "-";
    private static final String SEPARATOR_SEPARATOR = "+";
    private static final String ROW_SEPARATOR = "\n";

    public MultiplicationTable(int tableSize) {

        this.tableSize = tableSize;
        this.firstColumnWidth = calcFirstColumnWidth();
        this.otherColumnWidth = calcOtherColumnWidth();
        this.separator = generateSeparator();
    }

    public String generateTable() {

        StringBuilder table = new StringBuilder();
        table.append(generateHeader()).append(ROW_SEPARATOR);
        table.append(generateSeparator()).append(ROW_SEPARATOR);
        for (int i = 1; i <= tableSize; i++) {
            table.append(generateRow(i)).append(ROW_SEPARATOR);
            table.append(separator).append(ROW_SEPARATOR);
        }
        return table.toString();
    }

    private int calcFirstColumnWidth() {

        return String.valueOf(tableSize).length();
    }

    private int calcOtherColumnWidth() {

        return String.valueOf(tableSize * tableSize).length();
    }

    private String generateHeader() {

        StringBuilder header = new StringBuilder();
        header.append(CELL_FILLER.repeat(firstColumnWidth));
        for (int i = 1; i <= tableSize; i++) {
            int padding = otherColumnWidth - String.valueOf(i).length();
            header.append(CELL_SEPARATOR)
                    .append(CELL_FILLER.repeat(padding))
                    .append(i);
        }
        return header.toString();
    }

    private String generateSeparator() {

        StringBuilder sep = new StringBuilder();
        sep.append(SEPARATOR_FILLER.repeat(firstColumnWidth));
        for (int i = 0; i < tableSize; i++) {
            sep.append(SEPARATOR_SEPARATOR).append(SEPARATOR_FILLER.repeat(otherColumnWidth));
        }
        return sep.toString();
    }

    private String generateRow(int rowNumber) {

        StringBuilder row = new StringBuilder();
        row.append(CELL_FILLER.repeat(firstColumnWidth - String.valueOf(rowNumber).length()))
                .append(rowNumber);
        for (int i = 1; i <= tableSize; i++) {
            int padding = otherColumnWidth - String.valueOf(i * rowNumber).length();
            row.append(CELL_SEPARATOR)
                    .append(CELL_FILLER.repeat(padding))
                    .append(i * rowNumber);
        }
        return row.toString();
    }
}
