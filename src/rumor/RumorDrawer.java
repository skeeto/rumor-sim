package rumor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.val;

/**
 * Observes a spreading rumor, creating an image representing each
 * stage of its spread.
 */
@Log
@RequiredArgsConstructor
public class RumorDrawer implements Observer {

    private static final Color IGNORANT = Color.BLACK;
    private static final Color SPREADER = Color.RED;
    private static final Color STIFLER = Color.WHITE;

    @NonNull
    private final String prefix;
    private final int width;
    private final int scale;

    /** Keep track of how many steps the rumor has taken. */
    private int counter = 0;

    /**
     * Save an image of the rumor state.
     * @param rumor the rumor
     * @param arg   the Person array of the population
     */
    @Override
    public void update(Observable rumor, Object arg) {
        Person[] people = (Person[]) arg;
        int height = (people.length + width - 1) / width;
        val im = new BufferedImage(width * scale, height * scale,
                                   BufferedImage.TYPE_INT_RGB);
        val g = im.createGraphics();
        for (int i = 0; i < people.length; i++) {
            int x = i % width;
            int y = i / width;
            switch (people[i].getMode()) {
            case IGNORANT:
                g.setColor(IGNORANT);
                break;
            case SPREADER:
                g.setColor(SPREADER);
                break;
            case STIFLER:
                g.setColor(STIFLER);
                break;
            }
            g.fillRect(x * scale, y * scale, scale, scale);
        }
        g.dispose();
        File file = new File(String.format("%s%08d.png", prefix, counter));
        try {
            ImageIO.write(im, "PNG", file);
        } catch (IOException e) {
            log.warning("failed to write " + file);
        }
        counter++;
    }
}
