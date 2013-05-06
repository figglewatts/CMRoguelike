package uk.uncodedstudios.CMRoguelike.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EnemyTextures {
	private EnemyTextures() { }
	
	public static Texture orcTexture;
	public static Texture goblinTexture;
	public static Texture trollTexture;
	
	public static void Initialize() {
		orcTexture = new Texture(Gdx.files.internal("textures/enemy/orc.png"));
		goblinTexture = new Texture(Gdx.files.internal("textures/enemy/goblin.png"));
		trollTexture = new Texture(Gdx.files.internal("textures/enemy/troll.png"));
	}
}
