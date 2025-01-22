/*
 *  File: PdiExample.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package de.jaret.examples.timebars.pdi.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import de.jaret.examples.timebars.pdi.model.Duty;
import de.jaret.examples.timebars.pdi.model.PdiData;
import de.jaret.examples.timebars.pdi.model.PersonenDisposition;
import de.jaret.util.date.Interval;
import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.TimeBarViewerSynchronizer;
import de.jaret.util.ui.timebars.model.TimeBarRow;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.dnd.IntervalListTransferable;
import de.jaret.util.ui.timebars.swing.dnd.RowIntervalTuple;
import de.jaret.util.ui.timebars.swing.renderer.DefaultGapRenderer;

/**
 * @author Peter Kliem
 * @version $Id: PdiExample.java 426 2007-05-13 15:41:49Z olk $
 */
public class PdiExample {
    public static void main(String[] args) {
        JFrame f = new JFrame(PdiExample.class.getName());
        f.setSize(1200, 700);
        f.getContentPane().setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PdiData.createModels(200, 28, 240);
        // createModels(20, 10, 20);
        
        final TimeBarViewer tbv = new TimeBarViewer(PdiData._dispoModel, false, false);
        tbv.setName("upper");
        tbv.setTimeScalePosition(TimeBarViewer.TIMESCALE_POSITION_TOP);
        tbv.setTimeScaleRenderer(new PdiTimeScaleRenderer(PdiData._pdiCalendar));
        tbv.setHeaderRenderer(new PdiHeaderRenderer());
        tbv.setGridRenderer(new PdiGridRenderer(PdiData._pdiCalendar));
        tbv.setStartDate(new JaretDate());
        tbv.setDrawRowGrid(true);
        tbv.setTimeBarRenderer(new PdiRenderer());
        tbv.setPixelPerSecond(200.0 / (24.0 * 60.0 * 60.0));
        tbv.setGapRenderer(new DefaultGapRenderer());
        // Dienste ohne dispo info
        TimeBarViewer tbv2 = new TimeBarViewer(PdiData._dutyMass.getTimeBarModel(null), false, false);
        tbv2.setTimeScalePosition(TimeBarViewer.TIMESCALE_POSITION_NONE);
        tbv2.setName("lower");
        tbv2.setGridRenderer(new PdiGridRenderer(PdiData._pdiCalendar));
        tbv2.setDrawRowGrid(true);
        tbv2.setTimeBarRenderer(new PdiRenderer());
        tbv2.setPixelPerSecond(200.0 / (24.0 * 60.0 * 60.0));
        tbv2.setAdjustMinMaxDatesByModel(false);
        tbv2.setMinDate(tbv.getMinDate());
        tbv2.setMaxDate(tbv.getMaxDate());
        tbv2.setStartDate(tbv.getStartDate());
        tbv2.setYAxisWidth(tbv.getYAxisWidth());

        // DragSource dragSource = DragSource.getDefaultDragSource();
        // DragGestureListener dgl = new TimeBarViewerDragGestureListener();
        // DragGestureRecognizer dgr =
        // dragSource.createDefaultDragGestureRecognizer(tbv2._diagram,
        // DnDConstants.ACTION_MOVE, dgl);
        /*
         * DropTargetListener dtl = new TimeBarDropTargetListener(); DropTarget
         * dt = new DropTarget(this, dtl);
         */
        DropTargetListener dtl = new ZuweisenDropTargetListener(tbv);
        DropTarget dt = new DropTarget(tbv, dtl);

        // synchronize the TimeBarViewers by a synchronizer
        TimeBarViewerSynchronizer synchronizer = new TimeBarViewerSynchronizer(false, true, true);
        synchronizer.addViewer(tbv);
        synchronizer.addViewer(tbv2);

        JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitter.add(tbv);
        splitter.add(tbv2);
        // f.getContentPane().add(tbv, BorderLayout.CENTER);
        f.getContentPane().add(splitter, BorderLayout.CENTER);

        // add the control panel
        ControlPanel1 cp = new ControlPanel1(tbv, null, false);
        f.getContentPane().add(cp, BorderLayout.NORTH);
        // add the control panel ketten
        ControlPanel2 cp2 = new ControlPanel2(tbv2, null);
        f.getContentPane().add(cp2, BorderLayout.SOUTH);

        f.setVisible(true);

        splitter.setDividerLocation(0.5);

    }

    static class TimeBarViewerDragGestureListener implements DragGestureListener {
        public void dragGestureRecognized(DragGestureEvent e) {
            Component c = e.getComponent();
            System.out.println("component " + c);
            // TimeeBarViewer tbv = (D)
            /*
             * TimeBarViewer tbv = (TimeBarViewer) ((Diagram) c)._timeBarViewer;
             * TimeBarSelectionModel selModel = tbv.getSelectionModel(); if
             * (selModel.hasIntervalSelection()) { List l =
             * selModel.getSelectedIntervals(); List rowIntervalTuples =
             * buildRowIntervalTuples(l);
             * 
             * IntervalListTransferable ilt = new
             * IntervalListTransferable(rowIntervalTuples); e.startDrag(null,
             * ilt); } else { // nothing to drag System.out.println("nothing to
             * drag"); } } private List buildRowIntervalTuples(List l) { List
             * rowIntervalTuples = new ArrayList(); List list = new
             * ArrayList(l); RowIntervalTuple tuple = new RowIntervalTuple(null,
             * list); rowIntervalTuples.add(tuple); return rowIntervalTuples; }
             */
        }
    }

    static class ZuweisenDropTargetListener extends DropTargetAdapter {
        TimeBarViewer _tbv;

        public ZuweisenDropTargetListener(TimeBarViewer tbv) {
            _tbv = tbv;
        }

        public void dragOver(DropTargetDragEvent e) {
            if (e.isDataFlavorSupported(IntervalListTransferable.intervalListFlavor)) {
                TimeBarRow row = _tbv.rowForY(e.getLocation().y);
                if (row != null && e.isDataFlavorSupported(IntervalListTransferable.intervalListFlavor)) {
                    // the transferable is not
                    if (row.getIntervals(_tbv.dateForX(e.getLocation().x)).size() == 0) {
                        _tbv.highlightRow(row);
                        e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
                        return;
                    }
                }
                e.rejectDrag();
                _tbv.deHighlightRow();
            }
        }

        public void dragExit(DropTargetEvent dte) {
            _tbv.deHighlightRow();
        }

        public void drop(DropTargetDropEvent e) {
            try {
                Transferable tr = e.getTransferable();
                DataFlavor[] flavors = tr.getTransferDataFlavors();
                PersonenDisposition pdispo = (PersonenDisposition) _tbv.rowForY(e.getLocation().y);
                for (int i = 0; i < flavors.length; i++) {
                    if (flavors[i].equals(IntervalListTransferable.intervalListFlavor)) {
                        // e.rejectDrop();
                        e.acceptDrop(e.getDropAction());
                        List l = (List) e.getTransferable()
                                .getTransferData(IntervalListTransferable.intervalListFlavor);
                        List intervals = ((RowIntervalTuple) l.get(0)).getIntervals();
                        Iterator it = intervals.iterator();
                        while (it.hasNext()) {
                            Interval interval = (Interval) it.next();
                            Duty d = (Duty) interval;
                            if (pdispo.allowed(d)) {
                                pdispo.addDienst(d);
                            }
                        }
                        _tbv.deHighlightRow();
                        e.dropComplete(true);
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            e.rejectDrop();
        }
    }

}
