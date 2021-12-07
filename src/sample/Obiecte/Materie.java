package sample.Obiecte;

public class Materie
{
    private int idMaterie;
    private String nume_Materie;
    private String clasa;

    public Materie(int idMaterie, String nume_Materie, int idManual, String clasa) {
        this.idMaterie = idMaterie;
        this.nume_Materie = nume_Materie;
        this.clasa = clasa;
    }

    public Materie() {
    }

    @Override
    public String toString() {
        return nume_Materie;
    }

    public String numeMaterie() {
        return nume_Materie;
    }

    public String dateMaterie() {
        return "Materie{" +
                "idMaterie=" + idMaterie +
                ", nume_Materie='" + nume_Materie + '\'' +
                ", clasa='" + clasa + '\'' +
                '}';
    }

    public int getIdMaterie() {
        return idMaterie;
    }

    public void setIdMaterie(int idMaterie) {
        this.idMaterie = idMaterie;
    }

    public String getNume_Materie() {
        return nume_Materie;
    }

    public void setNume_Materie(String nume_Materie) {
        this.nume_Materie = nume_Materie;
    }

    public String getClasa() {
        return clasa;
    }

    public void setClasa(String clasa) {
        this.clasa = clasa;
    }
}
