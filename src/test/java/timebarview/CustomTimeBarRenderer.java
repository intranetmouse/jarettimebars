package timebarview;

import de.jaret.util.date.Interval;
import java.awt.Color;

import javax.swing.JComponent;

import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.DefaultTimeBarRenderer;

public class CustomTimeBarRenderer extends DefaultTimeBarRenderer {
    private Color _unselected;
    private Color _selected;

    public CustomTimeBarRenderer(Color unselected, Color selected) {
        _unselected = unselected;
        _selected = selected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent defaultGetTimeBarRendererComponent(TimeBarViewer tbv, Interval value, boolean isSelected, boolean overlapping) {
        _component.setText(value.toString());
        _component.setToolTipText(value.toString());
        if (isSelected) {
            _component.setBackground(_selected);
        } else {
            _component.setBackground(_unselected);
        }
        return _component;
    }
}