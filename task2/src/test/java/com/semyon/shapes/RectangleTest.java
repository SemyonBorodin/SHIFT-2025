package com.semyon.shapes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.semyon.exceptions.ShapeValidationException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RectangleTest {

    @Test
    void validRectangleCreation() {
        assertDoesNotThrow(() -> new Rectangle(10.0, 5.0));
    }

    @ParameterizedTest
    @CsvSource({
            "-100, 1",
            "1, -0.0001"
    })
    void rectangleWithNonPositiveSideIsInvalid(double sideA, double sideB){
        assertThatThrownBy(() -> new Rectangle(sideA, sideB))
                .isInstanceOf(ShapeValidationException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 12",
            "5, 5, 25"
    })
    void testgetAreaCorrectly(double sideA, double sideB, double expectedArea) {
        Rectangle rectangle = new Rectangle(sideA, sideB);
        assertThat(rectangle.getArea()).isCloseTo(expectedArea, within(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 14",
            "5, 5, 20",
            "25, 4, 58"
    })
    void testPerimeterAreaCorrectly(double sideA, double sideB, double expectedPerimeter){
        Rectangle rectangle = new Rectangle(sideA, sideB);
        assertThat(rectangle.getPerimeter()).isCloseTo(expectedPerimeter, within(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5",
            "1, 1, 1.41"
    })
    void shouldGetDiagonalCorrectly(double sideA, double sideB, double expectedDiagonal) {
        Rectangle rectangle = new Rectangle(sideA, sideB);
        assertThat(rectangle.getDiagonal()).isCloseTo(expectedDiagonal, within(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 3",
            "4, 3, 3"
    })
    void shouldIdentifyShorterSide(double sideA, double sideB, double expectedValue) {
        Rectangle rectangle = new Rectangle(sideA, sideB);
        assertThat(rectangle.getShorterSide()).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 4",
            "4, 3, 4"
    })
    void shouldIdentifyLongerSide(double sideA, double sideB, double expectedValue) {
        Rectangle rectangle = new Rectangle(sideA, sideB);
        assertThat(rectangle.getLongerSide()).isEqualTo(expectedValue);
    }

    @Test
    void rectangleCharacteristics(){
        Rectangle rectangle = new Rectangle(3.0, 4.0);
        var expectedCharacteristics = "Тип фигуры: Прямоугольник\n" +
                "Площадь: 12.00 кв. мм\n" +
                "Периметр: 14.00 мм\n" +
                "Длина диагонали: 5.00 мм\n" +
                "Длина: 4.00 мм\n" +
                "Ширина: 3.00 мм";
        assertThat(rectangle.getCharacteristics()).matches(expectedCharacteristics);

    }
}
