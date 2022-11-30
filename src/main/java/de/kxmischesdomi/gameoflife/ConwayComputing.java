package de.kxmischesdomi.gameoflife;

import de.kxmischesdomi.gameoflife.data.CellData;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class ConwayComputing {

	private GameOfLife gameOfLife;

	public boolean running = false;
	public long lastComputingMillis = 0;
	public float speed = 1;

	public ConwayComputing(GameOfLife gameOfLife) {
		this.gameOfLife = gameOfLife;
	}

	public void checkForNextGen() {
		if (!running) return;
		if (System.currentTimeMillis() - lastComputingMillis < 1000 / speed) return;
		lastComputingMillis = System.currentTimeMillis();
		startNextGen();
	}

	public void startNextGen() {
		Collection<CellData> data = collectMajorCellData();
		Map<Integer, Map<Integer, CellData>> cellDataMap = gameOfLife.getCellStorage().getCellDataMap();
		cellDataMap.clear();

		data.forEach(cellData -> {
			int livingNeighbors = cellData.getLivingNeighbors();
			if (cellData.isAlive()) {
				if (livingNeighbors == 2 || livingNeighbors == 3) {
					cellDataMap.computeIfAbsent(cellData.getX(), integer -> new HashMap<>()).put(cellData.getY(), cellData);
				}
			} else {
				if (livingNeighbors == 3) {
					cellData.setAlive(true);
					cellDataMap.computeIfAbsent(cellData.getX(), integer -> new HashMap<>()).put(cellData.getY(), cellData);
				}
			}
		});
	}

	public Collection<CellData> collectMajorCellData() {
		Map<Integer, Map<Integer, CellData>> majorCellData = new HashMap<>();

		CellStorage cellStorage = gameOfLife.getCellStorage();
		Map<Integer, Map<Integer, CellData>> cellDataMap = cellStorage.getCellDataMap();

		for (Map.Entry<Integer, Map<Integer, CellData>> entry : cellDataMap.entrySet()) {
			for (Map.Entry<Integer, CellData> dataEntry : entry.getValue().entrySet()) {

				for (int xOff = -1; xOff < 2; xOff++) {
					for (int yOff = -1; yOff < 2; yOff++) {
						int x = entry.getKey() + xOff;
						int y = dataEntry.getKey() + yOff;
						boolean alive = xOff == 0 && yOff == 0;

						Map<Integer, CellData> map = majorCellData.computeIfAbsent(x, integer -> new HashMap<>());
						CellData data = map.get(y);
						if (data == null) {
							data = new CellData(x, y);
							map.put(y, data);
						}
						if (alive) {
							data.setAlive(true);
						} else {
							data.setLivingNeighbors(data.getLivingNeighbors() + 1);
						}
					}
				}


			}
		}

		return majorCellData.values().stream().flatMap(map -> map.values().stream()).collect(Collectors.toList());
	}

}
