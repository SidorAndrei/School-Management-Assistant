package sample.Obiecte;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

public class Profesor
{
    private int ID;
    private String numeProfesor;
    private String prenumeProfesor;
    private long cnp;
    private String judet;
    private String localitate;
    private String Strada;
    private int numar;
    private String bloc;
    private String scara;
    private int etaj;
    private int apartament;
    private String materie;
    private String telefonProfesor;
    private Date data_adaugare;
    private Date data_concediere;
    private LocalDate dataNastere;


    public Profesor(String numeProfesor, String prenumeProfesor, long cnp, String judet, String localitate, String strada,
                    int numar, String bloc, String scara, int etaj, int apartament, String materie, String telefonProfesor) {
        this.numeProfesor = numeProfesor;
        this.prenumeProfesor = prenumeProfesor;
        this.cnp = cnp;
        this.judet = judet;
        this.localitate = localitate;
        Strada = strada;
        this.numar = numar;
        this.bloc = bloc;
        this.scara = scara;
        this.etaj = etaj;
        this.apartament = apartament;
        this.materie = materie;
        this.telefonProfesor= telefonProfesor;
    }

    public Profesor() {

    }

    public void DataNastere() throws ParseException {
        Long checkedcnp= Long.valueOf(this.cnp);
        int day,year,month;
        checkedcnp/=1000000;
        day= (int) (checkedcnp%100);
        checkedcnp/=100;
        month= (int) (checkedcnp%100);
        checkedcnp/=100;
        year= (int) (checkedcnp%100);
        checkedcnp/=100;
        if(checkedcnp==1||checkedcnp==2) year+=1900;
        if(checkedcnp==5||checkedcnp==6) year+=2000;

        dataNastere= LocalDate.of(year,month,day);
    }

    public LocalDate getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(LocalDate dataNastere) {
        this.dataNastere = dataNastere;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNumeProfesor(String numeProfesor) {
        this.numeProfesor = numeProfesor;
    }

    public void setPrenumeProfesor(String prenumeProfesor) {
        this.prenumeProfesor = prenumeProfesor;
    }

    public void setCnp(long cnp) throws ParseException {
        this.cnp = cnp;
        DataNastere();
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public void setStrada(String strada) {
        Strada = strada;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public void setScara(String scara) {
        this.scara = scara;
    }

    public void setEtaj(int etaj) {
        this.etaj = etaj;
    }

    public void setApartament(int apartament) {
        this.apartament = apartament;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public void setTelefonProfesor(String telefonProfesor) {
        this.telefonProfesor = telefonProfesor;
    }

    public Date getData_adaugare() {
        return data_adaugare;
    }

    public void setData_adaugare(Date data_adaugare) {
        this.data_adaugare = data_adaugare;
    }

    public Date getData_concediere() {
        return data_concediere;
    }

    public void setData_concediere(Date data_concediere) {
        this.data_concediere = data_concediere;
    }

    public String getNumeProfesor() {
        return numeProfesor;
    }

    public String getPrenumeProfesor() {
        return prenumeProfesor;
    }

    public long getCnp() {
        return cnp;
    }

    public String getJudet() {
        return judet;
    }

    public String getLocalitate() {
        return localitate;
    }

    public String getStrada() {
        return Strada;
    }

    public int getNumar() {
        return numar;
    }

    public String getBloc() {
        return bloc;
    }

    public int getEtaj() {
        return etaj;
    }

    public int getApartament() {
        return apartament;
    }

    public String getMaterie() {
        return materie;
    }

    public String getTelefonProfesor() {
        return telefonProfesor;
    }

    public String getScara() {
        return scara;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "ID=" + ID +
                ", numeProfesor='" + numeProfesor + '\'' +
                ", prenumeProfesor='" + prenumeProfesor + '\'' +
                ", cnp='" + cnp + '\'' +
                ", judet='" + judet + '\'' +
                ", localitate='" + localitate + '\'' +
                ", Strada='" + Strada + '\'' +
                ", numar=" + numar +
                ", bloc='" + bloc + '\'' +
                ", scara='" + scara + '\'' +
                ", etaj=" + etaj +
                ", apartament=" + apartament +
                ", materie='" + materie + '\'' +
                ", telefonProfesor='" + telefonProfesor + '\'' +
                ", data_adaugare=" + data_adaugare +
                ", data_concediere=" + data_concediere +
                '}';
    }
}
