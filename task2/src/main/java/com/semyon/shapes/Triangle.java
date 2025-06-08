package com.semyon.shapes;

import com.semyon.exceptions.ShapeValidationException;
import com.semyon.exceptions.TriangleInequalityException;

public class Triangle extends Shape{

    private static final ShapeType TYPE = ShapeType.TRIANGLE;

    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;

        validateTriangle(sideA, sideB, sideC);
    }

    // Стороны могут образовывать треугольник если и только если выполнено неравенство треугольника
    public static boolean isTriangleInequalitySatisfied(double sideA, double sideB, double sideC) {
        return (sideA + sideB > sideC) &&
                (sideA + sideC > sideB) &&
                (sideB + sideC > sideA);
    }
    public static void validateTriangle(double sideA, double sideB, double sideC) {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new ShapeValidationException(ErrorMessages.INVALID_TRIANGLE_PARAMS + "sideA = " + sideA);
        }
        if (!isTriangleInequalitySatisfied(sideA, sideB, sideC)) {
            throw new TriangleInequalityException(ErrorMessages.TRIANGLE_INEQUALITY_IS_VIOLATED
                    + sideA + " " + sideB + " " + sideC);
        }
    }

    @Override
    public double getArea() {
        var semiPerimeter = (this.sideA + this.sideB + this.sideC) / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - this.sideA)
                * (semiPerimeter - this.sideB) * (semiPerimeter - this.sideC));
    }

    @Override
    public double getPerimeter() {
        return this.sideA + this.sideB + this.sideC;
    }

    @Override
    public String getDescription() {
        return TYPE.getDescription();
    }

    public double getAngleOppositeToSideA(){
        // Для вычисления используется теорема косинусов
        return Math.toDegrees(Math.acos((sideB * sideB + sideC * sideC - sideA * sideA) / (2 * sideB * sideC)));
    }
    public double getAngleOppositeToSideB() {
        return Math.toDegrees(Math.acos((sideA * sideA + sideC * sideC - sideB * sideB) / (2 * sideA * sideC)));
    }

    public double getAngleOppositeToSideC() {
        return Math.toDegrees(Math.acos((sideA * sideA + sideB * sideB - sideC * sideC) / (2 * sideA * sideB)));
    }
    public String getCharacteristics(){
        return super.getCharacteristics() + String.format(
                        "Длина стороны sideA: %.2f мм, противолежащий ей угол: %.2f градусов" +
                        "\nДлина стороны sideB: %.2f мм, противолежащий ей угол: %.2f градусов" +
                        "\nДлина стороны sideC: %.2f мм, противолежащий ей угол: %.2f градусов",
                sideA, getAngleOppositeToSideA(),
                sideB, getAngleOppositeToSideB(),
                sideC, getAngleOppositeToSideC()
        );
    }
}
