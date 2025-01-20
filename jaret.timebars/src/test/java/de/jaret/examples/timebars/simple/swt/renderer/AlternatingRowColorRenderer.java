/*
 *  File: AlternatingRowColorRenderer.java 
 *  Copyright (c) 2004-20012  Peter Kliem (Peter.Kliem@jaret.de)
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
package de.jaret.examples.timebars.simple.swt.renderer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.Printer;

import de.jaret.examples.timebars.fancy.model.FancyInterval;
import de.jaret.util.date.Interval;
import de.jaret.util.ui.timebars.TimeBarViewerDelegate;
import de.jaret.util.ui.timebars.model.TimeBarRow;
import de.jaret.util.ui.timebars.swt.TimeBarViewer;
import de.jaret.util.ui.timebars.swt.renderer.GlobalAssistantRenderer;
import de.jaret.util.ui.timebars.swt.renderer.RendererBase;


/**
 * Gloabl renderer that draws rows in alternating colors.
 * 
 * @author kliem
 * @version $Id: FancyGlobalRenderer.java 558 2007-09-08 07:40:22Z olk $
 */
public class AlternatingRowColorRenderer extends RendererBase implements GlobalAssistantRenderer {

    public AlternatingRowColorRenderer() {
        super(null);
    }

    public AlternatingRowColorRenderer(Printer printer) {
        super(printer);
    }

    public GlobalAssistantRenderer createPrintRenderer(Printer printer) {
        return new AlternatingRowColorRenderer(printer);
    }

    public void dispose() {

    }

    public void doRenderingBeforeIntervals(TimeBarViewerDelegate delegate, GC gc, boolean printing) {
            for (int r = 0; r < delegate.getModel().getRowCount(); r++) {
                TimeBarRow row = delegate.getModel().getRow(r);
                if (delegate.isRowDisplayed(row)) {
                    if (r%2==0) {
                        // every second row
                        Rectangle rect = TimeBarViewer.convertRect(delegate.getRowBounds(row));
                        int alpha = gc.getAlpha();
                        Color bg = gc.getBackground();
                        gc.setAlpha(30);
                        gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_DARK_GREEN));
                        gc.fillRectangle(rect);
                        gc.setAlpha(alpha);
                        gc.setBackground(bg);
                    }
                    
                }
        }
    }

    public void doRenderingLast(TimeBarViewerDelegate delegate, GC gc, boolean printing) {
    }


}
