package dk.smlaursen.TSNCF.architecture;

public class GCL {
	private final int afrequency;
	private final double aOffset;
	private final double aDuration;
	
	public GCL(double offset, double duration, int frequency){
		aOffset = offset;
		aDuration = duration;
		afrequency = frequency;
	}
	public double getOffset(){
		return aOffset;
	}
	
	public double getDuration(){
		return aDuration;
	}
	
	public int getFrequency(){
		return afrequency;
	}
	
	public String toString(){
		return "<" + aOffset + "," + aDuration + "," + afrequency + ">";
	}
}
