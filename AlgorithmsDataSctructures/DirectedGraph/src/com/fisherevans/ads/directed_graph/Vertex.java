package com.fisherevans.ads.directed_graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 12/4/13
 * Time: 5:57 PM
 * Represents a vertex in an acyclic directed graph
 */
public class Vertex
{
    private int _value;
    private Set<Vertex> _connections;

    /**
     * creates the vertex with a given value to represent it
     * @param value the value
     */
    public Vertex(int value)
    {
        _value = value;
        _connections = new HashSet<Vertex>();
    }

    /**
     * @return the value that represents this vertex
     */
    public int getValue()
    {
        return _value;
    }

    /**
     * create a connection going from this vertex to the passed vertex
     * @param vertex the vertex to make a connection to
     */
    public void addConnection(Vertex vertex)
    {
        _connections.add(vertex);
    }

    /**
     * remove a connection from this vertex to the passed vertex
     * @param vertex the vertex the connection to remove goes to
     */
    public void removeConnections(Vertex vertex)
    {
        _connections.remove(vertex);
    }

    /**
     * checks if this vertex directly connects to another vertex
     * @param vertex vertex to check for a connection
     * @return true if this vertex is directly connected to the given vertex, false if not
     */
    public boolean isConnected(Vertex vertex)
    {
        return _connections.contains(vertex);
    }

    /**
     * returns all vertices this vertex can connect to
     * @return the connected vertices
     */
    public Set<Vertex> getConnections()
    {
        Set<Vertex> clone = new HashSet<Vertex>(_connections.size());
        for(Vertex vertex:_connections)
            clone.add(vertex);
        return clone;
    }

    @Override
    public int hashCode()
    {
        return _value*31;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Vertex)
            return getValue() == ((Vertex) obj).getValue();
        else
            return false;
    }

    @Override
    public String toString()
    {
        String result = "";
        for(Vertex vertex:_connections)
            result += String.format("%4d -> %-4d\n", getValue(), vertex.getValue());
        return result;
    }
}
