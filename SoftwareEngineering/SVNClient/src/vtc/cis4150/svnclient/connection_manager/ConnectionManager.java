package vtc.cis4150.svnclient.connection_manager;

import vtc.cis4150.svnclient.SVNClient;
import vtc.cis4150.svnclient.exceptions.SettingValueTypeMisMatch;

import java.util.*;

/**
 * User: David Fisher Evans
 * Date: 10/29/13
 */
public class ConnectionManager
{
    private Set<WorkingCopy> _connections;
    private WorkingCopy _currentConnection;

    public void init()
    {
        _connections = new HashSet<WorkingCopy>();
        _currentConnection = null;

        try
        {
            String connectionsString = SVNClient.getLocalSettings().getString("connections");
            if(connectionsString != null)
            {
                String[] connections = connectionsString.split(";");
                for(int id = 0;id < connections.length;id++)
                {
                    try
                    {
                        WorkingCopy workingCopy = new WorkingCopy(connections[id]);
                        _connections.add(workingCopy);
                    } catch (Exception e)
                    {
                        System.out.println("Failed to load connection: " + connections[id]);
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (SettingValueTypeMisMatch settingValueTypeMisMatch)
        {
            System.out.println("No connections saved");
        }

        if(_connections.size() > 0)
            _currentConnection = getConnectionsSortedList().get(0);
    }

    public WorkingCopy getCurrentConnection()
    {
        return _currentConnection;
    }

    public void setCurrentConnection(WorkingCopy currentConnection)
    {
        if(!_connections.contains(currentConnection))
            _connections.add(currentConnection);

        _currentConnection = currentConnection;
    }

    public Set<WorkingCopy> getConnections()
    {
        return _connections;
    }

    public List<WorkingCopy> getConnectionsSortedList()
    {
        List<WorkingCopy> list = new ArrayList<WorkingCopy>(_connections);
        Collections.sort(list);
        return list;
    }

    public void addConnection(WorkingCopy workingCopy)
    {
        _connections.add(workingCopy);
    }

    public String toString()
    {
        if(_connections == null)
            return "";

        List<WorkingCopy> connectionsList = getConnectionsSortedList();

        String string = "";
        for(int id = 0;id < connectionsList.size();id++)
        {
            if(id > 0)
                string += ";";
            string += connectionsList.get(id).toString();
        }
        return string;
    }
}
