/*
 * ----------------------------------------------- *
 * @author: Arione Guedes dos Santos Junior
 * Grad. Sistemas de Informação - U.F.M.S.
 * Email: arioneguedes@gmail.com
 * Website: arioneguedes.com.br
 * ----------------------------------------------- *
 */
/* @author Arione Guedes dos Santos Junior */

package Relatorios_MODELOS;

import Conexao.Conexao;
import Log.arquivoLog;
import Uteis.Data;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;

public class Relatorio_Total_Detalhado
{
    private Conexao conexao;
    private Table tabela;
    public Relatorio_Total_Detalhado()
    {

    }
    private void criarPDF()
    {
        try
        {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            try
            {
                PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("Material_Permanente\\Relatório_Detalhado\\Relatório_Total_"+new Data().getDataSalv()+".pdf"));
                document.open();
            }
            catch (FileNotFoundException ex)
            {
                String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  FileNotFoundException: "+ex.getMessage();
                new arquivoLog(log);
            }
            Paragraph Titulo = new Paragraph("Materiais Permanentes",FontFactory.getFont(FontFactory.TIMES_ROMAN, 22, Font.BOLD, new Color(0, 0, 0)));
            Titulo.setAlignment(1);
            tabela = new Table(5,2);
            tabela.setBorderColor(new Color(0, 0, 0));
            tabela.setPadding(3);
            tabela.setSpacing(0);
            tabela.setBorderWidth(1);
            tabela.setWidth((float)110);
            tabela.setAutoFillEmptyCells(true);
            tabela.setAlignment(1);
            tabela.setCellsFitPage(true);
            tabela.setTableFitsPage(true);
            /*tamanho das celulas*/
            float fl [] = new float[5];
            fl[0] = (float) 25;
            fl[1] = (float) 60;//id
            fl[2] = (float) 150;//nome
            fl[3] = (float) 150;//quantidade
            fl[4] = (float) 25;//detalhes
            tabela.setWidths(fl);
            /*--------------------*/
            Cell c0 = new Cell("ID");
            tabela.addCell(c0);
            c0.setHorizontalAlignment(1);
            Cell c1 = new Cell("CP");
            c1.setHorizontalAlignment(1);
            tabela.addCell(c1);
            Cell c2 = new Cell("Nome");
            c2.setHorizontalAlignment(1);
            tabela.addCell(c2);
            Cell c3 = new Cell("Detalhes");
            c3.setHorizontalAlignment(1);
            tabela.addCell(c3);
            Cell c4 = new Cell("Qtd");
            c4.setHorizontalAlignment(1);
            tabela.addCell(c4);
            tabela.endHeaders();
            //adicionando os nomes e numeros dos cds
            conexao.executeSQL("SELECT * FROM bombeiro_19.material ORDER BY nome ASC");
            try
            {
                conexao.resultset.first();
                do
                {
                    Cell ide = new Cell(conexao.resultset.getObject("id_material").toString());
                    ide.setHorizontalAlignment(1);
                    Cell idproduto = new Cell(conexao.resultset.getObject("cp_codigo").toString());
                    idproduto.setHorizontalAlignment(2);
                    Cell nome = new Cell (conexao.resultset.getObject("nome").toString());
                    Cell qtd = new Cell(conexao.resultset.getObject("quantidade").toString());
                    qtd.setHorizontalAlignment(1);
                    Cell det = new Cell(conexao.resultset.getObject("descricao").toString());
                    tabela.addCell(ide);
                    tabela.addCell(idproduto);
                    tabela.addCell(nome);
                    tabela.addCell(det);
                    tabela.addCell(qtd);
                }while(conexao.resultset.next());
            }
            catch(SQLException erro)
            {
                String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...SQLException: "+erro.getMessage();
                new arquivoLog(log);
            }
            Paragraph paragrafo = new Paragraph("Lista Total de Equipamentos (ordem Alfabética)");
            paragrafo.setAlignment(1);
            document.add(Titulo);
            document.add(new Paragraph("\n"));
            document.add(paragrafo);
            document.add(tabela);
            document.close();
            File pdf = new File("Material_Permanente\\Relatório_Detalhado\\Relatório_Total_"+new Data().getDataSalv()+".pdf");
            Desktop.getDesktop().open(pdf);//visualizar arquivo .pdf
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"| ...arquivo Relatório_Total_"+new Data().getDataSalv()+".pdf criado com Sucesso!";
            new arquivoLog(log);
        }
        catch (DocumentException ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...DocumentException: "+ex.getMessage();
            new arquivoLog(log);
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...Exception: "+ex.getMessage();
            new arquivoLog(log);
        }
    }
   
    public void criarPDF(Conexao con)
    {
        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Criando Relatórios...";
        new arquivoLog(log);
        new File("Material_Permanente").mkdir();
        new File("Material_Permanente\\Relatório_Detalhado").mkdir();
        this.conexao = con;
        criarPDF();
    }
}
