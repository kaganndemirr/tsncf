package dk.smlaursen.TSNCF.evaluator;

import dk.smlaursen.TSNCF.solver.Multicast;

public interface Cost {

	void reset();
	
	double getTotalCost();
	
	String toDetailedString();
	
	/** Returns the WCD in us (Microseconds)*/
	public double getWCD(Multicast r);
	
}
