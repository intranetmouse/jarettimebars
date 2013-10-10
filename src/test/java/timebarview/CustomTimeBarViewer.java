package timebarview;

import de.jaret.util.ui.timebars.model.TimeBarModel;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;

/**
 * {@inheritDoc}
 */
@SuppressWarnings("serial")
public class CustomTimeBarViewer extends TimeBarViewer {
    /**
     * Constructs a timebar viewer.
     * 
     * @param model TimeBarModel to be used. The model may be <code>null</code>.
     * @param suppressXScroll if true the x scrollbar will not be displayed
     * @param suppressYScroll if true the y scrollbar will not be displayed
     */
    public CustomTimeBarViewer(TimeBarModel model, boolean suppressXScroll, boolean suppressYScroll) {
        super(model, suppressXScroll, suppressYScroll);
    }

    /**
     * Constructs a timebarviewer with both y and x scrollbars.
     * 
     * @param model timebarmodel to be displayed. The model may be <code>null</code>.
     */
    public CustomTimeBarViewer(TimeBarModel model) {
        this(model, false, false);
    }

    /**
     * Constructs a timebarviewer without a model.
     * 
     */
    public CustomTimeBarViewer() {
        this(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override public String getName() {
        return (_delegate == null) ? "" : _delegate.getName();
    }
}