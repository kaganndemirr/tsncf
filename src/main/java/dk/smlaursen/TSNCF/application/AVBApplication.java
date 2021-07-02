package main.java.dk.smlaursen.TSNCF.application;

import java.util.Arrays;
import java.util.List;

import main.java.dk.smlaursen.TSNCF.architecture.EndSystem;

public class AVBApplication extends Application {
	private final AVBClass aAVBClass;
	private final List<String> aModes;
	private final int aNoOfFramesPerInterval;
	private final int aMaxFrameSize;
	
	public AVBApplication (String name, List<String> modes, AVBClass type, int payloadSize, int noOfFrames, EndSystem src, EndSystem ...dest){
		super(name, src, dest);
		aAVBClass = type;
		aModes = modes;
		aNoOfFramesPerInterval = noOfFrames;
		aMaxFrameSize = payloadSize;
	}
	
	public int getNoOfFramesPerInterval(){
		return aNoOfFramesPerInterval;
	}
	
	public int getMaxFrameSize(){
		return aMaxFrameSize;
	}
	public AVBClass getType(){
		return aAVBClass;
	}
	
	public List<String> getModes(){
		return aModes;
	}
	
	@Override
	public int getInterval() {
		return aAVBClass.getIntervalMicroSec();
	}
	
	@Override
	public int getDeadline(){
		return aAVBClass.getMaxEndToEndDelayMicroSec();
	}
	
	public String toString(){
		return "AVB " + aTitle + " " + aModes +
				" : " + aAVBClass +
				" (" + aNoOfFramesPerInterval + "x" + aMaxFrameSize + "B / " + getInterval() + "us)" +
				" (" + aSource + " -> " + Arrays.toString(aDestinations) + ")";
	}
}
