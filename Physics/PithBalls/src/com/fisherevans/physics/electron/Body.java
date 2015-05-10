package com.fisherevans.physics.electron;

import java.awt.*;

/**
 * Author: Fisher Evans
 * Date: 11/10/14
 */
public class Body {
    // kg
    private double _mass;

    // coulomb
    private double _charge;

    // meters
    private Vector _position;

    // meters/second
    private Vector _velocity;

    // meters/second
    private Vector _acceleration;

    private int id = -1;

    private Color _color = Color.white;

    public Body(double mass, double charge, Vector position, Vector velocity, Vector acceleration) {
        _mass = mass;
        _charge = charge;
        _position = position == null ? new Vector(0, 0) : position;
        _velocity = velocity == null ? new Vector(0, 0) : velocity;
        _acceleration = acceleration == null ? new Vector(0, 0) : acceleration;
    }

    public void step(double seconds) {
        _velocity.add(_acceleration.getCopy().scale(seconds));
        _position.add(_velocity.getCopy().scale(seconds));
    }

    public double getMass() {
        return _mass;
    }

    public void setMass(double mass) {
        _mass = mass;
    }

    public double getCharge() {
        return _charge;
    }

    public void setCharge(double charge) {
        _charge = charge;
    }

    public Vector getPosition() {
        return _position;
    }

    public void setPosition(Vector position) {
        _position = position;
    }

    public Vector getVelocity() {
        return _velocity;
    }

    public void setVelocity(Vector velocity) {
        _velocity = velocity;
    }

    public Vector getAcceleration() {
        return _acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        _acceleration = acceleration;
    }

    public Color getColor() {
        return _color;
    }

    public void setColor(Color color) {
        _color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
