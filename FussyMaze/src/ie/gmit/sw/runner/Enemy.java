package ie.gmit.sw.runner;

public class Enemy {
	
	private int eHealth;
	private int eHitDamage;
	public Enemy(int eHealth, int eHitDamage) {
		super();
		this.eHealth = eHealth;
		this.eHitDamage = eHitDamage;
	}
	public int geteHealth() {
		return eHealth;
	}
	public void seteHealth(int eHealth) {
		this.eHealth = eHealth;
	}
	public int geteHitDamage() {
		return eHitDamage;
	}
	public void seteHitDamage(int eHitDamage) {
		this.eHitDamage = eHitDamage;
	}
	
	

}
