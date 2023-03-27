import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    static final String FORMAT_RESET = "\u001b[0m";
    static final String MAGENTA = "\u001b[45m";
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner  = new Scanner(System.in);
        
        String urlTopMovies = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        String urlMostPopularMovies = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        String urlTopSeries = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
        String urlMostPopularSeries = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
        
        List<String> listaEndpoints = new ArrayList<>();
        listaEndpoints.add(urlTopMovies);
        listaEndpoints.add(urlMostPopularMovies);
        listaEndpoints.add(urlTopSeries);
        listaEndpoints.add(urlMostPopularSeries);

        mostrarMenu();

        var opcao = scanner.nextInt();
        scanner.close();

        mostrarResultados(opcao, listaEndpoints);

    }

    private static List<Map<String, String>> consumir(String url) throws IOException, InterruptedException {
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return JsonParser.parse(response.body());
    }

    private static void exibirDados(List<Map<String, String>> lista) {

        for (Map<String, String> filme : lista) {

            if (filme.get("rank").equals("1")) {
                System.out.println("\u001b[33m \u001b[40m \u001b[1m" + "\uD83D\uDC51 " + filme.get("title") + FORMAT_RESET);
                System.out.println("\u001b[34m \u001b[3m" + filme.get("image") + FORMAT_RESET);
                System.out.println("\u2B50 \u001b[5m" + filme.get("imDbRating") + FORMAT_RESET);
                System.out.println();
            } else {
                System.out.println("\u001b[33m \u001b[40m \u001b[1m" + filme.get("rank") +". "+ filme.get("title") + FORMAT_RESET);
                System.out.println("\u001b[34m \u001b[3m" + filme.get("image") + FORMAT_RESET);
                System.out.println("\u2B50 \u001b[5m" + filme.get("imDbRating") + FORMAT_RESET);
                System.out.println();
            }
        }
    }

    private static void mostrarMenu() {
        System.out.print("""            
                        [ MENU ]
        -------------------------------------------
        1. Mostrar os Top Filmes
        2. Mostrar os Filmes mais populares.
        3. Mostrar as Top Séries.
        4. Mostrar as Séries mais populares.
        -------------------------------------------
        Escolha uma opção:  """);
    }

    private static void mostrarResultados(int opcao, List<String> listaUrl) throws IOException, InterruptedException {
        switch (opcao) {
            case 1:
                System.out.println(MAGENTA + "\n-- TOP FILMES -----------\n" + FORMAT_RESET);
                exibirDados(consumir(listaUrl.get(0)));
                break;
            case 2:
                System.out.println(MAGENTA + "\n-- FILMES POPULARES -----------\n" + FORMAT_RESET);
                exibirDados(consumir(listaUrl.get(1)));
                break;
            case 3:
                System.out.println(MAGENTA + "\n-- TOP SÉRIES -----------\n" + FORMAT_RESET);
                exibirDados(consumir(listaUrl.get(2)));
                break;
            case 4:
                System.out.println(MAGENTA + "\n-- SÉRIES POPULARES -----------\n" + FORMAT_RESET);
                exibirDados(consumir(listaUrl.get(3)));
                break;
            default:
                break;
        }
    }
}
