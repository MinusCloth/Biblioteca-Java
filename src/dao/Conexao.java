
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    
    String url,user,password;
    Connection con;
    
    public Conexao(){
    
        url="jdbc:postgresql://localhost:5432/biblioteca";
        user="postgres";
        password="123456";
        
        try {
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection(url, user, password);
            System.out.println("Conexao realizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro na conexao");
        }
    }
    public Connection obterConexao(){
        return con;
    }
    
    
    
    
}
