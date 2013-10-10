/*
 *  File: CombiningTimeScaleRenderer.java 
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


import de.jaret.util.misc.Pair;
import de.jaret.util.ui.timebars.TimeBarViewerDelegate;
import de.jaret.util.ui.timebars.TimeBarViewerInterface;
import de.jaret.util.ui.timebars.swt.TimeBarViewer;

/**
 * A TimeScaleRenderer that combines two other TimeScaleRenderers.
 * @author kliem
 *
 */
public class CombiningTimeScaleRenderer implements TimeScaleRenderer{
    protected TimeScaleRenderer _outerRenderer;
    protected TimeScaleRenderer _innerRenderer;
    
    public CombiningTimeScaleRenderer(TimeScaleRenderer outerRenderer, TimeScaleRenderer innerRenderer) {
        _outerRenderer = outerRenderer;
        _innerRenderer = innerRenderer;
        if (_outerRenderer == null || _innerRenderer==null) {
            throw new IllegalArgumentException("renderes must both be set");
        }
    }
        
    
    @Override
    public void draw(GC gc, Rectangle drawingArea, TimeBarViewerDelegate delegate, boolean top, boolean printing) {
        Pair<Rectangle, Rectangle> rects = getDrawingareas(drawingArea, delegate, top);
        Rectangle outerRect = rects.getLeft();
        Rectangle innerRect = rects.getRight();
        
        // and now ... go draw
        _outerRenderer.draw(gc, outerRect, delegate, top, printing);
        _innerRenderer.draw(gc, innerRect, delegate, top, printing);
    }

    @Override
    public String getToolTipText(TimeBarViewer tbv, Rectangle drawingArea, int x, int y) {
        Pair<Rectangle, Rectangle> rects = getDrawingareas(drawingArea, tbv.getDelegate(), tbv.getTimeScalePosition() == TimeBarViewerInterface.TIMESCALE_POSITION_TOP);
        Rectangle outerRect = rects.getLeft();
        Rectangle innerRect = rects.getRight();

        if (outerRect.contains(x, y)) {
            return _outerRenderer.getToolTipText(tbv, outerRect, x, y);
        } else {
            return _innerRenderer.getToolTipText(tbv, innerRect, x, y);
        }
    }

    protected Pair<Rectangle, Rectangle> getDrawingareas(Rectangle drawingArea, TimeBarViewerDelegate delegate, boolean top) {
        boolean horizontal = delegate.getOrientation().equals(TimeBarViewerInterface.Orientation.HORIZONTAL);
        Rectangle da = drawingArea;
        // setup the rectangles for the renderers
        Rectangle outerRect = null;
        Rectangle innerRect = null;
        if (horizontal) {
            if (top) {
                outerRect = new Rectangle(da.x, da.y, da.width, _outerRenderer.getHeight());
                innerRect = new Rectangle(da.x, da.y+outerRect.height, da.width, _innerRenderer.getHeight());
            } else {
                // bottom
                innerRect = new Rectangle(da.x, da.y, da.width, _innerRenderer.getHeight());
                outerRect = new Rectangle(da.x, da.y+innerRect.height, da.width, _outerRenderer.getHeight());
            }
        } else {
            // vertical
            if (top) {
                outerRect = new Rectangle(da.x, da.y, _outerRenderer.getHeight(), da.height);
                innerRect = new Rectangle(da.x+outerRect.width, da.y, _innerRenderer.getHeight(), da.height);
            } else {
                // bottom
                innerRect = new Rectangle(da.x, da.y, _innerRenderer.getHeight(), da.height);
                outerRect = new Rectangle(da.x+innerRect.width, da.y, _outerRenderer.getHeight(), da.height);
            }
        }
        
        
        Pair<Rectangle, Rectangle> result = new Pair<Rectangle, Rectangle>(outerRect, innerRect);
        return result;
    }
    
    
    @Override
    public int getHeight() {
        return _outerRenderer.getHeight()+_innerRenderer.getHeight();
    }

    @Override
    public void dispose() {
        _outerRenderer.dispose();
        _innerRenderer.dispose();
    }

    @Override
    public TimeScaleRenderer createPrintRenderer(Printer printer) {
        return new CombiningTimeScaleRenderer(_outerRenderer.createPrintRenderer(printer), _innerRenderer.createPrintRenderer(printer));
    }

    /**
     * {@inheritDoc}
     */
    public boolean supportsOptimizedScrolling() {
        return _outerRenderer.supportsOptimizedScrolling() && _innerRenderer.supportsOptimizedScrolling();
    }

    
}
