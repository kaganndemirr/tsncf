package dk.smlaursen.TSNCF.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.smlaursen.TSNCF.application.Application;

public class Multicast {
		//The Application
		private final Application aApp;
		
		//The ArrayList of unicast routes making up the multicast
		private final List<Unicast> aRouting;
		
		public Multicast(Application app, List<Unicast> unicasts){
			aApp = app;
			aRouting = unicasts;
		}
		
		public List<Unicast> getUnicasts(){
			return aRouting;
		}
		
		public Application getApplication(){
			return aApp;
		}
		
		@Override
		public int hashCode(){
			return aApp.hashCode() + aRouting.hashCode();
		}
		
		@Override
		public boolean equals(Object o){
			if(o instanceof Multicast obj){
				return obj.getApplication().equals(getApplication()) &&
						obj.getUnicasts().equals(getUnicasts());
			}
			return false;
		}
		
		@Override
		public String toString(){
			return aApp.toString();
		}
		
		public static List<Multicast> generateMulticasts(Collection<Unicast> col){
			Map<Application, ArrayList<Unicast>> map = new HashMap<>();
			for(Unicast uc : col){
				if(!map.containsKey(uc.getApplication())){
					map.put(uc.getApplication(), new ArrayList<>());
				}
				map.get(uc.getApplication()).add(uc);
			}
			List<Multicast> mc = new ArrayList<>();
			for(Application a : map.keySet()){
				mc.add(new Multicast(a, map.get(a)));
			}
			return mc;
		}
}
