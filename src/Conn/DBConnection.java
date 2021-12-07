package Conn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Obiecte.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBConnection
{
    private Connection connection;
    private Statement statement;
    private String PW;
    private int CodAcces;
    private String userName;
    private String numeUtilizator;
    private String prenumeUtilizator;
    private Long idUtilizator;
    private String materieProfesor;
    private String SemestruActiv;

    public String getMaterieProfesor() {
        return materieProfesor;
    }

    private void setSemestru(String semestru){
        this.SemestruActiv = semestru;
    }

    public String getSemestruActiv() {
        return SemestruActiv;
    }

    public DBConnection() throws SQLException, ClassNotFoundException {
       Class.forName("oracle.jdbc.driver.OracleDriver");

        try {
            connection = DriverManager.getConnection( "jdbc:oracle:thin:@localhost:1521:orcl","system","123Andrei");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(connection!=null)System.out.println("Connected!");
        ResultSet resultSet = connection.createStatement().executeQuery("Select * FROM SEMESTRUACTIV");
        resultSet.next();
        setSemestru(resultSet.getString("SEMESTRU"));
    }

    public void setSemestruActiv(){
        ResultSet resultSet = null;
        try {
            resultSet = connection.createStatement().executeQuery("Select * FROM SEMESTRUACTIV");
            resultSet.next();
            if(resultSet.getString("SEMESTRU").equals("I")){
                connection.createStatement().executeQuery("UPDATE \"SYSTEM\".\"SEMESTRUACTIV\" SET SEMESTRU = 'II'");
                SemestruActiv="II";
            }else{
                connection.createStatement().executeQuery("UPDATE \"SYSTEM\".\"SEMESTRUACTIV\" SET SEMESTRU = 'I'");
                SemestruActiv="I";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean LoginConnection(String username, String password) throws SQLException {
        ResultSet RS;
        try {
            RS = connection.createStatement().executeQuery(String.format("SELECT Password FROM LoginCredentials WHERE Username='%s'", username));

            if (RS.next()) PW = RS.getString(1);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (PW != null && PW.equals(password)) {
            RS = connection.createStatement().executeQuery(String.format("SELECT * FROM LoginCredentials WHERE Username='%s'", username));
            RS.next();
            CodAcces = RS.getInt("CodAcces");
            if(CodAcces==5){
                idUtilizator =RS.getLong("ID_SECRETAR");
                numeUtilizator = RS.getString("NUME_SECRETAR");
                prenumeUtilizator = RS.getString("PRENUME_SECRETAR");
            }
            else if(CodAcces==4){
                idUtilizator =RS.getLong("id_profesor");
                numeUtilizator = RS.getString("nume_profesor");
                prenumeUtilizator = RS.getString("prenume_profesor");
                RS = connection.createStatement().executeQuery("Select Materie FROM PROFESORI WHERE Nume_PROFESOR='" + numeUtilizator +"' AND PRENUME_PROFESOR='"+ prenumeUtilizator +"'");
                if(RS.next())
                    materieProfesor = RS.getString("MATERIE");
                RS.close();
            }else
                if(CodAcces==1){
                    numeUtilizator=RS.getString("NUME_ELEV");
                    prenumeUtilizator=RS.getString("PRENUME_ELEV");
                    idUtilizator=RS.getLong("ID_ELEV");
                }else
                    if(CodAcces==2){
                        numeUtilizator=RS.getString("NUME_ELEV");
                        prenumeUtilizator=RS.getString("PRENUME_MAMA");
                        idUtilizator=RS.getLong("ID_ELEV");
                    }
                    else
                        if(CodAcces==3){
                            numeUtilizator=RS.getString("NUME_ELEV");
                            prenumeUtilizator=RS.getString("PRENUME_TATA");
                            idUtilizator=RS.getLong("ID_ELEV");
                        }
            userName = username;
            System.out.printf("%s %s %s %s %s ", idUtilizator, userName, CodAcces, numeUtilizator, prenumeUtilizator);
            return true;
        }
        return false;
    }

    // Adaugare Elevi

    public void AdaugareElev(Elev elev){
        try {
            connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");
            connection.createStatement().executeQuery("INSERT INTO ELEVI(ID_ELEV,NUME_ELEV,PRENUME_ELEV,CNP,JUDET,LOCALITATE,STRADA,NUMAR,BLOC,SCARA," +
                    "ETAJ,APARTAMENT,ID_CLASA,PRENUME_MAMA,PRENUME_TATA,TELEFON_ELEV,TELEFON_PARINTE,DATA_ADAUGARE) VALUES (SECVELEVI.NEXTVAL,'" + elev.getNumeElev() + "','" + elev.getPrenumeElev()+
                     "'," + elev.getCnp() + ",'" + elev.getJudet() + "','" + elev.getLocalitate() + "','" + elev.getStrada() + "'," + elev.getNumar() +
                    ",'" + elev.getBloc() + "','" + elev.getScara() + "'," + elev.getEtaj() + "," + elev.getApartament() + "," + elev.getIdClasa() +
                    ",'" + elev.getPrenumeMama() + "','" + elev.getPrenumeTata() + "','" + elev.getTelefonElev() + "','" + elev.getTelefonParinte() +
                    "','" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean VerificareCnpElev(long cnp) {
        ResultSet resultSet = null;
        try {
            resultSet=connection.createStatement().executeQuery("SELECT CNP FROM ELEVI WHERE CNP=" + cnp);
            if(resultSet.next()) {
                if (cnp==resultSet.getLong("CNP"))
                return true;
            }
        } catch (SQLException throwables) {
            return false;
        }
        return false;
    }

    public Elev CautareCnpElev(long cnp){
        ResultSet resultSet = null;
        Elev elev = null;
        try {
            connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");
            resultSet=connection.createStatement().executeQuery("SELECT * FROM ELEVI WHERE CNP=" + cnp);
            if(resultSet.next()){
                elev = new Elev();
                elev.setNumeElev(resultSet.getString("NUME_ELEV"));
                elev.setPrenumeElev(resultSet.getString("PRENUME_ELEV"));
                elev.setCnp(resultSet.getLong("CNP"));
                elev.setJudet(resultSet.getString("JUDET"));
                elev.setLocalitate(resultSet.getString("LOCALITATE"));
                elev.setStrada(resultSet.getString("STRADA"));
                elev.setNumar(resultSet.getInt("NUMAR"));
                elev.setBloc(resultSet.getString("BLOC"));
                elev.setScara(resultSet.getString("SCARA"));
                elev.setEtaj(resultSet.getInt("ETAJ"));
                elev.setApartament(resultSet.getInt("APARTAMENT"));
                elev.setPrenumeMama(resultSet.getString("PRENUME_MAMA"));
                elev.setPrenumeTata(resultSet.getString("PRENUME_TATA"));
                elev.setTelefonElev(resultSet.getString("TELEFON_ELEV"));
                elev.setTelefonParinte(resultSet.getString("TELEFON_PARINTE"));
                elev.setIdClasa(resultSet.getInt("ID_CLASA"));
                elev.setData_adaugare(resultSet.getDate("Data_Adaugare"));
                elev.setData_absolvire(resultSet.getDate("Data_Absolvire"));
                elev.setData_exmatriculare(resultSet.getDate("Data_Exmatriculare"));
            }
        } catch (SQLException | ParseException throwables) {
            return elev;
        }
        return elev;
    }

    public int VerificareLocClasa(int idClasa){
        ResultSet resultSet = null;
        int locuriOcupate=0;
        try {
            resultSet=connection.createStatement().executeQuery("SELECT * FROM ELEVI WHERE ID_CLASA=" + idClasa);
            while(resultSet.next()){
                locuriOcupate++;
            }
            return locuriOcupate;
        } catch (SQLException throwables) {
            return locuriOcupate;
        }
    }

    public String VerificareClasa(int IDClasa){
        ResultSet resultSet = null;
        String string= null;
        try {
            resultSet=connection.createStatement().executeQuery("SELECT * FROM CLASE WHERE ID_CLASA=" + IDClasa);
            if(resultSet.next()){
                string = resultSet.getString("NUMAR_CLASA") + " " + resultSet.getString("LITERA_CLASA");
                return string;
            }
        } catch (SQLException throwables) {
            return string;
        }
        return string;
    }

    public int ReturnareIdClasa(String clasa , String litera){
        int id = 0;
        System.out.println( clasa + "    " + litera);
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT ID_CLASA FROM CLASE WHERE NUMAR_CLASA = '"+clasa+"' AND lITERA_CLASA='"+litera+"'");
            rs.next();
            id=rs.getInt("ID_CLASA");
            System.out.println(id);
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public ObservableList<Elev> ReturnareElevi(){
        ObservableList <Elev> elevi = FXCollections.observableArrayList();
        ResultSet resultSet ;


        Elev elev;
        try{
            connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");
            resultSet=connection.createStatement().executeQuery("SELECT * FROM Elevi");

            while(resultSet.next()){
                elev = new Elev();
                elev.setIdElev(resultSet.getLong("ID_ELEV"));
                elev.setNumeElev(resultSet.getString("NUME_ELEV"));
                elev.setPrenumeElev(resultSet.getString("PRENUME_ELEV"));
                elev.setCnp(resultSet.getLong("CNP"));
                elev.setJudet(resultSet.getString("JUDET"));
                elev.setLocalitate(resultSet.getString("LOCALITATE"));
                elev.setStrada(resultSet.getString("STRADA"));
                elev.setNumar(resultSet.getInt("NUMAR"));
                elev.setBloc(resultSet.getString("BLOC"));
                elev.setScara(resultSet.getString("SCARA"));
                elev.setEtaj(resultSet.getInt("ETAJ"));
                elev.setApartament(resultSet.getInt("APARTAMENT"));
                elev.setPrenumeMama(resultSet.getString("PRENUME_MAMA"));
                elev.setPrenumeTata(resultSet.getString("PRENUME_TATA"));
                elev.setTelefonElev(resultSet.getString("TELEFON_ELEV"));
                elev.setTelefonParinte(resultSet.getString("TELEFON_PARINTE"));
                elev.setIdClasa(resultSet.getInt("ID_CLASA"));
                elev.setData_adaugare(resultSet.getDate("DATA_ADAUGARE"));
                elev.setData_absolvire(resultSet.getDate("DATA_ABSOLVIRE"));
                elev.setData_exmatriculare(resultSet.getDate("DATA_EXMATRICULARE"));
                elevi.add(elev);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            return elevi;
        }
        return elevi;
    }

    public ObservableList<Elev> FiltrareEleviExmatriculati(ObservableList<Elev> eleviPrimiti){
        ObservableList<Elev> elevi = FXCollections.observableArrayList();

        for (Elev e: eleviPrimiti) {
            if(e.getData_exmatriculare()!=null) elevi.add(e);
        }
        return elevi;
    }

    public ObservableList<Elev> FiltrareEleviAbsolventi(ObservableList<Elev> eleviPrimiti){
        ObservableList<Elev> elevi = FXCollections.observableArrayList();
        for (Elev e: eleviPrimiti) {
            if(e.getData_absolvire()!=null) elevi.add(e);
        }

        return elevi;
    }

    public ObservableList<Elev> FiltrareEleviActuali(ObservableList<Elev> eleviPrimiti){
        ObservableList<Elev> elevi = FXCollections.observableArrayList();
        for (Elev e: eleviPrimiti) {
            if(e.getData_absolvire()==null && e.getData_exmatriculare()==null) elevi.add(e);
        }
        return elevi;
    }

    public ObservableList<Clasa> ReturnareClase(){
        ObservableList<Clasa> clase = FXCollections.observableArrayList();
        try {
            ResultSet resultSet;
            Clasa clasa;
            resultSet=connection.createStatement().executeQuery("SELECT * FROM CLASE");
            while (resultSet.next()){
                clasa = new Clasa();
                clasa.setID(resultSet.getInt("ID_CLASA"));
                clasa.setNumar(resultSet.getString("NUMAR_CLASA"));
                clasa.setLitera(resultSet.getString("LITERA_CLASA"));
                clasa.setDiriginte(resultSet.getString("DIRIGINTE_CLASA"));
                clasa.setSala(resultSet.getInt("SALA_CLASA"));
                clase.add(clasa);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return clase;
    }

    // Vizualizare Elevi

    public ObservableList<Elev> FiltrareEleviSexMasculin(ObservableList<Elev> eleviPrimiti){
        ObservableList<Elev> elevi = FXCollections.observableArrayList();
        long n=(long)1000000;
            for (Elev e : eleviPrimiti) {
                long cnp = e.getCnp() / n;
                cnp = cnp / n;
                if (cnp == 1 || e.getCnp() / n / n == 5) elevi.add(e);
            }
        return elevi;
    }

    public ObservableList<Elev> FiltrareEleviSexFeminin(ObservableList<Elev> eleviPrimiti){
        ObservableList<Elev> elevi = FXCollections.observableArrayList();
        long n=(long)1000000;
        for (Elev e : eleviPrimiti) {
            long cnp = e.getCnp() / n;
            cnp = cnp / n;
            if (cnp== 2 || cnp == 6) elevi.add(e);
        }
        return elevi;
    }



    public  ObservableList<String> ReturnareLocalitateElevi(){
        ObservableList<String> localitati = FXCollections.observableArrayList();
        ResultSet resultSet ;
        String localitate;

        try {
            resultSet=connection.createStatement().executeQuery("SELECT * FROM ELEVI");
            while(resultSet.next()){
                localitate =resultSet.getString("Localitate");
                boolean OK = true;
                for (String s:localitati) {
                    if(s.toString().equals(localitate)) OK=false;
                }
                if(OK) localitati.add(localitate);
                localitate="";
            }
        } catch (SQLException throwables) {
            throwables.getNextException().printStackTrace();
            return localitati;
        }
        return localitati;
    }

    public ObservableList<Elev> ReturnareEleviDinLocalitate(String localitate){
        ObservableList<Elev> elevi = FXCollections.observableArrayList();
        ResultSet resultSet ;


        Elev elev;
        try{
            resultSet=connection.createStatement().executeQuery("SELECT * FROM Elevi WHERE LOCALITATE = '" + localitate + "'");

            while(resultSet.next()){
                elev = new Elev();
                elev.setIdElev(resultSet.getLong("ID_ELEV"));
                elev.setNumeElev(resultSet.getString("NUME_ELEV"));
                elev.setPrenumeElev(resultSet.getString("PRENUME_ELEV"));
                elev.setCnp(resultSet.getLong("CNP"));
                elev.setJudet(resultSet.getString("JUDET"));
                elev.setLocalitate(resultSet.getString("LOCALITATE"));
                elev.setStrada(resultSet.getString("STRADA"));
                elev.setNumar(resultSet.getInt("NUMAR"));
                elev.setBloc(resultSet.getString("BLOC"));
                elev.setScara(resultSet.getString("SCARA"));
                elev.setEtaj(resultSet.getInt("ETAJ"));
                elev.setApartament(resultSet.getInt("APARTAMENT"));
                elev.setPrenumeMama(resultSet.getString("PRENUME_MAMA"));
                elev.setPrenumeTata(resultSet.getString("PRENUME_TATA"));
                elev.setTelefonElev(resultSet.getString("TELEFON_ELEV"));
                elev.setTelefonParinte(resultSet.getString("TELEFON_PARINTE"));
                elev.setIdClasa(resultSet.getInt("ID_CLASA"));
                elev.setData_adaugare(resultSet.getDate("Data_Adaugare"));
                elev.setData_absolvire(resultSet.getDate("Data_Absolvire"));
                elev.setData_exmatriculare(resultSet.getDate("Data_Exmatriculare"));
                elevi.add(elev);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            return elevi;
        }
        return elevi;
    }

    public ObservableList<Elev> ReturnareEleviDinLocalitateDinClasa(String localitate,int idClasa){
        ObservableList<Elev> elevi = FXCollections.observableArrayList();
        ResultSet resultSet ;


        Elev elev;
        try {
            resultSet=connection.createStatement().executeQuery("SELECT * FROM Elevi WHERE LOCALITATE = '" + localitate + "' AND ID_CLASA = " + idClasa );

            while(resultSet.next()){
                elev = new Elev();
                elev.setIdElev(resultSet.getLong("ID_ELEV"));
                elev.setNumeElev(resultSet.getString("NUME_ELEV"));
                elev.setPrenumeElev(resultSet.getString("PRENUME_ELEV"));
                elev.setCnp(resultSet.getLong("CNP"));
                elev.setJudet(resultSet.getString("JUDET"));
                elev.setLocalitate(resultSet.getString("LOCALITATE"));
                elev.setStrada(resultSet.getString("STRADA"));
                elev.setNumar(resultSet.getInt("NUMAR"));
                elev.setBloc(resultSet.getString("BLOC"));
                elev.setScara(resultSet.getString("SCARA"));
                elev.setEtaj(resultSet.getInt("ETAJ"));
                elev.setApartament(resultSet.getInt("APARTAMENT"));
                elev.setPrenumeMama(resultSet.getString("PRENUME_MAMA"));
                elev.setPrenumeTata(resultSet.getString("PRENUME_TATA"));
                elev.setTelefonElev(resultSet.getString("TELEFON_ELEV"));
                elev.setTelefonParinte(resultSet.getString("TELEFON_PARINTE"));
                elev.setIdClasa(resultSet.getInt("ID_CLASA"));
                elev.setData_adaugare(resultSet.getDate("Data_Adaugare"));
                elev.setData_absolvire(resultSet.getDate("Data_Absolvire"));
                elev.setData_exmatriculare(resultSet.getDate("Data_Exmatriculare"));
                elevi.add(elev);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            return elevi;
        }
        return elevi;
    }

    // Adaugare Note

    public boolean AdaugareNota(Nota nota) throws SQLException {
        int idMaterie =ReturnareIdMaterie();
        connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");
        ResultSet rs = connection.createStatement().executeQuery("SELECT DATA FROM NOTE WHERE ID_ELEV = " + nota.getIdElev() +
                " AND ID_MATERIE = " + idMaterie + " ORDER BY DATA DESC");
        if(rs.next())
        if(rs.getDate("DATA")==Date.valueOf(LocalDate.now())) return false;
        else {
            connection.createStatement().executeQuery("INSERT INTO NOTE VALUES (SECVNOTE.NEXTVAL, " + nota.getNota() + ", " + nota.getIdElev()+", "+
                    idMaterie + ", " + idUtilizator + ",'" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "','" + SemestruActiv + "')");
            return true;
        }
        return false;
    }

    public Elev ReturnareElevDupaID(long idElev){
        Elev elev = new Elev();;
        try  {
            ResultSet resultSet=connection.createStatement().executeQuery("SELECT * FROM Elevi WHERE  ID_ELEV = " + idElev );
            if(resultSet.next()) {
                elev.setIdElev(resultSet.getLong("ID_ELEV"));
                elev.setNumeElev(resultSet.getString("NUME_ELEV"));
                elev.setPrenumeElev(resultSet.getString("PRENUME_ELEV"));
                elev.setCnp(resultSet.getLong("CNP"));
                elev.setJudet(resultSet.getString("JUDET"));
                elev.setLocalitate(resultSet.getString("LOCALITATE"));
                elev.setStrada(resultSet.getString("STRADA"));
                elev.setNumar(resultSet.getInt("NUMAR"));
                elev.setBloc(resultSet.getString("BLOC"));
                elev.setScara(resultSet.getString("SCARA"));
                elev.setEtaj(resultSet.getInt("ETAJ"));
                elev.setApartament(resultSet.getInt("APARTAMENT"));
                elev.setPrenumeMama(resultSet.getString("PRENUME_MAMA"));
                elev.setPrenumeTata(resultSet.getString("PRENUME_TATA"));
                elev.setTelefonElev(resultSet.getString("TELEFON_ELEV"));
                elev.setTelefonParinte(resultSet.getString("TELEFON_PARINTE"));
                elev.setIdClasa(resultSet.getInt("ID_CLASA"));
                elev.setData_adaugare(resultSet.getDate("Data_Adaugare"));
                elev.setData_absolvire(resultSet.getDate("Data_Absolvire"));
                elev.setData_exmatriculare(resultSet.getDate("Data_Exmatriculare"));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            return elev;
        }
        return elev;
    }

    public int ReturnareIdMaterie() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("Select ID_MATERIE FROM MATERII WHERE NUME_MATERIE='" + materieProfesor+"'");
        rs.next();
        return rs.getInt("ID_MATERIE");
    }

    public ObservableList<Elev> ReturnareEleviDinClasa(int idClasa){
        ObservableList<Elev> elevi = FXCollections.observableArrayList();
        ResultSet resultSet ;


        Elev elev;
        try {
            resultSet=connection.createStatement().executeQuery("SELECT * FROM Elevi WHERE ID_CLASA = " + idClasa);

            while(resultSet.next()){
                elev = new Elev();
                elev.setIdElev(resultSet.getLong("ID_ELEV"));
                elev.setNumeElev(resultSet.getString("NUME_ELEV"));
                elev.setPrenumeElev(resultSet.getString("PRENUME_ELEV"));
                elev.setCnp(resultSet.getLong("CNP"));
                elev.setJudet(resultSet.getString("JUDET"));
                elev.setLocalitate(resultSet.getString("LOCALITATE"));
                elev.setStrada(resultSet.getString("STRADA"));
                elev.setNumar(resultSet.getInt("NUMAR"));
                elev.setBloc(resultSet.getString("BLOC"));
                elev.setScara(resultSet.getString("SCARA"));
                elev.setEtaj(resultSet.getInt("ETAJ"));
                elev.setApartament(resultSet.getInt("APARTAMENT"));
                elev.setPrenumeMama(resultSet.getString("PRENUME_MAMA"));
                elev.setPrenumeTata(resultSet.getString("PRENUME_TATA"));
                elev.setTelefonElev(resultSet.getString("TELEFON_ELEV"));
                elev.setTelefonParinte(resultSet.getString("TELEFON_PARINTE"));
                elev.setIdClasa(resultSet.getInt("ID_CLASA"));
                elev.setData_adaugare(resultSet.getDate("Data_Adaugare"));
                elev.setData_absolvire(resultSet.getDate("Data_Absolvire"));
                elev.setData_exmatriculare(resultSet.getDate("Data_Exmatriculare"));
                elevi.add(elev);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            return elevi;
        }
        return elevi;
    }

    // Adaugare absente

    public void AdaugareAbsenta(Absenta absenta){
        try {
            connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");
            connection.createStatement().executeQuery("INSERT INTO ABSENTE VALUES (SECVABSENTE.NEXTVAL," + absenta.getIDElev() + ",'" +
                    absenta.getMaterie() + "','" + absenta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "','" +
                    absenta.getMotivata() + "','"+ ReturnareElevDupaID(absenta.getIDElev()).getClasa() + "','" + SemestruActiv +"')");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean VerificareAbsenta(Absenta absenta){
        ResultSet resultSet;
        try {
            connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");
            resultSet = connection.createStatement().executeQuery("SELECT * FROM ABSENTE WHERE ID_ELEV = " + absenta.getIDElev() + " AND MATERIE = '" +
                    absenta.getMaterie() + "' AND DATA = '" + absenta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "'");
            if(resultSet.next()) return true;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean VerificareAbsolvent(Absenta absenta){
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery("SELECT * FROM ELEVI WHERE ID_ELEV = " + absenta.getIDElev() + ")");
            if(resultSet.next()) return false;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Vizualizare Absente
    public ObservableList<Absenta> ReturnareAbsenteElev(Elev elev){
        ObservableList<Absenta> absente = FXCollections.observableArrayList();
        Absenta absenta;
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery("SELECT * FROM ABSENTE WHERE ID_ELEV=" + elev.getIdElev() + " AND CLASA='" + elev.getClasa() + "'");
            while(resultSet.next()){
                absenta = new Absenta();
                absenta.setData(resultSet.getDate("DATA").toLocalDate());
                absenta.setMotivata(resultSet.getString("MOTIVATA"));
                absenta.setMaterie(resultSet.getString("MATERIE"));
                absenta.setSemestru(resultSet.getString("SEMESTRU"));
                absente.add(absenta);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return absente;
    }


    // Adaugare profesori

    public void AdaugareProfesor(Profesor profesor){
        try{
            connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");

            StringBuilder sb= new StringBuilder();
            sb.append("SECVPROFESORI.NEXTVAL,");
            sb.append("'").append(profesor.getNumeProfesor()).append("',");
            sb.append("'").append(profesor.getPrenumeProfesor()).append("',");
            sb.append(profesor.getCnp());
            sb.append(",'").append(profesor.getJudet()).append("',");
            sb.append("'").append(profesor.getLocalitate()).append("',");
            sb.append("'").append(profesor.getStrada()).append("',");
            sb.append(profesor.getNumar());
            sb.append(",'").append(profesor.getBloc()).append("',");
            sb.append("'").append(profesor.getScara()).append("',");
            sb.append(profesor.getEtaj()).append(",");
            sb.append(profesor.getApartament());
            sb.append(",'").append(profesor.getMaterie()).append("',");
            sb.append("'").append(profesor.getTelefonProfesor()).append("','");

            connection.createStatement().executeQuery("INSERT INTO PROFESORI(ID_PROFESOR,NUME_PROFESOR,PRENUME_PROFESOR,CNP,JUDET,LOCALITATE,STRADA,NUMAR,BLOC,SCARA,ETAJ," +
                    "APARTAMENT,MATERIE,TELEFON_PROFESOR,DATA_ADAUGARE) VALUES ("+ sb + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean VerificareCnpProfesor(Profesor profesor) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM PROFESORI WHERE CNP = " + profesor.getCnp());
        if(resultSet.next()) return true;
        else return false;
    }

    public ObservableList<Materie> ReturnareMaterii(){
            ObservableList<Materie> materii = FXCollections.observableArrayList();
            ResultSet resultSet ;
            Materie materie;

            try {
                resultSet=connection.createStatement().executeQuery("SELECT * FROM MATERII");
                while(resultSet.next()){
                    materie = new Materie();
                    materie.setIdMaterie(resultSet.getInt("ID_MATERIE"));
                    materie.setNume_Materie(resultSet.getString("NUME_MATERIE"));
                    materie.setClasa(resultSet.getString("CLASA"));
                    materii.add(materie);
                }
            } catch (SQLException throwables) {
                throwables.getNextException().printStackTrace();
                return materii;
            }
            return materii;
    }

    // Vizualizare Profesori


    public ObservableList<Profesor> FiltrareProfesoriSexMasculin(ObservableList<Profesor> profesoriPrimiti){
        ObservableList<Profesor> profesori = FXCollections.observableArrayList();
        long n=(long)1000000;
        for (Profesor p : profesoriPrimiti) {
            long cnp = p.getCnp() / n;
            cnp = cnp / n;
            if (cnp == 1 || p.getCnp() / n / n == 5) profesori.add(p);
        }
        return profesori;
    }

    public ObservableList<Profesor> FiltrareProfesoriSexFeminin(ObservableList<Profesor> profesoriPrimiti){
        ObservableList<Profesor> profesori = FXCollections.observableArrayList();
        long n=(long)1000000;
        for (Profesor p : profesoriPrimiti) {
            long cnp = p.getCnp() / n;
            cnp = cnp / n;
            if (cnp== 2 || cnp == 6) profesori.add(p);
        }
        return profesori;
    }

    public ObservableList<Materie> ReturnareMateriiProfesori() {
        ObservableList<Materie> materii = FXCollections.observableArrayList();
        ResultSet resultSet ;
        Materie materie;

        try {
            resultSet=connection.createStatement().executeQuery("SELECT * FROM PROFESORI");
            while(resultSet.next()){
                materie = new Materie();
                materie.setNume_Materie(resultSet.getString("MATERIE"));
                boolean OK = true;
                for (Materie m:materii) {
                    if(m.toString().equals(materie.toString())) OK=false;
                }
                if(OK) materii.add(materie);
            }
        } catch (SQLException throwables) {
            throwables.getNextException().printStackTrace();
            return materii;
        }
        return materii;
    }

    public  ObservableList<String> ReturnareLocalitateProfesori(){
        ObservableList<String> localitati = FXCollections.observableArrayList();
        ResultSet resultSet ;
        String localitate;
        try {
            resultSet=connection.createStatement().executeQuery("SELECT * FROM PROFESORI");
            while(resultSet.next()){
                localitate =resultSet.getString("Localitate");
                boolean OK = true;
                for (String s:localitati) {
                    if(s.toString().equals(localitate)) OK=false;
                }
                if(OK) localitati.add(localitate);
                localitate="";
            }
        } catch (SQLException throwables) {
            throwables.getNextException().printStackTrace();
            return localitati;
        }
        return localitati;
    }

    public ObservableList<Profesor> ReturnareProfesori(){
        ObservableList<Profesor> profesori = FXCollections.observableArrayList();
        ResultSet resultSet;

        Profesor profesor;
        try{

            resultSet=connection.createStatement().executeQuery("SELECT * FROM PROFESORI");
            while(resultSet.next()){
                profesor= new Profesor();
                profesor.setID(resultSet.getInt("ID_PROFESOR"));
                profesor.setNumeProfesor(resultSet.getString("NUME_PROFESOR"));
                profesor.setPrenumeProfesor(resultSet.getString("PRENUME_PROFESOR"));
                profesor.setCnp(resultSet.getLong("CNP"));
                profesor.setJudet(resultSet.getString("JUDET"));
                profesor.setLocalitate(resultSet.getString("LOCALITATE"));
                profesor.setStrada(resultSet.getString("STRADA"));
                profesor.setNumar(resultSet.getInt("NUMAR"));
                profesor.setBloc(resultSet.getString("BLOC"));
                profesor.setScara(resultSet.getString("SCARA"));
                profesor.setEtaj(resultSet.getInt("ETAJ"));
                profesor.setApartament(resultSet.getInt("APARTAMENT"));
                profesor.setMaterie(resultSet.getString("MATERIE"));
                profesor.setTelefonProfesor(resultSet.getString("TELEFON_PROFESOR"));
                profesor.setData_adaugare(resultSet.getDate("DATA_ADAUGARE"));
                profesor.setData_concediere(resultSet.getDate("DATA_CONCEDIERE"));
                profesori.add(profesor);
            }
            return profesori;
        }catch (SQLException | ParseException throwables){
            throwables.printStackTrace();
        }
        return profesori;
    }

    public ObservableList<Profesor> ReturnareProfesoriDupaMaterie(String materieProfesor){
        ObservableList<Profesor> profesori = FXCollections.observableArrayList();
        ResultSet resultSet;

        Profesor profesor;
        try{

            resultSet=connection.createStatement().executeQuery("SELECT * FROM PROFESORI WHERE MATERIE='" + materieProfesor + "'");
            while(resultSet.next()){
                profesor= new Profesor();
                profesor.setID(resultSet.getInt("ID_PROFESOR"));
                profesor.setNumeProfesor(resultSet.getString("NUME_PROFESOR"));
                profesor.setPrenumeProfesor(resultSet.getString("PRENUME_PROFESOR"));
                profesor.setCnp(resultSet.getLong("CNP"));
                profesor.setJudet(resultSet.getString("JUDET"));
                profesor.setLocalitate(resultSet.getString("LOCALITATE"));
                profesor.setStrada(resultSet.getString("STRADA"));
                profesor.setNumar(resultSet.getInt("NUMAR"));
                profesor.setBloc(resultSet.getString("BLOC"));
                profesor.setScara(resultSet.getString("SCARA"));
                profesor.setEtaj(resultSet.getInt("ETAJ"));
                profesor.setApartament(resultSet.getInt("APARTAMENT"));
                profesor.setMaterie(resultSet.getString("MATERIE"));
                profesor.setTelefonProfesor(resultSet.getString("TELEFON_PROFESOR"));
                profesor.setData_adaugare(resultSet.getDate("DATA_ADAUGARE"));
                profesor.setData_concediere(resultSet.getDate("DATA_CONCEDIERE"));
                profesori.add(profesor);
            }
            return profesori;
        }catch (SQLException | ParseException throwables){
            throwables.printStackTrace();
        }
        return profesori;
    }

    public ObservableList<Profesor> ReturnareProfesoriDupaLocalitate(String localitateProfesor){
        ObservableList<Profesor> profesori = FXCollections.observableArrayList();
        ResultSet resultSet;

        Profesor profesor;
        try{

            resultSet=connection.createStatement().executeQuery("SELECT * FROM PROFESORI WHERE LOCALITATE='" + localitateProfesor + "'");
            while(resultSet.next()){
                profesor= new Profesor();
                profesor.setID(resultSet.getInt("ID_PROFESOR"));
                profesor.setNumeProfesor(resultSet.getString("NUME_PROFESOR"));
                profesor.setPrenumeProfesor(resultSet.getString("PRENUME_PROFESOR"));
                profesor.setCnp(resultSet.getLong("CNP"));
                profesor.setJudet(resultSet.getString("JUDET"));
                profesor.setLocalitate(resultSet.getString("LOCALITATE"));
                profesor.setStrada(resultSet.getString("STRADA"));
                profesor.setNumar(resultSet.getInt("NUMAR"));
                profesor.setBloc(resultSet.getString("BLOC"));
                profesor.setScara(resultSet.getString("SCARA"));
                profesor.setEtaj(resultSet.getInt("ETAJ"));
                profesor.setApartament(resultSet.getInt("APARTAMENT"));
                profesor.setMaterie(resultSet.getString("MATERIE"));
                profesor.setTelefonProfesor(resultSet.getString("TELEFON_PROFESOR"));
                profesor.setData_adaugare(resultSet.getDate("DATA_ADAUGARE"));
                profesor.setData_concediere(resultSet.getDate("DATA_CONCEDIERE"));
                profesori.add(profesor);
            }
            return profesori;
        }catch (SQLException | ParseException throwables){
            throwables.printStackTrace();
        }
        return profesori;
    }

    public ObservableList<Profesor> ReturnareProfesoriDupaMaterieSiLocalitate(String materieProfesor, String localitateProfesor){
        ObservableList<Profesor> profesori = FXCollections.observableArrayList();
        ResultSet resultSet;

        Profesor profesor;
        try{

            resultSet=connection.createStatement().executeQuery("SELECT * FROM PROFESORI WHERE MATERIE='" + materieProfesor + "' AND LOCALITATE = '" + localitateProfesor + "'" );
            while(resultSet.next()){
                profesor= new Profesor();
                profesor.setID(resultSet.getInt("ID_PROFESOR"));
                profesor.setNumeProfesor(resultSet.getString("NUME_PROFESOR"));
                profesor.setPrenumeProfesor(resultSet.getString("PRENUME_PROFESOR"));
                profesor.setCnp(resultSet.getLong("CNP"));
                profesor.setJudet(resultSet.getString("JUDET"));
                profesor.setLocalitate(resultSet.getString("LOCALITATE"));
                profesor.setStrada(resultSet.getString("STRADA"));
                profesor.setNumar(resultSet.getInt("NUMAR"));
                profesor.setBloc(resultSet.getString("BLOC"));
                profesor.setScara(resultSet.getString("SCARA"));
                profesor.setEtaj(resultSet.getInt("ETAJ"));
                profesor.setApartament(resultSet.getInt("APARTAMENT"));
                profesor.setMaterie(resultSet.getString("MATERIE"));
                profesor.setTelefonProfesor(resultSet.getString("TELEFON_PROFESOR"));
                profesor.setData_adaugare(resultSet.getDate("DATA_ADAUGARE"));
                profesor.setData_concediere(resultSet.getDate("DATA_CONCEDIERE"));
                profesori.add(profesor);
            }
            return profesori;
        }catch (SQLException | ParseException throwables){
            throwables.printStackTrace();
        }
        return profesori;
    }

    // Adaugare Cont Utilizator
    public int ReturnareIDElev(String nume , String prenume) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("Select * FROM ELEVI WHERE NUME_ELEV = '" + nume + "' AND PRENUME_ELEV='" + prenume + "'");
        resultSet.next();
        return resultSet.getInt("ID_ELEV");
    }

    public int ReturnareIDElev2(String nume , String prenume) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("Select * FROM ELEVI WHERE NUME_ELEV = '" + nume + "', AND PRENUME_MAMA='" + prenume + "'");
        resultSet.next();
        return resultSet.getInt("ID_ELEV");
    }

    public int ReturnareIDElev3(String nume , String prenume) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("Select * FROM ELEVI WHERE NUME_ELEV = '" + nume + "', AND PRENUME_TATA='" + prenume + "'");
        resultSet.next();
        return resultSet.getInt("ID_ELEV");
    }

    public int ReturnareIDProfesor(String nume , String prenume) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("Select * FROM ELEVI WHERE NUME_ELEV = '" + nume + "', AND PRENUME_TATA='" + prenume + "'");
        resultSet.next();
        return resultSet.getInt("ID_PROFESOR");
    }

    public int ReturnareIDSecretar(String nume , String prenume) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("Select * FROM ELEVI WHERE NUME_ELEV = '" + nume + "', AND PRENUME_TATA='" + prenume + "'");
        resultSet.next();
        return resultSet.getInt("ID_SECRETAR");
    }

    public void  AdaugareContUtilizator(ContUtilizator contUtilizator) throws SQLException {
        if (contUtilizator.getCodAcces()==1)
            connection.createStatement().executeQuery("INSERT INTO LOGINCREDENTIALS(ID,USERNAME,PASSWORD,CODACCES,NUME_ELEV,PRENUME_ELEV,ID_ELEV) VALUES (" +
                    contUtilizator.getID() + ",'" + contUtilizator.getUsername() + "','" + contUtilizator.getPassword() + "'," +
                    contUtilizator.getCodAcces() + ",'" + contUtilizator.getNumeUtilizator() + "','" +
                    contUtilizator.getPrenumeUtilizator() + "', " + ReturnareIDElev(contUtilizator.getNumeUtilizator(),contUtilizator.getPrenumeUtilizator()) + ")");
        if (contUtilizator.getCodAcces()==2)
            connection.createStatement().executeQuery("INSERT INTO LOGINCREDENTIALS(ID,USERNAME,PASSWORD,CODACCES,NUME_ELEV,PRENUME_MAMA) VALUES (" +
                    contUtilizator.getID() + ",'" + contUtilizator.getUsername() + "','" + contUtilizator.getPassword() + "'," +
                    contUtilizator.getCodAcces() + ",'" + contUtilizator.getNumeUtilizator() + "','" +
                    contUtilizator.getPrenumeUtilizator() + "', " + ReturnareIDElev2(contUtilizator.getNumeUtilizator(),contUtilizator.getPrenumeUtilizator()) + ")");
        if (contUtilizator.getCodAcces()==3)
            connection.createStatement().executeQuery("INSERT INTO LOGINCREDENTIALS(ID,USERNAME,PASSWORD,CODACCES,NUME_ELEV,PRENUME_TATA) VALUES (" +
                    contUtilizator.getID() + ",'" + contUtilizator.getUsername() + "','" + contUtilizator.getPassword() + "'," +
                    contUtilizator.getCodAcces() + ",'" + contUtilizator.getNumeUtilizator() + "','" +
                    contUtilizator.getPrenumeUtilizator() + "', " + ReturnareIDElev3(contUtilizator.getNumeUtilizator(),contUtilizator.getPrenumeUtilizator()) + ")");
        if (contUtilizator.getCodAcces()==4)
            connection.createStatement().executeQuery("INSERT INTO LOGINCREDENTIALS(ID,USERNAME,PASSWORD,CODACCES,NUME_PROFESOR,PRENUME_PROFESOR) VALUES (" +
                    contUtilizator.getID() + ",'" + contUtilizator.getUsername() + "','" + contUtilizator.getPassword() + "'," +
                    contUtilizator.getCodAcces() + ",'" + contUtilizator.getNumeUtilizator() + "','" +
                    contUtilizator.getPrenumeUtilizator() + "', " + ReturnareIDProfesor(contUtilizator.getNumeUtilizator(),contUtilizator.getPrenumeUtilizator()) + ")");
        if (contUtilizator.getCodAcces()==5)
            connection.createStatement().executeQuery("INSERT INTO LOGINCREDENTIALS(ID,USERNAME,PASSWORD,CODACCES,NUME_SECRETAR,PRENUME_SECRETAR) VALUES (" +
                    contUtilizator.getID() + ",'" + contUtilizator.getUsername() + "','" + contUtilizator.getPassword() + "'," +
                    contUtilizator.getCodAcces() + ",'" + contUtilizator.getNumeUtilizator() + "','" +
                    contUtilizator.getPrenumeUtilizator() + "', " + ReturnareIDSecretar(contUtilizator.getNumeUtilizator(),contUtilizator.getPrenumeUtilizator()) + ")");
    }

    public boolean VerificareUsername(String userName){
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery("SELECT * FROM LOGINCREDENTIALS WHERE USERNAME = '" + userName + "'");
            if(resultSet.next()) return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public int VerificareExistenta(String Nume, String Prenume , Long CNP, int CodAcces) throws SQLException {
        ResultSet resultSet;
        if(CodAcces ==1){
            resultSet = connection.createStatement().executeQuery("SELECT * FROM ELEVI WHERE NUME_ELEV='"+Nume+"' AND PRENUME_ELEV='"+ Prenume + "' AND " +
                    "CNP=" + CNP + "");
            if(resultSet.next()) return resultSet.getInt("ID_ELEV");
        }
        if(CodAcces ==2){
            resultSet = connection.createStatement().executeQuery("SELECT * FROM ELEVI WHERE NUME_ELEV='"+Nume+"' AND PRENUME_MAMA='"+ Prenume + "' AND " +
                    "CNP=" + CNP);
            if(resultSet.next()) return resultSet.getInt("ID_ELEV");
        }
        if(CodAcces ==3){
            resultSet = connection.createStatement().executeQuery("SELECT * FROM ELEVI WHERE NUME_ELEV='"+Nume+"' AND PRENUME_TATA='"+ Prenume + "' AND " +
                    "CNP=" + CNP );
            if(resultSet.next()) return resultSet.getInt("ID_ELEV");
        }
        if(CodAcces ==4){
            resultSet = connection.createStatement().executeQuery("SELECT * FROM PROFESORI WHERE NUME_PROFESOR='"+Nume+"' AND PRENUME_PROFESOR='"+ Prenume + "' AND " +
                    "CNP=" + CNP);
            if(resultSet.next()) return resultSet.getInt("ID_PROFESOR");
        }
        if(CodAcces ==5){
            resultSet = connection.createStatement().executeQuery("SELECT * FROM SECRETARI WHERE NUME_SECRETAR='"+Nume+"' AND PRENUME_SECRETAR='"+ Prenume + "' AND " +
                    "CNP=" + CNP);
            if(resultSet.next()) return resultSet.getInt("ID_SECRETAR");
        }
        return 0;
    }


    // Adaugare Manual
    public void AdaugareManual(Manual manual){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO MANUALE(ID_MANUAL,NUME_MANUAL,EDITURA,MATERIE,CLASA,DATA_ADAUGARE) VALUES ");
        sb.append("(").append(manual.getID_Manual()).append(",");
        sb.append("'").append(manual.getNume_manual()).append("','");
        sb.append(manual.getEditura()).append("','");
        sb.append(manual.getMaterie()).append("','");
        sb.append(manual.getClasa()).append("','");
        sb.append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("')");
        try {
            connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");
            connection.createStatement().executeQuery(String.valueOf(sb));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean VerificareExistentaManual(Manual manual){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM MANUALE WHERE NUME_MANUAL = '").append(manual.getNume_manual());
        sb.append("' AND EDITURA = '").append(manual.getEditura()).append("' AND CLASA = '").append(manual.getClasa()).append("'");

        try {
            ResultSet resultSet= connection.createStatement().executeQuery(sb.toString());
            if(resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public ObservableList<Materie> ReturnareMateriiClasa(String clasa){
        ObservableList<Materie> materii = FXCollections.observableArrayList();
        ResultSet resultSet ;
        Materie materie;

        try  {
            resultSet=connection.createStatement().executeQuery("SELECT * FROM MATERII WHERE CLASA='"+ clasa + "'");
            while(resultSet.next()){
                materie = new Materie();
                materie.setIdMaterie(resultSet.getInt("ID_MATERIE"));
                materie.setNume_Materie(resultSet.getString("NUME_MATERIE"));
                materie.setClasa(resultSet.getString("CLASA"));
                materii.add(materie);
            }
        } catch (SQLException throwables) {
            throwables.getNextException().printStackTrace();
            return materii;
        }
        return materii;
    }

    // Calculare Medii

    public ObservableList<Medie> CalculareMediiElevSelectat(Elev elev) throws SQLException {
        ObservableList<Materie> materii = FXCollections.observableArrayList();
        ObservableList<Nota> note = FXCollections.observableArrayList();
        ObservableList<Medie> medii = FXCollections.observableArrayList();
        ResultSet resultSet;
        Medie medie;
        Materie materie;
        Nota nota;
        int count1,count2;
        int suma1,suma2;
            resultSet = connection.createStatement().executeQuery("SELECT * FROM MATERII WHERE CLASA = '" + elev.getClasa() + "' ORDER BY NUME_MATERIE ASC");
            while (resultSet.next()){
                materie= new Materie();
                materie.setIdMaterie(resultSet.getInt("ID_MATERIE"));
                materie.setNume_Materie(resultSet.getString("NUME_MATERIE"));
                materii.add(materie);

            }
            for(Materie m: materii) {
                resultSet = connection.createStatement().executeQuery("SELECT * FROM NOTE WHERE ID_ELEV = " + elev.getIdElev() + " AND ID_MATERIE = " + m.getIdMaterie());
                while (resultSet.next()) {
                    nota = new Nota();
                    nota.setNota(resultSet.getInt("NOTA"));
                    nota.setIdMaterie(resultSet.getLong("ID_MATERIE"));
                    nota.setSemestru(resultSet.getString("SEMESTRU"));
                    note.add(nota);
                }
            }
            for (Materie m:materii) {
                count1=0;
                suma1=0;
                count2=0;
                suma2=0;
                if(!note.isEmpty())for(Nota n : note){
                    if(n.getIdMaterie()==m.getIdMaterie()){
                        if(n.getSemestru().equals("I")){
                            suma1+=n.getNota();
                            count1++;
                        }
                        if(n.getSemestru().equals("II")){
                            suma2+=n.getNota();
                            count2++;
                        }
                    }
                }
                medie=new Medie();
                medie.setMaterie(m.getNume_Materie());
                if(count1>=2){
                    medie.setMedie(String.valueOf(round((suma1/count1),0)));
                }else{
                    medie.setMedie("Nu exista destule note");
                }
                if(count2>=2){
                    medie.setMedie2(String.valueOf(round((suma1/count1),0)));
                }else if(SemestruActiv.equals("II")) {
                    medie.setMedie2("Nu exista destule note");
                }
                medii.add(medie);

            }

        return medii;
    }


    // Returnare note

    public ObservableList<Materie> ReturnareMateriElev(Elev elev) throws SQLException {
        ObservableList<Materie> materii = FXCollections.observableArrayList();
        Materie materie;
        ResultSet resultSet= connection.createStatement().executeQuery("SELECT * FROM MATERII WHERE CLASA='" + elev.getClasa() + "'");
        while(resultSet.next()){
            materie=new Materie();
            materie.setIdMaterie(resultSet.getInt("ID_MATERIE"));
            materie.setNume_Materie(resultSet.getString("Nume_Materie"));
            materii.add(materie);
        }
        return materii;
    }

    public ObservableList<Integer> ReturnareNoteMaterie(Materie m, Elev elev) throws SQLException {
        ObservableList<Integer> note = FXCollections.observableArrayList();
        int nota;
        ResultSet resultSet;

            resultSet=connection.createStatement().executeQuery("SELECT * FROM NOTE WHERE ID_MATERIE=" + m.getIdMaterie() + " AND ID_ELEV=" + elev.getIdElev());
            while (resultSet.next()){
                nota=resultSet.getInt("nota");
                note.add(nota);
            }
        return note;
    }

    // Terminare Semestru

    public boolean TerminareSemestru() throws SQLException {
        for(Elev e: FiltrareEleviActuali(ReturnareElevi())){
            if(VerificareMedii3(CalculareMediiElevSelectat(e))==false) return false;
        }
        return true;
    }

    public boolean VerificareMedii3(ObservableList<Medie> medii){
        for(Medie m:medii){
            System.out.println(m.getMedie() + " " + m.getMedie2());
            if(SemestruActiv.equals("I")) if(m.getMedie().equals("Nu exista destule note")) return false;
            if(SemestruActiv.equals("II")) if(m.getMedie2().equals("Nu exista destule note")) return false;
        }
        return true;
    }

    // Terminare An Scolar

    public String ReturnareClasa(int idClasa) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM CLASE WHERE ID_CLASA=" + idClasa);
        resultSet.next();
        return resultSet.getString("NUMAR_CLASA");
    }

    public boolean VerificareMedii2(ObservableList<Medie> medii){
        for(Medie m:medii){
            System.out.println(m.getMedie() + " " + m.getMedie2());
            if(m.getMedie().equals("Nu exista destule note")) return false;
            if(m.getMedie2().equals("Nu exista destule note")) return false;
        }
        return true;
    }

    public boolean TerminareAnScolar() throws SQLException {
        ObservableList<Elev> elevi = FiltrareEleviActuali(ReturnareElevi());
        boolean OK = false;
        for (Elev e : elevi) {
            e.setClasa(ReturnareClasa(e.getIdClasa()));
            if (VerificareMedii2(CalculareMediiElevSelectat(e))) {
                OK=true;
            }else {
                OK=false;
            }
        }
        if(OK){
            for (Elev e : elevi) {
                e.setClasa(ReturnareClasa(e.getIdClasa()));
                switch (e.getClasa()) {
                    case "XII":
                        connection.createStatement().executeQuery("UPDATE ELEVI SET ID_CLASA = '' WHERE ID_ELEV=" + e.getIdElev());
                        connection.createStatement().executeQuery("alter SESSION set NLS_DATE_FORMAT = 'DD/MM/YYYY' ");
                        connection.createStatement().executeQuery("UPDATE ELEVI SET DATA_ABSOLVIRE=" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " WHERE ID_ELEV=" + e.getIdElev());
                        break;
                    case "XI":
                        connection.createStatement().executeQuery("UPDATE ELEVI SET ID_CLASA = '" + (e.getIdClasa() + 4) + "' WHERE ID_ELEV=" + e.getIdElev());
                        break;
                    case "X":
                        connection.createStatement().executeQuery("UPDATE ELEVI SET ID_CLASA = '" + (e.getIdClasa() + 4) + "' WHERE ID_ELEV=" + e.getIdElev());
                        break;
                    case "IX":
                        connection.createStatement().executeQuery("UPDATE ELEVI SET ID_CLASA = '" + (e.getIdClasa() + 4) + "' WHERE ID_ELEV=" + e.getIdElev());
                        break;
                }
            }
        }
        return OK;
    }

    public String ReturnareMaterie(long idMaterie) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM MATERII WHERE ID_MATERIE = " + idMaterie);
        resultSet.next();
        return resultSet.getString("NUME_MATERIE");
    }

    public ObservableList<Nota> ReturnareNoteElev(Elev elev) throws SQLException {
        ObservableList<Nota> note =FXCollections.observableArrayList();
        Nota nota;
        ObservableList<Materie> materii = ReturnareMateriiClasa(elev.getClasa());
        for(Materie m:materii) {

            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM NOTE WHERE ID_ELEV = " + elev.getIdElev() + " AND ID_MATERIE = " + m.getIdMaterie());
            while (resultSet.next()) {

                nota = new Nota();
                nota.setNota(resultSet.getInt("NOTA"));
                nota.setIdMaterie(resultSet.getInt("ID_MATERIE"));
                nota.setData(resultSet.getDate("DATA").toLocalDate());
                nota.setSemestru(resultSet.getString("SEMESTRU"));

                note.add(nota);
            }
        }
        return note;
    }





    public void close() throws SQLException {
        statement.close();
        connection.close();
        System.out.println("Connection closed!");
    }


    public Long getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Long idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public String getUserName() {
        return userName;
    }

    public int getCodAcces() {
        return CodAcces;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public String getPrenumeUtilizator() {
        return prenumeUtilizator;
    }

    public void setCodAcces(int codAcces) {
        this.CodAcces = codAcces;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public void setPrenumeUtilizator(String prenumeUtilizator) {
        this.prenumeUtilizator = prenumeUtilizator;
    }


    private   double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
