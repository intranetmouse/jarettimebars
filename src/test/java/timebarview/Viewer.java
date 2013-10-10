package timebarview;

import de.jaret.util.date.JaretDate;
import de.jaret.util.date.iterator.DateIterator.Format;
import de.jaret.util.ui.timebars.model.DefaultRowHeader;
import de.jaret.util.ui.timebars.model.DefaultTimeBarModel;
import de.jaret.util.ui.timebars.model.DefaultTimeBarRowModel;
import de.jaret.util.ui.timebars.model.TimeBarSelectionModel;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.Random;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Viewer extends JFrame{
    public enum RowNames {Multicast, Unicast, MissionPhase};
    private final JaretDate TODAY = new JaretDate().setTime(0, 0, 0);
    private DefaultTimeBarModel _tbm;
    private EnumMap<RowNames, DefaultTimeBarRowModel> _rows = new EnumMap(RowNames.class);
    private TimeBarViewer _tbvEvents = new CustomTimeBarViewer();
    private JButton _reload = new JButton("Reload");

    public Viewer() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        add(_tbvEvents);
        add(_reload);
        _reload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAllEvents();
                loadEvents();
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        _tbm = new DefaultTimeBarModel();
        DefaultTimeBarRowModel tbr;
        for (RowNames name : RowNames.values()) {
            tbr = new DefaultTimeBarRowModel(new DefaultRowHeader(name.name()));
            _rows.put(name, tbr);
            _tbm.addRow(tbr);
        }
        TimeBarSelectionModel tbs = _tbvEvents.getSelectionModel();
        tbs.setMultipleSelectionAllowed(false);
        tbs.setRowSelectionAllowed(false);

        _tbvEvents.setModel(_tbm);
        _tbvEvents.setTimeScalePosition(TimeBarViewer.TIMESCALE_POSITION_TOP);
        _tbvEvents.addIntervalModificator(new NoNegativeIntervalModificator());
   //     _tbvEvents.setMinDate(TODAY.copy());
   //     _tbvEvents.setMaxDate(TODAY.copy().advanceDays(1));
        _tbvEvents.setTimeScaleRenderer(new CustomTimeScaleRenderer(Format.MEDIUM));
        _tbvEvents.setPixelPerSecond(1);
        loadEvents();
        pack();
    }

    public void clearAllEvents() {
        for (RowNames name :RowNames.values())
            clearEvents(name);
    }

    public void clearEvents(RowNames name) {
        if (_rows.get(name).getIntervals().size() == 0)
            return;
        _rows.get(name).clear();
   //     _tbvEvents.repaint();
    }

    public void addEvents(RowNames name, Vector<Event> events) {
        for(Event event : events)
            addEvent(name, event);
    }

    public void addEvent(RowNames name, Event event) {
        _rows.get(name).addInterval(new EventInterval(getTime(event.getStart()), getTime(event.getStop()), event));
        _tbvEvents.repaint();
    }

    public void loadEvents() {
        Vector<Event> events = new Vector<Event>();
        Random r = new Random();
        for(RowNames name : RowNames.values()) {
            for(int i = 0; i < 4; i++)
                events.add(new Event(r.nextInt(4), 5+r.nextInt(5)));
            addEvents(name, events);
            events.clear();
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Viewer view = new Viewer();
        view.setVisible(true);
    }

    private JaretDate getTime(int min) {
        return TODAY.copy().setTime(0, min, 0);
    }
}