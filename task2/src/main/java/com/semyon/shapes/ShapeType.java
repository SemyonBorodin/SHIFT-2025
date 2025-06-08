package com.semyon.shapes;

import com.semyon.exceptions.ShapeValidationException;

public enum ShapeType {
    CIRCLE(1, "Круг"),
    RECTANGLE(2, "Прямоугольник"),
    TRIANGLE(3, "Треугольник");

    private final int doubleParamsExpectedCount;
    private final String description;

    ShapeType(int doubleParamsCount, String description) {
        this.doubleParamsExpectedCount = doubleParamsCount;
        this.description = description;
    }

    public int getDoubleParamsExpectedCount() {
        return doubleParamsExpectedCount;
    }

    public String getDescription(){
        return description;
    }

    public static ShapeType fromString(String str) {
        try {
            return ShapeType.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ShapeValidationException("Передан неизвестный тип фигуры: " + str);
        }
    }
}
