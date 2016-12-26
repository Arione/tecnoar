/**
 * Esta classe define a interface e inicia o Aplicativo.
 * @author Arione
 */
package JFrame_TecnoAr;

import Uteis.Data;
import Log.arquivoLog;
import Threads.splash;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class init
{
    public static void main(String args[])
    {
        new init();
    }
    public init()
    {
        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Iniciando tela de Splash...";
        new arquivoLog(log);
        //setUImanager();
        initLookSystem();
        new splash().start();
    }
    /*Metodo para definir a interface*/
    private void initLookSystem()
    {
        try
        {
            UIManager.setLookAndFeel(new NimRODLookAndFeel());
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException ex1)
            {
                Logger.getLogger(init.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (InstantiationException ex1)
            {
                Logger.getLogger(init.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IllegalAccessException ex1)
            {
                Logger.getLogger(init.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (UnsupportedLookAndFeelException ex1)
            {
                Logger.getLogger(init.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
