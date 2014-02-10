package com.fisherevans.ads.directed_graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 12/4/13
 * Time: 4:43 PM
 * Data structure of an acyclic directed graph defined by Vertexes.
 */
public class DirectedGraph
{
    private Set<Vertex> _vertices;

    /** creates an empty graph **/
    public DirectedGraph()
    {
        init();
    }

    /** creates an empty graph and then loads edges from the given file
     * @param filePath path to file to load
     * @throws FileNotFoundException if the file is not found
     **/
    public DirectedGraph(String filePath) throws FileNotFoundException
    {
        init();
        readFromFile(filePath);
    }

    /** Initialized this structures fields **/
    public void init()
    {
        _vertices = new HashSet<Vertex>();
    }

    /**
     * Loads edges from a file. each line is to be "n m" where n and m are integers referencing to a vertex and n can go to m.
     * @param filePath path to file to load
     * @throws FileNotFoundException
     */
    public void readFromFile(String filePath) throws FileNotFoundException
    {
        File inputFile = new File(filePath);
        Scanner input = new Scanner(inputFile);
        String[] line;
        while(input.hasNext())
        {
            line = input.nextLine().split(" +"); // each line of the file split the line into an array
            try
            {
                addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[1])); // try to parse the line segments into ints and create and edge with it
            }
            catch(Exception e) { } // Ignore invalid lines
        }
        input.close();
    }

    /**
     * returns the vertex that corresponds with a given int
     * @param value the int the vertex your searching for correspods with
     * @return the vertex. null if not found.
     */
    public Vertex getVertex(Integer value)
    {
        Vertex v = null;
        for(Vertex vertex:_vertices)
        {
            if(vertex.getValue() == value)
            {
                v = vertex;
                break;
            }
        }
        return v;
    }

    /**
     * removes the vertex corresponding to this int from this graph
     * @param value the int of the vertex to remove
     */
    public void removeVertex(int value)
    {
        Vertex v = getVertex(value);
        if(v != null)
            _vertices.remove(v);
    }

    /**
     * adds a given int as a vertex to this graph if it does not already exist. if one does already it returns that vertext
     * @param value value of the vertex to add
     * @return the new or existing vertex
     */
    public Vertex addVertex(int value)
    {
        Vertex v = getVertex(value);
        if(v == null)
        {
            v = new Vertex(value);
            _vertices.add(v);
        }
        return v;
    }

    /**
     * will addVertex both from and to to ensure the vertexs exist. then create a connection from vertex From to vertex To
     * @param from the int of the vertext to connect from
     * @param to the int of the vertext to connect to
     */
    public void addEdge(int from, int to)
    {
        Vertex fromV = addVertex(from);
        Vertex toV = addVertex(to);
        fromV.addConnection(toV);
    }

    /**
     * removes an edge going from vertex from to vertex to if it exists
     * @param from the int value of the vertex the connection is coming from
     * @param to the int value of the vertex the connection is going to
     */
    public void removeEdge(int from, int to)
    {
        Vertex fromV = getVertex(from);
        Vertex toV = getVertex(to);
        if(fromV != null && toV != null)
            fromV.removeConnections(toV);
    }

    /**
     * Using a depth first method, finds a topological order of this graph
     * @return a linked list of vertices that make up the topology
     */
    public LinkedList<Vertex> findTopology()
    {
        LinkedList<Vertex> topology = new LinkedList<Vertex>(); // the final topology
        LinkedList<Vertex> visit = new LinkedList<Vertex>(_vertices); // nodes to visit
        visit.addAll(_vertices); // initially add all vertices
        Vertex vertex;
        while(visit.size() > 0) // while there are still nodes to visit
        {
            vertex = visit.remove(visit.size()-1); // get the last added vertex
            if(!(topology.contains(vertex))) // if it's not in the topology (not visited yet)
            {
                topology.add(vertex); // add it to the topology
                for(Vertex toAdd:vertex.getConnections())
                {
                    visit.remove(toAdd);// then add its connections to the end of toVisit array
                    visit.add(toAdd);
                }
            }
        }
        return topology;
    }

    /**
     * prints a pre-generated topology to STDOUT
     * @param topology the topology to print
     */
    public static void printTopology(LinkedList<Vertex> topology)
    {
        int id = 0;
        for(Vertex vertex:topology)
        {
            if(id++ > 0)
                System.out.print(", ");
            System.out.print(vertex.getValue());
        }
        System.out.println();
    }

    @Override
    public String toString()
    {
        String result = "";
        for(Vertex vertex:_vertices)
            result += vertex.toString();
        return result;
    }
}
