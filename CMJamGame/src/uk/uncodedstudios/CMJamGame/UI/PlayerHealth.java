package uk.uncodedstudios.CMJamGame.UI;

import uk.uncodedstudios.CMJamGame.CMJamGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class PlayerHealth {
	private PlayerHealth() { }
	
	private static final int fontSize = 32;
	public static BitmapFont font;
	
	public static void Initialize() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/8-bit Madness.ttf"));
		font = generator.generateFont(fontSize);
	}
	
	public static void Draw(SpriteBatch batch) {
		font.draw(batch, "HP: " + CMJamGame.player.getHealth() + "/" + CMJamGame.player.getMaxHealth(), 10, (Gdx.graphics.getHeight() - font.getLineHeight()));
		font.draw(batch, "Kills: " + CMJamGame.numberOfKills + "/" + CMJamGame.numberOfMonsters, 10, (Gdx.graphics.getHeight() - font.getLineHeight()*2));
	}
}
