package uk.uncodedstudios.CMRoguelike.Enemy;

import uk.uncodedstudios.CMRoguelike.JSON.JSONObject;

/**
 * @author Figglewatts
 */
public class JSONEnemy extends JSONObject {
	private String name;
	private int defense;
	private int attack;
	private int health;
	private int maxHealth;
	private int mana;
	private String texture;
	
	public String toString() {
		return this.name + ", " + Integer.toString(this.defense) + ", "
				+ Integer.toString(this.attack) + ", " + Integer.toString(this.health) + "/"
				+ Integer.toString(this.maxHealth) + ", " + Integer.toString(this.mana) + ", "
				+ this.texture;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}

	public String getTexture() {
		return texture;
	}
	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	public JSONEnemy() { }
}
