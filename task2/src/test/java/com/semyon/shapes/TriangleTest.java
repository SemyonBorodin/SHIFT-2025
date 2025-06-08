package com.semyon.shapes;

import com.semyon.exceptions.ShapeValidationException;
import com.semyon.exceptions.TriangleInequalityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;



public class TriangleTest {

    @Test
    void validTriangleCreation(){
        Triangle triangle = new Triangle(1, 1, 1);
        assertThat(triangle.getPerimeter()).isEqualTo(3);
        assertThat(triangle.getArea()).isCloseTo(0.43, within(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "100, 1, 1",
            "1, 100, 1",
            "1, 1, 100"
    })
    void invalidTriangleSidesShouldThrowException(double side1, double side2, double side3){
        assertThatThrownBy(() -> new Triangle(side1, side2, side3))
                .isInstanceOf(TriangleInequalityException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 1, 1",
            "1, -1, 1",
            "1, 1, -1"
    })
    void triangleWithNonPositiveSideIsInvalid(double sideA, double sideB, double sideC){
        assertThatThrownBy(() -> new Triangle(sideA, sideB, sideC))
                .isInstanceOf(ShapeValidationException.class);
    }

    @Test
    void testGetAngleOppositeToSideA(){
        Triangle triangle = new Triangle(1, 1, 1);
        assertThat(triangle.getAngleOppositeToSideA())
                .isCloseTo(60, within(0.1));
    }

    @Test
    void testgetAngleOppositeToSideB(){
        Triangle triangle = new Triangle(1, 1, 1);
        assertThat(triangle.getAngleOppositeToSideB())
                .isCloseTo(60, within(0.1));
    }

    @Test
    void testGetAngleOppositeToSideC(){
        Triangle triangle = new Triangle(1, 1, 1);
        assertThat(triangle.getAngleOppositeToSideC())
                .isCloseTo(60, within(0.1));
    }

    @Test
    void triangleCharacteristics(){
        Triangle triangle = new Triangle(3.0, 4.0, 5.0);
        var expectedCharacteristics = "Тип фигуры: Треугольник\n" +
                "Площадь: 6.00 кв. мм\n" +
                "Периметр: 12.00 мм\n" +
                "Длина стороны sideA: 3.00 мм, противолежащий ей угол: 36.87 градусов\n" +
                "Длина стороны sideB: 4.00 мм, противолежащий ей угол: 53.13 градусов\n" +
                "Длина стороны sideC: 5.00 мм, противолежащий ей угол: 90.00 градусов";
        assertThat(triangle.getCharacteristics()).matches(expectedCharacteristics);
    }
}
