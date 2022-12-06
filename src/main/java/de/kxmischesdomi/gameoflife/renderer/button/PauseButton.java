package de.kxmischesdomi.gameoflife.renderer.button;

import de.kxmischesdomi.gameoflife.ConwayComputing;
import de.kxmischesdomi.gameoflife.GameOfLife;
import de.kxmischesdomi.gameoflife.renderer.GameRenderer;
import processing.core.PApplet;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class PauseButton extends BooleanButton {

	private static final String pause = "Pause", play = "Play";

	// Value is true if the game is running
	public PauseButton(int x, int y, int width, int height) {
		super(x, y, width, height, false);
	}

	@Override
	public String getText() {
		return getValue() ? pause : play;
	}

	@Override
	public void setValue(boolean value) {
		super.setValue(value);
		ConwayComputing computing = GameOfLife.getInstance().getComputing();
		computing.running = value;
	}

}
