package de.kxmischesdomi.gameoflife.renderer.button;

import de.kxmischesdomi.gameoflife.renderer.GameRenderer;
import processing.event.MouseEvent;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class BooleanButton extends ControlButton {

	private final Function<Boolean, String> updateAction;

	private String text;
	private boolean value;

	public BooleanButton(int x, int y, int width, int height, boolean defaultValue, String text, Function<Boolean, String> updateAction) {
		super(x, y, width, height);
		this.value = defaultValue;
		this.text = text;
		this.updateAction = updateAction;
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
		text = updateAction.apply(value);
	}

	@Override
	public String getText() {
		return text;
	}

}
