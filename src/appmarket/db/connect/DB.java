package appmarket.db.connect;

public class DB 
{
    private static Connect con=null;

    private DB() {
    }    
    
    public static boolean DBconnect()
    {
        con=new Connect();
        return con.conectar("jdbc:postgresql://localhost/", "appMarket", "postgres", "postgres123");
    }
    public static Connect getCon()
    {
        return con;
    }    
}
