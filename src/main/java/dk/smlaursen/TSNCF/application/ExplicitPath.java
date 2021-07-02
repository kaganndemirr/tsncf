package main.java.dk.smlaursen.TSNCF.application;

import java.util.List;

import main.java.dk.smlaursen.TSNCF.architecture.Bridge;
import main.java.dk.smlaursen.TSNCF.architecture.GCL;

public class ExplicitPath {
	private final List<GCL> aGCL;
	private final List<List<Bridge>> aPath;
	
	public ExplicitPath(List<GCL> gcl, List<List<Bridge>> path){
		aGCL = gcl;
		aPath = path;
	}
	
	public List<GCL> getGCL(){
		return aGCL;
	}
	
	public List<List<Bridge>> getPath(){
		return aPath;
	}
	
	public String toString(){
		return aPath.toString();
	}
}
