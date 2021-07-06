package main.java.dk.smlaursen.TSNCF.evaluator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import main.java.dk.smlaursen.TSNCF.solver.Multicast;

public class ModifiedAVBEvaluatorCost implements Cost, Comparator<ModifiedAVBEvaluatorCost> {
	private double obj1;
	private double obj2;
	private double obj3;
	private boolean isUsed;
	private final Map<Multicast, Double> aWCDMap = new HashMap<>();
	
	public ModifiedAVBEvaluatorCost() {
		reset();
	}
	
	public void setWCD(Multicast m, Double wcd){
		aWCDMap.put(m, wcd);
	}
	
	public double getWCD(Multicast m){
		return aWCDMap.get(m);
	}
	
	public void add(Objective e, double value){
		isUsed = true;
		switch (e) {
			case one -> obj1 += value;
			case two -> obj2 += value;
			case three -> obj3 += value;
		}
	}
	
	@Override
	public double getTotalCost() {
		if(!isUsed){
			return Double.MAX_VALUE;
		}
		double w1 = 10000;
		double w2 = 3.0;
		double w3 = 1.0;
		return w1 * obj1 + w2 * obj2 + w3 * obj3;
	}

	@Override
	public void reset() {
		isUsed = false;
		obj1 = 0.0;
		obj2 = 0.0;
		obj3 = 0.0;
	}

	@Override
	public int compare(ModifiedAVBEvaluatorCost o1, ModifiedAVBEvaluatorCost o2) {
		return (int) Math.round(o1.getTotalCost() - o2.getTotalCost());
	}

	public enum Objective{
		one, two, three
	}
	
	public String toString(){
		return getTotalCost()+" (unschedulable = "+obj1+")";
	}
	
	public String toDetailedString(){
		return "Total : "+ this +" | o1 "+obj1+", o2 "+obj2+", o3 "+obj3+" -- "+aWCDMap+" --";
	}
}

