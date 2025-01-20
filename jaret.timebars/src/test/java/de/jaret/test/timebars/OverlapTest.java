package de.jaret.test.timebars;

import java.util.Random;

import org.junit.Test;

import de.jaret.util.date.IntervalImpl;
import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.TimeBarViewerDelegate;
import de.jaret.util.ui.timebars.model.DefaultTimeBarModel;
import de.jaret.util.ui.timebars.model.DefaultTimeBarRowModel;
import de.jaret.util.ui.timebars.model.TimeBarModel;
import de.jaret.util.ui.timebars.strategy.DefaultOverlapStrategy;

public class OverlapTest {

    @Test
    public void testUpdateOICache() {
        int count = 10000;
        boolean assumeSorted = true;
        TimeBarModel model = createModel(count);
        TimeBarViewerDelegate delegate = new TimeBarViewerDelegate(null);
        delegate.setModel(model);
        DefaultOverlapStrategy strategy = new DefaultOverlapStrategy(delegate);
        strategy.setAssumeSortedIntervals(assumeSorted);
        
        delegate.setOverlapStrategy(strategy);
        long time = System.currentTimeMillis();
        strategy.updateOICache(model.getRow(0));
        System.out.println("Overlapping "+count+" intervals "+(System.currentTimeMillis()-time)+"ms assumeSorted: "+assumeSorted);

        count = 10000;
        assumeSorted = false;
        model = createModel(count);
        delegate = new TimeBarViewerDelegate(null);
        delegate.setModel(model);
        strategy = new DefaultOverlapStrategy(delegate);
        strategy.setAssumeSortedIntervals(assumeSorted);
        
        delegate.setOverlapStrategy(strategy);
        time = System.currentTimeMillis();
        strategy.updateOICache(model.getRow(0));
        System.out.println("Overlapping "+count+" intervals "+(System.currentTimeMillis()-time)+"ms assumeSorted: "+assumeSorted);

    }

    DefaultTimeBarModel createModel(int count) {
        long time = System.currentTimeMillis();
        DefaultTimeBarModel model = new DefaultTimeBarModel();
        DefaultTimeBarRowModel row = new DefaultTimeBarRowModel();
        model.addRow(row);
        
        Random rnd = new Random(1);
        
        JaretDate start = new JaretDate();
        
        for(int i=0;i<count;i++) {
            start.advanceMinutes(rnd.nextDouble()*30);
            JaretDate end = start.copy().advanceMinutes(rnd.nextDouble()*30);
            IntervalImpl interval = new IntervalImpl(start, end);
            row.addInterval(interval);
        }
        
        System.out.println("create model "+count+ " "+(System.currentTimeMillis()-time)+"ms");
        
        return model;
        
    }
    
}
