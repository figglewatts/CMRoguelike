package uk.uncodedstudios.CMRoguelike.Enemy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.uncodedstudios.CMRoguelike.Dungeon.JSONRoom;
import uk.uncodedstudios.CMRoguelike.JSON.JSONObject;
import uk.uncodedstudios.CMRoguelike.util.JSONUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A class to stream enemies from a JSON file
 * @author Figglewatts
 * @version %I%, %G%
 */
public class EnemyReader {
	private EnemyReader() { }
	
	// a private list of all the enemies read from the JSON file
	// this is raw information, the enemies will be sorted in the HashMap
	private static List<JSONEnemy> enemyList = new ArrayList<JSONEnemy>();
	
	// a dictionary to hold all of the enemies we load
	// this could be optimized by only loading an enemy from the JSON
	// file when we need to use it, instead of loading them all at once
	public static Map<String, JSONEnemy> enemyDictionary = new HashMap<String, JSONEnemy>();
	
	// the Gson object to read from the JSON file
	private static Gson gson = new GsonBuilder().create();
	
	public static void Initialize() {
		try {
			enemyList = JSONUtil.readJsonStreamEnemy(Gdx.files.internal("data/enemies.json").read(), gson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (JSONObject enemy : enemyList) {
			// DEEZ CASTS
			enemyDictionary.put(((JSONEnemy)enemy).getName(), (JSONEnemy)enemy);
		}
	}
	
	/**
	 * Gets the JSONEnemy with index 'index'.
	 * @param index The point in the enemy list to get the JSONEnemy from.
	 * @return The corresponding JSONEnemy if it was found, <code>null</code> if not.
	 */
	public static JSONEnemy getAsJSONEnemy(int index) {
		try {
			return (JSONEnemy)enemyList.get(index);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	/**
	 * Gets the JSONEnemy with the name specified
	 * @param name The name of the enemy.
	 * @return The corresponding JSONEnemy if it was found, <code>null</code> if not.
	 */
	public static JSONEnemy getAsJSONEnemy(String name) {
		try {
			return enemyDictionary.get(name.toLowerCase());
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Creates a new enemy by searching the dictionary of enemies with name 'name' and placing it on the map and x pos 'x' and y pos 'y'
	 * @param name The name to search the HashMap for.
	 * @param x The X position to put the enemy at.
	 * @param y The Y position to put the enemy at.
	 * @return Your brand new enemy!
	 */
	public static BaseEnemy createEnemy(String name, int x, int y) {
		JSONEnemy enemy = getAsJSONEnemy(name);
		BaseEnemy enemyToReturn = new BaseEnemy(enemy.getName(), x, y, enemy.getDefense(), enemy.getAttack(), enemy.getHealth(), enemy.getMana());
		enemyToReturn.addEntityTexture(new Texture(Gdx.files.internal(enemy.getTexturePath())));
		enemyToReturn.addEntityTexture(new Texture(Gdx.files.internal(enemy.getAsciiTexturePath())));
		return enemyToReturn;
	}
}
