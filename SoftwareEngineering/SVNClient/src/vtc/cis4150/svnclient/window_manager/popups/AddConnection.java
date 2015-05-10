package vtc.cis4150.svnclient.window_manager.popups;

import net.miginfocom.swing.MigLayout;
import vtc.cis4150.svnclient.SVNClient;
import vtc.cis4150.svnclient.connection_manager.WorkingCopy;
import vtc.cis4150.svnclient.window_manager.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 11/30/13
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddConnection extends JPanel implements ActionListener
{
    private JLabel _nameLabel, _usernameLabel, _passwordLabel, _urlLabel, _localLabel, _error;
    private JTextField _name, _username, _url;
    private JPasswordField _password;
    private JButton _localButton, _create, _cancel, _test;
    private JFileChooser _local;

    private final int COLS = 20;

    public AddConnection()
    {
        super(new MigLayout("wrap 2,gap 10"));

        createForm();
    }

    public void createForm()
    {
        _nameLabel = new JLabel("Connection Name:");
        _usernameLabel = new JLabel("Username:");
        _passwordLabel = new JLabel("Password:");
        _urlLabel = new JLabel("SVN URL:");
        _localLabel = new JLabel("Local Root:");

        _name = new JTextField(COLS);
        _username = new JTextField(COLS);
        _password = new JPasswordField(COLS);
        _url = new JTextField(COLS);

        _local = new JFileChooser();
        _local.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        _local.addActionListener(this);

        _error = new JLabel(" ");
        _error.setForeground(Color.red);

        _localButton = new JButton("Select Root...");
        _localButton.addActionListener(this);

        _create = new JButton("Add Connection");
        _create.addActionListener(this);
        _cancel = new JButton("Cancel");
        _cancel.addActionListener(this);
        _test = new JButton("Test");
        _test.addActionListener(this);

        this.add(_nameLabel, "align right");
        this.add(_name);
        this.add(_usernameLabel, "align right");
        this.add(_username);
        this.add(_passwordLabel, "align right");
        this.add(_password);
        this.add(_urlLabel, "align right");
        this.add(_url);
        this.add(_localLabel, "align right");
        this.add(_localButton, "width 100%");
        this.add(_error, "span 2,align center");
        this.add(_cancel, "span 2, split 3, width 33%");
        this.add(_test, "width 33%");
        this.add(_create, "width 33%");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        _error.setText(" ");
        _error.setForeground(Color.red);
        if(e.getSource() == _localButton)
        {
            _local.showDialog(this, "Select Local Root");
        }
        else if(e.getSource() == _local)
        {
            File sel = _local.getSelectedFile();
            if(sel != null)
                _localButton.setText(".../" + sel.getName());
        }
        else if(e.getSource() == _cancel)
        {
            SVNClient.getWindowManager().disposePopupWindow();
        }
        else if(e.getSource() == _create)
        {
            WorkingCopy workingCopy = getWorkingCopy();
            if(workingCopy == null)
                _error.setText("Invalid input");
            else
            {
                if(!workingCopy.testConnection())
                {
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Connection Test failed, add anyway?","Connection Test Failed",JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.NO_OPTION)
                        return;
                }
                SVNClient.getConnectionManager().addConnection(workingCopy);
                SVNClient.getConnectionManager().setCurrentConnection(workingCopy);
                try
                {
                    workingCopy.checkout();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    JOptionPane.showConfirmDialog (SVNClient.getWindowManager().getMainWindow(), "Checking out the repository failed.","Checkout Failed", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                SVNClient.getWindowManager().disposePopupWindow();
                SVNClient.getWindowManager().init();
            }
        }
        else if(e.getSource() == _test)
        {
            WorkingCopy workingCopy = getWorkingCopy();
            if(workingCopy == null)
                _error.setText("Invalid input");
            else
            {
                if(workingCopy.testConnection())
                {
                    _error.setForeground(Color.green);
                    _error.setText("Connection Test passed!");
                }
                else
                {
                    _error.setText("Connection Test failed...");
                }
            }
        }
    }

    private WorkingCopy getWorkingCopy()
    {
        try
        {
            WorkingCopy workingCopy = new WorkingCopy(_name.getText(), _username.getText(),
                    _password.getText(),
                    _url.getText(),
                    _local.getSelectedFile().getAbsolutePath());
            return workingCopy;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
