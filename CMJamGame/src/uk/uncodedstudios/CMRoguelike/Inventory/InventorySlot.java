package uk.uncodedstudios.CMRoguelike.Inventory;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class InventorySlot extends ImageButton {
	private boolean occupied; // this is true if this slot is currently occupied

	public InventorySlot(Drawable imageUp, Drawable imageDown,
			Drawable imageChecked) {
		super(imageUp, imageDown, imageChecked);
		// TODO Auto-generated constructor stub
	}

	public InventorySlot(Drawable imageUp, Drawable imageDown) {
		super(imageUp, imageDown);
		// TODO Auto-generated constructor stub
	}

	public InventorySlot(Drawable imageUp) {
		super(imageUp);
		// TODO Auto-generated constructor stub
	}

	public InventorySlot(ImageButtonStyle style) {
		super(style);
		// TODO Auto-generated constructor stub
	}

	public InventorySlot(Skin skin, String styleName) {
		super(skin, styleName);
		// TODO Auto-generated constructor stub
	}

	public InventorySlot(Skin skin) {
		super(skin);
		// TODO Auto-generated constructor stub
	}
}
