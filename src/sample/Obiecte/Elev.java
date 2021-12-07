package sample.Obiecte;

import sample.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class Elev {
    private long idElev;
    private String numeElev;
    private String prenumeElev;
    private long cnp;
    private String judet;
    private String localitate;
    private String strada;
    private int numar;
    private String bloc;
    private String scara;
    private int etaj;
    private int apartament;
    private String prenumeMama;
    private String prenumeTata;
    private String telefonElev;
    private String telefonParinte;
    private int idClasa;
    private Date Data_adaugare;
    private Date Data_absolvire;
    private Date Data_exmatriculare;
    private String Clasa;
    private LocalDate dataNastere;


    public Elev(String numeElev, String prenumeElev, long cnp, String judet, String localitate, String strada, int numar, String bloc,
                String scara, int etaj, int apartament, int idClasa, String prenumeMama, String prenumeTata, String telefonElev, String telefonParinte) {
        this.numeElev = numeElev;
        this.prenumeElev = prenumeElev;
        this.cnp = cnp;
        this.judet = judet;
        this.localitate = localitate;
        this.strada = strada;
        this.numar = numar;
        this.bloc = bloc;
        this.scara = scara;
        this.etaj = etaj;
        this.apartament = apartament;
        this.idClasa = idClasa;
        this.prenumeMama = prenumeMama;
        this.prenumeTata = prenumeTata;
        this.telefonElev = telefonElev;
        this.telefonParinte = telefonParinte;
    }

    public Elev() {
    }

    @Override
    public String toString() {
        return numeElev + " " + prenumeElev;
    }

    public String dateElev(){
        return numeElev + " " + prenumeElev +
                " cu  cnp-ul " + cnp +
                ", domiciliat in judetul " + judet +
                ", localitatea " + localitate +
                ", strada " + strada +
                ", numarul " + numar +
                ", bloc " + bloc +
                ", scara " + scara +
                ", etajul " + etaj +
                ", apartament " + apartament +
                ", avand parintii " + prenumeMama +
                " si " + prenumeTata +
                "\nNumar contact Elev: " + telefonElev +
                "\nNumar contact Parinte: " + telefonParinte +
                "\n Elev in clasa=" + Main.getDbconnection().VerificareClasa(idClasa);
    }

    public void DataNastere() throws ParseException {
        Long checkedcnp=this.cnp;
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


    public String getClasa() {
        return Clasa;
    }

    public void setClasa(String clasa) {
        Clasa = clasa;
    }

    public Date getData_absolvire() {
        return Data_absolvire;
    }

    public void setData_absolvire(Date data_absolvire) {
        Data_absolvire = data_absolvire;
    }

    public Date getData_exmatriculare() {
        return Data_exmatriculare;
    }

    public void setData_exmatriculare(Date data_exmatriculare) {
        Data_exmatriculare = data_exmatriculare;
    }

    public Date getData_adaugare() {
        return Data_adaugare;
    }

    public void setData_adaugare(java.sql.Date data_adaugare) {
        Data_adaugare = data_adaugare;
    }

    public long getIdElev() {
        return idElev;
    }

    public void setIdElev(long idElev) {
        this.idElev = idElev;
    }

    public String getNumeElev() {
        return numeElev;
    }

    public String getPrenumeElev() {
        return prenumeElev;
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
        return strada;
    }

    public int getNumar() {
        return numar;
    }

    public String getBloc() {
        return bloc;
    }

    public String getScara() {
        return scara;
    }

    public int getEtaj() {
        return etaj;
    }

    public int getApartament() {
        return apartament;
    }

    public String getPrenumeMama() {
        return prenumeMama;
    }

    public String getPrenumeTata() {
        return prenumeTata;
    }

    public String getTelefonElev() {
        return telefonElev;
    }

    public String getTelefonParinte() {
        return telefonParinte;
    }

    public int getIdClasa() {
        return idClasa;
    }

    public void setNumeElev(String numeElev) {
        this.numeElev = numeElev;
    }

    public void setPrenumeElev(String prenumeElev) {
        this.prenumeElev = prenumeElev;
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
        this.strada = strada;
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

    public void setPrenumeMama(String prenumeMama) {
        this.prenumeMama = prenumeMama;
    }

    public void setPrenumeTata(String prenumeTata) {
        this.prenumeTata = prenumeTata;
    }

    public void setTelefonElev(String telefonElev) {
        this.telefonElev = telefonElev;
    }

    public void setTelefonParinte(String telefonParinte) {
        this.telefonParinte = telefonParinte;
    }

    public void setIdClasa(int idClasa) {
        this.idClasa = idClasa;
    }


    public LocalDate getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(LocalDate dataNastere) {
        this.dataNastere = dataNastere;
    }
}
