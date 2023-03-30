import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

public class GeradorDeFiguras {
    
    private void criar(InputStream inputStream,
        String nomeArquivo, String textoSticker, InputStream inputStreamSobreposicao) throws IOException {
        // leitura da imagem

        // InputStream inputStream =
        //    new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_1.jpg")
        //    .openStream();
        
        BufferedImage imagemOriginal = ImageIO.read(inputStream);
            
        // cria nova imagem em memoria com transparência e com tamanho novo
        int altura = imagemOriginal.getHeight();
        int largura = imagemOriginal.getWidth();
        int novaAltura = altura + 200; // 200px
        
        BufferedImage novaImagem =
            new BufferedImage(largura, novaAltura, Transparency.TRANSLUCENT);
        
        // copiar a imagem original pra nova imagem (em memória)
        Graphics2D g = (Graphics2D) novaImagem.getGraphics();
        g.drawImage(imagemOriginal, 0, 0, null);

        BufferedImage imgSobreposicao = ImageIO.read(inputStreamSobreposicao);
        int posicaoImgSobreposicaoY = novaAltura - imgSobreposicao.getHeight();
        g.drawImage(imgSobreposicao, 0, posicaoImgSobreposicaoY, null);

        // definir fonte
        var fonte = new Font("Impact", Font.PLAIN, 70);
        
        g.setColor(Color.YELLOW);
        g.setFont(fonte);
        
        // escrever uma frase na nova imagem
        String texto = textoSticker;

        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, g);

        int textoWidth = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - textoWidth) / 2;
        int posicaoTextoY = novaAltura - 100;

        g.drawString(texto, posicaoTextoX, posicaoTextoY);

        /// definindo o contorno para o texto
        FontRenderContext fontRenderContext = g.getFontRenderContext();
        var textoLayout = new TextLayout(texto, fonte, fontRenderContext);
        Shape outline = textoLayout.getOutline(null);
        AffineTransform transform = g.getTransform();

        transform.translate(posicaoTextoX, posicaoTextoY);
        g.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.004f);
        g.setStroke(outlineStroke);

        g.setColor(Color.BLACK);
        g.draw(outline);
        g.setClip(outline);

        // escrever a nova imagem em arquivo
        File path = new File("saida/stickers/" + nomeArquivo);
        boolean pathValido = path.mkdirs();

        if (pathValido) {
            ImageIO.write(novaImagem, "png", path);
        }
        
        ImageIO.write(novaImagem, "png", path);

        inputStream.close(); 
    }

    public void gerarFiguras(List<Conteudo> conteudos, ExtratorDeConteudo extrator) throws IOException {

        if (extrator instanceof ExtratorDeConteudoImdb) {
            for (Conteudo conteudo : conteudos) {
                String urlImagem = conteudo.getUrlImagem();
                String urlImagemHD = urlImagem.replaceFirst("(@?\\.)([0-9A-Z,_]+).jpg$", "$1.jpg");
    
                String titulo = conteudo.getTitulo();
                String textoCustom;
                InputStream imgDean;
    
                double nota = Double.parseDouble(conteudo.getNota());
    
                if (nota > 8) {
                    textoCustom = "FINO SENHORES";
                    imgDean = new FileInputStream(new File("sobreposicao/dean-contente.png"));
                } else if (nota >= 6.5 && nota <= 8) {
                    textoCustom = "BRABO";
                    imgDean = new FileInputStream(new File("sobreposicao/dean-contente.png"));
                } else {
                    textoCustom = "HMMMMM";
                    imgDean = new FileInputStream(new File("sobreposicao/dean-nao-contente.png"));
                }
    
                InputStream inputStream = new URL(urlImagemHD).openStream();
                String nomeArquivo = titulo.replace(": ", " – ") + ".png";
    
                this.criar(inputStream, nomeArquivo, textoCustom, imgDean);
    
                System.out.println("Gerando figurinha de " + titulo);
                System.out.println();
            }

        } else if (extrator instanceof ExtratorDeConteudoNasa) {
            String tituloTexto;

            for (int i = 0; i < conteudos.size(); i++) {
                InputStream inputStream = new URL(conteudos.get(i).getUrlImagem())
                    .openStream();

                String nomeArquivo = "saida/stickers/" + conteudos.get(i).getTitulo() + ".png";
                tituloTexto = conteudos.get(i).getTitulo();

                this.criar(inputStream, nomeArquivo, tituloTexto);

                System.out.println();
            }
        }
    }

    private void criar(InputStream inputStream, String nomeArquivo, String tituloTexto) throws IOException {
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, Transparency.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 64);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        graphics.drawString(tituloTexto, 100, novaAltura - 100);
        
        File path = new File("saida/stickers/" + nomeArquivo);
        boolean pathValido = path.mkdirs();

        if (pathValido) {
            ImageIO.write(novaImagem, "png", path);
        }

        ImageIO.write(novaImagem, "png", path);
    }
}
