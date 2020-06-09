package app.Game.Object.Entity;

public class Unit {

	private int maxHealth, currentHealth, speedX;
	private static int centerX;
	private static int centerY;
	
	// Behavioral Methods
	public void update() {
		centerX += speedX;
		speedX = getSpeedX();
	}

	public void die() {
		// TODO
	}

	public void attack() {
		// TODO
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public static int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		Unit.centerX = centerX;
	}

	public static int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		Unit.centerY = centerY;
	}

}
