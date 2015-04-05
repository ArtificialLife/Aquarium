import java.util.List;
import java.util.Map;
import java.util.Random;


public class Grammar {
	
	private String axiom;
	private Map<String, String> rules;
	private int iteraciones;
	
	public Grammar(String axiom, Map<String, String> rules, int iteraciones){
		this.axiom = axiom;
		this.rules = rules;
		this.iteraciones = iteraciones;
		
	}
	
	public String generateLSystem(){
		String generated = axiom;
		for(int i=0;i<iteraciones;i++){
			for(String key:rules.keySet()){
				generated = generated.replace(key, rules.get(key));
			}
		}
		return generated;
	}

}
