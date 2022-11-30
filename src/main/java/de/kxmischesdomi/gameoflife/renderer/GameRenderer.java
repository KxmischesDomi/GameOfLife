package de.kxmischesdomi.gameoflife.renderer;

import de.kxmischesdomi.gameoflife.GameOfLife;
import de.kxmischesdomi.gameoflife.data.CellData;
import de.kxmischesdomi.gameoflife.renderer.button.ClearButton;
import de.kxmischesdomi.gameoflife.renderer.button.ControlButton;
import de.kxmischesdomi.gameoflife.renderer.button.PauseButton;
import de.kxmischesdomi.gameoflife.renderer.button.SpeedButton;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class GameRenderer extends PApplet {

	public int mouseX, mouseY;

	private final List<Renderable> renderables = new ArrayList<>();
	private final List<Interactable> interactables = new ArrayList<>();

	private float zoom = 10;
	private float posX, posY;

	float mouseHoldX, mouseHoldY;

	@Override
	public void settings() {
		size(1920 / 3, 1080 / 3);

		int buttonCount = 3;
		int buttonMargin = 10;
		int buttonWidth = 200;
		int buttonHeight = 200;

		int middleX = 1920 / 2;
		int startX = middleX - (buttonCount * buttonWidth + (buttonCount - 1) * buttonMargin) / 2;
		int buttonDistance = buttonWidth + buttonMargin;

		PauseButton pauseButton = new PauseButton(startX, 0, buttonWidth, buttonHeight);
		renderables.add(pauseButton);
		interactables.add(pauseButton);

		SpeedButton speedButton = new SpeedButton(startX + buttonDistance, 0, buttonWidth, buttonHeight);
		renderables.add(speedButton);
		interactables.add(speedButton);

		ClearButton clearButton = new ClearButton(startX + buttonDistance * 2, 0, buttonWidth, buttonHeight);
		renderables.add(clearButton);
		interactables.add(clearButton);
	}

	@Override
	public void setup() {
		surface.setResizable(true);

	}

	@Override
	public void draw() {
		applyLogic();

		background(0);

		prepareRendering();

		pushStyle();
		fill(20);
		rect(0, 0, 1920, 1080);
		popStyle();

		drawGrid();
		drawCells();

		// Draw all renderables
		new LinkedList<>(renderables).forEach(renderable -> renderable.render(this));
	}

	private void applyLogic() {
		GameOfLife.getInstance().getComputing().checkForNextGen();
	}

	private void drawGrid() {
		int alpha = 255 - (int) (zoom * 2.5f);
		if (alpha <= 0) return;

		pushStyle();
		int cellwidth = (int) Math.ceil(1920f / zoom);
		int verticalCells = (int) Math.ceil(1080f / cellwidth);
		int horizontalCells = (int) Math.ceil(1920f / cellwidth);

		float xOffset = posX % cellwidth;
		float yOffset = posY % cellwidth;

		strokeWeight(1);
		stroke(255, 255, 255, alpha);

		for (int x = 0; x <= horizontalCells; x++) {
			// draw vertical lines
			float xPos = x * cellwidth + xOffset;
			if (xPos < 0 || xPos > 1920) continue;
			line(xPos, 0, xPos, 1080);
		}

		for (int y = 0; y <= verticalCells; y++) {
			// draw horizontal lines
			float yPos = y * cellwidth + yOffset;
			if (yPos < 0 || yPos > 1080) continue;
			line(0, yPos, 1920, yPos);
		}

		popStyle();
	}

	private void drawCells() {
		GameOfLife game = GameOfLife.getInstance();
		Collection<CellData> cells = game.getCellStorage().getCells();
		for (CellData cell : cells) {
			drawCell(cell.getX(), cell.getY());
		}
		debug();

	}

	private void debug() {
		GameOfLife game = GameOfLife.getInstance();
		for (CellData data : game.getComputing().collectMajorCellData()) {
			if (data.isAlive()) {
				if (data.getLivingNeighbors() != 2 && data.getLivingNeighbors() != 3) {
					int color = 0xffff0000;
					drawCell(data.getX(), data.getY(), color);
				}
			} else {
				if (data.getLivingNeighbors() == 3) {
					int color = 0x6600ff00;
					drawCell(data.getX(), data.getY(), color);
				}
			}
		}
	}

	private void drawCell(int x, int y) {
		drawCell(x, y, 0xffffffff);
	}

	private void drawCell(int x, int y, int rgb) {
		pushStyle();
		int cellwidth = (int) Math.ceil(1920f / zoom);

		int startX = (int) (x * cellwidth + this.posX);
		int startY = (int) (y * cellwidth + this.posY);
		int endX = startX + cellwidth;
		int endY = startY + cellwidth;

		if (startX > 1920 || endX < 0 || startY > 1080 || endY < 0) return;

		startX = Math.max(startX, 0);
		startY = Math.max(startY, 0);
		endX = Math.min(endX, 1920);
		endY = Math.min(endY, 1080);

		fill(rgb);
		rect(startX, startY, endX - startX, endY - startY);
		popStyle();
	}

	/**
	 * Prepares the rendering in scaling the matrix and translating it to the center to retrain a 1920 by 1080 resolution
	 */
	public void prepareRendering() {
		// Scale matrix that it is retrains shape and size relative to the window size
		float scale = Math.min(width / 1920f, height / 1080f);
		scale(scale);

		// Center game area of 1920 by 1080
		float xDiff = (width / scale - 1920) / 2;
		float yDiff = (height / scale - 1080) / 2;
		translate(xDiff, yDiff);

		// translate mouse coordinates to the scaled and translated matrix
		mouseX = (int) ((super.mouseX / scale) - xDiff);
		mouseY = (int) ((super.mouseY / scale) - yDiff);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		mouseHoldX = mouseX;
		mouseHoldY = mouseY;
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		posX -= (mouseHoldX - mouseX);
		posY -= (mouseHoldY - mouseY);
		mouseHoldX = mouseX;
		mouseHoldY = mouseY;
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		// make zoom greater the further out
		float diff = event.getCount() * (zoom / 10);
		float newZoom = zoom + diff;
		if (newZoom < 10 || newZoom > 5000) return;
		zoom = newZoom;

		int zoomPosX = mouseX;
		int zoomPosY = mouseY;

		if (diff > 1) {
			zoomPosX = 1920 / 2;
			zoomPosY = 1080 / 2;
		}


		posX += (zoomPosX - posX) * diff / zoom;
		posY += (zoomPosY - posY) * diff / zoom;


	}

	@Override
	public void mouseClicked(MouseEvent event) {
		for (Interactable interactable : new LinkedList<>(interactables)) {
			if (interactable.mouseClicked(this, event)) return;
		}
		int cellwidth = (int) Math.ceil(1920f / zoom);
		int x = (int) Math.floor((mouseX - posX) / cellwidth);
		int y = (int) Math.floor((mouseY - posY) / cellwidth);
		GameOfLife.getInstance().getCellStorage().toggleCell(x, y);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		for (Interactable interactable : new LinkedList<>(interactables)) {
			if (interactable.mouseReleased(this, event)) return;
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		for (Interactable interactable : new LinkedList<>(interactables)) {
			if (interactable.keyPressed(this, event)) return;
		}
	}

}
