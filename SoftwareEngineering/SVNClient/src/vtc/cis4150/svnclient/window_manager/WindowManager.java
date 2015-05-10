package vtc.cis4150.svnclient.window_manager;


import net.miginfocom.swing.MigLayout;
import vtc.cis4150.svnclient.SVNClient;
import vtc.cis4150.svnclient.exceptions.UnableToSaveSettings;
import vtc.cis4150.svnclient.window_manager.connection_display.ConnectionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User: David Fisher Evans
 * Date: 11/14/13
 */
public class WindowManager extends WindowAdapter
{
    private JFrame _mainWindow;
    private JDialog _popup;
    private SVNMenuBar _menuBar;
    private ActionPanel _actionPanel;
    private ConnectionPanel _connectionPanel;

    private static final String ACTION_HEIGHT = "40px";

    private static final String ACTION_MIG = "width max(400, 100%),height " + ACTION_HEIGHT;
    private static final String CONNECTION_MIG = "width max(400, 100%),height 100%-" + ACTION_HEIGHT;

    public void init()
    {
        if(_mainWindow != null)
            _mainWindow.dispose();

        createMainWindow();
    }

    private void createMainWindow()
    {
        _mainWindow = new JFrame("SVNClient");
        _mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _mainWindow.setSize(800, 600);
        _mainWindow.addWindowListener(this);

        _menuBar = new SVNMenuBar(); // SET MENU BAR
        _mainWindow.setJMenuBar(_menuBar);
        _mainWindow.setLayout(new MigLayout("gap 0 0,flowy"));
        _actionPanel = new ActionPanel(); // SET ACTION PANEL
        _mainWindow.add(_actionPanel, ACTION_MIG);

        try
        {
            setConnectionPanel(SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel()); // SET BLANK CONNECTION PANEL
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        _mainWindow.setVisible(true);

        WindowManager.centerWindow(_mainWindow);
    }

    private void createMenuBar()
    {
    }

    public void setPopupWindow(JPanel popupWindow, String title)
    {
        if(_popup != null)
            _popup.dispose();

        _popup = new JDialog(_mainWindow, title);
        _popup.add(popupWindow);
        _popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _popup.pack();
        _popup.setVisible(true);

        WindowManager.centerDialog(_popup);
    }

    public void disposePopupWindow()
    {
        if(_popup != null)
            _popup.dispose();
    }

    public void setConnectionPanel(ConnectionPanel connectionPanel)
    {
        if(_connectionPanel != null)
            _mainWindow.remove(_connectionPanel);

        _connectionPanel = connectionPanel;
        _mainWindow.add(_connectionPanel, CONNECTION_MIG);
        _mainWindow.revalidate();
        _mainWindow.repaint();
    }

    public void setConnectionPanel()
    {
        setConnectionPanel(SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel());
    }

    public JFrame getMainWindow()
    {
        return _mainWindow;
    }

    public JMenuBar getMenuBar()
    {
        return _menuBar;
    }

    public ActionPanel getActionPanel()
    {
        return _actionPanel;
    }

    public ConnectionPanel getConnectionPanel()
    {
        return _connectionPanel;
    }

    public static void centerWindow(JFrame frame)
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    public static void centerDialog(JDialog dialog)
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dim.width / 2 - dialog.getSize().width / 2, dim.height / 2 - dialog.getSize().height / 2);
    }

    public void confirmExit()
    {

        int dialogResult = JOptionPane.showConfirmDialog (null, "Are yous ure you want to exit the program?","Confirm Exit",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION)
        {
            try
            {
                SVNClient.getLocalSettings().save();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        try
        {
            SVNClient.getLocalSettings().save();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        super.windowClosing(e);
    }
}
