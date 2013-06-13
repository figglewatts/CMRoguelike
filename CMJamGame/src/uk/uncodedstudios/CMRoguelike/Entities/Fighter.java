package uk.uncodedstudios.CMRoguelike.Entities;

import com.badlogic.gdx.graphics.Color;

import uk.uncodedstudios.CMRoguelike.CMRoguelike;
import uk.uncodedstudios.CMRoguelike.Entity;
import uk.uncodedstudios.CMRoguelike.Character.Player;
import uk.uncodedstudios.CMRoguelike.Messaging.MessageBox;


public class Fighter extends Entity {
	
	private int defense;
	private int attack;
	private int health;
	private int maxHealth;
	private int mana;
	private boolean isAlive = true;

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
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Fighter() {
		super();
	}
	public Fighter(String name, int x, int y, int defense, int attack, int health, int mana) {
		super(name, x, y);
		this.defense = defense;
		this.attack = attack;
		this.health = health;
		this.maxHealth = health;
		this.mana = mana;
	}
	
	public void takeDamage(int damage) {
		if (damage > 0) {
			this.health -= damage;
		}
		
		if (this.health <= 0 && this.isAlive == true) {
			synchronized (CMRoguelike.entityList) {
				if (this instanceof Player) {
					this.setCanDraw(false);
					this.setAlive(false);
				} else {
					this.die();
					CMRoguelike.numberOfKills += 1;
				}
			}
		}
		
		if (this.health < 0) {
			this.setHealth(0);
		}
	}
	
	public void attack(Fighter target) {
		int damage = this.attack - target.defense;
		
		if (damage > 0) {
			MessageBox.message(this.getName() + " attacks " + target.getName() + " for " + damage + " hit points!", Color.RED);
			target.takeDamage(damage);
		} else {
			MessageBox.message(this.getName() + " attacks " + target.getName() + ", but it has no effect!", Color.RED);
		}
	}
	
	public void heal(int amount) {
		this.health += amount;
		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
	}
	
	public void die() {
		Entity deadBody = new Entity("Dead " + this.getName(), this.getxPos(), this.getyPos(), EntityTextures.deadBody);
		deadBody.setBlocksMovement(false);
	}
}
