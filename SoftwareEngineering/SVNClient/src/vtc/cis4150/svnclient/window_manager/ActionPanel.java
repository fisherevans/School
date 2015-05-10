package vtc.cis4150.svnclient.window_manager;

import net.miginfocom.swing.MigLayout;
import org.tmatesoft.svn.core.SVNException;
import vtc.cis4150.svnclient.SVNClient;
import vtc.cis4150.svnclient.connection_manager.ConnectionManager;
import vtc.cis4150.svnclient.connection_manager.WorkingCopy;
import vtc.cis4150.svnclient.connection_manager.WorkingCopyCompare;
import vtc.cis4150.svnclient.window_manager.popups.AddConnection;
import vtc.cis4150.svnclient.window_manager.popups.EditConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * User: David Fisher Evans
 * Date: 11/14/13
 */
public class ActionPanel extends JPanel implements ActionListener
{
    private JToolBar _toolbar;
    private JComboBox _connectionDropDown;

    private final String[] NO_CONNECTIONS_ARRAY = new String[] {"No Saved Connections..."};

    private ButtonType _buttonType;

    public enum ButtonType { Image, Text, Both };

    public ActionPanel()
    {
        super(new MigLayout());

        _buttonType = ButtonType.Image;

        init();
    }

    public void init()
    {
        for(Component comp:this.getComponents())
            this.remove(comp);

        setupConnectionDropDown();
        setupToolbar();
    }

    public ButtonType getButtonType()
    {
        return _buttonType;
    }

    public void setButtonType(ButtonType buttonType)
    {
        _buttonType = buttonType;
    }

    private void setupConnectionDropDown()
    {
        _connectionDropDown = new JComboBox<>();

        List<WorkingCopy> connectionList = SVNClient.getConnectionManager().getConnectionsSortedList();
        if(connectionList != null && connectionList.size() > 0)
        {
            String[] connectionListNames = new String[connectionList.size()];
            int selectedId = 0;
            for(int id = 0;id < connectionList.size();id++)
            {
                connectionListNames[id] = connectionList.get(id).toDescription();
                if(connectionList.get(id) == SVNClient.getConnectionManager().getCurrentConnection())
                    selectedId = id;
            }

            _connectionDropDown = new JComboBox(connectionListNames);
            _connectionDropDown.setSelectedIndex(selectedId);
        }
        else
            _connectionDropDown = new JComboBox(NO_CONNECTIONS_ARRAY);

        _connectionDropDown.addActionListener(this);
        this.add(_connectionDropDown);
    }

    private void setupToolbar()
    {
        ToolTipManager.sharedInstance().setDismissDelay(500);

        _toolbar = new JToolBar();
        _toolbar.setFloatable(false);

        _toolbar.add(getButton("connection_add", _buttonType, "Add Connection", "res/green_plus.png", "Add a new connection to your connection list"));
        _toolbar.add(getButton("connection_test", _buttonType, "Test Connection", "res/blue_check.png", "Test to this connection"));
        _toolbar.add(getButton("connection_refresh", _buttonType, "Refresh Connection View", "res/yellow_recycle.png", "Refreshes the Connection View"));
        _toolbar.add(getButton("connection_edit", _buttonType, "Edit Connection", "res/grey_wrench.png", "Edit this connection's settings"));
        _toolbar.add(getButton("connection_del", _buttonType, "Delete Connection", "res/red_x.png", "Removes the current connection from your connection list"));
        _toolbar.addSeparator();
        _toolbar.add(getButton("all_commit", _buttonType, "Commit All", "res/all_commit.png", "Commits all local changes remotely"));
        _toolbar.add(getButton("all_add", _buttonType, "Add All", "res/all_add.png", "Add all new local files"));
        _toolbar.add(getButton("all_delete_remote", _buttonType, "Delete All Remotely", "res/all_delete_remote.png", "Deletes files on remote server"));
        _toolbar.addSeparator();
        _toolbar.add(getButton("all_pull", _buttonType, "Update All", "res/all_pull.png", "Updates all remote changes locally"));
        _toolbar.add(getButton("all_delete_local", _buttonType, "Delete All Locally", "res/all_delete_local.png", "Deletes files locally"));
        //_toolbar.add(getButton("all_sub", _buttonType, "Remove All", "res/all_sub.png", "Remove all old Local files"));

        this.add(_toolbar);
    }

    private JButton getButton(String actionCommand, ButtonType buttonType, String buttonText, String imageLocation, String toolTip)
    {
        ImageIcon icon = new ImageIcon(SVNClient.getFileIOManager().getImage(imageLocation));

        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        if(buttonType == ButtonType.Image || buttonType == ButtonType.Both)
        {
            try
            {
                button.setIcon(icon);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(buttonType == ButtonType.Text || buttonType == ButtonType.Both)
            button.setText(buttonText);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(_toolbar.getComponentIndex((Component) e.getSource()) >= 0) // Toolbar action
        {
            System.out.println("Toolbar Button Pressed: " + e.getActionCommand());
            WorkingCopy workingCopy = SVNClient.getConnectionManager().getCurrentConnection();
            switch(e.getActionCommand())
            {
                case "connection_add":
                    SVNClient.getWindowManager().setPopupWindow(new AddConnection(), "Add New Connection");
                    break;
                case "connection_edit":
                    if(workingCopy != null)
                        SVNClient.getWindowManager().setPopupWindow(new EditConnection(workingCopy), "Edit Connection");
                    break;
                case "connection_del":
                    if(workingCopy == null)
                        break;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to remove the connection: " + workingCopy.toDescription()
                            ,"Remove Connection Confirmation",JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION)
                    {
                        SVNClient.getConnectionManager().getConnections().remove(workingCopy);
                        SVNClient.getWindowManager().init();
                    }
                    break;
                case "connection_refresh":
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "all_pull":
                    try
                    {
                        SVNClient.getConnectionManager().getCurrentConnection().updateAll(SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().getExplorer().getRoot());
                    }
                    catch (SVNException e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "all_commit":
                    try
                    {
                        String message = "";
                        while(message != null && message.replaceAll(" ","").equals(""))
                        {
                            message = JOptionPane.showInputDialog(SVNClient.getWindowManager().getMainWindow(), "Commit message:", "Commit All Changes", JOptionPane.PLAIN_MESSAGE);
                        }
                        if(message == null)
                            return;
                        SVNClient.getConnectionManager().getCurrentConnection().commitAll(SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().getExplorer().getRoot(), message);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "all_add":
                    try
                    {
                        String message = "";
                        while(message != null && message.replaceAll(" ","").equals(""))
                        {
                            message = JOptionPane.showInputDialog(SVNClient.getWindowManager().getMainWindow(), "Commit message:", "Commit All Changes", JOptionPane.PLAIN_MESSAGE);
                        }
                        if(message == null)
                            return;
                        WorkingCopyCompare.Entry root = SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().getExplorer().getRoot();
                        SVNClient.getConnectionManager().getCurrentConnection().addAll(root, message);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "all_sub":
                    try
                    {

                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "all_delete_remote":
                    try
                    {
                        String message = "";
                        while(message != null && message.replaceAll(" ","").equals(""))
                        {
                            message = JOptionPane.showInputDialog(SVNClient.getWindowManager().getMainWindow(), "Commit message:", "Delete fIles Remotely", JOptionPane.PLAIN_MESSAGE);
                        }
                        if(message == null)
                            return;
                        SVNClient.getConnectionManager().getCurrentConnection().deleteAllRemote(SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().getExplorer().getRoot(), message);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "all_delete_local":
                    try
                    {
                        SVNClient.getConnectionManager().getCurrentConnection().deleteAllLocal(SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().getExplorer().getRoot());
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "connection_test":
                    if(workingCopy == null)
                        break;
                    boolean connectionTest = workingCopy.testConnection();
                    String message = connectionTest ?
                            "Connection test passed!" :
                            "Connection test failed...";

                    JOptionPane.showConfirmDialog (SVNClient.getWindowManager().getMainWindow(), message,"Connection Test Results", JOptionPane.PLAIN_MESSAGE);
                    break;
                default:
                    // Do nothing
                    break;
            }
            System.out.println("Done action");
        }
        else if(e.getSource() == _connectionDropDown) // New Connection
        {
            System.out.println("New Connection Selected: " + _connectionDropDown.getSelectedItem().toString());
            ConnectionManager cm = SVNClient.getConnectionManager();
            if(cm.getConnections().size() > 0)
            {
                int connectionId = _connectionDropDown.getSelectedIndex();
                WorkingCopy selectedWorkingCopy = cm.getConnectionsSortedList().get(connectionId);
                cm.setCurrentConnection(selectedWorkingCopy);
                SVNClient.getWindowManager().setConnectionPanel();
            }
        }
    }
}
