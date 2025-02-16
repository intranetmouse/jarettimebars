/*
 *  File: TBRect.java 
 *  Copyright (c) 2004-2008  Peter Kliem (Peter.Kliem@jaret.de)
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
package de.jaret.util.ui.timebars.model;

import de.jaret.util.date.JaretDate;

/**
 * Simple struct holding the information about a region in the time bar viewer.
 * 
 * @author kliem
 * @version $Id: TBRect.java 800 2008-12-27 22:27:33Z kliem $
 */
public class TBRect {
    /** staring date. */
    public JaretDate startDate;
    /** ending date. */
    public JaretDate endDate;
    /** first row. */
    public TimeBarRow startRow;
    /** last row. */
    public TimeBarRow endRow;

}
