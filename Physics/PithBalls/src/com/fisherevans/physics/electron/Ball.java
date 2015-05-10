package com.fisherevans.physics.electron;

/**
 * Author: Fisher Evans
 * Date: 11/10/14
 */
public class Ball extends Body {

    // meters
    private double _radius;

    public double density, restitution;

    public Ball(double mass, double charge, Vector position, Vector velocity, Vector acceleration, double radius) {
        super(mass, charge, position, velocity, acceleration);
        setRadius(radius);
    }

    public double getRadius() {
        return _radius;
    }

    public void setRadius(double radius) {
        _radius = radius;
        density = getMass()/(Math.PI*Math.pow(radius, 2));
    }

    public double getDensity() {
        return density;
    }

    public double getRestitution() {
        return restitution;
    }

    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }
}
