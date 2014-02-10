package com.fisherevans.poolsim;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/23/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class PoolSim extends BasicGame implements MouseListener
{
    public static AppGameContainer APP;
    public static final int IN_PX_SCALE = 17,
                            TABLE_HEIGHT_IN = 44,
                            TABLE_WIDTH_IN = 88,
                            TABLE_HEIGHT = TABLE_HEIGHT_IN * IN_PX_SCALE,
                            TABLE_WIDTH = TABLE_WIDTH_IN * IN_PX_SCALE,
                            BUMPER_SIZE = 4* IN_PX_SCALE,
                            HUD_HEIGHT = 64,
                            TABLE_PADDING = 10,
                            POCKET_RADIUS = 2;

    public static final Color TABLE_GREEN = new Color(70, 153, 77),
                              TABLE_BUMPER = new Color(87, 74, 34);

    private Ball _cueBall;
    public static ArrayList<Ball> balls;
    public static ArrayList<Pocket> pockets;

    private boolean mousePress = false;
    private boolean movement = false;
    private int mouseStartYAbs = 0;
    private int mouseLastYAbs = 0;
    private int mouseStartX = 0;
    private int mouseStartY = 0;

    private int _stripeIn = 0,
                _solidIn = 0;

    public static final Color[] BALL_COLORS = {
            Color.yellow,
            Color.blue,
            Color.red,
            new Color(1f, 0, 1f), // purple
            Color.black,
            new Color(1f, 0.5f, 0), // orange
            Color.green,
            new Color(0.6f, 0, 0), // dark red
            new Color(1f, 0, 1f), // purple
            Color.yellow,
            Color.blue,
            Color.red,
            new Color(1f, 0.5f, 0), // orange
            Color.green,
            new Color(0.6f, 0, 0), // dark red
    };

    public PoolSim()
    {
        super("Pool Simulator - Fisher Evans");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException
    {
        APP.setMouseCursor(new Image("bin/mouse.png"), 0, 0);

        _cueBall = new Ball(66f, 22f, Color.white, false);
        balls = new ArrayList<Ball>();
        balls.add(_cueBall);


        double xDiff = Math.sqrt((Ball.DEFAULT_RADIUS*2* Ball.DEFAULT_RADIUS*2))-0.15;// - 0.25;
        int ballColor = 0;
        for(int row = 0;row < 5;row++)
        {
            for(int col = 0;col <= row;col++)
            {
                Color c = BALL_COLORS[ballColor%15];
                balls.add(new Ball((float)(22f-(row*xDiff)), 22f + col* Ball.DEFAULT_RADIUS*2 - ((row* Ball.DEFAULT_RADIUS*2)/2), c, (ballColor % 2 != 0)));
                ballColor++;
            }
        }//*/

        pockets = new ArrayList<Pocket>();
        pockets.add(new Pocket(0, 0));
        pockets.add(new Pocket(0, TABLE_HEIGHT_IN));

        pockets.add(new Pocket(TABLE_WIDTH_IN/2, 0));
        pockets.add(new Pocket(TABLE_WIDTH_IN/2, TABLE_HEIGHT_IN));

        pockets.add(new Pocket(TABLE_WIDTH_IN, 0));
        pockets.add(new Pocket(TABLE_WIDTH_IN, TABLE_HEIGHT_IN));

        _stripeIn = 0;
        _solidIn = 0;
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException
    {
        float delta = (float)(((double)i)/1000.0);

        movement = false;

        ArrayList<Ball> ballsToRemove = new ArrayList<Ball>();
        for(Ball ball: balls)
        {
            ball.update(delta);
            movement = movement || ball.velocity().length() != 0 ? true : movement;

            for(Pocket pocket:PoolSim.pockets)
            {
                if(pocket.isIn(ball) && !ball.inBounds())
                {
                    if(ball.getColor().equals(Color.black))
                    {
                        init(gameContainer);
                        return;
                    }
                    ballsToRemove.add(ball);
                    System.out.println("Sinking: " + (ball.isStriped() ? "Striped" : "Solid") + " " + ball.getColor());
                }
            }
        }
        for(Ball ball:ballsToRemove)
        {
            if(!ball.equals(_cueBall))
            {
                balls.remove(ball);
                if(ball.isStriped())
                    _stripeIn++;
                else
                    _solidIn++;
            }
            else
            {
                _cueBall.setPosition(new Vector2f(66f, 22f));
                _cueBall.setVelocity(new Vector2f(0f, 0f));
            }
        }

        if(false || !movement)
        {
            Input input = gameContainer.getInput();
            if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
            {
                Vector2f newPos = new Vector2f(input.getMouseX()-TABLE_PADDING-BUMPER_SIZE, input.getMouseY()-TABLE_PADDING-BUMPER_SIZE);
                newPos.scale(1f/((float)IN_PX_SCALE));
                _cueBall.setPosition(newPos);
                _cueBall.setVelocity(new Vector2f(0, 0));
            }
            else if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
            {
                if(!mousePress)
                {
                    mousePress = true;
                    mouseStartY = input.getMouseY();
                    mouseStartX = input.getMouseX();
                    mouseStartYAbs = input.getAbsoluteMouseY();
                    mouseLastYAbs = input.getAbsoluteMouseY();
                }
                else
                {
                    mouseLastYAbs = input.getAbsoluteMouseY();
                }
            }
            else if(mousePress == true)
            {
                mousePress = false;
                float offX = -(_cueBall.position().x*IN_PX_SCALE - (mouseStartX-TABLE_PADDING-BUMPER_SIZE))/((float)IN_PX_SCALE);
                float offY = -(_cueBall.position().y*IN_PX_SCALE - (mouseStartY-TABLE_PADDING-BUMPER_SIZE))/((float)IN_PX_SCALE);
                Vector2f off = new Vector2f(offX, offY);
                off.normalise().scale(Math.abs(mouseLastYAbs - mouseStartYAbs)*2f);

                _cueBall.velocity().set(off.x, off.y);

                System.out.println(offX + " - " + offY);
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException
    {
        graphics.setAntiAlias(true);
        Input input = gameContainer.getInput();

        graphics.setColor(new Color(TABLE_BUMPER));
        graphics.fillRect(TABLE_PADDING, TABLE_PADDING, TABLE_WIDTH+(2*BUMPER_SIZE), TABLE_HEIGHT+(2*BUMPER_SIZE));

        graphics.setColor(new Color(0.2f, 0.2f, 0.2f));
        for(Pocket pocket:pockets)
        {
            graphics.fillOval(TABLE_PADDING+BUMPER_SIZE + IN_PX_SCALE*(pocket.position().x-pocket.radius()), TABLE_PADDING+BUMPER_SIZE + IN_PX_SCALE*(pocket.position().y-pocket.radius()), pocket.diameter() *IN_PX_SCALE, pocket.diameter() *IN_PX_SCALE);
        }

        graphics.setColor(new Color(TABLE_GREEN));
        graphics.fillRect(TABLE_PADDING+BUMPER_SIZE, TABLE_PADDING+BUMPER_SIZE, TABLE_WIDTH, TABLE_HEIGHT);

        for(Ball ball: balls)
        {
            graphics.setColor(ball.getColor());
            graphics.fillOval(TABLE_PADDING+BUMPER_SIZE + IN_PX_SCALE*(ball.position().x-ball.radius()), TABLE_PADDING+BUMPER_SIZE + IN_PX_SCALE*(ball.position().y-ball.radius()), ball.diameter() *IN_PX_SCALE, ball.diameter() *IN_PX_SCALE);

            if(ball.isStriped())
            {
                graphics.setColor(Color.white);
                graphics.fillOval(TABLE_PADDING+BUMPER_SIZE + IN_PX_SCALE*(ball.position().x-(ball.radius()/2f)), TABLE_PADDING+BUMPER_SIZE + IN_PX_SCALE*(ball.position().y-(ball.radius()/2f)), ball.diameter()/2f *((float)IN_PX_SCALE), ball.diameter()/2f *((float)IN_PX_SCALE));
            }
        }

        if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) graphics.setColor(Color.black);
        else graphics.setColor((!movement ? new Color(0.5f, 1, 0.5f) : new Color(1, 0.5f, 0.5f)));

        Vector2f aimPos = mousePress ? new Vector2f(mouseStartX, mouseStartY) : new Vector2f(input.getMouseX(), input.getMouseY());
        graphics.drawOval(aimPos.x-(_cueBall.radius()*IN_PX_SCALE), aimPos.y-(_cueBall.radius()*IN_PX_SCALE), _cueBall.diameter() *IN_PX_SCALE, _cueBall.diameter() *IN_PX_SCALE);
        if(mousePress)
        {
            graphics.setColor(Color.black);
            graphics.drawString("Hit Speed: " + String.format("%.1f", Math.abs(mouseStartYAbs-mouseLastYAbs)*2.0/12.0) + " feet/second", aimPos.x+(_cueBall.radius()*IN_PX_SCALE)*1.2f+1, aimPos.y-(_cueBall.radius()*IN_PX_SCALE)+1+10);
            graphics.setColor(Color.white);
            graphics.drawString("Hit Speed: " + String.format("%.1f", Math.abs(mouseStartYAbs-mouseLastYAbs)*2.0/12.0) + " feet/second", aimPos.x+(_cueBall.radius()*IN_PX_SCALE)*1.2f, aimPos.y-(_cueBall.radius()*IN_PX_SCALE)+10);
        }

        graphics.setColor(Color.white);
        graphics.fillRect(0, (2*TABLE_PADDING) + (2*BUMPER_SIZE) + TABLE_HEIGHT, TABLE_PADDING*2 + BUMPER_SIZE*2 + TABLE_WIDTH, HUD_HEIGHT);

        int settingsTop = (2*TABLE_PADDING) + (2*BUMPER_SIZE) + TABLE_HEIGHT;
        graphics.setColor(Color.black);
        graphics.drawString("Cue Ball Speed: " + String.format("%.1f", _cueBall.velocity().length()/12) + " feet/second", 20, settingsTop + 5);
        graphics.drawString("Stripes In: " + _stripeIn, 400, settingsTop + 5);
        graphics.drawString("Solids In: " + _solidIn, 400, settingsTop + 30);
    }

    public static void main(String[] args) throws SlickException
    {
        APP = new AppGameContainer(new PoolSim());
        APP.setDisplayMode(TABLE_PADDING * 2 + BUMPER_SIZE * 2 + TABLE_WIDTH, TABLE_PADDING * 2 + BUMPER_SIZE * 2 + TABLE_HEIGHT + HUD_HEIGHT, false);
        APP.start();
    }
}
