package com.semyon.shapes;

import com.semyon.exceptions.ShapeValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CircleTest {

    @Test
    void validCircleCreation() {
        assertDoesNotThrow(() -> new Circle(1.0));
    }

    @Test
    void shouldCalculatePerimeter() {
        var expectedPerimeter = 2 * Math.PI;

        Circle circle = new Circle(1.0);
        assertThat(circle.getPerimeter())
                .isCloseTo(expectedPerimeter, within(0.01));
    }

    @Test
    void shouldCalculateArea() {
        var expectedArea = Math.PI;

        Circle circle = new Circle(1.0);
        assertThat(circle.getArea())
                .isCloseTo(expectedArea, within(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "-100",
            "0",
    })
    void invalidCircleSidesShouldThrowException(double radius){
        assertThatThrownBy(() -> new Circle(radius))
                .isInstanceOf(ShapeValidationException.class);
    }

    @Test
    void circleCharacteristics(){
        Circle circle = new Circle(1.5);
        var expectedCharacteristics = "Тип фигуры: Круг\n" +
                "Площадь: 7.07 кв. мм\n" +
                "Периметр: 9.42 мм\n" +
                "Радиус: 1.50 мм\n" +
                "Диаметр: 3.00 мм";
        assertThat(circle.getCharacteristics()).matches(expectedCharacteristics);
    }

    @Test
    void invalidShapeTypeShouldThrowException() {
        assertThatThrownBy(() -> {
            ShapeType.fromString("INVALID_SHAPE_NAME").toString();})
                .isInstanceOf(ShapeValidationException.class);
    }
}
