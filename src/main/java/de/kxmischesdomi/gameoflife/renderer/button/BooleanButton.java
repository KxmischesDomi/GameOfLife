package de.kxmischesdomi.gameoflife.renderer.button;

import de.kxmischesdomi.gameoflife.renderer.GameRenderer;
import processing.event.MouseEvent;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class BooleanButton extends ControlButton {

	private boolean value;

	public BooleanButton(int x, int y, int width, int height, boolean defaultValue) {
		super(x, y, width, height);
		this.value = defaultValue;
	}

	@Override
	public void onClicked() {
		setValue(!getValue());
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

}
