package vtc.cis4150.svnclient.window_manager;

import vtc.cis4150.svnclient.SVNClient;
import vtc.cis4150.svnclient.connection_manager.WorkingCopy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 11/25/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SVNMenuBar extends JMenuBar implements ActionListener
{
    private JMenu _fileMenu;
    private JMenuItem _exitItem;

    private JMenu _settingsMenu;
    private JCheckBoxMenuItem _buttonToggle;

    public SVNMenuBar()
    {
        _fileMenu = new JMenu("File");

        _exitItem = new JMenuItem("Exit");
        _exitItem.addActionListener(this);
        _fileMenu.add(_exitItem);

        this.add(_fileMenu);

        _settingsMenu = new JMenu("Settings");

        _buttonToggle = new JCheckBoxMenuItem("Display Button Text");
        _buttonToggle.setState(false);
        _buttonToggle.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ActionPanel.ButtonType type = ActionPanel.ButtonType.Image;
                if(_buttonToggle.getState())
                    type = ActionPanel.ButtonType.Both;

                SVNClient.getWindowManager().getActionPanel().setButtonType(type);

                SVNClient.getWindowManager().getActionPanel().init();

                for(WorkingCopy copy:SVNClient.getConnectionManager().getConnections())
                {
                    try
                    {
                        copy.getConnectionPanel().getExplorer().getExplorerActions().init();
                    }
                    catch(Exception e1)
                    {

                    }
                }

                SVNClient.getWindowManager().getMainWindow().revalidate();
                SVNClient.getWindowManager().getMainWindow().repaint();
            }
        });
        _settingsMenu.add(_buttonToggle);

        this.add(_settingsMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if(_exitItem == source)
        {
            SVNClient.getWindowManager().confirmExit();
        }
    }
}
