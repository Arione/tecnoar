
/*
 * ----------------------------------------------- *
 * @author: Arione Guedes dos Santos Junior
 * Grad. Sistemas de Informação - U.F.M.S.
 * Email: arioneguedes@gmail.com
 * Website: arioneguedes.com.br
 * ----------------------------------------------- *
 */
package Relatorios_MODELOS;

import Conexao.Conexao;
import Log.arquivoLog;
import Uteis.Data;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class jasperLivroAdjunto
{

    /**
     * @param Metodo construtor
     */
    public jasperLivroAdjunto(String indice,Conexao conexao)
    {
        try
        {
            HashMap parametros = new HashMap();
            /* Adicionando o "param" como parâmetro*/
            parametros.put("PROTOCOLO",indice);
            new File("LivroAdjunto_PDF").mkdir();
            JasperPrint jasperPrint = JasperFillManager.fillReport("livroadjunto.jasper", parametros, conexao.getConection() );
            JasperFillManager.fillReportToFile( "livroadjunto.jasper", parametros, conexao.getConection() );
            JasperExportManager.exportReportToPdfFile( "livroadjunto.jrprint","LivroAdjunto_PDF/LivroAdjunto_"+indice+".pdf" );
            Desktop.getDesktop().open(new File("LivroAdjunto_PDF/LivroAdjunto_"+indice+".pdf"));
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Relatório "+indice+" criado com sucesso!";
            new arquivoLog(log);
        }
        catch(Exception er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
        }
    }
}
/**
* @param Eu prefiro agradar uma pessoa inteligente, do que um bando de idiotas!
*/
