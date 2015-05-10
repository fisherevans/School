package com.fisherevans.physics.electron;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Author: Fisher Evans
 * Date: 11/11/14
 */
public class Control implements KeyListener {
    private Driver driver;

    public double massMin = 0.0005, massMax = 0.001;

    public double radiusMin = 0.025, radiusMax = 0.05;

    public double velocityMin = 0, velocityMax = 0;

    public double chargeMin = 5E-8, chargeMax = 9.9E-8;

    public int countNeg = 1, countPos = 2;

    public double timeScale = 1.0;

    public double area = 1.0;

    public double k =  8.9875517873681764E9;

    public double restitutionMin = 0.3, restitutionMax = 0.7;

    public double viewScale = 500, accScale = 0.5;

    public boolean paused = false, follow = false, showStats = true;

    public int statsScroll = 0;

    public int viewDX = 0, viewDY = 0;

    public String input = "", output = "", alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@$^&*()_+-=[]\\;',./{}|\"<>?`~ #%:";

    public Control(Driver driver) {
        this.driver = driver;
    }

    public void execute() {
        String[] split = input.toLowerCase().split(" ");
        int l = split.length;
        input = "";
        output = "";
        try {
            if("mass".equals(split[0])) {
                if(l == 2){
                    massMin = massMax = Double.parseDouble(split[1]);
                } else if(l == 3) {
                    massMin = Double.parseDouble(split[1]);
                    massMax = Double.parseDouble(split[2]);
                }
                output = "[kg] Min Mass = " + massMin + " - Max Mass = " + massMax;
            } else if("vel".equals(split[0])) {
                if(l == 2){
                    velocityMin = velocityMax = Double.parseDouble(split[1]);
                } else if(l == 3) {
                    velocityMin = Double.parseDouble(split[1]);
                    velocityMax = Double.parseDouble(split[2]);
                }
                output = "[m/s] Min Velocity = " + velocityMin + " - Max Velocity = " + velocityMax;
            } else if("charge".equals(split[0])) {
                if(l == 2){
                    chargeMin = chargeMax = Double.parseDouble(split[1]);
                } else if(l == 3) {
                    chargeMin = Double.parseDouble(split[1]);
                    chargeMax = Double.parseDouble(split[2]);
                }
                output = "[c] Min Charge = " + chargeMin + " - Max Charge = " + chargeMax;
            } else if("count".equals(split[0])) {
                if(l == 2){
                    countNeg = countPos = Integer.parseInt(split[1]);
                } else if(l == 3) {
                    countNeg = Integer.parseInt(split[1]);
                    countPos = Integer.parseInt(split[2]);
                }
                output = "(-) Balls = " + countNeg + " - (+) Balls = " + countPos;
            } else if("radius".equals(split[0])) {
                if(l == 2){
                    radiusMin = radiusMax = Double.parseDouble(split[1]);
                } else if(l == 3) {
                    radiusMin = Double.parseDouble(split[1]);
                    radiusMax = Double.parseDouble(split[2]);
                }
                output = "[m] Min Radius = " + radiusMin + " - Max Radius = " + radiusMax;
            } else if("timescale".equals(split[0])) {
                if(l == 2){
                    timeScale = Double.parseDouble(split[1]);
                }
                output = "[s] Time Scale = " + radiusMax;
            } else if("area".equals(split[0])) {
                if(l == 2){
                    area = Double.parseDouble(split[1]);
                }
                output = "[m] Spawn Area = " + radiusMax;
            } else if("k".equals(split[0])) {
                if(l == 2){
                    k = Double.parseDouble(split[1]);
                }
                output = "[N·m*m/C/C (m/F)] Coulomb's constant = " + k;
            } else if("rest".equals(split[0])) {
                if(l == 2){
                    restitutionMin = restitutionMax = Double.parseDouble(split[1]);
                } else if(l == 3) {
                    restitutionMin = Double.parseDouble(split[1]);
                    restitutionMax = Double.parseDouble(split[2]);
                }
                output = "Coefficient of restitution Min = " + restitutionMin + " - Max = " + restitutionMax;
            } else if("viewscale".equals(split[0])) {
                if(l == 2){
                    viewScale = Integer.parseInt(split[1]);
                }
                output = "[s] View Scale = " + radiusMax;
            } else {
                output = "Invalid command.";
            }
        } catch(Exception e) {
            output = "Command failed! " + e.getMessage();
        }
    }

    public void restartSim() {
        statsScroll = 0;
        driver.resetWorld();
    }

    public void resetView() {
        viewDX = viewDY = 0;
        viewScale = 500;
        statsScroll = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(alphabet.contains("" + e.getKeyChar()))
            input += e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            execute();
        } else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && input.length() > 0) {
            input = input.substring(0, input.length()-1);
        } else if(e.getKeyCode() == KeyEvent.VK_F1) {
            paused = !paused;
        } else if(e.getKeyCode() == KeyEvent.VK_F2) {
            showStats = !showStats;
        } else if(e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            statsScroll += 15;
        } else if(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            statsScroll -= 15;
            statsScroll = statsScroll < 0 ? 0 : statsScroll;
        } else if(e.getKeyCode() == KeyEvent.VK_F3) {
            timeScale -= 0.001;
            timeScale = timeScale < 0 ? 0 : timeScale;
        } else if(e.getKeyCode() == KeyEvent.VK_F4) {
            timeScale += 0.001;
        } else if(e.getKeyCode() == KeyEvent.VK_F5) {
            viewScale -= 15;
            viewScale = viewScale < 1 ? 1 : viewScale;
        } else if(e.getKeyCode() == KeyEvent.VK_F6) {
            viewScale += 15;
        } else if(e.getKeyCode() == KeyEvent.VK_UP) {
            viewDY += viewScale/30.0;
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            viewDY -= viewScale/30.0;
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            viewDX -= viewScale/30.0;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            viewDX += viewScale/30.0;
        } else if(e.getKeyCode() == KeyEvent.VK_F7) {
            accScale -= 0.1;
            accScale = accScale < 0 ? 0 : accScale;
        } else if(e.getKeyCode() == KeyEvent.VK_F8) {
            accScale += 0.1;
        } else if(e.getKeyCode() == KeyEvent.VK_F9) {
            follow = !follow;
        } else if(e.getKeyCode() == KeyEvent.VK_F10) {
            resetView();
        } else if(e.getKeyCode() == KeyEvent.VK_F12) {
            restartSim();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
