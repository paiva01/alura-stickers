import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {

    static final String FORMAT_RESET = "\u001b[0m";
    static final String MAGENTA = "\u001b[45m";
    static final String AZUL = "\u001b[44m";
    static final String VERDE = "\u001b[42m";
    static final String VERMELHO = "\u001b[41m";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        mostrarMenu();
        manipularDados(scanner);
        
        scanner.close();
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
                System.out.println("Título: " + conteudo.getTitulo());
                System.out.println("Imagem: " + conteudo.getUrlImagem());
                System.out.println();
            }

        }
    }

    private static void manipularDados(Scanner scanner) throws Exception {
        
        int opcao;

        try {
           opcao = scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Dado inválido! Digite apenas números!");
        }

        switch (opcao) {
            case 1:
                System.out.println(MAGENTA + "\n-- TOP FILMES -----------\n" + FORMAT_RESET);
                
                var api = API.IMDB_TOP_MOVIES;
                consumir(api);

                break;
            case 2:
                System.out.println(MAGENTA + "\n-- FILMES POPULARES -----------\n" + FORMAT_RESET);

                api = API.IMDB_POP_MOVIES;
                consumir(api);

                break;
            case 3:
                System.out.println(MAGENTA + "\n-- TOP SÉRIES -----------\n" + FORMAT_RESET);

                api = API.IMDB_TOP_SERIES;
                consumir(api);

                break;
            case 4:
                System.out.println(MAGENTA + "\n-- SÉRIES POPULARES -----------\n" + FORMAT_RESET);

                api = API.IMDB_POP_SERIES;
                consumir(api);

                break;
            case 5:
                System.out.println(AZUL + "\nGerando figurinhas ...\n" + FORMAT_RESET);

                api = API.IMDB_TOP_MOVIES;
                consumir2(api);

                System.out.println(VERDE + "Figurinhas criadas com sucesso!" + FORMAT_RESET);

                break;
            case 6:
                System.out.println(AZUL + "\n-- Imagens da Semana -----\n" + FORMAT_RESET);

                api = API.NASA_WEEK_IMAGEs;
                consumir(api);

                break;
            case 7:
                System.out.println(AZUL + "\nSalvando imagem ...\n" + FORMAT_RESET);

                api = API.NASA_DAY_IMAGE;
                consumir2(api);

                System.out.println(VERDE + "Imagem salva com sucesso!" + FORMAT_RESET);
                break;
            case 8:
                System.out.println(AZUL + "\nGerando figurinhas imagem ...\n" + FORMAT_RESET);

                api = API.LANGS_API;
                consumir2(api);

                System.out.println(VERDE + "Figurinhas salvas com sucesso!" + FORMAT_RESET);
                
                break;
            case 0:
                System.out.println(VERMELHO + "\nEncerrando..." + FORMAT_RESET);
                Thread.sleep(2000);
                System.out.println(AZUL + "\nPrograma finalizado." + FORMAT_RESET);

                System.exit(0);

                break;
            default:
                System.err.println("\nERRO! Opção inválida, leia e tente novamente.\n");
                
                Thread.sleep(2500);
                
                mostrarMenu();
                manipularDados(scanner);

                break;
        }

    }

    private static void consumir(API api) {
        String url = api.getUrl();
        ExtratorDeConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.consumir(url);

        exibirDados(json, extrator);
    }

    private static void consumir2(API api) throws Exception {
        String url = api.getUrl();
        ExtratorDeConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.consumir(url);

        List<Conteudo> lista = extrator.extrairDados(json);

        var gerador = new GeradorDeFiguras();

        gerador.gerarFiguras(lista, extrator);
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
                7. Salvar imagem Astronômica do dia.
                                ---
                8. Gerar figurinhas das linguagens.
                ------------------------------------------
                0. Para encerrar o programa.
                ------------------------------------------
                Escolha uma opção:  """);
    }
}
