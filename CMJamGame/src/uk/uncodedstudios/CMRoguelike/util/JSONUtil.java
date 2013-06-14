package uk.uncodedstudios.CMRoguelike.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uk.uncodedstudios.CMRoguelike.Dungeon.JSONRoom;
import uk.uncodedstudios.CMRoguelike.JSON.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * @author Figglewatts
 */
public class JSONUtil {
	private JSONUtil() { }
	
	/**
	 * Gets a list of elements from a JSON file.
	 * @param in The input stream to read from.
	 * @param gson The Gson object to read the JSON file with.
	 * @return A list of elements read from the JSON file specified with the input stream.
	 * @throws IOException
	 */
	public static List<JSONObject> readJsonStream(InputStream in, Gson gson) throws IOException
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
