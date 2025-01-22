package de.jaret.examples.timebars.pdi.model;

import java.util.Iterator;
import java.util.List;

import de.jaret.util.date.JaretDate;
import de.jaret.util.misc.FormatHelper;

public final class PdiData
{
    public static DutyMass _dutyMass;
    public static DispoModel _dispoModel;
    public static PdiCalendar _pdiCalendar;
    private static String[] orte = { "MSTH", "MOP", "MPH", "RL" };
    static String[] vornamen = { "Peter", "Dieter", "Thomas", "Frank", "Oliver", "Ulf", "Jonathan", "Philipp",
            "Norbert", "Josef", "Johannes", "Kathrin", "Jutta", "Stefan", "Martin", "Lukas", "Horst", "Gero", "Karsten" };
    static String[] nachnamen = { "Mller", "Meier", "Hartmann", "Mildenberger", "Kliem", "Hilken", "Sieber", "Schulz",
            "Scholz", "Koch", "Doe", "Schwedt", "Kanne", "Topf", "Stach", "Langenhan" };

    /**
     * 
     */
    public static void createModels(int personen, int tage, int diensteProTag) {
        PdiCalendar kalender = createKalender(tage);
        _pdiCalendar = kalender;
        _dutyMass = createDienstMasse(kalender, diensteProTag);
        _dispoModel = new DispoModel(kalender);
        for (int i = 0; i < personen; i++) {
            PersonenDisposition pd = createPersonenDispo(kalender);
            _dispoModel.addPersonenDispo(pd);
        }
    }

    /**
     * @return
     */
    private static PersonenDisposition createPersonenDispo(PdiCalendar kalender) {
        Person person = createPerson();
        PersonenDisposition pd = new PersonenDisposition(kalender, person);
        Iterator it = kalender.getTage().iterator();
        int tcount = 1;
        while (it.hasNext()) {
            PdiDay tag = (PdiDay) it.next();
            List dienste = _dutyMass.getDienste(tag);
            Duty d = null;
            for (int i = 0; d == null && i < 5; i++) {
                Duty dd = (Duty) dienste.get((int) (dienste.size() * Math.random()));
                if (dd.getAssignedTo() == null) {
                    if (pd.allowed(dd)) {
                        d = dd;
                    }
                }
            }
            if (d != null) {
                pd.addDienst(d);
                // System.out.println("dienst "+d.toString());
            }
        }
        return pd;
    }

    /**
     * @return
     */
    private static DutyMass createDienstMasse(PdiCalendar kalender, int diensteProTag) {
        DutyMass dm = new DutyMass();
        Iterator it = kalender.getTage().iterator();
        int tcount = 1;
        while (it.hasNext()) {
            PdiDay tag = (PdiDay) it.next();
            for (int i = 0; i < diensteProTag; i++) {
                String dienstnr = FormatHelper.NFInt2Digits().format(tcount) + FormatHelper.NFInt2Digits().format(i);
                JaretDate begin = tag.getDate().copy();
                begin.advanceMinutes(Math.random() * 22 * 60);
                JaretDate end = begin.copy();
                end.advanceMinutes(3 * 60 + Math.random() * 60 * 8);
                Duty d = new Duty(dienstnr, tag, begin, end);
                d.setBeginOrt(orte[(int) (Math.random() * orte.length)]);
                d.setEndeOrt(orte[(int) (Math.random() * orte.length)]);
                d.setBezahlteZeitSeconds((int) (d.getEnd().diffSeconds(d.getBegin())));

                dm.addDienst(tag, d);
            }
            tcount++;
        }
    
        return dm;
    }

    /**
     * @param tage
     * @return
     */
    private static PdiCalendar createKalender(int tage) {
        PdiCalendar kalender = new PdiCalendar();
        JaretDate date = new JaretDate();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        for (int i = 0; i < tage; i++) {
            PdiDay tag = new PdiDay("BT" + date.getDayOfWeekString(), date.copy());
            kalender.addTag(tag);
            date.advanceDays(1);
        }
        return kalender;
    }
    static private Person createPerson() {
        String name = vornamen[(int) (vornamen.length * Math.random())] + " "
                + nachnamen[(int) (nachnamen.length * Math.random())];
        Person person = new Person(name, "bhof");
        return person;
    }
}