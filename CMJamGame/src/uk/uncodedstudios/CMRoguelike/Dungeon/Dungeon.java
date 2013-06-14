package uk.uncodedstudios.CMRoguelike.Dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import uk.uncodedstudios.CMRoguelike.CMRoguelike;
import uk.uncodedstudios.CMRoguelike.Character.Player;
import uk.uncodedstudios.CMRoguelike.Enemy.BaseEnemy;
import uk.uncodedstudios.CMRoguelike.Enemy.EnemyTextures;
import uk.uncodedstudios.CMRoguelike.Messaging.MessageBox;
import uk.uncodedstudios.CMRoguelike.util.MapUtil;
import uk.uncodedstudios.uncode2d.tileengine.*;
import uk.uncodedstudios.uncode2d.camera.*;

/**
 * @author Figglewatts
 */
public class Dungeon {
	private Dungeon() { }
	
	private static List<JSONRoom> roomsMade = new ArrayList<JSONRoom>();
	//private static List<Room> roomList = new ArrayList<Room>();
	
	private static Texture dungeonTilesTexture;
	
	private static final int ROOM_MAX_SIZE = 14; // the maximum size (in tiles) a room can be
	private static final int ROOM_MIN_SIZE = 4; // the minumum size (in tiles) a room can be
	private static final int ROOM_PADDING_X = 20; // the amount of tiles away from the edge each room can be
	private static final int ROOM_PADDING_Y = 20; // the amount of tiles away from the edge each room can be
	private static final int MAX_ROOMS = 100; // the max amount of rooms there can be on a map
	private static final int MAP_WIDTH = 150; // the width of the map
	private static final int MAP_HEIGHT = 150; // the height of the map
	
	private static final int MAX_ROOM_MONSTERS = 2; // the maximum amount of monsters per room
	
	private static Random rand = new Random();
	
	public static Level dungeon;
	
	private static int numberOfMonsters;
	
	private static void createRoom(JSONRoom room) {
		//JSONRoom room = RoomReader.roomList.get(roomIndex);
		int xPos = room.getX();
		int yPos = room.getY();
		int w = room.getWidth();
		int h = room.getHeight();
		
		for (int y = yPos; y < yPos+h; y++) {
			for (int x = xPos; x < xPos+w; x++) {
				if (room.getTileIDFromPos(x-xPos, y-yPos) != 0) {
					System.out.println(room.getTileIDFromPos((x-xPos), (y-yPos)));
					dungeon.Rows.get(y).Columns.get(x).setTileID(room.getTileIDFromPos(x-xPos, y-yPos));
					dungeon.Rows.get(y).Columns.get(x).setIsSolid(false);
				}
			}
		}
	}
	
	/*private static void createRoom(Room room)
	{
		//System.out.println("X: " + room.getRoomX() + ", Y: " + room.getRoomY() + ", X2: " + (room.getRoomX() + room.getRoomWidth()) + ", Y2: " + (room.getRoomY() + room.getRoomHeight()));
		
		// go through the tiles in the rectangle and make them passable
		for (int y = (int)room.getRoomY()+1; (y < room.getRoomY()+1 + room.getRoomHeight()); y++)
		{
			for (int x = (int)room.getRoomX()+1; (x < room.getRoomX()+1 + room.getRoomHeight()); x++)
			{
				dungeon.Rows.get(y).Columns.get(x).setIsSolid(false);
				dungeon.Rows.get(y).Columns.get(x).setTileID(1);
			}
		}
	}*/
	
	private static void createHorizontalTunnel(int x1, int x2, int y)
	{
		for (int x = Math.min(x1, x2); x < Math.max(x1, x2)+1; x++)
		{
			dungeon.Rows.get(y).Columns.get(x).setIsSolid(false);
		}
	}
	
	private static void createVerticalTunnel(int y1, int y2, int x)
	{
		for (int y = Math.min(y1, y2); y < Math.max(y1, y2)+1; y++)
		{
			dungeon.Rows.get(y).Columns.get(x).setIsSolid(false);
		}
	}
	
	public static void Generate() {
		// fill the map with blocked tiles
		for (int y = 0; y < dungeon.MapHeight; y++)
		{
			for (int x = 0; x < dungeon.MapWidth; x++)
			{
				dungeon.Rows.get(y).Columns.get(x).setIsSolid(true);
			}
		}
		
		int numberOfRooms = 0;
		
		for (int r = 0; r < MAX_ROOMS; r++)
		{
			// generate a random room index
			int index = rand.nextInt(RoomReader.roomList.size());
			
			JSONRoom newRoom = RoomReader.getAsJSONRoom(index);
			
			// generate a random width and height for the room
			//int w = ROOM_MIN_SIZE + rand.nextInt(ROOM_MAX_SIZE - ROOM_MIN_SIZE);
			//int h = ROOM_MIN_SIZE + rand.nextInt(ROOM_MAX_SIZE - ROOM_MIN_SIZE);
			
			// generate a random position for the room
			int x = ROOM_PADDING_X + (rand.nextInt(MAP_WIDTH - ROOM_PADDING_X)) - newRoom.getWidth() - 2;
			int y = ROOM_PADDING_Y + (rand.nextInt(MAP_HEIGHT - ROOM_PADDING_Y)) - newRoom.getHeight() - 2;
			
			//Room newRoom = new Room(x, y, w, h);

			newRoom.setRoomBounds(x, y);
			
			// run through other rooms to see if they intersect
			boolean failed = false;
			for (JSONRoom otherRoom : roomsMade)
			{
				if (newRoom.getRoomBounds().overlaps(otherRoom.getRoomBounds())) {
					failed = true;
					break;
				} else if ((newRoom.getX() + newRoom.getWidth()) > MAP_WIDTH-1-ROOM_PADDING_X
						|| (newRoom.getY() + newRoom.getHeight()) > MAP_HEIGHT-1-ROOM_PADDING_Y) {
					failed = true;
					break;
				} else if (newRoom.getX() <= 0 || newRoom.getY() <= 0) {
					failed = true;
					break;
				}
			}
			
			if (failed == false) {
				// no intersections, S'ALL GOOD
				
				// paint it to the map's tiles
				createRoom(newRoom);
				
				// TODO: spawn start items based on class
				
				// generate entities
				if (numberOfRooms != 0) {
					//GenerateEntities(newRoom);
				}
				
				int newX = (int)newRoom.getCenter().x;
				int newY = (int)newRoom.getCenter().y;
				
				if (numberOfRooms == 0) {
					System.out.println(newRoom.getX() + ", " + newRoom.getY());
					CMRoguelike.player.setxPos(newX*Tile.RenderTileWidth);
					CMRoguelike.player.setyPos(newY*Tile.RenderTileHeight);
					setCameraToPlayer(newX, newY);
					System.out.println("PlayerPos: " + new Vector2(CMRoguelike.player.getxPos(), CMRoguelike.player.getyPos()) + ", CameraPos: " + Camera.Location);
				} else {
					int random = rand.nextInt(numberOfRooms);
					
					int prevX = (int)roomsMade.get(numberOfRooms-1).getCenter().x;
					int prevY = (int)roomsMade.get(numberOfRooms-1).getCenter().y;
					
					// get a random number that's either 0 or 1
					if (rand.nextInt(1) == 1) {
						createHorizontalTunnel(prevX, newX, prevY);
						createVerticalTunnel(prevY, newY, newX);
					} else {
						createVerticalTunnel(prevY, newY, prevX);
						createHorizontalTunnel(prevX, newX, newY);
					}
				}
				
				// finally, append the new room to the list of rooms that have been generated
				roomsMade.add(newRoom);
				numberOfRooms++;
			}
		}
		
		SetupMap();
		
		CMRoguelike.numberOfMonsters = numberOfMonsters;
		
		MessageBox.message(CMRoguelike.player.getxPosInTiles() + ", " + CMRoguelike.player.getyPosInTiles());
	}
	
	public static void GenerateEntities(JSONRoom room) {
		int numMonsters = rand.nextInt(MAX_ROOM_MONSTERS);
		
		numberOfMonsters += numMonsters;
		
		for (int i = 0; i <= MAX_ROOM_MONSTERS; i++) 
		{
			int size = MapUtil.GetRoomNonSolidTiles(room).size();
			Vector2 location = MapUtil.GetRoomNonSolidTiles(room).get(rand.nextInt(size));
			
			if (!MapUtil.IsBlocked(location)) {
				int xPosInPx = (int)location.x * Tile.RenderTileWidth;
				int yPosInPx = (int)location.y * Tile.RenderTileHeight;
				int chance = rand.nextInt(99);
				if (chance < 70) {
					// create an orc
					BaseEnemy orc = new BaseEnemy("Orc", xPosInPx, yPosInPx, 0, 3, 10, 10);
					orc.setEntityTexture(EnemyTextures.orcTexture);
				} else if (chance >= 70 && chance < 90) {
					// create a goblin
					BaseEnemy goblin = new BaseEnemy("Goblin", xPosInPx, yPosInPx, 1, 3, 16, 10);
					goblin.setEntityTexture(EnemyTextures.goblinTexture);
				} else if (chance >= 90 && chance <= 99) {
					// create a troll
					BaseEnemy troll = new BaseEnemy("Troll", xPosInPx, yPosInPx, 2, 3, 25, 10);
					troll.setEntityTexture(EnemyTextures.trollTexture);
				}
			}
		}
	}
	
	public static void Initialize() {
		dungeon = new Level(MAP_WIDTH, MAP_HEIGHT);
		dungeonTilesTexture = new Texture(Gdx.files.internal("textures/level/level-sheet.png"));
		Tile.TileSetTexture = dungeonTilesTexture;
		Tile.RenderTileWidth = 32;
		Tile.RenderTileHeight = 32;
		
		dungeon.MapWidth = MAP_WIDTH;
		dungeon.MapHeight = MAP_HEIGHT;
	}
	
	public static void Draw(SpriteBatch batch, Level myMap, int squaresDown, int squaresAcross) {
		Vector2 firstSquare = new Vector2(Camera.Location.x / Tile.RenderTileWidth, Camera.Location.y / Tile.RenderTileHeight);
		int firstX = (int)firstSquare.x;
		int firstY = (int)firstSquare.y;
		
		Vector2 squareOffset = new Vector2(Camera.Location.x % Tile.RenderTileWidth, Camera.Location.y % Tile.RenderTileHeight);
		int offsetX = (int)squareOffset.x;
		int offsetY = (int)squareOffset.y;
		
		for (int y = 0; y < squaresDown; y++)
		{
			for (int x = 0; x < squaresAcross; x++)
			{
				//System.out.println("Viewable?: " + myMap.Rows.get(y).Columns.get(x).getIsViewable());
				batch.draw(Tile.TileSetTexture, 
						(float)((x * Tile.RenderTileWidth) - offsetX), 
						(float)(((y * Tile.RenderTileHeight) - offsetY)),
						(float)Tile.RenderTileWidth, 
						(float)Tile.RenderTileHeight,
						(int)Tile.GetSourceRectangle(myMap.Rows.get(y+firstY).Columns.get(x+firstX).getTileID()).x,
						(int)Tile.GetSourceRectangle(myMap.Rows.get(y+firstY).Columns.get(x+firstX).getTileID()).y,
						(int)Tile.GetSourceRectangle(myMap.Rows.get(y+firstY).Columns.get(x+firstX).getTileID()).width,
						(int)Tile.GetSourceRectangle(myMap.Rows.get(y+firstY).Columns.get(x+firstX).getTileID()).height,
						false, false);
				if (myMap.Rows.get(y+firstY).Columns.get(x+firstX).getIsViewable() == false &&
						myMap.Rows.get(y+firstY).Columns.get(x+firstX).getIsExplored() == true) {	
					batch.draw(Tile.TileSetTexture, 
							(float)((x * Tile.RenderTileWidth) - offsetX), 
							(float)(((y * Tile.RenderTileHeight) - offsetY)),
							(float)Tile.RenderTileWidth, 
							(float)Tile.RenderTileHeight,
							(int)Tile.GetSourceRectangle(2).x,
							(int)Tile.GetSourceRectangle(2).y,
							(int)Tile.GetSourceRectangle(2).width,
							(int)Tile.GetSourceRectangle(2).height,
							false, false);
				} else if (myMap.Rows.get(y+firstY).Columns.get(x+firstX).getIsExplored() == false) {
					batch.draw(Tile.TileSetTexture, 
							(float)((x * Tile.RenderTileWidth) - offsetX), 
							(float)(((y * Tile.RenderTileHeight) - offsetY)),
							(float)Tile.RenderTileWidth, 
							(float)Tile.RenderTileHeight,
							(int)Tile.GetSourceRectangle(3).x,
							(int)Tile.GetSourceRectangle(3).y,
							(int)Tile.GetSourceRectangle(3).width,
							(int)Tile.GetSourceRectangle(3).height,
							false, false);
				}
			}
		}
	}
	
	public static void SetupMap()
	{
		for (int y = 0; y < MAP_HEIGHT; y++)
		{
			for (int x = 0; x < MAP_WIDTH; x++)
			{
				if (dungeon.Rows.get(y).Columns.get(x).getIsSolid() == true) {
					dungeon.Rows.get(y).Columns.get(x).setTileID(0);
				} else {
					dungeon.Rows.get(y).Columns.get(x).setTileID(1);
				}
			}
		}
	}
	
	private static void setCameraToPlayer(int newX, int newY)
	{
		Camera.Location = new Vector2((newX*Tile.RenderTileWidth) - normalizeToTileDimensions(Gdx.graphics.getWidth()/2, true), (newY*Tile.RenderTileHeight) - normalizeToTileDimensions(Gdx.graphics.getHeight()/2, false));
	}
	
	private static int normalizeToTileDimensions(int num, boolean width)
	{
		if (width) {
			return Tile.RenderTileWidth*(num / Tile.RenderTileWidth);
		} else {
			return Tile.RenderTileHeight*(num / Tile.RenderTileHeight);
		}
	}
}
