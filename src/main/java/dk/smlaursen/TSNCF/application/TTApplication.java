package dk.smlaursen.TSNCF.application;

import java.util.Arrays;
import dk.smlaursen.TSNCF.architecture.EndSystem;

public class TTApplication extends Application{
	private final ExplicitPath explicitRoute;
	
	/** Assumes that all TTApplications are periodic so payloadSize and NoOfFrames is enough */
	public TTApplication(String name, ExplicitPath path, EndSystem src, EndSystem ...dest) {
		super(name, src, dest);
		explicitRoute = path;
	}
	
	public ExplicitPath getExplicitPath(){
		return explicitRoute;
	}
	
	@Override
	public int getInterval() {
		return 500;
	}
	
	@Override
	public String toString(){
		return "TT " + aTitle +
				" (" + aSource + " -> " + Arrays.toString(aDestinations) + ")" +
				" Route (" + explicitRoute + ")";
	}

	@Override
	public int getDeadline() {
		return 0;
	}
}
