import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class App {

    static final String FORMAT_RESET = "\u001b[0m";
    static final String MAGENTA = "\u001b[45m";
    static final String AZUL = "\u001b[44m";
    static final String VERDE = "\u001b[42m";
    static final String VERMELHO = "\u001b[41m";

    public static void main(String[] args) throws IOException, InterruptedException, FontFormatException {
        Scanner scanner = new Scanner(System.in);

        mostrarMenu();

        var opcao = scanner.nextInt();
        scanner.close();

        manipularDados(opcao);

    }

    private static void exibirDados(String json, ExtratorDeConteudo extrator) {
        List<Conteudo> conteudos = extrator.extrairDados(json);
        
        for (Conteudo conteudo : conteudos) {

            if (extrator instanceof ExtratorDeConteudoImdb) {
                if (conteudo.getRank().equals("1")) {
                    System.out.println(
                            "\u001b[33m \u001b[40m \u001b[1m" + "\uD83D\uDC51 " + conteudo.getTitulo() + FORMAT_RESET);
                    System.out.println("\u001b[34m \u001b[3m" + conteudo.getUrlImagem() + FORMAT_RESET);
                    System.out.println("\u2B50 \u001b[5m" + conteudo.getNota() + FORMAT_RESET);
                    System.out.println();
                } else {
                    System.out.println("\u001b[33m \u001b[40m \u001b[1m" + conteudo.getRank() + ". "
                            + conteudo.getTitulo() + FORMAT_RESET);
                    System.out.println("\u001b[34m \u001b[3m" + conteudo.getUrlImagem() + FORMAT_RESET);
                    System.out.println("\u2B50 \u001b[5m" + conteudo.getNota() + FORMAT_RESET);
                    System.out.println();
                }
            } else if (extrator instanceof ExtratorDeConteudoNasa) {
                System.out.println("Título: "+ conteudo.getTitulo());
                System.out.println("Imagem: "+ conteudo.getUrlImagem());
                System.out.println();
            }

        }
    }

    private static void manipularDados(int opcao) throws IOException, InterruptedException {
        switch (opcao) {
            case 1:
                System.out.println(MAGENTA + "\n-- TOP FILMES -----------\n" + FORMAT_RESET);
                var api = API.IMDB_TOP_MOVIES;
                String url = api.getUrl();
                ExtratorDeConteudo extrator = api.getExtrator();

                var http = new ClienteHttp();
                String json = http.consumir(url);

                exibirDados(json, extrator);
                
                break;
            case 2:
                System.out.println(MAGENTA + "\n-- FILMES POPULARES -----------\n" + FORMAT_RESET);
                
                api = API.IMDB_POP_MOVIES;
                url = api.getUrl();
                extrator = api.getExtrator();

                http = new ClienteHttp();
                json = http.consumir(url);

                exibirDados(json, extrator);

                break;
            case 3:
                System.out.println(MAGENTA + "\n-- TOP SÉRIES -----------\n" + FORMAT_RESET);
                
                api = API.IMDB_TOP_SERIES;
                url = api.getUrl();
                extrator = api.getExtrator();

                http = new ClienteHttp();
                json = http.consumir(url);

                exibirDados(json, extrator);
                
                break;
            case 4:
                System.out.println(MAGENTA + "\n-- SÉRIES POPULARES -----------\n" + FORMAT_RESET);
                
                api = API.IMDB_POP_SERIES;
                url = api.getUrl();
                extrator = api.getExtrator();

                http = new ClienteHttp();
                json = http.consumir(url);

                exibirDados(json, extrator);

                break;
            case 5:
                System.out.println(AZUL + "\nGerando figurinhas ...\n" + FORMAT_RESET);
                
                api = API.IMDB_TOP_MOVIES;
                url = api.getUrl();
                extrator = api.getExtrator();

                http = new ClienteHttp();
                json = http.consumir(url);

                List<Conteudo> conteudos = extrator.extrairDados(json);

                var gerador = new GeradorDeFiguras();

                gerador.gerarFiguras(conteudos);

                System.out.println(VERDE + "Figurinhas criadas com sucesso!" + FORMAT_RESET);

                break;
            case 6:
                System.out.println(AZUL + "\n-- Imagens da Semana -----\n" + FORMAT_RESET);
                
                api = API.NASA;
                url = api.getUrl();
                extrator = api.getExtrator();

                http = new ClienteHttp();
                json = http.consumir(url);

                exibirDados(json, extrator);

                break;
            case 0:
                System.out.println(VERMELHO + "\nEncerrando..." + FORMAT_RESET);
                Thread.sleep(2000);
                System.out.println(AZUL + "\nPrograma finalizado." + FORMAT_RESET);
                
                System.exit(0);
                
                break;   
            default:
                break;
        }

    }

    private static void mostrarMenu() {
        System.out.print("""
                                [ MENU ]
                ------------------------------------------
                1. Mostrar os Top Filmes
                2. Mostrar os Filmes mais populares.
                3. Mostrar as Top Séries.
                4. Mostrar as Séries mais populares.
                5. Gerar figurinhas dos Top Filmes.
                                ---
                6. Imagens Astronômicas da Semana.
                ------------------------------------------
                0. Para encerrar o programa.
                ------------------------------------------
                Escolha uma opção:  """);
    }
}
