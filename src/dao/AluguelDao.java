package dao;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Aluguel;
import models.Livro;
import models.Usuario;

public class AluguelDao {

    private static Connection con;
    private static Aluguel aluguel;

    public static void inserirAluguel(Aluguel aluguel) {

        String sql = "INSERT INTO alugueis (id_usuario,id_livros,data_locacao,data_devolucao) VALUES(?,?,?,?)";

        con = new Conexao().obterConexao();

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, aluguel.getIdUsuario());
            pstm.setInt(2, aluguel.getIdLivro());
            pstm.setDate(3, java.sql.Date.valueOf(aluguel.getDataAluguel()));
            pstm.setDate(4, java.sql.Date.valueOf(aluguel.getDataDevolucao()));

            pstm.executeUpdate();

            //atualizarAluguelDisponiveis(aluguel);
            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir aluguel, Erro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean verificarAluguelExistente(int idUsuario, int idLivro) {
        String sql = "SELECT COUNT(*) FROM alugueis WHERE id_usuario = ? AND id_livros = ?";
        con = new Conexao().obterConexao();
        boolean existe = false;

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idUsuario);
            pstm.setInt(2, idLivro);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1); // Obtém o número de registros encontrados
                existe = (count > 0); // Se count for maior que 0, o livro já está alugado
            }

            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar aluguel existente, Erro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return existe;
    }

    public static void atualizarAluguelDisponiveisDecrementar(Aluguel aluguel) {

        String sql = "UPDATE livros SET disponiveis = disponiveis - 1 WHERE id = ?";

        //colocar metodo para buscar livro por id
        con = new Conexao().obterConexao();

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, aluguel.getIdLivro());

            pstm.executeUpdate();

            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar aluguel, Erro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void atualizarAluguelDisponiveisIncrementar(Aluguel aluguel) {

        String sql = "UPDATE livros SET disponiveis = disponiveis + 1 WHERE id = ?";

        //colocar metodo para buscar livro por id
        con = new Conexao().obterConexao();

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, aluguel.getIdLivro());

            pstm.executeUpdate();

            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar aluguel, Erro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deletarAluguel(Aluguel aluguel) {

        String sql = "DELETE FROM alugueis WHERE id_usuario=? AND id_livros=? ";

        con = new Conexao().obterConexao();

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, aluguel.getIdUsuario());
            pstm.setInt(2, aluguel.getIdLivro());

            pstm.executeUpdate();

            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar aluguel, Erro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Este so o adm usara
    public static ArrayList<Aluguel> obterAlugueis() {

        con = new Conexao().obterConexao();
        ArrayList<Aluguel> listaAlugueis = new ArrayList();
        String sql = "SELECT * FROM alugueis";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                int idUsuario = rs.getInt("id_usuario");
                int idLivro = rs.getInt("id_livro");
                Aluguel aluguel = new Aluguel(id, idUsuario, idLivro);
                listaAlugueis.add(aluguel);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar aluguel, Erro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return listaAlugueis;
    }

    public static ArrayList<Livro> listaLivroUsuario(Usuario usuario) {

        con = new Conexao().obterConexao();
        ArrayList<Livro> listaLivroUsuario = new ArrayList();
        String sql = "SELECT l.id,l.titulo,l.autor,l.genero,l.disponiveis FROM alugueis a JOIN livros l ON a.id_livros=l.id WHERE a.id_usuario=?";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, usuario.getId());

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String genero = rs.getString("genero");
                int disponiveis = rs.getInt("disponiveis");

                Livro livro = new Livro(id, titulo, autor, genero, disponiveis);
                listaLivroUsuario.add(livro);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter nome do livro, Erro: " + e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        return listaLivroUsuario;

    }

    public static ArrayList<String[]> listarAlugueisComDetalhes() {
        con = new Conexao().obterConexao();
        ArrayList<String[]> listaAlugueis = new ArrayList<>();

        // Consulta que une as tabelas para obter nome do usuário e título do livro
        String sql = "SELECT u.nome AS nome_usuario, l.titulo AS titulo_livro "
                + "FROM alugueis a "
                + "JOIN usuarios u ON a.id_usuario = u.id "
                + "JOIN livros l ON a.id_livros = l.id";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String nomeUsuario = rs.getString("nome_usuario");
                String tituloLivro = rs.getString("titulo_livro");

                // Adiciona os resultados em um array de Strings para cada linha
                String[] detalhesAluguel = {nomeUsuario, tituloLivro};
                listaAlugueis.add(detalhesAluguel);
            }

            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter dados de aluguel: " + e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }

        return listaAlugueis;
    }

    public static ArrayList<String[]> listarAlugueisAtrasados() {
        con = new Conexao().obterConexao();
        ArrayList<String[]> listaAlugueisAtrasados = new ArrayList<>();

        // Consulta para pegar os usuários e livros cujas datas de devolução já passaram
        String sql = "SELECT u.nome AS nome_usuario, l.titulo AS titulo_livro, a.data_devolucao "
                + "FROM alugueis a "
                + "JOIN usuarios u ON a.id_usuario = u.id "
                + "JOIN livros l ON a.id_livros = l.id "
                + "WHERE a.data_devolucao < CURRENT_DATE";  // Verifica se a data de devolução já passou da data atual

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            

            while (rs.next()) {

                String nomeUsuario = rs.getString("nome_usuario");
                String tituloLivro = rs.getString("titulo_livro");
                String dataDevolucao = rs.getString("data_devolucao");

                // Adiciona os resultados em um array de Strings para cada linha
                String[] detalhesAluguelAtrasado = {nomeUsuario, tituloLivro, dataDevolucao};
                listaAlugueisAtrasados.add(detalhesAluguelAtrasado);
            }

            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter dados de aluguéis atrasados: " + e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        return listaAlugueisAtrasados;
    }
    public static ArrayList<String[]> listarAlugueisAtrasadosUsuario(Usuario usuario) {
        con = new Conexao().obterConexao();
        ArrayList<String[]> listaAlugueisAtrasados = new ArrayList<>();

        // Consulta para pegar os usuários e livros cujas datas de devolução já passaram
        String sql = "SELECT u.nome AS nome_usuario, l.titulo AS titulo_livro, a.data_devolucao "
               + "FROM alugueis a "
               + "JOIN usuarios u ON a.id_usuario = u.id "
               + "JOIN livros l ON a.id_livros = l.id "
               + "WHERE a.data_devolucao < CURRENT_DATE AND a.id_usuario = ?";  // Verifica se a data de devolução já passou da data atual

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, usuario.getId());

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                String nomeUsuario = rs.getString("nome_usuario");
                String tituloLivro = rs.getString("titulo_livro");
                String dataDevolucao = rs.getString("data_devolucao");

                // Adiciona os resultados em um array de Strings para cada linha
                String[] detalhesAluguelAtrasado = {nomeUsuario, tituloLivro, dataDevolucao};
                listaAlugueisAtrasados.add(detalhesAluguelAtrasado);
            }

            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter dados de aluguéis atrasados: " + e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        return listaAlugueisAtrasados;
    }

}
