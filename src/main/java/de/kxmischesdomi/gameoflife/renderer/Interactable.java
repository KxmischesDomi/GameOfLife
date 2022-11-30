package de.kxmischesdomi.gameoflife.renderer;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public interface Interactable {

	default boolean keyPressed(GameRenderer applet, KeyEvent event) {
		return false;
	}
	default boolean mouseClicked(GameRenderer applet, MouseEvent event) {
		return false;
	}
	default boolean mouseReleased(GameRenderer applet, MouseEvent event) {
		return false;
	}

}
