package timebarview;

import de.jaret.util.date.Interval;
import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.mod.DefaultIntervalModificator;
import de.jaret.util.ui.timebars.model.TimeBarRow;

public class NoNegativeIntervalModificator extends DefaultIntervalModificator {
    private final JaretDate TODAY = new JaretDate().setTime(0, 0, 0);

    @Override
    public boolean newBeginAllowed(TimeBarRow row, Interval interval, JaretDate newBegin) {
        return (newBegin.compareTo(TODAY) >= 0);
    }

    @Override
    public boolean shiftAllowed(TimeBarRow row, Interval interval, JaretDate newBegin) {
        return (newBegin.compareTo(TODAY) >= 0);
    }

    @Override
    public double getSecondGridSnap() {
        return 60; //snap to minutes
    }
}