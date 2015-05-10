package vtc.cis4150.svnclient.window_manager.connection_display;

import net.miginfocom.swing.MigLayout;
import org.tmatesoft.svn.core.SVNException;
import vtc.cis4150.svnclient.connection_manager.WorkingCopy;

import javax.swing.*;
import java.awt.*;

/**
 * User: David Fisher Evans
 * Date: 11/14/13
 */
public class ConnectionPanel extends JPanel
{
    private ExplorerPanel _explorer;
    private FileViewerPanel _fileViewer;
    private RevisionHistoryPanel _revisionHistory;
    private WorkingCopy _workingCopy;

    public ConnectionPanel(WorkingCopy workingCopy)
    {
        super(new MigLayout());
        //setBackground(new Color(0f, 1f, 0f));
        _workingCopy = workingCopy;
    }

    public void init()
    {
        for(Component comp:this.getComponents())
            this.remove(comp);

        try
        {
            _workingCopy.updateClient();
        } catch (SVNException e)
        {
            e.printStackTrace();
        }

        _explorer = new ExplorerPanel(this);
        _fileViewer = new FileViewerPanel(this);
        _revisionHistory = new RevisionHistoryPanel(this);

        this.add(_explorer, "dock west, width 25%, height 100%");
        this.add(_fileViewer, "wrap, width 75%, height 70%");
        this.add(_revisionHistory, "width 75%, height 30%");
        this.revalidate();
        this.repaint();
    }

    public ExplorerPanel getExplorer()
    {
        return _explorer;
    }

    public void setExplorer(ExplorerPanel explorer)
    {
        _explorer = explorer;
    }

    public FileViewerPanel getFileViewer()
    {
        return _fileViewer;
    }

    public void setFileViewer(FileViewerPanel fileViewer)
    {
        _fileViewer = fileViewer;
    }

    public RevisionHistoryPanel getRevisionHistory()
    {
        return _revisionHistory;
    }

    public void setRevisionHistory(RevisionHistoryPanel revisionHistory)
    {
        _revisionHistory = revisionHistory;
    }

    public WorkingCopy getWorkingCopy()
    {
        return _workingCopy;
    }
}
