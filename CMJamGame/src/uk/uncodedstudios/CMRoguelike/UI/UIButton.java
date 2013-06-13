package uk.uncodedstudios.CMRoguelike.UI;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIButton extends UITextblock {
	private Texture buttonTexture;
	private boolean disabled;
	private boolean pressed;
	private Rectangle bounds;
	private List<ClickListener> listenerList = new ArrayList<ClickListener>();
	
	public Texture getButtonTexture() {
		return buttonTexture;
	}
	public void setButtonTexture(Texture buttonTexture) {
		this.buttonTexture = buttonTexture;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isPressed() {
		return pressed;
	}
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	public int getBoundsX() {
		return (int)this.bounds.x;
	}
	public int getBoundsY() {
		return (int)this.bounds.y;
	}
	public int getBoundsWidth() {
		return (int)this.bounds.width;
	}
	public int getBoundsHeight() {
		return (int)this.bounds.height;
	}
	
	public UIButton(String id, Vector2 pos, Vector2 offset, BitmapFont font,
			String text, Color tint, Texture tex) {
		super(id, pos, offset, font, text, tint);
		buttonTexture = tex;
		this.bounds = new Rectangle(this.getXPos(), this.getYPos(), buttonTexture.getWidth(), buttonTexture.getHeight());
	}
	
	// ---- INPUT LISTENERS ----
	public void AddClickListener(ClickListener l) {
		listenerList.add(l);
	}
	
	public void RemoveClickListener(ClickListener l) {
		listenerList.remove(l);
	}
	
	protected void fireClicked(InputEvent e, float x, float y) {
		ClickListener[] ls = (ClickListener[])listenerList.toArray();
		for (ClickListener l : ls) {
			l.clicked(e, x, y);
		}
	}
	
	// ---- HELPER METHODS ----
	public boolean Contains(Vector2 location) {
		return this.isVisible() && this.bounds.contains(location.x, location.y);
	}
	
	public void HitTest(Vector2 location) {
		if (this.isVisible() && !this.isDisabled()) {
			if (Contains(location)) {
				pressed = true;
				fireClicked(new InputEvent(), location.x, location.y);
			}
		}
	}
}
