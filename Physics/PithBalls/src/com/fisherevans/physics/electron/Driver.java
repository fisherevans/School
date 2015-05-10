package com.fisherevans.physics.electron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Author: Fisher Evans
 * Date: 11/10/14
 */
public class Driver implements ActionListener {
    public static double runTime = 0;

    public Control control;

    private World _world;

    private JFrame _frame;

    private Display _display;

    private Timer _timer;

    private long _lastPaint;

    public static void main(String[] args) {
        new Driver();
    }

    public Driver() {
        control = new Control(this);
        _world = new World(control);
        loadDisplay();

        _lastPaint = System.currentTimeMillis();
        _timer = new Timer(6, this);
        _timer.start();
    }

    private void loadWorld() {
        _world = new World(control);
        int failCount = 0;
        float maxDensity = (float)(new Ball(control.massMax, 0, null, null, null, control.radiusMin).getDensity());
        for(int i = 0;i < control.countNeg + control.countPos;i++) {
            double mass = random(control.massMin, control.massMax);
            double charge = random(control.chargeMin, control.chargeMax)*(i < control.countPos ? 1.0 : -1.0);
            double radius = random(control.radiusMin, control.radiusMax);
            Vector position = new Vector(random(0, control.area), random(0, control.area)).subtract(new Vector(control.area/2.0, control.area/2.0));
            Vector velocity = Vector.fromAngle(random(0, Math.PI*2.0), random(control.velocityMin, control.velocityMax));
            Ball ball = new Ball(mass, charge, position, velocity, null, radius);
            Color c = Color.getHSBColor((float)random(0, 1), 1f, 1f - (float)(ball.getDensity())/maxDensity*0.75f);
            ball.setColor(c);
            ball.setId(i+1);
            ball.setRestitution(random(control.restitutionMin, control.restitutionMax));
            boolean add = true;
            for(Body other:_world.getBodies()) {
                Ball otherBall = (Ball) other;
                double distance = ball.getPosition().distance(otherBall.getPosition());
                if(distance < ball.getRadius()+otherBall.getRadius()) {
                    add = false;
                    break;
                }
            }
            if(add)
                _world.getBodies().add(ball);
            else {
                i--;
                if(failCount++ > 100) {
                    control.output = "Bad settings, failed to fit balls in area.";
                    _world.getBodies().clear();
                    break;
                }
            }
        }
        _world.step(0);
    }

    public void resetWorld() {
        runTime = 0;
        loadWorld();
        _display.setWorld(_world);
    }

    private void loadDisplay() {
        _frame = new JFrame("Coulomb's Law");
        _display = new Display(_world, control);
        _display.setPreferredSize(new Dimension(800, 600));
        _frame.add(_display);
        _frame.addKeyListener(control);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.pack();
        _frame.setVisible(true);
        _frame.setExtendedState(_frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    private double random(double min, double max) {
        return Math.random()*(max-min) + min;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long thisPaint = System.currentTimeMillis();
        if(!control.paused) {
            double seconds = (thisPaint-_lastPaint)/1000.0* control.timeScale;
            runTime += seconds;
            _world.step(seconds);
        }
        if(control.follow) {
            double xMax = -Double.MAX_VALUE, xMin = Double.MAX_VALUE;
            double yMax = -Double.MAX_VALUE, yMin = Double.MAX_VALUE;
            for(Body body:_world.getBodies()) {
                xMax = Math.max(xMax, body.getPosition().getX());
                yMax = Math.max(yMax, body.getPosition().getY());
                xMin = Math.min(xMin, body.getPosition().getX());
                yMin = Math.min(yMin, body.getPosition().getY());
            }
            control.viewDX = (int)((xMax+xMin)/2.0*control.viewScale);
            control.viewDY = (int)((yMax+yMin)/2.0*control.viewScale);
        }
        _lastPaint = thisPaint;
        _display.repaint();
    }
}
