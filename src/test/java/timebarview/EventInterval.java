package timebarview;

import de.jaret.util.date.IntervalImpl;
import de.jaret.util.date.JaretDate;

public class EventInterval extends IntervalImpl {
    private Event _event;

    public EventInterval(JaretDate from, JaretDate to, Event event) {
        super(from, to);
        _event = event;
    }

    public EventInterval(EventInterval intv) {
        this(intv.getBegin().copy(), intv.getEnd().copy(), new Event(intv.getEvent()));
    }

    /**
     * @return the _event
     */
    public Event getEvent() {
        return _event;
    }

    /**
     * @param event the _event to set
     */
    public void setEvent(Event event) {
        _event = event;
    }

    @Override
    public String toString() {
        return _event.getTitle();
    }
}