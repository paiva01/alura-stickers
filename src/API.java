import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public enum API {

    IMDB_TOP_MOVIES("https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060",
            new ExtratorDeConteudoImdb()),
    IMDB_TOP_SERIES("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json",
            new ExtratorDeConteudoImdb()),
    IMDB_POP_MOVIES("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json",
            new ExtratorDeConteudoImdb()),
    IMDB_POP_SERIES("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json",
            new ExtratorDeConteudoImdb()),
    NASA_DAY_IMAGE("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date="
        + obterDataAtual() + "&end_date=" + obterDataAtual(), new ExtratorDeConteudoNasa()),
    NASA_WEEK_IMAGEs("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date="
        + obterDataDeSeteDiasAtras() + "&end_date=" + obterDataAtual(), new ExtratorDeConteudoNasa()),
    LANGS_API("http://localhost:8080/langs", new ExtratorDeConteudoLangs());

    private String url;
    private ExtratorDeConteudo extrator;

    API(String url, ExtratorDeConteudo extrator) {
        this.url = url;
        this.extrator = extrator;
    }

    public String getUrl() {
        return url;
    }

    public ExtratorDeConteudo getExtrator() {
        return extrator;
    }

    private static String obterDataAtual() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private static String obterDataDeSeteDiasAtras() {
        return LocalDate.now().minusDays(7)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
