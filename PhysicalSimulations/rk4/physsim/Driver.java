package com.fisherevans.physsim;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/16/13
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver
{
    public Driver(String[] args)
    {
        if(args.length <= 0)
            help();

        switch(args[0])
        {
            case "eval":
                new RK4Eval(args[1]);
                break;
            default:
                help();
        }
    }

    public void help()
    {
        p("No valid arguments given...");
        p("Usage: rk4.jar (eval|graph|animate) (filename)");
        System.exit(99);
    }

    // Public methods
    public static void p(String msg) { System.out.println(msg); }
    public static void p(Double msg) { System.out.println(msg+""); }

    // Start Method
    public static void main(String[] args) { new Driver(args); }
}
