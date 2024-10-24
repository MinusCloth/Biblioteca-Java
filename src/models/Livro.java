 
package models;

 
public class Livro {
    
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private int disponiveis;

    public Livro(int id, String titulo, String autor, String genero, int disponiveis) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponiveis = disponiveis;
    }
    public Livro( String titulo, String autor, String genero, int disponiveis) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponiveis = disponiveis;
    }
    public Livro(){
    
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getQuantidadeDisponivel() {
        return disponiveis;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.disponiveis = quantidadeDisponivel;
    }


    
}
