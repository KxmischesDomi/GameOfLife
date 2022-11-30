package de.kxmischesdomi.gameoflife;

import de.kxmischesdomi.gameoflife.renderer.GameRenderer;
import processing.core.PApplet;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class GameOfLife {

	private static GameOfLife instance;

	private ConwayComputing computing;
	private CellStorage cellStorage;

	public GameOfLife() {
		instance = this;
		computing = new ConwayComputing(this);
		cellStorage = new CellStorage();
		PApplet.main(GameRenderer.class);
	}

	public ConwayComputing getComputing() {
		return computing;
	}

	public CellStorage getCellStorage() {
		return cellStorage;
	}

	public static GameOfLife getInstance() {
		return instance;
	}

}
