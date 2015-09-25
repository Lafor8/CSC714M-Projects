package tagalogStemmer.system;

import java.util.ArrayList;


public class Routine {
	private ArrayList<Rule> ruleList;

	public Routine (){
		ruleList = new ArrayList<Rule>();
	}
	
	public void addRule(ProductionRule productionRule) {
		ruleList.add(productionRule);
	}
	
	public ArrayList<Rule> getRules(){
		return ruleList;
	}

}
