
package models;

import java.time.LocalDate;


public class Aluguel {
    private int id;
    private int idUsuario;
    private int idLivro;
    private LocalDate dataAluguel;
    private LocalDate dataDevolucao;

    public Aluguel(int id, int idUsuario, int idLivro) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataAluguel = LocalDate.now();
        this.dataDevolucao = dataAluguel.plusDays(15);
    }
    public Aluguel(int idUsuario, int idLivro) {
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataAluguel = LocalDate.now();
        this.dataDevolucao = dataAluguel.plusDays(15);
    }
    public Aluguel() {
       
    }

    public LocalDate getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(LocalDate dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }
    
    
    
}
