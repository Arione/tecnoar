package Uteis;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Unzip
{

    // specify buffer size for extraction
    static final int BUFFER = 2048;
    /**
     * <h4>Arione Guedes dos Santos Junior</h4>
     * <strong>Metodo para extrair um arquivo compactado, sem compressao</strong>
     * @param fileExtrair Nome ou endereço do arquivo para extrair
     * @param diretorioExtrair Nome ou endereço do diretório para extrair
     */
    public static void ExtrairBD(String fileExtrair,String diretorioExtrair)
    {
        try
        {
            // Specify file to decompress
//            String inFileName = "lib\\Nao_Apagar.zip";
            String inFileName = fileExtrair;
            // Specify destination where file will be unzipped
//            String destinationDirectory = "BD_Backup\\";
            String destinationDirectory = diretorioExtrair;

            File sourceZipFile = new File(inFileName);
            File unzipDestinationDirectory = new File(destinationDirectory);

            // Open Zip file for reading
            ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);

            // Create an enumeration of the entries in the zip file
            Enumeration zipFileEntries = zipFile.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements())
            {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

                String currentEntry = entry.getName();
                System.out.println("Extracting: " + entry);

                File destFile =
                        new File(unzipDestinationDirectory, currentEntry);

                // grab file's parent directory structure
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

                // extract file if not a directory
                if (!entry.isDirectory())
                {
                    BufferedInputStream is =
                            new BufferedInputStream(zipFile.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest =
                            new BufferedOutputStream(fos, BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1)
                    {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
//                    copiarArquivos.copyAll(new File("BD_Backup"), new File("BD"), true);
                }
            }
            zipFile.close();
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
