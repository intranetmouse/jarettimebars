/*
 *  File: FzdModel.java 
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
package de.jaret.examples.timebars.fzd.model;

import de.jaret.util.date.JaretDate;

public final class FzdModel
{
    public static UmlaufKettenModel _ukettenModel;
    public static ZuteilungsModel _zuteilungsModel;

    public static ZuteilungsModel createFzdModel(int fahrzeugAnzahl, int averageLengthInMinutes, int countPerUmlauf,
            int umlaufCount) {
        ZuteilungsModel model = new ZuteilungsModel();
        _ukettenModel = new UmlaufKettenModel();
        int row;
        for (row = 0; row < fahrzeugAnzahl; row++) {
            Fahrzeug fahrzeug = new Fahrzeug("4230" + row);
            UmlaufKette kette = new UmlaufKette("kette " + row);
            int km = (int) (Math.random() * 1000);
            fahrzeug.getFahrzeugInfo().setKilometer(km);
            JaretDate date = new JaretDate();
            for (int i = 0; i < umlaufCount; i++) {
                Umlauf umlauf = new Umlauf("U-" + row + "-" + i);
                for (int f = 0; f < countPerUmlauf; f++) {
                    Fahrt fahrt = new Fahrt("MSTH", "MSTH", "F-" + umlauf.getUmlaufbezeichnug() + ":" + f);
                    int length = averageLengthInMinutes / 2 + (int) (Math.random() * (double) averageLengthInMinutes);
                    fahrt.setBegin(date.copy());
                    date.advanceMinutes(length);
                    fahrt.setEnd(date.copy());
    
                    umlauf.addFahrt(fahrt);
                    fahrt.setUmlauf(umlauf);
    
                    int pause = (int) (Math.random() * (double) averageLengthInMinutes / 5);
                    date.advanceMinutes(pause);
                }
                fahrzeug.addUmlauf(umlauf);
                kette.addUmlauf(umlauf);
                date.advanceMinutes(120);
            }
            model.addFahrzeug(fahrzeug);
            _ukettenModel.addUmlaufKette(kette);
        }
        // leere Fahrzeuge
        int addFahrzeuge = 5;
        for (; row < fahrzeugAnzahl + addFahrzeuge; row++) {
            Fahrzeug fahrzeug = new Fahrzeug("4230" + row);
            int km = (int) (Math.random() * 1000);
            fahrzeug.getFahrzeugInfo().setKilometer(km);
            model.addFahrzeug(fahrzeug);
        }
        // nicht zugeteilte Umläufe und Ketten
        int additionalKetten = 5;
        for (row = 0; row < additionalKetten; row++) {
            UmlaufKette kette = new UmlaufKette("Zuskette " + row);
            JaretDate date = new JaretDate();
            for (int i = 0; i < umlaufCount; i++) {
                Umlauf umlauf = new Umlauf("U-" + row + "-" + i);
                for (int f = 0; f < countPerUmlauf; f++) {
                    Fahrt fahrt = new Fahrt("MSTH", "MSTH", "F-" + umlauf.getUmlaufbezeichnug() + ":" + f);
                    int length = averageLengthInMinutes / 2 + (int) (Math.random() * (double) averageLengthInMinutes);
                    fahrt.setBegin(date.copy());
                    date.advanceMinutes(length);
                    fahrt.setEnd(date.copy());
    
                    umlauf.addFahrt(fahrt);
                    fahrt.setUmlauf(umlauf);
    
                    int pause = (int) (Math.random() * (double) averageLengthInMinutes / 5);
                    date.advanceMinutes(pause);
                }
                kette.addUmlauf(umlauf);
                date.advanceMinutes(120);
            }
            _ukettenModel.addUmlaufKette(kette);
        }
    
        System.out.println("Created " + ((fahrzeugAnzahl + additionalKetten) * countPerUmlauf * umlaufCount)
                + " Intervals");
        _zuteilungsModel = model;
        return model;
    }
}