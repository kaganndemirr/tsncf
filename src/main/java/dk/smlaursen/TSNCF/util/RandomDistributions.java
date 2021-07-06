package dk.smlaursen.TSNCF.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomDistributions {
	
	public static int RouletteWheelDistribution(int size){
	   double r = ThreadLocalRandom.current().nextDouble();
	   int index = 0;
	   double val = 0.5;
		while (!(r > val) && index < size - 1) {
			index++;
			val = val / 2.0;
		}
	   return index;
	}
}
