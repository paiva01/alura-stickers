import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoImdb implements ExtratorDeConteudo {
    
    public List<Conteudo> extrairDados(String json) {
        
        List<Map<String, String>> listaDeAtributos = JsonParser.parse(json);
        List<Conteudo> conteudos = new ArrayList<>();
        
        // popular a lista de conteudos
        for (Map<String, String> atributos : listaDeAtributos) {
            String titulo = atributos.get("title");
            String urlImagem = atributos.get("image")
                .replaceAll("(@+)(.*).jpg$", "$1.jpg");
            
            String nota = atributos.get("imDbRating");
            String rank = atributos.get("rank");
            
            conteudos.add(new Conteudo(titulo, urlImagem, nota, rank));
        }

        return conteudos;
    }
    
}
