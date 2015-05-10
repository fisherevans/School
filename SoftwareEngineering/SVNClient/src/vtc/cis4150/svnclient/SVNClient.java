package vtc.cis4150.svnclient;

import vtc.cis4150.svnclient.connection_manager.ConnectionManager;
import vtc.cis4150.svnclient.exceptions.UnableToLoadSettings;
import vtc.cis4150.svnclient.io.FileIOManager;
import vtc.cis4150.svnclient.local_settings.LocalSettings;
import vtc.cis4150.svnclient.window_manager.WindowManager;

/**
 * User: David Fisher Evans
 * Date: 10/29/13
 */
public class SVNClient
{
    private static ConnectionManager _connectionManager;
    private static LocalSettings _localSettings;
    private static WindowManager _windowManager;
    private static FileIOManager _fileIOManager;

    public SVNClient(String[] args)
    {
        _connectionManager = new ConnectionManager();
        _localSettings = new LocalSettings();
        _windowManager = new WindowManager();
        _fileIOManager = new FileIOManager();
    }

    public void init()
    {
        try
        {
            _localSettings.load();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            _localSettings.loadDefaultSettings();
        }

        _connectionManager.init();

        _windowManager.init();
    }

    public static ConnectionManager getConnectionManager()
    {
        return _connectionManager;
    }

    public static LocalSettings getLocalSettings()
    {
        return _localSettings;
    }

    public static WindowManager getWindowManager()
    {
        return _windowManager;
    }

    public static FileIOManager getFileIOManager() {
        return _fileIOManager;
    }
}
