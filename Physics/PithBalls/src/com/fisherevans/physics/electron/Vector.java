package com.fisherevans.physics.electron;

/**
 * Author: Fisher Evans
 * Date: 11/10/14
 */
public class Vector {
    private double _x;

    private double _y;

    public Vector(double x, double y) {
        _x = x;
        _y = y;
    }

    public Vector zero() {
        _x = 0;
        _y = 0;
        return this;
    }

    public double distance(Vector other) {
        return Math.sqrt(Math.pow(other.getX() - getX(), 2) + Math.pow(other.getY() - getY(), 2));
    }

    public double angleBetween(Vector other) {
        double dx = other.getX() - getX();
        double dy = other.getY() - getY();
        return Math.atan2(dy, dx);
    }

    /**
     * adds a given vector to this one
     * @param vector the vector to add
     * @return this vector after the addition
     */
    public Vector add(Vector vector) {
        _x += vector.getX();
        _y += vector.getY();
        return this;
    }

    /**
     * subtract a given vector to this one
     * @param vector the vector to subtract
     * @return this vector after the subtraction
     */
    public Vector subtract(Vector vector) {
        _x -= vector.getX();
        _y -= vector.getY();
        return this;
    }

    /**
     * multiplies a given vector to this one
     * @param vector the vector to multiply by
     * @return this vector after the multiplication
     */
    public Vector multiply(Vector vector) {
        _x *= vector.getX();
        _y *= vector.getY();
        return this;
    }

    /**
     * scales thi vector by a given magnitude
     * @param magnitude the amount to scale by
     * @return this vector after the scaling
     */
    public Vector scale(double magnitude) {
        _x *= magnitude;
        _y *= magnitude;
        return this;
    }

    /**
     * @return the squared length of this vecotr
     */
    public double squaredLength() {
        return _x*_x + _y*_y;
    }

    /**
     * @return the length of this vecotr
     */
    public double length() {
        return Math.sqrt(squaredLength());
    }

    /**
     * returns the result of dot multiplying this vector by another
     * @param vector the vector to dot multiply with
     * @return the dot multiply result
     */
    public double dot(Vector vector) {
        return _x*vector.getX() + _y*vector.getY();
    }

    /**
     * normalizes this vector
     * @return this vector after normalization
     */
    public Vector normalize() {
        double startLength = length();
        _x /= startLength;
        _y /= startLength;
        return this;
    }

    /**
     * gets the angle produced by this vector
     * @return the angle
     */
    public double getAngle() {
        return Math.atan2(_y, _x);
    }

    /**
     * creates an identical copy of this vector in a new instance
     * @return the copy
     */
    public Vector getCopy() {
        return new Vector(_x, _y);
    }

    public double getX() {
        return _x;
    }

    public void setX(double x) {
        _x = x;
    }

    public double getY() {
        return _y;
    }

    public void setY(double y) {
        _y = y;
    }

    /**
     * creats a vector from an angle and magnitude
     * @param angle the angle of the vector
     * @param length the length of the vector
     * @return the vector based on the given angle and length
     */
    public static Vector fromAngle(double angle, double length) {
        double x = Math.cos(angle)*length;
        double y = Math.sin(angle)*length;
        return new Vector(x, y);
    }

    @Override
    public String toString() {
        return String.format("%03.3f, %03.3f", _x, _y);
    }
}
