package Uteis;

import Log.arquivoLog;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Guedes
 */
public class redimensionarImagens
{
    public BufferedImage image,new_img;
    /**
     * Criar cópia e Redimensionar a Imagem.
     * @param imageURL Informar o local exata onde esta localizado o arquivo
     * @param local Informar o local para armazenamento ("Imagens/Red/")
     * @param new_w Informar nova Width (largura)
     * @param new_h Informar novo Height (Altura)
     * @return Este método irá retornar um valor false, se houve erros, true se não houver erros durante as instruções.
     */
    public boolean redimensionar(String imageURL,String local,int new_w,int new_h)
    {
        try
        {
            File f1 = new File(imageURL);
            File f2 = new File(local+f1.getName());
            copiarArquivos.copy(f1, f2, false);
            image = ImageIO.read(f2);
            new_img = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = new_img.createGraphics();
            g.drawImage(image, 0, 0, new_w, new_h, null);
            File fl = new File(f2.getAbsolutePath());
            ImageIO.write(new_img, "JPG", fl);
            return true;
        }
        catch(FileNotFoundException fnfe)
        {
            new File(local).mkdir();
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"...FileNotFoundException.: "+fnfe.getMessage();
            new arquivoLog(log);
            File arquivo = new File("Exceptions.log");
            try
            {
                Desktop.getDesktop().open(arquivo); //visualizar arquivo .pdf
            } catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }
            return false;
            //redimensionar(imageURL, new_w, new_h);
        }
        catch(Exception err)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"...Exeption.: "+err.getMessage();
            new arquivoLog(log);
            return false;
        }
    }
    public static void redimenImagem(String caminhoImg, String novoCaminho, Integer imgAltura, Integer imgLargura)
    {
        try
        {
            File f1 = new File(caminhoImg);
            File f2 = new File(novoCaminho+f1.getName());
            copiarArquivos.copy(f1, f2, true);
            Double novaImgLargura = null;
            Double novaImgAltura = null;
            Double imgProporcao = null;
            Graphics2D g2d = null;
            BufferedImage imagem = null, novaImagem = null;

            //--- Obtém a imagem a ser redimensionada ---
            imagem = ImageIO.read(f2);

            //--- Obtém a largura da imagem ---
            novaImgLargura = (double) imagem.getWidth();

            //--- Obtám a altura da imagem ---
            novaImgAltura = (double) imagem.getHeight();

            //--- Verifica se a altura ou largura da imagem recebida é maior do que os ---
            //--- parâmetros de altura e largura recebidos para o redimensionamento   ---
            if (novaImgLargura >= imgLargura)
            {
                imgProporcao = (novaImgAltura / novaImgLargura);//calcula a proporção
                novaImgLargura = (double) imgLargura;

                //--- altura deve <= ao parâmetro imgAltura e proporcional a largura ---
                novaImgAltura = (novaImgLargura * imgProporcao);

                //--- se altura for maior do que o parâmetro imgAltura, diminui-se a largura de ---
                //--- forma que a altura seja igual ao parâmetro imgAltura e proporcional a largura ---
                while (novaImgAltura > imgAltura)
                {
                    novaImgLargura = (double) (--imgLargura);
                    novaImgAltura = (novaImgLargura * imgProporcao);
                }
            }
            else if (novaImgAltura >= imgAltura)
            {
                imgProporcao = (novaImgLargura / novaImgAltura);//calcula a proporção
                novaImgAltura = (double) imgAltura;

                //--- se largura for maior do que o parâmetro imgLargura, diminui-se a altura de ---
                //--- forma que a largura seja igual ao parâmetro imglargura e proporcional a altura ---
                while (novaImgLargura > imgLargura)
                {
                    novaImgAltura = (double) (--imgAltura);
                    novaImgLargura = (novaImgAltura * imgProporcao);
                }
            }
//            System.out.println(novaImgAltura+"-"+novaImgLargura);
            novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
            g2d = novaImagem.createGraphics();
            g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(), novaImgAltura.intValue(), null);
            ImageIO.write(novaImagem, "JPG", f2);

//            return;
        }
        catch(IOException ioe)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"...IOException: "+ioe.getMessage();
            new arquivoLog(log);
        }
    }
}
