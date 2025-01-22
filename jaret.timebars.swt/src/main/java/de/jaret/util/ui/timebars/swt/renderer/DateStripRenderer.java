/*
 *  File: DateStripRenderer.java 
 *  Copyright (c) Peter Kliem (peter@kliemax.de)
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
package de.jaret.util.ui.timebars.swt.renderer;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.Printer;

import de.jaret.util.date.JaretDate;
import de.jaret.util.swt.SwtGraphicsHelper;
import de.jaret.util.ui.timebars.TimeBarViewerDelegate;
import de.jaret.util.ui.timebars.TimeBarViewerInterface;
import de.jaret.util.ui.timebars.swt.TimeBarViewer;

/**
 * A simple TimeScaleRenderer that will always show the starting date. This can be quite useful in combination with
 * other TimeScaleRenderers using a CombiningTimeScaleRenderer.
 * 
 * @author kliem
 * 
 */
public class DateStripRenderer extends RendererBase implements TimeScaleRenderer {

    public DateStripRenderer() {
        super(null);
    }

    public DateStripRenderer(Printer printer) {
        super(printer);
    }

    @Override
    public void draw(GC gc, Rectangle drawingArea, TimeBarViewerDelegate delegate, boolean top, boolean printing) {
        boolean horizontal = delegate.getOrientation().equals(TimeBarViewerInterface.Orientation.HORIZONTAL);
        JaretDate startDate = delegate.getStartDate().copy();
        if (horizontal) {

            // clearbackground
            // TODO
            SwtGraphicsHelper.drawStringLeftAlignedVCenter(gc, startDate.toDisplayString(),
                    delegate.getYAxisWidth() + 5, drawingArea.y + drawingArea.height / 2);
            gc.drawLine(drawingArea.x, drawingArea.y + drawingArea.height - 1, drawingArea.x + drawingArea.width,
                    drawingArea.y + drawingArea.height - 1);
        } else {
            SwtGraphicsHelper.drawStringVertical(gc, startDate.toDisplayString(), drawingArea.x , drawingArea.y + 5);

            
            gc.drawLine(drawingArea.x+drawingArea.width-1, drawingArea.y, drawingArea.x+drawingArea.width-1,
                    drawingArea.y + drawingArea.height - 1);

        }
    }

    @Override
    public String getToolTipText(TimeBarViewer tbv, Rectangle drawingArea, int x, int y) {
        return null;
    }

    @Override
    public int getHeight() {
        return scaleY(20);
    }

    @Override
    public void dispose() {
        // nothing to dispose
    }

    @Override
    public TimeScaleRenderer createPrintRenderer(Printer printer) {
        return new DateStripRenderer(printer);
    }

    /**
     * {@inheritDoc}
     */
    public boolean supportsOptimizedScrolling() {
        return false;
    }

    
}
