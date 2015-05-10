package vtc.cis4150.svnclient.window_manager.connection_display;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * User: David Fisher Evans
 * Date: 11/14/13
 */
public class FileViewerPanel extends JPanel
{
    private JTextArea _textArea;
    private JLabel _jLabel;
    private JScrollPane _scrollPane;
    private ConnectionPanel _connectionPanel;

    public FileViewerPanel(ConnectionPanel connectionPanel)
    {
        super(new MigLayout());

        _connectionPanel = connectionPanel;

        UIManager.put("TextArea.margin", new Insets(2,10,2,10));
        _textArea = new JTextArea("");
        _textArea.setEditable(false);
        _textArea.setWrapStyleWord(false);
        _textArea.setLineWrap(false);
        _textArea.setTabSize(4);
        _textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        _jLabel = new JLabel(" ");
        _jLabel.setHorizontalAlignment(JLabel.LEFT);

        _scrollPane = new JScrollPane(_textArea);

        this.add(_scrollPane, "width 100%, height 100%");
        this.add(_jLabel, "dock south");
    }

    public JTextArea getTextArea()
    {
        return _textArea;
    }

    public JLabel getjLabel()
    {
        return _jLabel;
    }

    public JScrollPane getScrollPane()
    {
        return _scrollPane;
    }
}
