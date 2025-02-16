/*
 *  File: IIntervalModificator.java 
 *  Copyright (c) 2004-2009  Peter Kliem (Peter.Kliem@jaret.de)
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
package de.jaret.util.ui.timebars.mod;

import de.jaret.util.date.Interval;
import de.jaret.util.ui.timebars.model.TimeBarRow;

/**
 * Extension of the interval modificator interface that includes a more specific method for getting the gridsnap for an interval.
 * The specific grid snap will be used if returned (instead of t he unspecific snap)
 * 
 * 
 * @author kliem
 * @version $Id$
 */
public interface IIntervalModificator extends IntervalModificator {
    /**
     * If this method returns a positive value this is used as the modification interval. The value is given in seconds.
     * 
     * @param row row of the interval
     * @param interval interval in question
     * @return the positive grid snap or a negative value indicating no grid snap
     */
    double getSecondGridSnap(TimeBarRow row, Interval interval);

}
