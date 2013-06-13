package uk.uncodedstudios.CMRoguelike.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class UITextblock extends UIWidget{
	private Vector2 textOffset;
	private BitmapFont textFont;
	private String textMessage;
	private Color textTint;
	
	public Vector2 getTextOffset() {
		return textOffset;
	}
	public void setTextOffset(Vector2 textOffset) {
		this.textOffset = textOffset;
	}
	public int getXOffset() {
		return (int)textOffset.x;
	}
	public int getYOffset() {
		return (int)textOffset.y;
	}
	public BitmapFont getFont() {
		return textFont;
	}
	public void setFont(BitmapFont font) {
		this.textFont = font;
	}
	public String getText() {
		return textMessage;
	}
	public void setText(String text) {
		this.textMessage = text;
	}
	public Color getTint() {
		return textTint;
	}
	public void setTint(Color tint) {
		this.textTint = tint;
	}
	
	public UITextblock(String id, Vector2 pos, Vector2 offset, BitmapFont font, String text, Color tint) {
		super(id, pos);
		textOffset = offset;
		textFont = font;
		textMessage = text;
		textTint = tint;
	}
	
	public void Draw(SpriteBatch batch) {
		textFont.setColor(textTint);
		if (this.isVisible()) {
			textFont.draw(batch, textMessage, this.getXPos() + this.getXOffset(), this.getYPos() + this.getYOffset());
		}
	}
}
