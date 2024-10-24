package dao;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Livro;

public class LivroDao {

    public static Connection con;
    public static Livro livro;

    public static void inserirLivro(Livro livro) {

        con = new Conexao().obterConexao();
        String sql = "INSERT INTO livros (titulo,autor,genero,disponiveis) VALUES (?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, livro.getTitulo());
            pstm.setString(2, livro.getAutor());
            pstm.setString(3, livro.getGenero());
            pstm.setInt(4, livro.getQuantidadeDisponivel());
            pstm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Livro inserido com sucesso!");

            pstm.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir livro, Erro: " + e, "AVISO", JOptionPane.WARNING_MESSAGE);
        }

    }
    public static boolean verificarSeLivroExiste(Livro livro){
        
        con = new Conexao().obterConexao();
        String sql="SELECT COUNT(*) FROM livros WHERE titulo=? AND autor=? AND genero=?";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,livro.getTitulo());
            pstm.setString(2,livro.getAutor());
            pstm.setString(3,livro.getGenero());
            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                int count = rs.getInt(1);
                return count>0;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao veriricar se o livro existe, Erro: " + e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }return false;
         
    }

    public static void atualizarLivro(Livro livro) {

        con = new Conexao().obterConexao();
        String sql = "UPDATE livros SET titulo=?,autor=?,genero=?,disponiveis=? WHERE id=?";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, livro.getTitulo());
            pstm.setString(2, livro.getAutor());
            pstm.setString(3, livro.getGenero());
            pstm.setInt(4, livro.getQuantidadeDisponivel());
            pstm.setInt(5,livro.getId());
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");

            pstm.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar livro, Erro: " + e, "AVISO", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void deletarLivro(Livro livro) {

        con = new Conexao().obterConexao();
        String sql = "DELETE FROM livros WHERE titulo=? AND autor=? AND genero=? AND disponiveis=?";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, livro.getTitulo());
            pstm.setString(2, livro.getAutor());
            pstm.setString(3, livro.getGenero());
            pstm.setInt(4, livro.getQuantidadeDisponivel());
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Livro deletado com sucesso!");

            pstm.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar livro, Erro: " + e, "AVISO", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void deletarLivroPorId(Livro livro) {

        con = new Conexao().obterConexao();
        String sql = "DELETE FROM livros WHERE id=?";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, livro.getId());
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Livro deletado com sucesso!");

            pstm.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar livro, Erro: " + e, "AVISO", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static ArrayList<Livro> listarLivros() {

        String sql = "SELECT * FROM livros ORDER BY ID";
        con = new Conexao().obterConexao();
        ArrayList<Livro> listaLivros = new ArrayList();

        try {

            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String genero = rs.getString("genero");
                int disponiveis = rs.getInt("disponiveis");

                Livro livro = new Livro(id, titulo, autor, genero, disponiveis);
                listaLivros.add(livro);
            }
            pstm.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao listar livros, Erro: "+e,"AVISO",JOptionPane.ERROR_MESSAGE);
        }return listaLivros;

    }
    
    public static Livro obterLivroPorId(int id){
    
        con = new Conexao().obterConexao();
        
        String sql = "SELECT * FROM livros WHERE id=?";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                String titulo=rs.getString("titulo");
                String autor=rs.getString("autor");
                String genero=rs.getString("genero");
                int disponiveis=rs.getInt("disponiveis");
                
                Livro livro = new Livro(id,titulo,autor,genero,disponiveis);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao obter livro por id, Erro: "+e,"AVISO",JOptionPane.ERROR_MESSAGE);
        }return livro;
    
    }

}
