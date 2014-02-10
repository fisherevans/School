package com.fisherevans.ads.directed_graph;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 12/4/13
 * Time: 4:43 PM
 * Will load a DirectedGraph from file, fine a topological ordering and print it out.
 */
public class Launcher
{
    public static void main(String[] args)
    {
        args = new String[] { "res/smalltest.txt" };
        //args = new String[] { "res/largetest.txt" };

        if(args.length == 0)
            System.out.println("This program will read a file describing a list of edges in an acyclic directed graph, then compute a topological ordering.\n" +
                    "Please pass a relative or absolute file path to a text file containing a list of edges.");
        else
        {
            DirectedGraph graph = null;
            try
            {
                graph = new DirectedGraph(args[0]);
                System.out.println("Generated Graph:");
                System.out.println(graph);

                System.out.println("\nGenerated Topology:");
                DirectedGraph.printTopology(graph.findTopology());
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Failed to load edges from file passed. (Missing? Permissions? Improper formatting?)");
                System.exit(1);
            }
        }
    }
}
