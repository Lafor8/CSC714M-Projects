package tagalogStemmer.system;

import java.util.ArrayList;

import tagalogStemmer.models.Rule;


public class Routine {
	private ArrayList<Rule> ruleList;

	public Routine (){
		ruleList = new ArrayList<Rule>();
	}
	
	public void addRule(Rule rule) {
		ruleList.add(rule);
	}
	
	public ArrayList<Rule> getRules(){
		return ruleList;
	}

}
