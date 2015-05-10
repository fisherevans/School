package vtc.cis4150.svnclient.window_manager.connection_display;

import net.miginfocom.swing.MigLayout;
import org.tmatesoft.svn.core.SVNException;
import vtc.cis4150.svnclient.SVNClient;
import vtc.cis4150.svnclient.connection_manager.WorkingCopyCompare;
import vtc.cis4150.svnclient.window_manager.ActionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 12/3/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExplorerActions extends JPanel implements ActionListener
{
    private ExplorerPanel _parent;
    private JToolBar _toolbar;

    public ExplorerActions(ExplorerPanel parent)
    {
        super(new MigLayout());
        _parent = parent;
        init();
    }

    public void init()
    {
        for(Component comp:this.getComponents())
            this.remove(comp);

        setupToolbar();
    }

    private void setupToolbar()
    {
        ToolTipManager.sharedInstance().setDismissDelay(500);

        _toolbar = new JToolBar();
        _toolbar.setFloatable(false);

        ActionPanel.ButtonType type = SVNClient.getWindowManager().getActionPanel().getButtonType();

        _toolbar.add(getButton("single_commit", type, "Commit Single", "res/single_commit.png", "Commits single local change remotely"));
        _toolbar.add(getButton("single_add", type, "Add Single", "res/single_add.png", "Add single new local file"));
        _toolbar.add(getButton("single_delete_remote", type, "Delete Single Remotely", "res/single_delete_remote.png", "Deletes file on remote server"));
        _toolbar.addSeparator();
        _toolbar.add(getButton("single_pull", type, "Pull Single", "res/single_pull.png", "Pulls single remote change locally"));
        _toolbar.add(getButton("single_delete_local", type, "Delete Single Locally", "res/single_delete_local.png", "Deletes file in working copy"));

        this.add(_toolbar);
    }

    private JButton getButton(String actionCommand, ActionPanel.ButtonType buttonType, String buttonText, String imageLocation, String toolTip)
    {
        ImageIcon icon = new ImageIcon(SVNClient.getFileIOManager().getImage(imageLocation));

        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        if(buttonType == ActionPanel.ButtonType.Image || buttonType == ActionPanel.ButtonType.Both)
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

        if(buttonType == ActionPanel.ButtonType.Text || buttonType == ActionPanel.ButtonType.Both)
            button.setText(buttonText);

        return button;
    }

    private void error(String message)
    {
        JOptionPane.showMessageDialog(SVNClient.getWindowManager().getMainWindow(), message);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        WorkingCopyCompare.Entry entry = _parent.getSelected();
        if(entry == null)
            return;

        try
        {
            switch(e.getActionCommand())
            {
                case "single_pull":
                    try
                    {
                        SVNClient.getConnectionManager().getCurrentConnection().updateSingle(entry);
                    }
                    catch (SVNException e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "single_commit":
                    try
                    {
                        String message = "";
                        while(message != null && message.replaceAll(" ","").equals(""))
                        {
                            message = JOptionPane.showInputDialog(SVNClient.getWindowManager().getMainWindow(), "Commit message:", "Commit All Changes", JOptionPane.PLAIN_MESSAGE);
                        }
                        if(message == null)
                            return;
                        SVNClient.getConnectionManager().getCurrentConnection().commitSingle(entry, message);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "single_add":
                    try
                    {
                        String message = "";
                        while(message != null && message.replaceAll(" ","").equals(""))
                        {
                            message = JOptionPane.showInputDialog(SVNClient.getWindowManager().getMainWindow(), "Commit message:", "Add File", JOptionPane.PLAIN_MESSAGE);
                        }
                        if(message == null)
                            return;
                        SVNClient.getConnectionManager().getCurrentConnection().addSingle(entry, message);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "single_sub":
                    try
                    {

                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "single_delete_remote":
                    try
                    {
                        String message = "";
                        while(message != null && message.replaceAll(" ","").equals(""))
                        {
                            message = JOptionPane.showInputDialog(SVNClient.getWindowManager().getMainWindow(), "Commit message:", "Delete File Remotely", JOptionPane.PLAIN_MESSAGE);
                        }
                        if(message == null)
                            return;
                        SVNClient.getConnectionManager().getCurrentConnection().deleteSingleRemote(entry, message);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
                case "single_delete_local":
                    try
                    {
                        SVNClient.getConnectionManager().getCurrentConnection().deleteSingleLocal(entry);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    SVNClient.getConnectionManager().getCurrentConnection().getConnectionPanel().init();
                    break;
            }
        }
        catch (Exception e1)
        {
            error(e1.getMessage());
            e1.printStackTrace();
        }
        System.out.println("Explorer action done");
    }
}
