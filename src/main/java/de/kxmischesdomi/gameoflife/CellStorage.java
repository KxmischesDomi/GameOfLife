package de.kxmischesdomi.gameoflife;

import de.kxmischesdomi.gameoflife.data.CellData;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class CellStorage {

	// TODO: CELL DATA OBJECT IS NOT NECESSARY IN THIS MAP. COULD BE REPLACED BY A LIST WITH Y COORDINATES OF LIVING CELLS
	private Map<Integer, Map<Integer, CellData>> cellDataMap;

	public CellStorage() {
		this.cellDataMap = new TreeMap<>();
	}

	public CellData getCell(int x, int y) {
		Map<Integer, CellData> map = cellDataMap.get(x);
		if (map == null) return null;
		return map.get(y);
	}

	public Collection<CellData> getCells() {
		return cellDataMap.values().stream().flatMap(map -> map.values().stream()).collect(Collectors.toList());
	}

	public void toggleCell(int x, int y) {
		Map<Integer, CellData> map = cellDataMap.computeIfAbsent(x, k -> new TreeMap<>());
		CellData cellData = map.get(y);
		if (cellData == null) {
			cellData = new CellData(x, y);
			cellData.setAlive(true);
			map.put(y, cellData);
		} else {
			map.remove(y);
		}
	}

	public Map<Integer, Map<Integer, CellData>> getCellDataMap() {
		return cellDataMap;
	}

}
