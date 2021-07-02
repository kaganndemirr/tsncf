package main.java.dk.smlaursen.TSNCF.solver;

import java.util.List;

import main.java.dk.smlaursen.TSNCF.evaluator.Cost;

/** Return type wrapper */
public class Solution {
	private final Cost aCost;
	private final List<Multicast> aRouting;

	public Solution(Cost c, List<Multicast> m){
		aCost = c;
		aRouting = m;
	}
	
	public List<Multicast> getRouting(){
		return aRouting;
	}
	
	public Cost getCost(){
		return aCost;
	}
}
