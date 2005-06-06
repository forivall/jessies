package e.ptextarea;


import java.awt.*;

/**
 * A PTabSegment is a PLineSegment which knows how to draw, and calculate positions of,
 * one or more tab characters.
 * 
 * @author Phil Norman
 */

public class PTabSegment extends PAbstractSegment {
    public static final PTabSegment SINGLE_TAB = new PTabSegment(null, 0, 1);
    
    private static final int MIN_TAB_WIDTH_IN_PIXELS = 5;
    
    private static final int SPACES_PER_TAB = 8;
    
    public PTabSegment(PTextArea textArea, int start, int end) {
        super(textArea, start, end, PStyle.NORMAL);
        if (end == start) {
            throw new IllegalArgumentException("getDisplayWidth incorrectly doesn't always return zero for zero length tab segments");
        }
    }
    
    public PLineSegment subSegment(int start, int end) {
        return new PTabSegment(textArea, start + this.start, end + this.start);
    }
    
    private int tabWidth(FontMetrics metrics) {
        return (SPACES_PER_TAB * metrics.charWidth(' '));
    }
    
    public int getDisplayWidth(FontMetrics metrics, int startX) {
        final int tabWidth = tabWidth(metrics);
        int x = startX + MIN_TAB_WIDTH_IN_PIXELS + tabWidth * getLength();
        x -= x % tabWidth;
        return (x - startX);
    }
    
    public int getCharOffset(FontMetrics metrics, int startX, int x) {
        final int tabWidth = tabWidth(metrics);
        for (int i = 0; i < getLength(); i++) {
            int nextX = startX + MIN_TAB_WIDTH_IN_PIXELS + tabWidth;
            nextX -= nextX % tabWidth;
            if (x < nextX) {
                if (x > (nextX + startX) / 2) {
                    return i + 1;
                } else {
                    return i;
                }
            }
            startX = nextX;
        }
        return getLength();
    }
    
    public void paint(Graphics2D graphics, int x, int yBaseline) { }
}
