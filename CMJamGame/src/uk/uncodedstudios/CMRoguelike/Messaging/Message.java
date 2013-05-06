package uk.uncodedstudios.CMRoguelike.Messaging;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Message {
	private String text;
	private Color color;
	private int xPos;
	private int yPos;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getXPos() {
		return xPos;
	}
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	public int getYPos() {
		return yPos;
	}
	public void setYPos(float f) {
		this.yPos = (int)f;
	}
	
	public Message(String text) {
		this.text = text;
		this.color = Color.WHITE;
	}
	public Message(String text, Color color) {
		this.text = text;
		this.color = color;
	}
	
	public void draw(SpriteBatch batch) {
		Color c = MessageBox.font.getColor();
		MessageBox.font.setColor(new Color(this.color.r - 0.5F, this.color.g - 0.5F, this.color.b - 0.5F, this.color.a));
		MessageBox.font.draw(batch, this.text, this.xPos+2, this.yPos-2);
		MessageBox.font.setColor(this.color);
		MessageBox.font.draw(batch, this.text, this.xPos, this.yPos);
		MessageBox.font.setColor(c);
	}
}
