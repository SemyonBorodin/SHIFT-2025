package com.semyon.shapes;

import com.semyon.exceptions.ShapeValidationException;

public class Circle extends Shape{

    private static final ShapeType TYPE = ShapeType.CIRCLE;

    private final double radius;

    public Circle(Double radius) {
        if(!isValidCircle(radius)){
            throw new ShapeValidationException(ErrorMessages.INVALID_CIRCLE_PARAMS + radius);
        }
        this.radius = radius;
    }
    @Override
    public String getDescription() {
        return TYPE.getDescription();
    }

    public static boolean isValidCircle(Double radius){
        return radius > 0;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    @Override
    public String getCharacteristics(){
        return super.getCharacteristics() +
                String.format("Радиус: %.2f мм" +
                        "\nДиаметр: %.2f мм",
                        radius,
                        2 * radius);
    }
}
