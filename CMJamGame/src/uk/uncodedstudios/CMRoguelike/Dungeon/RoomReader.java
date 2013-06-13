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

import com.badlogic.gdx.Gdx;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

/**
 * @author Figglewatts
 */
public class RoomReader {
	private RoomReader() { }
	
	public static List<JSONRoom> roomList = new ArrayList<JSONRoom>();
	
	public static Map<String, JSONRoom> roomMap = new HashMap<String, JSONRoom>();
	
	private static Gson gson = new GsonBuilder().create();
	
	public static void Initialize()
	{
		try {
			roomList = readJsonStream(Gdx.files.internal("data/rooms.json").read());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (JSONRoom room : roomList) {
			roomMap.put(room.getName(), room);
		}
	}
	
	private static List<JSONRoom> readJsonStream(InputStream in) throws IOException
	{
		JsonReader reader = new JsonReader(new InputStreamReader(in));
		JsonParser jsonParser = new JsonParser();
		JsonArray roomArray = jsonParser.parse(reader).getAsJsonArray();
		List<JSONRoom> rooms = new ArrayList<JSONRoom>();
		for (JsonElement aRoom : roomArray) {
			JSONRoom aJSONRoom = gson.fromJson(aRoom, JSONRoom.class);
			rooms.add(aJSONRoom);
		}
		return rooms;
	}
}
