package sample.Obiecte;

public class Manual {
    private final String ID_Manual="SECVMANUALE.NEXTVAL";
    private String nume_manual;
    private String materie;
    private String editura;
    private String clasa;

    public Manual(String nume_manual, String editura, String materie, String clasa) {
        this.nume_manual = nume_manual;
        this.materie = materie;
        this.editura = editura;
        this.clasa = clasa;
    }

    @Override
    public String toString() {
        return nume_manual + " de la editura " + editura;
    }

    public String getNume_manual() {
        return nume_manual;
    }

    public void setNume_manual(String nume_manual) {
        this.nume_manual = nume_manual;
    }

    public String getID_Manual() {
        return ID_Manual;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public String getEditura() {
        return editura;
    }

    public void setEditura(String editura) {
        this.editura = editura;
    }

    public String getClasa() {
        return clasa;
    }

    public void setClasa(String clasa) {
        this.clasa = clasa;
    }
}
