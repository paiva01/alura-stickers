public class Conteudo {

    private final String rank;
    private final String titulo;
    private final String urlImagem;    
    private final String nota;    
    
    public Conteudo(String titulo, String urlImagem, String nota, String rank) {
        this.rank = rank;
        this.titulo = titulo;
        this.urlImagem = urlImagem;
        this.nota = nota;
    }

    public Conteudo(String titulo, String urlImagem) {
        this.titulo = titulo;
        this.urlImagem = urlImagem;
        this.nota = null;
        this.rank = null;
    }

    public String getTitulo() {
        return titulo;
    }
    public String getUrlImagem() {
        return urlImagem;
    }
    public String getNota() {
        return nota;
    }
    public String getRank() {
        return rank;
    }
}
