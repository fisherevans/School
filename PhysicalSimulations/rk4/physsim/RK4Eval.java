package com.fisherevans.physsim;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/16/13
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class RK4Eval
{
    public static String EXTENSION = ".rk4";
    private  ScriptEngine _jsEng;

    private double _from, _to, _step;

    private Map<String, Double> _globals;
    private Map<String, Double> _time;

    private Map<String, String> _equations;
    private Map<String, Double> _values;

    private Map<String, String> _exact;

    private BufferedWriter _out;

    public RK4Eval(String fileName)
    {
        _globals = new HashMap<>();
        _time = new HashMap<>();
        _equations = new HashMap<>();
        _values = new HashMap<>();
        _exact = new HashMap<>();

        _jsEng = (new ScriptEngineManager()).getEngineByName("JavaScript");

        loadFile(fileName);
        verifyData();

        startOutput(fileName);
        stepThroughRK4();
    }

    private void stepThroughRK4()
    {

        writeOutHeader();

        for(double time = _from;time <= _to;time += _step)
        {
            for(String exactKey : _exact.keySet())
                _values.put(exactKey, exactEval(_exact.get(exactKey)));

            writeOutRow(time);

            Map<String, Double> k1 = rk4(time, _values, _equations, "k1");
            Map<String, Double> k2 = rk4(time, k1, _equations, "k2");
            Map<String, Double> k3 = rk4(time, k2, _equations, "k3");
            Map<String, Double> k4 = rk4(time, k3, _equations, "k4");

            for(String valueKey : _equations.keySet())
                _values.put(valueKey, _values.get(valueKey) + ((_step*(1.0/6.0))*(k1.get(valueKey) + 2.0*k2.get(valueKey) + 2.0*k3.get(valueKey) + k4.get(valueKey))));
        }
    }

    private void startOutput(String fileName)
    {
        fileName += Graph.EXTENSION;

        try
        {
            File file = new File(fileName);

            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            _out = new BufferedWriter(fw);
        }
        catch(Exception e)
        {
            Driver.p("Failed to open: " + fileName);
            System.exit(5);
        }
    }

    private Map<String, Double> rk4(double time, Map<String, Double> values, Map<String, String> equations, String k)
    {
        double secondCoefficient = 0;

        switch(k)
        {
            case "k1": secondCoefficient = 0; break;
            case "k2": secondCoefficient = _step*0.5; break;
            case "k3": secondCoefficient = _step*0.5; break;
            case "k4": secondCoefficient = _step; break;
            default: secondCoefficient = 0; break;
        }

        for(String valueKey : _equations.keySet())
            values.put(valueKey, values.get(valueKey) + values.get(valueKey)*secondCoefficient);

        Map<String, Double> results = new HashMap<>();
        for(String eqnKey : _equations.keySet())
        {
            String eqn = equations.get(eqnKey);
            eqn = eqn.replaceAll("%time%", time+"");
            for(String valueKey : _equations.keySet())
                eqn = eqn.replaceAll("%"+valueKey+"%", values.get(valueKey)+"");
            for(String globalKey : _globals.keySet())
                eqn = eqn.replaceAll("%"+globalKey+"%", _globals.get(globalKey)+"");
            //Driver.p(eqn);
            results.put(eqnKey, eval(eqn));
        }
        return results;
    }

    private double exactEval(String eqn)
    {
        for(String globalKey : _globals.keySet())
            eqn = eqn.replaceAll("%"+globalKey+"%", _globals.get(globalKey)+"");
        for(String valueKey : _values.keySet())
            eqn = eqn.replaceAll("%"+valueKey+"%", _values.get(valueKey)+"");
        return eval(eqn);
    }

    private double globalEval(String eqn)
    {
        for(String globalKey : _globals.keySet())
            eqn = eqn.replaceAll("%"+globalKey+"%", _globals.get(globalKey)+"");
        return eval(eqn);
    }

    private double eval(String eqn)
    {
        double result = 0;
        try
        {
            result = (Double)(_jsEng.eval(eqn));
        }
        catch(Exception e)
        {
            Driver.p("Failed to evaluate: " + eqn);
            e.printStackTrace();
            System.exit(1);
        }
        return result;
    }

    private void verifyData()
    {
        if(_from >= _to)
        {
            Driver.p("Please define a valid 'from' and 'to' range... (FROM:" + _from + ", TO:" + _to + ")");
            System.exit(3);
        }
        if(_step <= 0)
        {
            Driver.p("Please define a valid 'step'... (STEP:" + _step + ")");
            System.exit(3);
        }

        Set<String> eqnKeys = _equations.keySet();
        Set<String> valKeys = _values.keySet();

        boolean valid = true;

        for(String eqnKey : eqnKeys)
        {
            if(!_values.containsKey(eqnKey))
            {
                Driver.p("The key '" + eqnKey + "' does not have an inital value defined.");
                valid = false;
            }
        }
        for(String valKey : valKeys)
        {
            if(!_equations.containsKey(valKey))
            {
                Driver.p("The key '" + valKey + "' does not have an equation defined.");
                valid = false;
            }
        }


        if(valid == false)
        {
            Driver.p("One or more variables are missing an equation or initial value.");
            System.exit(4);
        }
    }

    private void writeOutHeader()
    {
        try
        {
            _out.write("time");
            for(String key : _equations.keySet())
                _out.write(","+key);
            for(String key : _exact.keySet())
                _out.write(","+key);
            _out.newLine();
            _out.flush();
        }
        catch(Exception e)
        {
            Driver.p("Failed to write to output file.");
            System.exit(6);
        }
    }

    private void writeOutRow(double time)
    {
        try
        {
            _out.write(time+"");
            for(String key : _equations.keySet())
                _out.write(","+_values.get(key));
            for(String key : _exact.keySet())
                _out.write(","+_values.get(key));
            _out.newLine();
            _out.flush();
        }
        catch(Exception e)
        {
            Driver.p("Failed to write to output file.");
            System.exit(6);
        }
    }

    private void loadFile(String fileName)
    {
        fileName += EXTENSION;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String[] line;
            String fullLine, left, right;
            while((fullLine = br.readLine()) != null)
            {
                fullLine = fullLine.replaceAll(" +", "");
                if(!fullLine.equals("") && !fullLine.startsWith("#"))
                {
                    //Driver.p(">"+fullLine);
                    line = fullLine.split(":");

                    switch(line[0])
                    {
                        case "time":
                            switch(line[1])
                            {
                                case "from":
                                    _from = Double.parseDouble(line[2]); break;
                                case "to":
                                    _to = Double.parseDouble(line[2]); break;
                                case "step":
                                    _step = Double.parseDouble(line[2]); break;
                            }
                            break;
                        case "global":
                            _globals.put(line[1], globalEval(line[2])); break;
                        case "variable":
                            _values.put(line[1], Double.parseDouble(line[2]));
                            _equations.put(line[1], line[3]);
                            break;
                        case "exact":
                            _exact.put(line[1], line[2]);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        catch(Exception e)
        {
            Driver.p("Failed to read: " + fileName);
            System.exit(2);
        }
    }
}
