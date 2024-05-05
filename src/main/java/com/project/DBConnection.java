package com.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection con;

    public void getDBConn()
    {
        synchronized (""){
            try{

                if(this.getCon() == null || this.getCon().isClosed())
                {

                    try {
                        String url = "jdbc:mysql://localhost:3306/game1";
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        setCon(DriverManager.getConnection(url,"root","1234"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{}
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    public static Connection getCon(){
        return con;
    }
    public static void setCon(Connection aCon)
    {
        con = aCon;
    }
    public static void closeConnection(){
        try{
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

}
