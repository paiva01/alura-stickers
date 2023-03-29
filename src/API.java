public enum API {

    IMDB_TOP_MOVIES("https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060",
        new ExtratorDeConteudoImdb()),
    IMDB_TOP_SERIES("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json",
        new ExtratorDeConteudoImdb()),
    IMDB_POP_MOVIES("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json",
        new ExtratorDeConteudoImdb()),
    IMDB_POP_SERIES("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json",
        new ExtratorDeConteudoImdb()),
    NASA("https://api.nasa.gov/planetary/apod?api_key=gWPNOzHaGbfsekt7y1cf1jKCU9rZCeE9jkUD7U4v&start_date=2023-03-22&end_date=2023-03-29",
        new ExtratorDeConteudoNasa());

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
}
