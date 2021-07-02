package main.java.dk.smlaursen.TSNCF.solver;

import org.jgrapht.GraphPath;

import main.java.dk.smlaursen.TSNCF.application.Application;
import main.java.dk.smlaursen.TSNCF.architecture.GCLEdge;
import main.java.dk.smlaursen.TSNCF.architecture.Node;

/** Class representing the routing of a single destination*/
public class Unicast extends Route {
	//The ArrayList of GraphPaths (One for each destination)
	private GraphPath<Node, GCLEdge> aRoute;
	
	public Unicast(Application app, Node destNode,  GraphPath<Node, GCLEdge> route){
		aApp = app;
		aDestNode = destNode;
		aRoute = route;
	}
	
	public GraphPath<Node, GCLEdge> getRoute(){
		return aRoute;
	}
}

