package com.fisherevans.physics.electron;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Author: Fisher Evans
 * Date: 11/10/14
 */
public class Display extends JPanel {
    public static final int STAT_W = 300;
    public static final int STAT_H = 140;
    private static final Font font = new Font("Monospaced", Font.PLAIN, 12);
    private static final Font font2 = new Font("Monospaced", Font.BOLD, 32);
    private World _world;
    private Control _control;
    private boolean _first = true;

    public Display(World world, Control control) {
        _world = world;
        _control = control;
    }

    public void setWorld(World world) {
        _first = true;
        _world = world;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font);
        g2.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));

        float w = getWidth();
        float h = getHeight();
        float hw = w/2f;
        float hh = h/2f;

        g2.setColor(new Color(1f, 1f, 1f, _first ? 1f : 0.005f));
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        float mw = w/(float)_control.viewScale;
        float mh = h/(float)_control.viewScale;
        float gdx = mw*(float)_control.viewScale/2f;
        float gdy = mh*(float)_control.viewScale/2f;
        int mdx = (int)((float)_control.viewScale%mw);
        g2.setColor(Color.lightGray);
        for(float mx = -1-mdx;mx < mw+1-mdx;mx++) {
            float x = mx*(float)_control.viewScale - (_control.viewDX%(float)_control.viewScale) + gdx;
            g2.drawLine((int)x, 0, (int)x, (int)getHeight());
        }
        for(float my = -1;my < mh+1;my++) {
            float y = my*(float)_control.viewScale + (_control.viewDY%(float)_control.viewScale) + gdy;
            g2.drawLine((int)0, (int)y, (int)w, (int)y);
        }

        Vector accScaled;
        int bid = 0;
        for(Body body:_world.getBodies()) {
            float drawX = (float)(body.getPosition().getX()*_control.viewScale) + hw - _control.viewDX;
            float drawY = (float)(-body.getPosition().getY()*_control.viewScale) + hh + _control.viewDY;
            accScaled = body.getAcceleration().getCopy().scale(_control.viewScale*_control.accScale);
            float accX = (float)(drawX + accScaled.getX());
            float accY = (float)(drawY + -accScaled.getY());
            float drawR = (float)(((Ball)body).getRadius()*_control.viewScale);
            float drawD = drawR*2f;
            g2.setColor(body.getColor());
            Ellipse2D.Float e = new Ellipse2D.Float(drawX-drawR, drawY-drawR, drawD, drawD);
            g2.fill(e);
            g2.setColor(Color.black);
            g2.draw(e);
            g2.drawLine((int)drawX, (int)drawY, (int)accX, (int)accY);
            float cScale = (float)(Math.abs(body.getCharge())/_control.chargeMax);
            cScale = cScale < 0 ? 0 : cScale > 1 ? 1 : cScale;
            g2.setFont(font2);
            g2.setColor(new Color(0f, 0f, 0f, cScale));
            g2.drawString(body.getCharge() > 0 ? "+" : "-", drawX+drawR*0.7f, drawY-drawR*0.7f);
            g2.setFont(font);
            g2.setColor(Color.darkGray);
            if(_control.showStats)
                g2.drawString(body.getId() + "", drawX-drawR*1.1f, drawY-drawR*1.1f);
        }

        if(_control.showStats) {
            for(Body body:_world.getBodies())
                drawStats((Ball)body, g2, 5, 5 + bid++*(STAT_H+5));
            drawSettings(g2);
            drawButtons(g2);
            drawCommands(g2);
        }

        /*
        g2.setColor(new Color(1f, 1f, 1f));
        g2.fillRect((int) (hw - _control.viewScale / 2f)-6, 7, (int) _control.viewScale+12, 20);
        g2.setColor(Color.darkGray);
        g2.drawLine((int) (hw - _control.viewScale / 2f), 8, (int) (hw + _control.viewScale / 2f), 8);
        g2.drawString("1 Meter", (int) (hw - _control.viewScale / 2f + 5), 23);
        */

        g2.setColor(new Color(0.8f, 0.8f, 0.8f));
        g2.fillRect(0, (int)h - 40, (int)w, (int)h);
        g2.setColor(Color.black);
        g2.drawString("> " + _control.input, 5, h - 23);
        g2.setColor(Color.red);
        g2.drawString(_control.output, 5, h - 5);

        _first = false;
    }

    private void drawSettings(Graphics2D g2) {
        NumberFormat formatter = new DecimalFormat("0.####E0");
        int x = getWidth() - STAT_W - 5;
        int y = 5;
        g2.setColor(new Color(1f, 1f, 1f));
        g2.fillRect(x, y, STAT_W, STAT_H + 15*6);
        g2.setColor(Color.black);
        g2.drawRect(x, y, STAT_W, STAT_H + 15*6);

        int line = 1;
        g2.drawString("Paused: " + (_control.paused ? "True " : "False") + " - Following: " + (_control.follow ? "True " : "False"), x + 3, y + (line++ * 15));
        g2.drawString("Time: " + String.format("%.3fs", Driver.runTime), x + 3, y + (line++ * 15));
        g2.drawString(String.format("Time Scale: %.3f s", _control.timeScale), x + 3, y + (line++ * 15));
        g2.drawString("View Scale: 1m -> " + (int)(_control.viewScale) + "px", x + 3, y + (line++ * 15));
        g2.drawString(String.format("Acc Scale: %.1f", _control.accScale), x + 3, y + (line++ * 15));
        g2.drawString("Pan: " + (int)(_control.viewDX) + "px, " + (int)(_control.viewDY) + "px", x + 3, y + (line++ * 15));
        g2.drawString(String.format("K: %s N·m*m/C/C (m/F)", formatter.format(_control.k)), x + 3, y + (line++ * 15));
        g2.drawString("----------------", x + 3, y + (line++ * 15));
        g2.drawString(String.format("Spawn Area: %.2f m", _control.area), x + 3, y + (line++ * 15));
        g2.drawString(String.format("Mass: %.5f - %.5f kg", _control.massMin, _control.massMax), x + 3, y + (line++ * 15));
        g2.drawString(String.format("Vel: %.5f - %.5f m/s", _control.velocityMin, _control.velocityMax), x + 3, y + (line++ * 15));
        g2.drawString(String.format("Charge: %s - %s c", formatter.format(_control.chargeMin), formatter.format(_control.chargeMax)), x + 3, y + (line++ * 15));
        g2.drawString(String.format("Radius: %.3f - %.3f m", _control.radiusMin, _control.radiusMax), x + 3, y + (line++ * 15));
        g2.drawString(String.format("Count: +%d, -%d", _control.countPos, _control.countNeg), x + 3, y + (line++ * 15));
        g2.drawString(String.format("Restitution: %.3f - %.3f", _control.restitutionMin - _control.restitutionMax), x + 3, y + (line++ * 15));
    }

    private void drawCommands(Graphics2D g2) {
        NumberFormat formatter = new DecimalFormat("0.####E0");
        int x = getWidth() - STAT_W - 4;
        int y = 10 + STAT_H + 15*6;
        g2.setColor(new Color(1f, 1f, 1f));
        g2.fillRect(x, y, STAT_W, STAT_H + 15*3);
        g2.setColor(Color.black);
        g2.drawRect(x, y, STAT_W, STAT_H + 15*3);

        int line = 1;
        g2.drawString("Type a command and press enter to execute", x + 3, y + (line++ * 15));
        g2.drawString("Commands:", x + 3, y + (line++ * 15));
        g2.drawString("mass [double > min max|single]", x + 3, y + (line++ * 15));
        g2.drawString("vel [double > min max|single]", x + 3, y + (line++ * 15));
        g2.drawString("charge [double > min max|single]", x + 3, y + (line++ * 15));
        g2.drawString("count [int > neg pos|both]", x + 3, y + (line++ * 15));
        g2.drawString("radius [double > min max|single]", x + 3, y + (line++ * 15));
        g2.drawString("rest [double > min max|single]", x + 3, y + (line++ * 15));
        g2.drawString("timescale double", x + 3, y + (line++ * 15));
        g2.drawString("viewscale int", x + 3, y + (line++ * 15));
        g2.drawString("area double", x + 3, y + (line++ * 15));
        g2.drawString("k double", x + 3, y + (line++ * 15));
    }

    private void drawButtons(Graphics2D g2) {
        NumberFormat formatter = new DecimalFormat("0.####E0");
        int x = getWidth() - STAT_W - 5;
        int y = 15 + 2*STAT_H + 15*9;
        g2.setColor(new Color(1f, 1f, 1f));
        g2.fillRect(x, y, STAT_W, STAT_H + 15*2);
        g2.setColor(Color.black);
        g2.drawRect(x, y, STAT_W, STAT_H + 15*2);

        int line = 1;
        g2.drawString("Buttons:", x + 3, y + (line++ * 15));
        g2.drawString("Page Up/Page Down: Scroll ball list", x + 3, y + (line++ * 15));
        g2.drawString("Arrow Keys: Pan View", x + 3, y + (line++ * 15));
        g2.drawString("F1: Pause Sim", x + 3, y + (line++ * 15));
        g2.drawString("F2: Show Stat Windows", x + 3, y + (line++ * 15));
        g2.drawString("F3/F4: Adjust Time Scale", x + 3, y + (line++ * 15));
        g2.drawString("F5/F6: Adjust View Scale", x + 3, y + (line++ * 15));
        g2.drawString("F7/F8: Adjust Acc Line Scale", x + 3, y + (line++ * 15));
        g2.drawString("F9: Follow Balls", x + 3, y + (line++ * 15));
        g2.drawString("F10: Reset View", x + 3, y + (line++ * 15));
        g2.drawString("F12: Restart Sim", x + 3, y + (line++ * 15));
    }

    private void drawStats(Ball ball, Graphics2D g2, int x, int y) {
        y -= _control.statsScroll;
        NumberFormat formatter = new DecimalFormat("0.####E0");
        g2.setColor(new Color(1f, 1f, 1f));
        g2.fillRect(x, y, STAT_W, STAT_H);
        g2.setColor(Color.black);
        g2.drawRect(x, y, STAT_W, STAT_H);

        int line = 1;

        g2.setColor(ball.getColor().darker().darker());
        g2.drawString("ID: " + ball.getId(), x + 3, y + (line++ * 15));
        g2.drawString("Position: " + ball.getPosition().toString() + " m", x + 3, y + (line++ * 15));
        g2.drawString("Velocity: " + ball.getVelocity().toString() + " m/s", x + 3, y + (line++ * 15));
        g2.drawString("Acceleration: " + ball.getAcceleration().toString() + " m/s/s", x + 3, y + (line++ * 15));
        g2.drawString("Charge: " + formatter.format(ball.getCharge()) + " c", x + 3, y + (line++ * 15));
        g2.drawString("Mass: " + String.format("%.6f kg", ball.getMass()), x + 3, y + (line++ * 15));
        g2.drawString("Radius: " + String.format("%.6f m", ball.getRadius()), x+3, y+(line++*15));
        g2.drawString("Density: " + String.format("%.9f kg/m/m", ball.getDensity()), x+3, y+(line++*15));
        g2.drawString("Restitution: " + String.format("%.3f", ball.getRestitution()), x+3, y+(line++*15));
    }
}
