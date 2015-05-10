package vtc.cis4150.svnclient.window_manager.connection_display;

import net.miginfocom.swing.MigLayout;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;
import vtc.cis4150.svnclient.SVNClient;
import vtc.cis4150.svnclient.connection_manager.WorkingCopyCompare;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * User: David Fisher Evans
 * Date: 11/14/13
 */
public class ExplorerPanel extends JPanel implements TreeSelectionListener
{
    private JScrollPane _treeView;
    private JTree _tree;
    private WorkingCopyCompare.Entry _root;
    private ConnectionPanel _connectionPanel;
    private JavaTreeNodeRenderer _javaTreeNodeRenderer;
    private ExplorerActions _explorerActions;

    public ExplorerPanel(ConnectionPanel connectionPanel)
    {
        super(new MigLayout("wrap 1"));
        _connectionPanel = connectionPanel;

        try
        {
            _root = new WorkingCopyCompare(connectionPanel.getWorkingCopy()).getLocalTree(_connectionPanel.getWorkingCopy().getLocalRoot(), null, "");
            _javaTreeNodeRenderer = new JavaTreeNodeRenderer();
            DefaultMutableTreeNode root = getRepoTree(_root);
            DefaultTreeModel model = new DefaultTreeModel(root);
            model.setAsksAllowsChildren(true);
            _tree = new JTree(model);
            _tree.setRowHeight(24);
            _tree.setCellRenderer(_javaTreeNodeRenderer);
            _tree.addTreeSelectionListener(this);
            _tree.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 7));
            _treeView = new JScrollPane(_tree);

            this.add(_treeView, "width 100%, height 100%-40px");

            _explorerActions = new ExplorerActions(this);
            this.add(_explorerActions, "width 100%, height 40px");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public DefaultMutableTreeNode getRepoTree(WorkingCopyCompare.Entry topEntry) throws SVNException
    {
        String name = topEntry.getName();
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(name, true);
        top.setUserObject(topEntry);
        try
        {
            for(WorkingCopyCompare.Entry childEntry:topEntry.getChildren())
            {
                if(childEntry.getEntryType() == WorkingCopyCompare.EntryType.Directory)
                {
                    DefaultMutableTreeNode dir = getRepoTree(childEntry);
                    top.add(dir);
                }
                else
                {
                    String fileName = childEntry.getName();
                    DefaultMutableTreeNode file = new DefaultMutableTreeNode(fileName, false);
                    file.setAllowsChildren(false);
                    file.setUserObject(childEntry);
                    top.add(file);
                }
            }
        }
        catch(Exception e)
        {
            // empty folder normally
        }
        return top;
    }

    private String getFileName(String fullPath)
    {
        return fullPath.replaceAll(".*/", "");
    }

    @Override
    public void valueChanged(TreeSelectionEvent e)
    {
        Object selected = e.getNewLeadSelectionPath().getLastPathComponent();
        if(selected instanceof DefaultMutableTreeNode && ((DefaultMutableTreeNode) selected).getUserObject() instanceof WorkingCopyCompare.Entry)
        {
            WorkingCopyCompare.Entry localFile = (WorkingCopyCompare.Entry)((DefaultMutableTreeNode) selected).getUserObject();
            if(localFile != null)
            {
                String fileLocation = SVNClient.getConnectionManager().getCurrentConnection().getLocalRoot() + "/" + localFile.getFullPath();
                String fileContents = getFileContents(fileLocation);
                JTextArea textArea = _connectionPanel.getFileViewer().getTextArea();
                JLabel jLabel = _connectionPanel.getFileViewer().getjLabel();
                if(fileContents != null)
                {
                    textArea.setForeground(Color.black);
                    textArea.setText(fileContents);
                    jLabel.setText("Viewing file: " + localFile.getFullPath());
                }
                else
                {
                    textArea.setForeground(Color.red);
                    textArea.setText(" ");
                    jLabel.setText(" ");
                }
                textArea.setCaretPosition(0);
            }

            RevisionHistoryPanel revP = _connectionPanel.getRevisionHistory();
            try
            {
                if(localFile.getEntryStatus() == WorkingCopyCompare.EntryStatus.Different && localFile.getEntryType() == WorkingCopyCompare.EntryType.File)
                {
                    System.out.println("Print diffs");
                    String diffs = _connectionPanel.getWorkingCopy().getDiff(localFile.getFullPath());
                    revP.updateRevs(diffs);
                }
                else
                {
                    revP.clearRevs();
                }
                //revP.updateRevs(_connectionPanel.getWorkingCopy().getRevisionHistory(localFile));
            }
            catch(Exception e1)
            {
                //e1.printStackTrace();
            }
        }
    }

    public String getFileContents(String file)
    {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String inputFile = "";
            String textFieldReadable = bufferedReader.readLine();

            while (textFieldReadable != null){
                inputFile += textFieldReadable + "\n";
                textFieldReadable = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
            return inputFile;
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println("Cannot read file contents: " + ex.getMessage());
            return null;
        }
    }

    public WorkingCopyCompare.Entry getSelected()
    {
        WorkingCopyCompare.Entry entry = null;
        try
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) _tree.getLastSelectedPathComponent();
            return (WorkingCopyCompare.Entry) node.getUserObject();
        }
        catch (Exception e)
        {
            return entry;
        }
    }

    public WorkingCopyCompare.Entry getRoot()
    {
        return _root;
    }

    public ExplorerActions getExplorerActions()
    {
        return _explorerActions;
    }
}
