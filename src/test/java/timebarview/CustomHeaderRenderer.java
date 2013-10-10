package timebarview;

import de.jaret.util.ui.timebars.swing.renderer.DefaultHeaderRenderer;

public class CustomHeaderRenderer extends DefaultHeaderRenderer {
    /** component used for rendering. */
    private int _width;

    public CustomHeaderRenderer(int width) {
        _width = width;
    }

    /**
     * {@inheritDoc}
     */
    @Override public int getWidth() {
        return _width;
    }
}