package dao;

import java.sql.Connection;
import models.Usuario;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuarioDao {

    private static Connection con;
    private static Usuario usuario;

    public void inserirUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nome,email,senha) values(?,?,?)";

        con = new Conexao().obterConexao();

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());

            pstm.executeUpdate();
            
            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir usuário, Erro: " + e);
        }

    }

    public void atualizarUsuario(Usuario usuario) {

        String sql = "UPDATE usuarios SET nome=?,email=?,senha=? WHERE id=?";
        con = new Conexao().obterConexao();
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            pstm.setInt(4, usuario.getId());

            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário, Erro: " + e);
        }

    }

    public void deletarUsuarioPorId(Usuario usuario) {

        String sql = "DELETE FROM usuarios WHERE id=?";
        con = new Conexao().obterConexao();
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, usuario.getId());

            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso!");
            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar usuário, Erro: " + e);
        }

    }

    public ArrayList<Usuario> listarUsuarios() {

        String sql = "SELECT * FROM usuarios";
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        con = new Conexao().obterConexao();
        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                Usuario usuario = new Usuario(id, nome, email, senha);
                listaUsuarios.add(usuario);

            }

            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar usuário, Erro: " + e);
        }
        return listaUsuarios;

    }

    public static boolean autenticarUsuario(String email, String senha) {

        con = new Conexao().obterConexao();
        String sql = "SELECT id FROM usuarios WHERE email=? AND senha=?";

        try {
            PreparedStatement pstm = con.prepareCall(sql);
            pstm.setString(1, email);
            pstm.setString(2, senha);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao autenticar usuário, Erro: " + e);
        }
        return false;
    }

    public static boolean verificarSeExiste(Usuario usuario) {

        String sql = "SELECT COUNT(*) FROM usuarios WHERE nome=? AND email=? AND senha=?";
        con=new Conexao().obterConexao();
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
              int count = rs.getInt(1);
              return count>0;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar se a pessoa já existe: " + e);
        }return false;

    }
    
    public static void obterId(Usuario usuario){
        
        con=new Conexao().obterConexao();
        String sql = "SELECT id FROM usuarios WHERE email=? AND senha=?";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, usuario.getEmail());
            pstm.setString(2, usuario.getSenha());
            
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                usuario.setId(id);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter id da pessoa: " + e);
        }
        
        
    }

}
