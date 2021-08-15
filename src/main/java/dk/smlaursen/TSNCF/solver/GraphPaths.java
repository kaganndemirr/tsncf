package dk.smlaursen.TSNCF.solver;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.EppsteinKShortestPath;
import org.jgrapht.graph.GraphWalk;
import dk.smlaursen.TSNCF.application.Application;
import dk.smlaursen.TSNCF.application.TTApplication;
import dk.smlaursen.TSNCF.architecture.GCL;
import dk.smlaursen.TSNCF.architecture.GCLEdge;
import dk.smlaursen.TSNCF.architecture.Node;

public class GraphPaths {
	
	//For storing the AVB- and TT-Applications 
	private final List<Unicast> ttRoutes;
	private final List<UnicastCandidates> avbRoutes;

	public GraphPaths(final Graph<Node, GCLEdge> topology,final List<Application> applications, int MAX_HOPS, int K){
		///////////////////////////////////////////////////////
		//-- First we retrieve all individual graphPaths  -- //
		///////////////////////////////////////////////////////

		avbRoutes = new ArrayList<>();
		ttRoutes  = new ArrayList<>();
		// Loop through each application and add it's K shortest paths to above arraylist
		for(Application app : applications){
			//If TT-Application (explicitlyRouted) parse to VLAN and continue
			if(app instanceof TTApplication){
				ttRoutes.addAll(convertTTApplicationToRouting((TTApplication) app, topology));
				continue;
			}

			//Else SR-Application
			EppsteinKShortestPath<Node, GCLEdge> shortestPaths = new EppsteinKShortestPath<>(topology);

			//For each destinations
			int noOfDests = app.getDestinations().length;
			for(int d = 0; d < noOfDests; d++){
				//Up to K paths to the destination exists
				ArrayList<GraphPath<Node,GCLEdge>> appPaths = new ArrayList<>(K);
				//Retrieve the K shortest paths to the destination
				List<GraphPath<Node, GCLEdge>> sp = shortestPaths.getPaths(app.getSource(), app.getDestinations()[d], K);
				//Abort If no such exists as the problem cannot be solved
				if(sp == null){
					throw new InputMismatchException("Aborting, could not find a path from "+app.getSource()+" to "+app.getDestinations()[d]+" within "+MAX_HOPS+" hops");
				} else {
					appPaths.addAll(sp);
					//Remove excess reserved space
					appPaths.trimToSize();
					//Add paths to global VLAN list
					new UnicastCandidates(app, app.getDestinations()[d], appPaths).get_cost();
					avbRoutes.add(new UnicastCandidates(app, app.getDestinations()[d], appPaths));
				}
			}
		}
	}
	
	public List<UnicastCandidates> getAVBRoutingCandidates(){
		return avbRoutes;
	}
	
	public List<Unicast> getTTRoutes(){
		return ttRoutes;
	}
	
	/**Method which converts the routing of a {@link TTApplication} to a {@link Unicast}*/
	private List<Unicast> convertTTApplicationToRouting(TTApplication ttApp, Graph<Node, GCLEdge> graph){
		ArrayList<Unicast> aRouting = new ArrayList<>(ttApp.getDestinations().length);
		try{
			for(int i=0; i<ttApp.getDestinations().length; i++){
				List<GCLEdge> edgeList = new ArrayList<>();
				
				Node prev = ttApp.getSource();
				for(Node curr : ttApp.getExplicitPath().getPath().get(i)){
					edgeList.add(graph.getEdge(prev, curr));
					prev = curr;
				}
				edgeList.add(graph.getEdge(prev, ttApp.getDestinations()[i]));
				
				for(int u = 0; u < edgeList.size(); u++){
					List<GCL> gcls = new LinkedList<>();
					//Add offset (Here we just use the duration of the transmission as offset per hop)
					for(GCL g : ttApp.getExplicitPath().getGCL()){
						gcls.add(new GCL(g.getOffset()+g.getDuration()*u, g.getDuration(), g.getFrequency()));
					}
					//Put the GCL on all the GCLEdges in the edgeList
					edgeList.get(u).addGCL(gcls);
				}
				GraphPath<Node, GCLEdge> gp = new GraphWalk<>(graph, ttApp.getSource(), ttApp.getDestinations()[i], edgeList, 1.0);
				aRouting.add(new Unicast(ttApp,ttApp.getDestinations()[i], gp));
			}
			
			return aRouting;
		} catch(IllegalArgumentException e){
			throw new IllegalArgumentException("The specified vertice-Route for "+ttApp.getTitle()+" do not form a path.");
		}
	}
}
