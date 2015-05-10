package vtc.cis4150.svnclient.window_manager.connection_display;

import vtc.cis4150.svnclient.connection_manager.WorkingCopyCompare;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 12/2/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class JavaTreeNodeRenderer extends DefaultTreeCellRenderer
{
    private static final Icon FILE_SAME = new ImageIcon("res/file_same.png", "No Changes");
    private static final Icon FILE_REMOTE = new ImageIcon("res/file_remote.png", "File Exists Remotely");
    private static final Icon FILE_LOCAL = new ImageIcon("res/file_local.png", "File Exists Locally");
    private static final Icon FILE_DIFF = new ImageIcon("res/file_diff.png", "Remote Differs From Local");
    private static final Icon FOLDER = new ImageIcon("res/folder.png", "Folder");
    private static final Icon FOLDER_REMOTE = new ImageIcon("res/folder_remote.png", "Folder Exists Remotely");
    private static final Icon FOLDER_LOCAL = new ImageIcon("res/folder_local.png", "Folder Exists Locally");

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded,
                leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;

        if(node.getUserObject() instanceof WorkingCopyCompare.Entry)
        {
            WorkingCopyCompare.Entry userObject = (WorkingCopyCompare.Entry)node.getUserObject();
            setFont(new Font("Sans", Font.BOLD, 12));
            if(userObject.getEntryType() == WorkingCopyCompare.EntryType.Directory)
            {
                switch(userObject.getEntryStatus())
                {
                    case ExistsRemotely:
                        setIcon(FOLDER_REMOTE);
                        break;
                    case ExistsLocally:
                        setIcon(FOLDER_LOCAL);
                        break;
                    default:
                        setIcon(FOLDER);
                        setFont(new Font("Sans", Font.PLAIN, 12));
                        break;
                }
            }
            else
            {
                switch(userObject.getEntryStatus())
                {
                    case ExistsRemotely:
                        setIcon(FILE_REMOTE);
                        break;
                    case ExistsLocally:
                        setIcon(FILE_LOCAL);
                        break;
                    case Different:
                        setIcon(FILE_DIFF);
                        break;
                    default:
                        setIcon(FILE_SAME);
                        setFont(new Font("Sans", Font.PLAIN, 12));
                        break;
                }
            }
        }
        return this;
    }


}
