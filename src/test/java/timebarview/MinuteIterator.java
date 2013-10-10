package timebarview;

import de.jaret.util.date.JaretDate;
import de.jaret.util.date.iterator.DateIterator.Format;
import de.jaret.util.date.iterator.IIteratorFormatter;

public class MinuteIterator extends de.jaret.util.date.iterator.MinuteIterator {
    public MinuteIterator(int minuteStep) {
        super(minuteStep);
        _defaultFormatter = new IIteratorFormatter() {
            public String getLabel(JaretDate date, Format format) {
                switch(format) {
                    case SHORT:
                        return Integer.toString(date.getMinutes());
                    case MEDIUM:
                        return NF.format(date.getHours()) + ":" + NF.format(date.getMinutes());
                    case LONG:
                        return NF.format(date.getHours()) + ":" + NF.format(date.getMinutes()) + ":" + NF.format(date.getSeconds());
                }
                return "";
            }
        };
    }
}