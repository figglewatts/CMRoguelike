package uk.uncodedstudios.CMRoguelike.Dungeon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.uncodedstudios.CMRoguelike.JSON.JSONObject;
import uk.uncodedstudios.CMRoguelike.util.JSONUtil;

import com.badlogic.gdx.Gdx;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

/**
 * @author Figglewatts
 */
public class RoomReader {
	private RoomReader() { }
	
	public static List<JSONObject> roomList = new ArrayList<JSONObject>();
	
	public static Map<String, JSONRoom> roomMap = new HashMap<String, JSONRoom>();
	
	private static Gson gson = new GsonBuilder().create();
	
	public static void Initialize()
	{
		try {
			roomList = JSONUtil.readJsonStream(Gdx.files.internal("data/rooms.json").read(), gson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (JSONObject room : roomList) {
			// DEEZ CASTS
			roomMap.put(((JSONRoom)room).getName(), (JSONRoom)room);
		}
	}
	
	public static JSONRoom getAsJSONRoom(int index) {
		try {
			return (JSONRoom)roomList.get(index);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
}
