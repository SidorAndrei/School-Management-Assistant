package sample.Obiecte;

import sample.Main;

import java.sql.SQLException;
import java.time.LocalDate;

public class Nota
{
    int Nota;
    long idElev;
    long idMaterie;
    String semestru;
    LocalDate data;
    String materie;



    public Nota(int nota, long idElev) {
        Nota = nota;
        this.idElev = idElev;
    }

    public Nota() {
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public long getIdMaterie() {
        return idMaterie;
    }

    public void setIdMaterie(long idMaterie) throws SQLException {
        this.idMaterie = idMaterie;
        this.materie = Main.getDbconnection().ReturnareMaterie(idMaterie);
    }

    public int getNota() {
        return Nota;
    }

    public long getIdElev() {
        return idElev;
    }

    public void setNota(int nota) {
        Nota = nota;
    }

    public void setIdElev(long idElev) {
        this.idElev = idElev;
    }

    public String getSemestru() {
        return semestru;
    }

    public void setSemestru(String semestru) {
        this.semestru = semestru;
    }
}
