package vtc.cis4150.svnclient.window_manager.connection_display;

import net.miginfocom.swing.MigLayout;
import org.tmatesoft.svn.core.io.SVNFileRevision;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * User: David Fisher Evans
 * Date: 11/14/13
 */
public class RevisionHistoryPanel extends JPanel
{
    private ConnectionPanel _connectionPanel;
    private JScrollPane _scroll;
    private JPanel _diffPane;
    private final Font lineFont = new Font("Monospaced", Font.PLAIN, 12);

    public RevisionHistoryPanel(ConnectionPanel connectionPanel)
    {
        super(new MigLayout());
        _connectionPanel = connectionPanel;

        _diffPane = new JPanel(new MigLayout("wrap 1"));
        _diffPane.setBackground(Color.white);

        _scroll = new JScrollPane(_diffPane);

        this.add(_scroll, "width 100%, height 100%");
    }

    public JLabel getLine(String line)
    {
        JLabel label = new JLabel(line);
        label.setFont(lineFont);
        Color c = Color.gray;

        if(line.startsWith("-"))
            c = new Color(140, 0, 0);
        else if(line.startsWith("+"))
            c = new Color(0, 140, 0);
        else if(line.startsWith(" "))
            c = new Color(40, 40, 40);

        label.setForeground(c);
        return label;
    }

    public void updateRevs(String text)
    {
        _diffPane.removeAll();
        String[] lines = text.split("\n");
        for(String line:lines)
        {
            _diffPane.add(getLine(line));
        }
        this.revalidate();
        this.repaint();
    }

    public void clearRevs()
    {
        _diffPane.removeAll();
        this.revalidate();
        this.repaint();
    }
}
