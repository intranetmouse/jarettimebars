package de.jaret.examples.timebars.events.model;

import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.model.DefaultRowHeader;
import de.jaret.util.ui.timebars.model.DefaultTimeBarModel;
import de.jaret.util.ui.timebars.model.DefaultTimeBarRowModel;
import de.jaret.util.ui.timebars.model.TimeBarModel;

public class ModelCreator {
    public static TimeBarModel createModel() {
        DefaultTimeBarModel model = new DefaultTimeBarModel();

        JaretDate date = new JaretDate();

        int length = 120;

        DefaultRowHeader header = new DefaultRowHeader("r4");
        DefaultTimeBarRowModel tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);
        SampleEvent event = new SampleEvent(date.copy().advanceMinutes(length));
        event.setLabel("label 1");
        tbr.addInterval(event);
        
        
        header = new DefaultRowHeader("r5");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);
        event = new SampleEvent(date.copy().advanceMinutes(length));
        event.setLabel("label 2");
        tbr.addInterval(event);
        event = new SampleEvent(date.copy().advanceMinutes(length*2));
        event.setLabel("label 3");
        tbr.addInterval(event);

        header = new DefaultRowHeader("r6");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r7");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r8");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r9");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r10");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r11");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r12");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r13");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r14");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        return model;
    }

}
