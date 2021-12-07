package sample.Obiecte;

public class ContUtilizator {
    private final String ID= "SECVLOGINCREDENTIALS.NEXTVAL";
    private String username;
    private String password;
    private int codAcces;
    private String numeUtilizator;
    private String prenumeUtilizator;
    private int idUtilizator;


    public ContUtilizator(String username, String password, int codAcces, String numeUtilizator, String prenumeUtilizator, int idUtilizator) {
        this.username = username;
        this.password = password;
        this.codAcces = codAcces;
        this.numeUtilizator = numeUtilizator;
        this.prenumeUtilizator = prenumeUtilizator;
        this.idUtilizator = idUtilizator;
    }

    public ContUtilizator() {
    }

    @Override
    public String toString() {
        return username;
    }

    public int getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public String getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodAcces() {
        return codAcces;
    }

    public void setCodAcces(int codAcces) {
        this.codAcces = codAcces;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getPrenumeUtilizator() {
        return prenumeUtilizator;
    }

    public void setPrenumeUtilizator(String prenumeUtilizator) {
        this.prenumeUtilizator = prenumeUtilizator;
    }
}
