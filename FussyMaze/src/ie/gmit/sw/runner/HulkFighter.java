package ie.gmit.sw.runner;

import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
//import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class HulkFighter {
	
	//private float damage = 0;

	
	public float hulkfight(int health, int power, int smash){
		float damage;
		//String fileName = "fcl/fighting.fcl";
        FIS fis = FIS.load("fcl/fighting.fcl",true);
		
		FunctionBlock functionBlock = fis.getFunctionBlock("battle");
		fis.setVariable("health", health);
		fis.setVariable("power", power);
		fis.setVariable("smash", smash);
		
		return damage = (float) (fis.getVariable("damage").getValue());
	}
}
