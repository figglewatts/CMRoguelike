package uk.uncodedstudios.CMRoguelike.Inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item {
	private String itemName;
	private String itemShortName;
	private String itemDescription;
	private Sprite itemIcon;
	private int numberOfItems;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemShortName() {
		return itemShortName;
	}
	public void setItemShortName(String itemShortName) {
		this.itemShortName = itemShortName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public Sprite getItemIcon() {
		return itemIcon;
	}
	public void setItemIcon(Sprite itemIcon) {
		this.itemIcon = itemIcon;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	
	public Item(String name, String shortName, String description) {
		itemName = name;
		itemShortName = shortName;
		itemDescription = description;
	}
	public Item(String name, String shortName, String description, Sprite icon) {
		itemName = name;
		itemShortName = shortName;
		itemDescription = description;
		itemIcon = icon;
	}
	
	public void Use() {
		// overidden by subclasses
	}
	
	public void Drop() {
		// TODO: add drop code
	}
	public void Drop(int numberOfItems) {
		// TODO: add drop code for number of items
	}
}
