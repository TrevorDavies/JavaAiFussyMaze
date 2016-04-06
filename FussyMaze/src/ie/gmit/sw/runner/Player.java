package ie.gmit.sw.runner;

public class Player {
	
	private int pHealth;
	private int pPower;
	private int pSmash;
	
	public Player(int pHealth, int pPower, int pSmash) {
		super();
		this.pHealth = pHealth;
		this.pPower = pPower;
		this.pSmash = pSmash;
	}

	public int getpHealth() {
		return pHealth;
	}

	public void setpHealth(int pHealth) {
		this.pHealth = pHealth;
	}

	public int getpPower() {
		return pPower;
	}

	public void setpPower(int pPower) {
		this.pPower = pPower;
	}

	public int getpSmash() {
		return pSmash;
	}

	public void setpSmash(int pSmash) {
		this.pSmash = pSmash;
	}
	
	

}
