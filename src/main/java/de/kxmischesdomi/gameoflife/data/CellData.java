package de.kxmischesdomi.gameoflife.data;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class CellData {

	private final int x, y;
	private int livingNeighbors = 0;
	private boolean isAlive = false;

	public CellData(int x, int y) {
		this.x = x;
		this.y = y;
		isAlive = false;
		livingNeighbors = 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLivingNeighbors() {
		return livingNeighbors;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setLivingNeighbors(int livingNeighbors) {
		this.livingNeighbors = livingNeighbors;
	}

	public void setAlive(boolean alive) {
		isAlive = alive;
	}

}
