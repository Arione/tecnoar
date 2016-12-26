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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Vector;

public class Relatorio_Geral
{
    Conexao conexao;;
    public Relatorio_Geral()
    {

    }
    /**
     *
     * @param sql A sql que será feita a consulta no Banco de dados
     * @param local O local da consulta e nome do arquivo .pdf
     */
    private void criarPDF(String sql,String local)
    {
        try
        {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            try
            {
                PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("Material_Permanente\\Relatório_Geral\\"+local+"_"+new Data().getDataSalv()+".pdf"));
                document.open();
            }
            catch (FileNotFoundException ex)
            {
                String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  FileNotFoundException: "+ex.getMessage();
                new arquivoLog(log);
            }
            Paragraph Titulo = new Paragraph("Materiais Permanentes",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 22, Font.BOLD, new Color(0, 0, 0)));
            Titulo.setAlignment(1);
            String pagrfo="";
            Table tabela = new Table(4,2);
            tabela.setBorderColor(new Color(0, 0, 0));
            tabela.setPadding(3);
            tabela.setSpacing(0);
            tabela.setBorderWidth(1);
            tabela.setWidth((float)110);
            tabela.setAutoFillEmptyCells(true);
            tabela.setAlignment(1);
            tabela.setCellsFitPage(true);
            /*tamanho das celulas*/
            float fl [] = new float[4];
            fl[0] = (float) 50;//id
            fl[1] = (float) 100;//nome
            fl[2] = (float) 200;//quantidade
            fl[3] = (float) 30;//detalhes
            tabela.setWidths(fl);
            /*--------------------*/
            Cell c1 = new Cell("CP.:");
            c1.setHorizontalAlignment(1);
            tabela.addCell(c1);
            Cell c2 = new Cell("Nome.:");
            c2.setHorizontalAlignment(1);
            tabela.addCell(c2);
            Cell c3 = new Cell("Detalhes");
            c3.setHorizontalAlignment(1);
            tabela.addCell(c3);
            Cell c4 = new Cell("Qtd.:");
            c4.setHorizontalAlignment(1);
            tabela.addCell(c4);

            tabela.endHeaders();
            //adicionando os nomes e numeros dos cds
            conexao.executeSQL(sql);
            try
            {
                conexao.resultset.first();
                pagrfo = conexao.resultset.getString("destino");
                do
                {
                    Cell idproduto = new Cell(conexao.resultset.getObject("cp_codigo").toString());
                    idproduto.setHorizontalAlignment(1);
                    Cell nome = new Cell (conexao.resultset.getObject("nome").toString());
                    Cell descricao = new Cell (conexao.resultset.getObject("descricao").toString());
                    Cell qtd = new Cell(conexao.resultset.getObject("quantidade").toString());
                    qtd.setHorizontalAlignment(1);
                    tabela.addCell(idproduto);
                    tabela.addCell(nome);
                    tabela.addCell(descricao);
                    tabela.addCell(qtd);
                }while(conexao.resultset.next());
            }
            catch(SQLException erro)
            {
                String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...SQLException: "+erro.getMessage();
                new arquivoLog(log);
            }
            Paragraph paragrafo = new Paragraph(pagrfo);
            paragrafo.setAlignment(1);
            document.add(Titulo);
            document.add(new Paragraph("\n"));
            document.add(paragrafo);
            document.add(tabela);
            document.add(new Paragraph("\n"+new Data().getData()));
            document.close();
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"| ...arquivo "+local+"_"+new Data().getDataSalv()+".pdf criado com Sucesso!";
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
    public boolean criarPDF(Conexao con)
    {
        new File("Material_Permanente").mkdir();
        new File("Material_Permanente\\Relatório_Geral").mkdir();
        this.conexao = con;
        Vector lista_sql = new Vector();
        Vector lista_local = new Vector();
        conexao.executeSQL("SELECT * FROM bombeiro_19.material_destino ORDER BY nome ASC");
        try
        {
            conexao.resultset.first();
            if(conexao.resultset.getRow()==0)//verifica se o bd esta vazio
            {
                return false;
            }
            else
            {
                do
                {
                    lista_local.add(conexao.resultset.getObject("nome"));
                    lista_sql.add("SELECT * FROM bombeiro_19.material WHERE destino LIKE '"+conexao.resultset.getString("nome")+"'");
                }while(conexao.resultset.next());
            }
            for(int i=0;i<lista_local.size();i++)
            {
                criarPDF(lista_sql.get(i).toString(), lista_local.get(i).toString());
            }
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  SQLException: "+er.getMessage();
            new arquivoLog(log);
            System.out.println(log);
            return false;
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
            new arquivoLog(log);
            return false;
        }
        return true;
    }
}
