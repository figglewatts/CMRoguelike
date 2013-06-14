package uk.uncodedstudios.CMRoguelike.Enemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.uncodedstudios.CMRoguelike.JSON.JSONObject;
import uk.uncodedstudios.CMRoguelike.util.JSONUtil;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Figglewatts
 */
public class EnemyReader {
	private EnemyReader() { }
	
	// a private list of all the enemies read from the JSON file
	// this is raw information, the enemies will be sorted in the HashMap
	private static List<JSONObject> enemyList = new ArrayList<JSONObject>();
	
	// a dictionary to hold all of the enemies we load
	// this could be optimized by only loading an enemy from the JSON
	// file when we need to use it, instead of loading them all at once
	public static Map<String, JSONEnemy> enemyDictionary = new HashMap<String, JSONEnemy>();
	
	// the Gson object to read from the JSON file
	private static Gson gson = new GsonBuilder().create();
	
	public static void Initialize() {
		try {
			enemyList = JSONUtil.readJsonStream(Gdx.files.internal("data/enemies.json").read(), gson);
		} catch () {
			
		}
	}
}
