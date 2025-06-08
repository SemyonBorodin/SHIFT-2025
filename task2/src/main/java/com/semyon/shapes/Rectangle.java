package com.semyon.shapes;

import com.semyon.exceptions.ShapeValidationException;

public class Rectangle extends Shape{

    private static final ShapeType TYPE = ShapeType.RECTANGLE;

    private final double sideA;
    private final double sideB;

    public Rectangle(double sideA, double sideB) {
        validateRectangle(sideA, sideB);
        this.sideA = sideA;
        this.sideB = sideB;
    }

    private static void validateRectangle(double sideA, double sideB) {
        if (sideA <= 0 || sideB <= 0){
            throw  new ShapeValidationException(ErrorMessages.INVALID_RECTANGLE_PARAMS + sideA + " " + sideB);
        }
    }

    @Override
    public double getArea() {
        return this.sideA * this.sideB;
    }

    @Override
    public double getPerimeter() {
        return this.sideA + this.sideA + this.sideB + this.sideB;
    }

    @Override
    protected String getDescription() {
        return TYPE.getDescription();
    }

    public double getDiagonal(){
        return Math.sqrt(this.sideA * this.sideA + this.sideB * this.sideB);
    }

    public double getShorterSide(){
        return Math.min(this.sideA, this.sideB);
    }
    public double getLongerSide(){
        return Math.max(this.sideB, this.sideA);
    }

    @Override
    public String getCharacteristics(){

        return super.getCharacteristics() + String.format(
                "Длина диагонали: %.2f мм" +
                        "\nДлина: %.2f мм" +
                        "\nШирина: %.2f мм",
                getDiagonal(),
                getLongerSide(),
                getShorterSide()
        );
    }
}
