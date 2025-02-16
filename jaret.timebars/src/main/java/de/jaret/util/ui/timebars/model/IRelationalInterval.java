/*
 *  File: IRelationalInterval.java 
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

import java.util.List;

import de.jaret.util.date.Interval;

/**
 * Interval carrying a list of relations.
 * 
 * @author kliem
 * @version $Id: IRelationalInterval.java 722 2008-03-18 19:34:03Z kliem $
 */
public interface IRelationalInterval extends Interval {

    /** relation property name constant. */
    String RELATIONS = "Relations";

    /**
     * Retrieve all relations of the interval.
     * 
     * @return list of relations
     */
    List<IIntervalRelation> getRelations();

    /**
     * Add an interval relation.
     * 
     * @param relation relation to be added
     */
    void addRelation(IIntervalRelation relation);

    /**
     * Remove a relation.
     * 
     * @param relation relation to be removed.
     */
    void removeRelation(IIntervalRelation relation);

}
