import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoImdb implements ExtratorDeConteudo {
    
    public List<Conteudo> extrairDados(String json) {
        
        List<Map<String, String>> listaDeAtributos = JsonParser.parse(json);
        
        // popular a lista de conteudos
        return listaDeAtributos.stream().map(atributos ->
            new Conteudo(atributos.get("title"),
            atributos.get("image").replaceAll("(@+)(.*).jpg$", "$1.jpg"),
            atributos.get("imDbRating"), atributos.get("rank")))
            .toList();
    }
    
}
