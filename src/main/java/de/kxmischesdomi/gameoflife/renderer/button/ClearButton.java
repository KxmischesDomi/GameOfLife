package de.kxmischesdomi.gameoflife.renderer.button;

import de.kxmischesdomi.gameoflife.GameOfLife;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class ClearButton extends ControlButton {

    public ClearButton(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void onClicked() {
        GameOfLife.getInstance().getCellStorage().getCellDataMap().clear();
    }

    @Override
    public String getText() {
        return "Clear";
    }
}
