package sample.Obiecte;

import sample.Main;

import java.sql.Date;
import java.time.LocalDate;

public class Absenta {
    private long IDElev;
    private String Materie;
    private LocalDate Data;
    private String Motivata;
    private String Semestru;

    public Absenta(long IDElev, String materie, LocalDate data, String motivata) {
        this.IDElev = IDElev;
        Materie = materie;
        Data = data;
        Motivata = motivata;
    }

    public Absenta() {
    }

    @Override
    public String toString() {
        if(Motivata.equals("NU")) return "\"" + Main.getDbconnection().ReturnareElevDupaID(IDElev) + " la materia " + Materie + " in data de " + Data + " absenta nemotivata" + "\"";
        else return "\"" + Main.getDbconnection().ReturnareElevDupaID(IDElev) + " la materia " + Materie + " in data de " + Data + " absenta motivata" + "\"";
    }

    public void setIDElev(long IDElev) {
        this.IDElev = IDElev;
    }

    public String getSemestru() {
        return Semestru;
    }

    public void setSemestru(String semestru) {
        Semestru = semestru;
    }

    public long getIDElev() {
        return IDElev;
    }

    public void setIDElev(int IDElev) {
        this.IDElev = IDElev;
    }

    public String getMaterie() {
        return Materie;
    }

    public void setMaterie(String materie) {
        Materie = materie;
    }

    public LocalDate getData() {
        return Data;
    }

    public void setData(LocalDate data) {
        Data = data;
    }

    public String getMotivata() {
        return Motivata;
    }

    public void setMotivata(String motivata) {
        Motivata = motivata;
    }
}
