package uk.uncodedstudios.CMJamGame.Messaging;

import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MessageBox {
	private MessageBox() { }
	
	private static final int fontSize = 32;
	private static final int MAX_MESSAGES = 8;
	
	public static BitmapFont font;
	
	public static List<Message> messageList = new ArrayList<Message>();
	
	public static void Initialize() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/8-bit Madness.ttf"));
		font = generator.generateFont(fontSize);
	}
	
	public static void message(String messageText)
	{
		// if the number of messages on screen is more than the max
		if (messageList.size() == MAX_MESSAGES) {
			// remove the first message
			messageList.remove(0);
		}
		
		messageList.add(new Message(messageText));
	}
	public static void message(String messageText, Color messageColor)
	{
		// if the number of messages on screen is more than the max
		if (messageList.size() == MAX_MESSAGES) {
			// remove the first message
			messageList.remove(0);
		}
		
		messageList.add(new Message(messageText, messageColor));
	}
}
