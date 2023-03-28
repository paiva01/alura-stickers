import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradorDeFiguras {
    
    public void criar(InputStream inputStream, String nomeArquivo) throws IOException {
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
        
        // definir fonte
        var fonte = new Font(Font.SERIF, Font.CENTER_BASELINE, 70);
        g.setColor(Color.YELLOW);
        g.setFont(fonte);
        
        // escrever uma frase na nova imagem
        g.drawString("FINO SENHORES", 100, novaAltura - 100);
        
        // escrever a nova imagem em arquivo
        File path = new File("saida/stickers/" + nomeArquivo);
        boolean pathValido = path.mkdirs();

        if (pathValido) {
            ImageIO.write(novaImagem, "png", path);
        }
        
        inputStream.close(); 
    }
    
}
