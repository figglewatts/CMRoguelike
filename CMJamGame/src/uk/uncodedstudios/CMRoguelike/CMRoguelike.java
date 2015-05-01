package uk.uncodedstudios.CMRoguelike;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import uk.uncodedstudios.CMRoguelike.Character.Player;
import uk.uncodedstudios.CMRoguelike.Dungeon.Dungeon;
import uk.uncodedstudios.CMRoguelike.Dungeon.RoomReader;
import uk.uncodedstudios.CMRoguelike.Enemy.BaseEnemy;
import uk.uncodedstudios.CMRoguelike.Enemy.EnemyReader;
import uk.uncodedstudios.CMRoguelike.Enemy.EnemyTextures;
import uk.uncodedstudios.CMRoguelike.Entities.EntityTextures;
import uk.uncodedstudios.CMRoguelike.Entities.Fighter;
import uk.uncodedstudios.CMRoguelike.Messaging.Message;
import uk.uncodedstudios.CMRoguelike.Messaging.MessageBox;
import uk.uncodedstudios.CMRoguelike.UI.PlayerHealth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import figglewatts.slagd.*;
import figglewatts.slagd.graphics.tile.Tile;

public class CMRoguelike extends SlagdAdapter {
	public static float timestep = 60F;
	
	public static OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Stage stage;
	
	private boolean canGameOverSequence = true;
	
	private final int squaresAcross = 26;
	private final int squaresDown = 16;
	
	// TODO: add better support for player ASCII textures (JSON perhaps?)
	public static Texture playerTexture;
	public static Texture playerTextureAscii;
	public static Player player;
	
	// TODO: add better support for dungeon ASCII textures
	public static Texture dungeonTilesTexture;
	public static Texture dungeonTilesTextureAscii;
	
	public static List<Entity> entityList = new ArrayList<Entity>();
	
	public static boolean turnTake = true;
	public static int numberOfTurns = 0;
	
	public static int numberOfMonsters;
	public static int numberOfKills;
	
	public static boolean recomputeFOV = true;
	
	public static boolean drawAscii = false; // should we draw ASCII characters instead of textures?
	public static boolean oldDrawAscii = false;

	@Override
	public void dispose() {
		batch.dispose();
	}
	
	private void HandleKeys()
	{
		if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			if (Gdx.input.isKeyPressed(Keys.W)) {
				player.moveOrAttack(0, 1);
			} if (Gdx.input.isKeyPressed(Keys.A)) {
				player.moveOrAttack(-1, 0);
			} if (Gdx.input.isKeyPressed(Keys.S)) {
				player.moveOrAttack(0, -1);
			} if (Gdx.input.isKeyPressed(Keys.D)) {
				player.moveOrAttack(1, 0);
			} 
		} else {
			player.canMoveOrAttack = true;
		}
		
		// toggle the drawing of ASCII characters
		if (Gdx.input.isKeyPressed(Keys.BACKSLASH) && oldDrawAscii == drawAscii) {
			oldDrawAscii = drawAscii;
			drawAscii = !drawAscii;
		}
		if (!Gdx.input.isKeyPressed(Keys.BACKSLASH) && oldDrawAscii != drawAscii) {
			oldDrawAscii = drawAscii;
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	protected void draw() {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// HERE
		Dungeon.Draw(batch, Dungeon.dungeon, squaresDown, squaresAcross);
		
		//Camera.Location = new Vector2(player.getxPos() + Gdx.graphics.getWidth()/2, player.getyPos() + Gdx.graphics.getWidth()/2);
		
		Entity[] entityArray = entityList.toArray(new Entity[entityList.size()]);
		for (Entity e : entityArray)
		{
			e.draw(batch, drawAscii);
			if (e instanceof BaseEnemy) {
				if (turnTake) {
					if (((BaseEnemy)e).isAlive()) {
						((BaseEnemy)e).takeTurn();					}
				}
			}
		}
		
		/*for (Iterator<Entity> it = entityList.iterator(); it.hasNext();)
		{
			Entity e = (Entity)it.next();
			e.draw(batch);
			if (e instanceof BaseEnemy) {
				if (turnTake) {
					if (((BaseEnemy)e).isAlive()) {
						((BaseEnemy)e).takeTurn();
					}
				}
			}
		}*/
		
		// print the game messages
		int y = 0;
		for (Message message : MessageBox.messageList)
		{
			message.setYPos(Gdx.graphics.getHeight() - (y * (MessageBox.font.getLineHeight())));
			message.setXPos(10);
			message.draw(batch);
			y++;
		}
		
		PlayerHealth.Draw(batch);
		
		batch.end();
		
		if (turnTake == false) {
			// 2 dodgy 2 furious
			// TODO: Dirty code 2: Dirty harder
			if (camera.position.x < 0) {
				camera.position.x = 0;
			}
			if (camera.position.x > (Dungeon.dungeon.getMapWidth() - squaresAcross) * Tile.TILE_WIDTH) {
				camera.position.x = (Dungeon.dungeon.getMapWidth() - squaresAcross) * Tile.TILE_WIDTH;
			}
			if (camera.position.y < 0) {
				camera.position.y = 0;
			}
			if (camera.position.y > (Dungeon.dungeon.getMapHeight() - squaresDown) * Tile.TILE_HEIGHT) {
				camera.position.y = (Dungeon.dungeon.getMapHeight() - squaresDown) * Tile.TILE_HEIGHT;
			}
		}
		
		if (turnTake && player.isAlive()) {
			numberOfTurns += 1;
			recomputeFOV = true;
			if ((numberOfTurns % 4) == 0) {
				player.heal(1);
			}
			turnTake = false;
		} else {
			if (player.isAlive()) {
				HandleKeys();
			}
		}
		if (recomputeFOV) {
			//player.FOV(Dungeon.dungeon);
			recomputeFOV = false;
		}
		if (!player.isAlive() && canGameOverSequence) {
			MessageBox.message("You have died!", Color.ORANGE);
			canGameOverSequence = false;
		}
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void start() {
		// TODO Auto-generated method stub
		Settings.VIRTUAL_VIEWPORT_WIDTH = 320;
		Settings.VIRTUAL_VIEWPORT_HEIGHT = 224;
		Tile.TILE_WIDTH = 16;
		Tile.TILE_HEIGHT = 16;
		
		stage = new Stage();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		dungeonTilesTexture = new Texture(Gdx.files.internal("textures/level/level-sheet.png"));
		dungeonTilesTextureAscii = new Texture(Gdx.files.internal("textures/level/level-sheet-ascii.png"));
		Tile.addTilesheet(dungeonTilesTexture);
		
		// TODO: add support for different sprites for different races
		playerTexture = new Texture(Gdx.files.internal("textures/player/human.png"));
		playerTextureAscii = new Texture(Gdx.files.internal("textures/player/human-ascii.png"));
		player = new Player("player", 0, 0, 1, 3, 30, 10);
		player.addEntityTexture(playerTexture);
		player.addEntityTexture(playerTextureAscii);
		player.setScale(2);
		
		// TODO: add support for loading stuff
		RoomReader.Initialize();
		EnemyReader.Initialize();
		
		EnemyTextures.Initialize();
		EntityTextures.Initialize();
		
		Dungeon.Initialize();
		Dungeon.Generate();
		
		//player.FOV(Dungeon.dungeon);
		
		//System.out.println(Dungeon.dungeon.Rows.get(player.ySample).Columns.get(player.xSample).getIsExplored());
		
		// HERE
		
		MessageBox.Initialize();
		MessageBox.message("Welcome to the game!", Color.RED);
		//MessageBox.message("LOADS OF BUGS!", Color.MAGENTA);
		
		PlayerHealth.Initialize();
	}

	@Override
	protected void update(double arg0) {
		// TODO Auto-generated method stub
		
	}
}