package de.kxmischesdomi.gameoflife.renderer.button;

import de.kxmischesdomi.gameoflife.GameOfLife;

import java.text.DecimalFormat;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class SpeedButton extends IntegerButton {

    public static final float[] speeds = { 1, 1.5f, 2, 3, 5, 0.5f };

    public SpeedButton(int x, int y, int width, int height) {
        super(x, y, width, height, 0, speeds.length - 1, 0);
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
        GameOfLife.getInstance().getComputing().speed = getSpeed();
    }

    @Override
    public String getText() {
        DecimalFormat df = new DecimalFormat("#.#");
        return String.format("%sx", df.format(getSpeed()));
    }

    private float getSpeed() {
        return speeds[Math.max(0, Math.min(speeds.length - 1, getValue()))];
    }

}
