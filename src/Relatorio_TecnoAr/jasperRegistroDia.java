
/*
 * ----------------------------------------------- *
 * @author: Arione Guedes dos Santos Junior
 * Grad. Sistemas de Informação - U.F.M.S.
 * Email: arioneguedes@gmail.com
 * Website: arioneguedes.com.br
 * ----------------------------------------------- *
 */
package Relatorio_TecnoAr;

import Relatorios_MODELOS.*;
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

public class jasperRegistroDia
{

    /**
     * @param Metodo construtor
     */
    public jasperRegistroDia(String indice,String data,Conexao conexao)
    {
        try
        {
            HashMap parametros = new HashMap();
            parametros.put("id_cliente",indice);
            parametros.put("data_servico",data);
            System.out.println(indice+" - "+data);
            new File("servico").mkdir();
            JasperPrint jasperPrint = JasperFillManager.fillReport("servico.jasper", parametros, conexao.getConection() );
            JasperFillManager.fillReportToFile( "servico.jasper", parametros, conexao.getConection() );
            JasperExportManager.exportReportToPdfFile( "servico.jrprint","servico/servico_"+data+".pdf" );
            Desktop.getDesktop().open(new File("servico/servico_"+data+".pdf"));
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Relatório "+indice+" criado com sucesso!";
            new arquivoLog(log);
        }
        catch(Exception er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: jasper "+er.getMessage();
            new arquivoLog(log);
        }
    }
}
/**
* @param Eu prefiro agradar uma pessoa inteligente, do que um bando de idiotas!
*/
