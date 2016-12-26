package Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
/**
 *
 * @author Guedes_note
 */
public class arquivoLog
{
    public arquivoLog(String erros)
    {
        EscreverLog(erros);
    }
    private void EscreverLog(String erros)
    {

        try
        {
            Vector texto = new Vector();
            arquivo = new File("Exceptions.log");
            fileReader = new FileReader(arquivo);
            bufferedReader = new BufferedReader(fileReader);

            while(bufferedReader.ready())
               texto.add(bufferedReader.readLine());

            fileWriter = new FileWriter(arquivo);
            bufferedWriter = new BufferedWriter(fileWriter);
            for(int i=0;i<texto.size();i++)
            {
                bufferedWriter.write(texto.get(i).toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.write(erros);
            bufferedReader.close();
            bufferedWriter.close();
        } catch (FileNotFoundException ex)
        {
            try {
                    arquivo.createNewFile();
                    EscreverLog(erros);
                } catch (IOException ex1) {System.exit(0);}
        } catch(IOException err){System.exit(0);}

    }
    private File arquivo;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
}
