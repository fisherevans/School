package com.fisherevans.physics.electron;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 11/10/14
 */
public class World {
    private List<Body> _bodies;
    private Control _control;

    public World(Control control) {
        _bodies = new ArrayList<Body>();
        _control = control;
    }

    public List<Body> getBodies() {
        return _bodies;
    }

    public void step(double seconds) {
        double qq, d, force, theta, distance, charge;
        Vector forceVector;
        Ball bodyA, bodyB;
        for(Body body:_bodies) {
            body.step(seconds);
            body.getAcceleration().zero();
        }
        for(int aid = 0;aid < _bodies.size() - 1;aid++) {
            bodyA = (Ball) _bodies.get(aid);
            for(int bid = aid + 1;bid < _bodies.size();bid++) {
                bodyB = (Ball) _bodies.get(bid);
                distance = bodyA.getPosition().distance(bodyB.getPosition());
                if(distance < bodyA.getRadius()+bodyB.getRadius()) {
                    charge = (bodyA.getCharge()+bodyB.getCharge())/2.0;
                    bodyA.setCharge(charge);
                    bodyB.setCharge(charge);
                    resolveCollision(bodyA, bodyB);
                }
            }
        }
        for(int aid = 0;aid < _bodies.size() - 1;aid++) {
            bodyA = (Ball) _bodies.get(aid);
            for(int bid = aid + 1;bid < _bodies.size();bid++) {
                bodyB = (Ball) _bodies.get(bid);
                qq = bodyA.getCharge()*bodyB.getCharge();
                d = bodyB.getPosition().distance(bodyA.getPosition());
                force = _control.k*qq/Math.pow(d, 2.0);
                theta = bodyA.getPosition().angleBetween(bodyB.getPosition());
                forceVector = Vector.fromAngle(theta, force);
                bodyA.getAcceleration().add(forceVector.getCopy().scale(-1.0/bodyA.getMass()));
                bodyB.getAcceleration().add(forceVector.getCopy().scale(1.0/bodyB.getMass()));
            }
        }
        for(int aid = 0;aid < _bodies.size() - 1;aid++) {
            bodyA = (Ball) _bodies.get(aid);
            for(int bid = aid + 1;bid < _bodies.size();bid++) {
                bodyB = (Ball) _bodies.get(bid);
                distance = bodyA.getPosition().distance(bodyB.getPosition());
                if(distance < bodyA.getRadius()+bodyB.getRadius()) {
                    charge = (bodyA.getCharge()+bodyB.getCharge())/2.0;
                    bodyA.setCharge(charge);
                    bodyB.setCharge(charge);
                    resolveCollision(bodyA, bodyB);
                }
            }
        }
    }

    public static void resolveCollision(Ball b1, Ball b2)
    {
        Vector delta = b1.getPosition().getCopy().subtract(b2.getPosition());
        double d = delta.length();

        // get the mtd
        // minimum translation distance to push balls apart after intersecting
        Vector mtd = delta.getCopy().scale(((b1.getRadius() + b2.getRadius())-d)/d);

        // resolve intersection --
        // inverse mass quantities
        double im1 = 1/b1.getMass();
        double im2 = 1/b2.getMass();

        // push-pull them apart based off their mass
        b1.getPosition().add(mtd.getCopy().scale(im1 / (im1+im2)));
        b2.getPosition().add(mtd.getCopy().scale(im2 / (im1+im2)));

        // impact speed
        Vector v = b1.getVelocity().getCopy().subtract(b2.getVelocity());
        double vn = v.getCopy().dot(mtd.getCopy().normalize());

        // sphere intersecting but moving away from each other already
        if(vn > 0) return;

        // collision impulse
        double i1 = (-1*(1f + b1.getRestitution())*vn)/(im1 + im2);
        double i2 = (-1*(1f + b2.getRestitution())*vn)/(im1 + im2);
        Vector impulse1 = mtd.getCopy().normalize().scale(i1);
        Vector impulse2 = mtd.getCopy().normalize().scale(i2);

        // change in momentum
        b1.getVelocity().add(impulse1.getCopy().scale(im1));
        b2.getVelocity().subtract(impulse2.getCopy().scale(im2));
    }
}
