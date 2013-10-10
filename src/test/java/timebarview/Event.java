package timebarview;

public class Event {
    protected int _start;
    protected int _stop;

    public Event() { }
    
    public Event(int start, int stop) {
        _start = start;
        _stop = stop;
    }

    public Event(Event toCopy) {
        _start = toCopy.getStart();
        _stop = toCopy.getStop();
    }

    /**
     * @return the _start
     */
    public int getStart() {
        return _start;
    }

    /**
     * @param start the _start to set
     */
    public void setStart(int start) {
        _start = start;
    }

    /**
     * @return the _stop
     */
    public int getStop() {
        return _stop;
    }

    /**
     * @param stop the _stop to set
     */
    public void setStop(int stop) {
        _stop = stop;
    }

    public String getTitle() {
        return String.format("%d - %d", _start, _stop);
    }
}