package de.kxmischesdomi.gameoflife.renderer.button;

import de.kxmischesdomi.gameoflife.renderer.GameRenderer;
import de.kxmischesdomi.gameoflife.renderer.Interactable;
import de.kxmischesdomi.gameoflife.renderer.Renderable;
import processing.event.MouseEvent;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public abstract class ControlButton implements Renderable, Interactable {

	protected final int x, y, width, height;

	public ControlButton(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean mouseClicked(GameRenderer applet, MouseEvent event) {
		if (isHovered(applet)) {
			onClicked();
			return true;
		}
		return Interactable.super.mouseClicked(applet, event);
	}

	public abstract void onClicked();

	@Override
	public void render(GameRenderer applet) {
		applet.pushStyle();
		int color = getColor();
		if (isHovered(applet)) {
			color = applet.lerpColor(color, applet.color(255), 0.5F);
		}
		applet.fill(color);
		applet.rect(x, y, width, height);
		applet.popStyle();
		renderButtonContent(applet);
	}

	public boolean isHovered(GameRenderer applet) {
		return applet.mouseX >= x && applet.mouseX <= x + width && applet.mouseY >= y && applet.mouseY <= y + height;
	}

	public int getColor() {
		return 0xAA000000;
	}

	public void renderButtonContent(GameRenderer applet) {
		applet.pushStyle();
		applet.fill(0xFFFFFFFF);
		applet.textSize(80);
		applet.textAlign(GameRenderer.CENTER, GameRenderer.CENTER);
		applet.text(getText(), x + width / 2F, y + height / 2F);
		applet.popStyle();
	}

	public String getText() {
		return "";
	}

}
