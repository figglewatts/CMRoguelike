package uk.uncodedstudios.CMRoguelike.Camera;

import com.badlogic.gdx.math.Vector2;

public class Camera {
	private Camera() { }
	
	public static Vector2 position = new Vector2(Vector2.Zero);
	
	private float zoom;

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	
}
