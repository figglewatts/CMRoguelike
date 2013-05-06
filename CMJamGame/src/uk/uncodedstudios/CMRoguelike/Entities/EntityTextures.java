package uk.uncodedstudios.CMRoguelike.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EntityTextures {
	private EntityTextures() { }
	
	public static Texture deadBody;
	
	public static void Initialize() {
		deadBody = new Texture(Gdx.files.internal("textures/entity/deadBody.png"));
	}
}
