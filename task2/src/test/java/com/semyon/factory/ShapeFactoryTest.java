package com.semyon.factory;
import com.semyon.exceptions.ShapeValidationException;
import com.semyon.shapes.Shape;
import com.semyon.shapes.ShapeFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShapeFactoryTest {

    @ParameterizedTest
    @CsvSource({
            "' ', 1", //Circle
            "'1', 2", //Rectangle
            "'1 1', 3"  //Triangle
    })
    void invalidCountParamsForShapeShouldThrowException(String paramsString, int expectedCount){
        List<String> params = paramsString.isEmpty() ? List.of() : Arrays.asList(paramsString.split("\\s+"));
        assertThatThrownBy(() -> ShapeFactory.parseDoubleParams(params, expectedCount))
                .isInstanceOf(ShapeValidationException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "'CIRCLE', '1'",
            "'RECTANGLE', '1 1'",
            "'TRIANGLE', '1 1 1'"
    })
    void createShapeSuccessTest(String shapeName, String paramsString) {
        List<String> params = Arrays.asList(paramsString.split("\\s+"));

        assertDoesNotThrow(() -> {
            new ShapeFactory().createShape(shapeName, params);
        });
    }

    @Test
    void invalidShapeNameShouldThrowException() throws ShapeValidationException {
        var shapeName = "INVALID_SHAPE_NAME";
        List<String> params =  new ArrayList<>(Arrays.asList("1", "1", "1"));
        ShapeFactory shapeFactory = new ShapeFactory();

        assertThatThrownBy(() -> shapeFactory.createShape(shapeName, params))
                .isInstanceOf(ShapeValidationException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "'circle', '1'",
            "'rectangle', '1 1'",
            "'triangle', '1 1 1 a b c'"
    })
    void shouldConvertLowercaseShapeNameToUppercase(String lowercaseName, String paramsString) {
        List<String> params = Arrays.asList(paramsString.split("\\s+"));
        String uppercaseName = lowercaseName.toUpperCase();

        Shape lowerCaseShape = new ShapeFactory().createShape(lowercaseName, params);
        Shape upperCaseShape = new ShapeFactory().createShape(uppercaseName, params);

        assertThat(lowerCaseShape)
                .usingRecursiveComparison()
                .isEqualTo(upperCaseShape);
    }
}

