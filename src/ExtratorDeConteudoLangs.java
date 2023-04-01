import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoLangs implements ExtratorDeConteudo {
    public List<Conteudo> extrairDados(String json) {
        
        List<Map<String, String>> listaDeAtributos = JsonParser.parse(json);
        
        // popular a lista de conteudos
        return listaDeAtributos.stream().map(atributos ->
            new Conteudo(atributos.get("name"),
            atributos.get("logo")))
            .toList();
    }
}
