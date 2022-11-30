package de.kxmischesdomi.gameoflife.renderer.button;

import de.kxmischesdomi.gameoflife.renderer.GameRenderer;
import processing.event.MouseEvent;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class IntegerButton extends ControlButton {

    private int value;
    private int minValue, maxValue;

    public IntegerButton(int x, int y, int width, int height, int minValue, int maxValue, int defaultValue) {
        super(x, y, width, height);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = defaultValue;
    }

    @Override
    public void onClicked() {
        setValue(getValue() + 1);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value > maxValue) {
            this.value = minValue;
        } else if (value < minValue) {
            this.value = maxValue;
        } else {
            this.value = value;
        }
    }

}
