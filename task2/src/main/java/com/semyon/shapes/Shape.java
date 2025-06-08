package com.semyon.shapes;

public abstract class Shape {

    protected abstract double getArea();
    protected abstract double getPerimeter();
    protected abstract String getDescription();

    public String getCharacteristics() {
        return String.format("Тип фигуры: " + getDescription() + "\nПлощадь: %.2f кв. мм\nПериметр: %.2f мм\n",
                getArea(), getPerimeter());
    }
}
