package Conn;

public class ActiveConnection
{

    private String username;
    private int codAcces;
    private String numeUtilizator;
    private String prenumeUtilizator;



    public ActiveConnection(String username,int codAcces,String numeProfesor,String prenumeProfesor){
        this.username=username;
        this.codAcces=codAcces;
        this.numeUtilizator=numeProfesor;
        this.prenumeUtilizator=prenumeProfesor;
    }

    public String getUsername() {
        return username;
    }

    public int getCodAcces() {
        return codAcces;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public String getPrenumeUtilizator() {
        return prenumeUtilizator;
    }
}
