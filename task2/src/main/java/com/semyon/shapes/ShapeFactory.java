package com.semyon.shapes;
import com.semyon.exceptions.ShapeValidationException;
import com.semyon.exceptions.TriangleInequalityException;

import java.util.ArrayList;
import java.util.List;

public class ShapeFactory {

    public Shape createShape(String typeString, List<String> params) throws ShapeValidationException, TriangleInequalityException {

            ShapeType shapeType = ShapeType.fromString(typeString.stripLeading());
            List<Double> doubleParams = parseDoubleParams(params, shapeType.getDoubleParamsExpectedCount());

            return switch (shapeType) {
                case CIRCLE -> new Circle(doubleParams.get(0));
                case RECTANGLE -> new Rectangle(doubleParams.get(0), doubleParams.get(1));
                case TRIANGLE -> new Triangle(doubleParams.get(0), doubleParams.get(1), doubleParams.get(2));
            };
    }

    private static boolean isDouble(String param) {
        try {
            Double.parseDouble(param);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<Double> parseDoubleParams(List<String> params, int expectedCount) {
        List<Double> doubleParameters = new ArrayList<>();

        for (String param : params) {
            if (isDouble(param)) {
                doubleParameters.add(Double.parseDouble(param));
            }
        }
        var doubleParamsPassedCount = doubleParameters.size();

        if(doubleParamsPassedCount < expectedCount){
            throw new ShapeValidationException("Недостаточно параметров" +
                    "\nОжидалось параметров: " + expectedCount +
                    "\nБыло передано: " + doubleParamsPassedCount );
        }
     return doubleParameters;
    }
}