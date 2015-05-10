package vtc.cis4150.svnclient.io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

/**
 * User: David Fisher Evans
 * Date: 11/14/13
 */
public class FileIOManager
{
    private String _root;

    public void setRoot(String root)
    {
        _root = root;
    }

    public String getRoot()
    {
        return _root;
    }

    public File getFile(String filename)
    {
        return new File(filename);
    }

    public BufferedReader getReader(File file)
    {
        return null;
    }

    public BufferedWriter getWriter(File file)
    {
        return null;
    }

    public Image getImage(String filename)
    {
        Image image = null;

        try
        {
            image = ImageIO.read(getFile(filename));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return image;
    }
}
