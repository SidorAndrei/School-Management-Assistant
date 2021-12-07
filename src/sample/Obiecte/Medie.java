package sample.Obiecte;

public class Medie
{

    private String medie;
    private String materie;
    private String medie2;

    public Medie() {
    }

    public Medie(String medie, String materie , String medie2) {
        this.medie = medie;
        this.materie = materie;
        this.medie2 = medie2;
    }

    @Override
    public String toString() {
        return materie + " " + medie + " " + medie2;
    }

    public String getMedie() {
        return medie;
    }

    public void setMedie(String medie) {
        this.medie = medie;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public String getMedie2() {
        return medie2;
    }

    public void setMedie2(String medie2) {
        this.medie2 = medie2;
    }
}
