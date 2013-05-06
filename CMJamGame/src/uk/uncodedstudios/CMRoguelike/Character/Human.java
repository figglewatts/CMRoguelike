package uk.uncodedstudios.CMRoguelike.Character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Human extends Player {
	
	public Human(String name, int x, int y, int defense, int attack, int health, int mana) {
		super(name, x, y, defense, attack, health, mana);
	}

	private void Initialize()
	{
		this.setHealth(10);
		this.setHunger(100);
		this.setMana(10);
	}
}
