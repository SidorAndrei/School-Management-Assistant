package sample.Obiecte;

public class Clasa
{
    private int ID;
    private String numar;
    private String litera;
    private String diriginte;
    private int sala;

    public Clasa() {
    }

    @Override
    public String toString(){
        return  numar + " " + litera;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getLitera() {
        return litera;
    }

    public void setLitera(String litera) {
        this.litera = litera;
    }

    public String getDiriginte() {
        return diriginte;
    }

    public void setDiriginte(String diriginte) {
        this.diriginte = diriginte;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }
}
