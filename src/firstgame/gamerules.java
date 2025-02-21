package firstgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class gamerules {


    public static float enforceHorizontalBoundary(GameContainer container, float x, Image img) {
        // Linker Rand
        if (x < 0) {
            x = 0;
        }
        // Rechter Rand (Fensterbreite - Bildbreite)
        if (x + img.getWidth() > container.getWidth()) {
            x = container.getWidth() - img.getWidth();
        }
        return x;
    }

    public static float enforceVerticalBoundary(GameContainer container, float y, Image img) {
        // Oberer Rand
        if (y < 0) {
            y = 0;
        }
        // Unterer Rand (Fensterhöhe - Bildhöhe)
        if (y + img.getHeight() > container.getHeight()) {
            y = container.getHeight() - img.getHeight();
        }
        return y;
    }
}
