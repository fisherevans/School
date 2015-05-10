package vtc.cis4150.svnclient.local_settings;

import vtc.cis4150.svnclient.SVNClient;
import vtc.cis4150.svnclient.exceptions.SettingValueTypeMisMatch;
import vtc.cis4150.svnclient.exceptions.UnableToLoadSettings;
import vtc.cis4150.svnclient.exceptions.UnableToSaveSettings;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

/**
 * User: David Fisher Evans
 * Date: 10/29/13
 */
public class LocalSettings
{
    private HashMap<String, Object> _settings;

    public static final String DEFAULT_SETTINGS = "settings.ser";

    public LocalSettings()
    {
        _settings = new HashMap<String, Object>();

        // For Testing -
    }

    public void load() throws Exception
    {
        load(DEFAULT_SETTINGS);
    }

    public void load(String filename) throws Exception
    {
        InputStream file = new FileInputStream(filename);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        _settings = (HashMap<String, Object>)input.readObject();
        input.close();
    }

    public void save() throws Exception
    {
        save(DEFAULT_SETTINGS);
    }

    public void save(String filename) throws Exception
    {
        _settings.put("connections", SVNClient.getConnectionManager().toString());

        OutputStream file = new FileOutputStream(filename);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(_settings);

        output.flush();
        output.close();
    }

    public void loadDefaultSettings()
    {
        /**
         * User:     fisherevans
         * Password: PassworD1234
         * URL:      https://subversion.assembla.com/svn/fishertest/
         */
    }

    public Set<String> getSettingKeys()
    {
        return _settings.keySet();
    }

    public Object set(String settingKey, Object settingValue)
    {
        Object temp = null;
        if(_settings.containsKey(settingKey))
            temp = _settings.get(settingKey);
        _settings.put(settingKey, settingValue);
        return temp;
    }

    public Object get(String settingKey)
    {
        if(_settings.containsKey(settingKey))
            return _settings.get(settingKey);
        return null;
    }

    public Integer getInteger(String settingKey) throws SettingValueTypeMisMatch
    {
        Object myInteger = _settings.get(settingKey);
        if(!(myInteger instanceof Integer))
            throw new SettingValueTypeMisMatch();
        else
            return (Integer)myInteger;
    }

    public Float getFloat(String settingKey) throws SettingValueTypeMisMatch
    {
        Object myFloat = _settings.get(settingKey);
        if(!(myFloat instanceof String))
            throw new SettingValueTypeMisMatch();
        else
            return (Float)myFloat;
    }

    public String getString(String settingKey) throws SettingValueTypeMisMatch
    {
        Object myString = _settings.get(settingKey);
        if(!(myString instanceof String))
            throw new SettingValueTypeMisMatch();
        else
            return (String)myString;
    }

    public Boolean getBoolean(String settingKey) throws SettingValueTypeMisMatch
    {
        Object myBool = _settings.get(settingKey);
        if(!(myBool instanceof Boolean))
            throw new SettingValueTypeMisMatch();
        else
            return (Boolean)myBool;
    }
}
