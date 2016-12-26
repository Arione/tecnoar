package Relatorios_MODELOS;

import Conexao.Conexao;
import Log.arquivoLog;
import Uteis.Data;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guedes
 */
public  class RelatorioPDF
{
    Conexao conexao;
    Document document;
    public RelatorioPDF(Conexao con)
    {
        this.conexao = con;
        document = new Document(new Rectangle(595, 842),60,20,30,20);
        try
        {
            new File("Relatorio_PDF").mkdir();
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("Relatorio_PDF/Relatorio.pdf"));
            document.open();
        }
        catch (Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (fudeu aki) Exception: "+ex.getMessage();
            new arquivoLog(log);
        } 
    }
     /**
     * <h3>Este metodo serve para criar a primeira pagina do livro</h3>
     * @return <strong>true</strong> se nao ouver erros, <strong>false</strong> caso haja algum erro na criacao do arquivo
     */
    private boolean primeiraPagina()
    {
        try
        {
            Paragraph Titulo = new Paragraph("ESTADO DE MATO GROSSO DO SUL\n"
                    + "SECRETARIA DE JUSTIÇA E DE SEGURANÇA PÚBLICA\n"
                    + "CORPO DE BOMBEIROS MILITAR\n"
                    + "DIRETORIA DE SERVIÇOS TÉCNICOS\n"
                    + "RELATÓRIOS",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0)));
            Titulo.setAlignment(1);
            document.add(Titulo);
//            Image img = Image.getInstance(this.getClass().getResource("../Imagens/Brasao_relatorio.png"));
            Image img = Image.getInstance("Brasao_relatorio.png");
            img.setAlignment(Element.ALIGN_CENTER);
            Paragraph Titulo2 = new Paragraph("\n");
            Titulo2.setAlignment(1);
            document.add(Titulo2);
            document.add(img);
            Paragraph Tit3 = new Paragraph("\n\nDesenvolvido por:\nArione Guedes dos Santos Junior\nCB COV BM - Mat. 221.362-11",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
            Tit3.setAlignment(1);
            document.add(Tit3);
            document.newPage();
                        
            return true;
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (Relatorio_Livro linha xx) Exception: "+ex.getMessage();
            new arquivoLog(log);
            Paragraph Titdata = new Paragraph("\nPESQUISA SEM RESULTADO",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
            Titdata.setAlignment(1);
            try
            {
                document.add(Titdata);
            }
            catch (DocumentException ex1)
            {
                Logger.getLogger(RelatorioPDF.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }
    
    private boolean certificado(String dataInicio,String dataFinal)        
    {
        try
        {
            //Pesquisa no banco de dados
            //conexao.executeSQL("SELECT * from quartel_sst.certificado WHERE cert_data BETWEEN '"+Data.DateToString(dataInicial)+"' AND '"+Data.DateToString(dataFinal)+"'");
            conexao.executeSQL("SELECT * from quartel_sst.certificado WHERE cert_data_emissao BETWEEN '"+dataInicio+"' "
                    + "AND "
                    + "'"+dataFinal+"' ORDER BY cert_data_emissao ASC");
            conexao.resultset.first();
                Table tabela = new Table(2,1);
                tabela.setBorderColor(Color.BLACK);
                tabela.setPadding(1);
                tabela.setSpacing(1);
                tabela.setBorderWidth(1);
                tabela.setAlignment(1);
                /*tamanho das celulas*/
                float fl [] = new float[2];
                fl[0] = (float) 150;//id
                fl[1] = (float) 60;//nome
                tabela.setWidths(fl);
                
                Paragraph titProp = new Paragraph("PROPRIETÁRIO: ",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cell1 = new Cell(titProp);
                cell1.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell1.setBorder(0);
                tabela.addCell(cell1);
                /********************************************************************************/
                Paragraph observacoes = new Paragraph("TAXA",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cell12 = new Cell(observacoes);
                cell12.setBorder(0);
                tabela.addCell(cell12);
                /********************************************************************************/
                
            double total = 0;
            int qtd = 0;
            do
            {
                total+=conexao.resultset.getDouble("cert_taxa");
                qtd ++;
                Paragraph titCel = new Paragraph("|"+conexao.resultset.getString("cert_data_emissao")+"| - "+conexao.resultset.getString("cert_proprietario"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cellTit = new Cell(titCel);
                cellTit.setHorizontalAlignment(Cell.ALIGN_LEFT);
                tabela.addCell(cellTit);
                /********************************************************************************/
                Paragraph titCel2 = new Paragraph(conexao.resultset.getString("cert_taxa"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cellTit2 = new Cell(titCel2);
                cellTit2.setHorizontalAlignment(Cell.ALIGN_CENTER);
                tabela.addCell(cellTit2);
                /********************************************************************************/
            }
            while(conexao.resultset.next());
            DecimalFormat def = new DecimalFormat("#########.##");
                //titulo da pagina
                Paragraph Titulo2 = new Paragraph("\nQuantidade de CERTIFICADO e Valores arrecadados",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
                Titulo2.setAlignment(1);
                document.add(Titulo2);
                
                //data escolhida
                Paragraph Titdata = new Paragraph("\nPesquisa feitas entre "+Data.alteraData(dataInicio)+" e "+Data.alteraData(dataFinal),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
                Titdata.setAlignment(1);
                document.add(Titdata);
                
                Paragraph Tit3 = new Paragraph("\nCertificados expedidos: "+qtd+"\nValor total arrecadado: "+def.format(total)+" R$",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0)));
                Tit3.setAlignment(0);
                /********************************************************************************/
                document.add(tabela);
                document.add(Tit3);
                document.newPage();
            
            return true;
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (Relatorio_Livro linha xx) Exception: "+ex.getMessage();
            new arquivoLog(log);
            Paragraph Tit3 = new Paragraph("\nQuantidade de CERTIFICADO e Valores arrecadados",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
            Tit3.setAlignment(0);
            /********************************************************************************/
               
            Paragraph Titdata = new Paragraph("\nPESQUISA SEM RESULTADO",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
            Titdata.setAlignment(1);
            try
            {
                document.add(Tit3);
                document.add(Titdata);
                document.newPage();
            }
            catch (DocumentException ex1)
            {
                Logger.getLogger(RelatorioPDF.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }
    private boolean requerimento(String dataInicio,String dataFinal)        
    {
        try
        {
            //Pesquisa no banco de dados
            //conexao.executeSQL("SELECT * from quartel_sst.certificado WHERE cert_data BETWEEN '"+Data.DateToString(dataInicial)+"' AND '"+Data.DateToString(dataFinal)+"'");
            conexao.executeSQL("SELECT * from quartel_sst.requerimento WHERE req_data BETWEEN '"+dataInicio+"' "
                    + "AND "
                    + "'"+dataFinal+"' ORDER BY req_data ASC");
            conexao.resultset.first();
                Table tabela = new Table(2,1);
                tabela.setBorderColor(Color.BLACK);
                tabela.setPadding(1);
                tabela.setSpacing(1);
                tabela.setBorderWidth(1);
                tabela.setAlignment(1);
                /*tamanho das celulas*/
                float fl [] = new float[2];
                fl[0] = (float) 150;//id
                fl[1] = (float) 60;//nome
                tabela.setWidths(fl);
                
                Paragraph titProp = new Paragraph("PROPRIETÁRIO: ",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cell1 = new Cell(titProp);
                cell1.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell1.setBorder(0);
                tabela.addCell(cell1);
                /********************************************************************************/
                Paragraph observacoes = new Paragraph("TAXA",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cell12 = new Cell(observacoes);
                cell12.setBorder(0);
                tabela.addCell(cell12);
                /********************************************************************************/
                
            double total = 0;
            int qtd = 0;
            do
            {
                total+=conexao.resultset.getDouble("req_taxa");
                qtd ++;
                Paragraph titCel = new Paragraph("|"+conexao.resultset.getString("req_data")+"| - "+
                        conexao.resultset.getString("req_proprietario"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cellTit = new Cell(titCel);
                cellTit.setHorizontalAlignment(Cell.ALIGN_LEFT);
                tabela.addCell(cellTit);
                /********************************************************************************/
                Paragraph titCel2 = new Paragraph(conexao.resultset.getString("req_taxa"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cellTit2 = new Cell(titCel2);
                cellTit2.setHorizontalAlignment(Cell.ALIGN_CENTER);
                tabela.addCell(cellTit2);
                /********************************************************************************/
            }
            while(conexao.resultset.next());
            DecimalFormat def = new DecimalFormat("#########.##");
                //titulo da pagina
                Paragraph Titulo2 = new Paragraph("\nQuantidade de REQUERIMENTO e Valores arrecadados",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
                Titulo2.setAlignment(1);
                document.add(Titulo2);
                
                //data escolhida
                Paragraph Titdata = new Paragraph("\nPesquisa feitas entre "+Data.alteraData(dataInicio)+" e "+Data.alteraData(dataFinal),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
                Titdata.setAlignment(1);
                document.add(Titdata);
                
                Paragraph Tit3 = new Paragraph("\nRequerimentos expedidos: "+qtd+"\nValor total arrecadado: "+def.format(total)+" R$",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0)));
                Tit3.setAlignment(0);
                /********************************************************************************/
                document.add(tabela);
                document.add(Tit3);
                document.newPage();
            
            return true;
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (Relatorio_Livro linha xx) Exception: "+ex.getMessage();
            new arquivoLog(log);
            Paragraph Tit3 = new Paragraph("\nQuantidade de REQUERIMENTO e Valores arrecadados",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
            Tit3.setAlignment(0);
            /********************************************************************************/
               
            Paragraph Titdata = new Paragraph("\nPESQUISA SEM RESULTADO",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
            Titdata.setAlignment(1);
            try
            {
                document.add(Tit3);
                document.add(Titdata);
                document.newPage();
            }
            catch (DocumentException ex1)
            {
                Logger.getLogger(RelatorioPDF.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }
    private boolean exigencia(String dataInicio,String dataFinal)        
    {
        try
        {
            //Pesquisa no banco de dados
            //conexao.executeSQL("SELECT * from quartel_sst.certificado WHERE cert_data BETWEEN '"+Data.DateToString(dataInicial)+"' AND '"+Data.DateToString(dataFinal)+"'");
            conexao.executeSQL("SELECT * from quartel_sst.notificacao WHERE data BETWEEN '"+dataInicio+"' "
                    + "AND "
                    + "'"+dataFinal+"' ORDER BY data ASC");
            conexao.resultset.first();
                Table tabela = new Table(1,1);
                tabela.setBorderColor(Color.BLACK);
                tabela.setPadding(1);
                tabela.setSpacing(1);
                tabela.setBorderWidth(1);
                tabela.setAlignment(1);
                /*tamanho das celulas*/
                float fl [] = new float[1];
                fl[0] = (float) 350;//id
                tabela.setWidths(fl);
                
                Paragraph titProp = new Paragraph("PROPRIETÁRIO: ",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cell1 = new Cell(titProp);
                cell1.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell1.setBorder(0);
                tabela.addCell(cell1);
                
            int qtd = 0;
            do
            {
                qtd ++;
                Paragraph titCel = new Paragraph("|"+conexao.resultset.getString("data")+"| - "+conexao.resultset.getString("proprietario"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cellTit = new Cell(titCel);
                cellTit.setHorizontalAlignment(Cell.ALIGN_LEFT);
                tabela.addCell(cellTit);

            }
            while(conexao.resultset.next());
            DecimalFormat def = new DecimalFormat("#########.##");
                //titulo da pagina
                Paragraph Titulo2 = new Paragraph("\nQuantidade de EXIGÊNCIAS DE VISTORIA realizados",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
                Titulo2.setAlignment(1);
                document.add(Titulo2);
                
                //data escolhida
                Paragraph Titdata = new Paragraph("\nPesquisa feitas entre "+Data.alteraData(dataInicio)+" e "+Data.alteraData(dataFinal),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
                Titdata.setAlignment(1);
                document.add(Titdata);
                
                Paragraph Tit3 = new Paragraph("\nExigências de vistoria expedidos: "+qtd,
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0)));
                Tit3.setAlignment(0);
                /********************************************************************************/
                document.add(tabela);
                document.add(Tit3);
                document.newPage();
            
            return true;
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (Relatorio_Livro linha xx) Exception: "+ex.getMessage();
            new arquivoLog(log);
            Paragraph Tit3 = new Paragraph("\nQuantidade de EXIGÊNCIAS DE VISTORIA realizados",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
            Tit3.setAlignment(0);
            /********************************************************************************/
               
            Paragraph Titdata = new Paragraph("\nPESQUISA SEM RESULTADO",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
            Titdata.setAlignment(1);
            try
            {
                document.add(Tit3);
                document.add(Titdata);
                document.newPage();
            }
            catch (DocumentException ex1)
            {
                Logger.getLogger(RelatorioPDF.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }
    private boolean ppcip_analise(String dataInicio,String dataFinal)        
    {
        try
        {
            //Pesquisa no banco de dados
            //conexao.executeSQL("SELECT * from quartel_sst.certificado WHERE cert_data BETWEEN '"+Data.DateToString(dataInicial)+"' AND '"+Data.DateToString(dataFinal)+"'");
            conexao.executeSQL("SELECT * from quartel_sst.ppcip WHERE ppcip_data_recebimento BETWEEN '"+dataInicio+"' "
                    + "AND "
                    + "'"+dataFinal+"' AND ppcip_situacao LIKE 'Análise' ORDER BY ppcip_data_recebimento ASC");
            conexao.resultset.first();
                Table tabela = new Table(2,1);
                tabela.setBorderColor(Color.BLACK);
                tabela.setPadding(1);
                tabela.setSpacing(1);
                tabela.setBorderWidth(1);
                tabela.setAlignment(1);
                /*tamanho das celulas*/
                float fl [] = new float[2];
                fl[0] = (float) 150;//id
                fl[1] = (float) 60;//nome
                tabela.setWidths(fl);
                
                Paragraph titProp = new Paragraph("PROPRIETÁRIO: ",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cell1 = new Cell(titProp);
                cell1.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell1.setBorder(0);
                tabela.addCell(cell1);
                /********************************************************************************/
                Paragraph observacoes = new Paragraph("TAXA",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cell12 = new Cell(observacoes);
                cell12.setBorder(0);
                tabela.addCell(cell12);
                /********************************************************************************/
                
            double total = 0;
            int qtd = 0;
            do
            {
                total+=conexao.resultset.getDouble("ppcip_taxa");
                qtd ++;
                Paragraph titCel = new Paragraph("|"+conexao.resultset.getString("ppcip_data_recebimento")+"| - "+
                        conexao.resultset.getString("ppcip_proprietario"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cellTit = new Cell(titCel);
                cellTit.setHorizontalAlignment(Cell.ALIGN_LEFT);
                tabela.addCell(cellTit);
                /********************************************************************************/
                Paragraph titCel2 = new Paragraph(conexao.resultset.getString("ppcip_taxa"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cellTit2 = new Cell(titCel2);
                cellTit2.setHorizontalAlignment(Cell.ALIGN_CENTER);
                tabela.addCell(cellTit2);
                /********************************************************************************/
            }
            while(conexao.resultset.next());
            DecimalFormat def = new DecimalFormat("#########.##");
                //titulo da pagina
                Paragraph Titulo2 = new Paragraph("\nQuantidade de PPCIP para ANÁLISE e Valores arrecadados",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
                Titulo2.setAlignment(1);
                document.add(Titulo2);
                
                //data escolhida
                Paragraph Titdata = new Paragraph("\nPesquisa feitas entre "+Data.alteraData(dataInicio)+" e "+Data.alteraData(dataFinal),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
                Titdata.setAlignment(1);
                document.add(Titdata);
                
                Paragraph Tit3 = new Paragraph("\nProjetos recebidos: "+qtd+"\nValor total arrecadado: "+def.format(total)+" R$",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0)));
                Tit3.setAlignment(0);
                /********************************************************************************/
                document.add(tabela);
                document.add(Tit3);
                document.newPage();
            
            return true;
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (Relatorio_Livro linha xx) Exception: "+ex.getMessage();
            new arquivoLog(log);
            Paragraph Tit3 = new Paragraph("\nQuantidade de PPCIP para ANÁLISE e Valores arrecadados :",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
            Tit3.setAlignment(0);
            /********************************************************************************/
               
            Paragraph Titdata = new Paragraph("\nPESQUISA SEM RESULTADO",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
            Titdata.setAlignment(1);
            try
            {
                document.add(Tit3);
                document.add(Titdata);
                document.newPage();
            }
            catch (DocumentException ex1)
            {
                Logger.getLogger(RelatorioPDF.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }
    private boolean ppcip_reanalise(String dataInicio,String dataFinal)        
    {
        try
        {
            //Pesquisa no banco de dados
            //conexao.executeSQL("SELECT * from quartel_sst.certificado WHERE cert_data BETWEEN '"+Data.DateToString(dataInicial)+"' AND '"+Data.DateToString(dataFinal)+"'");
            conexao.executeSQL("SELECT * from quartel_sst.ppcip WHERE ppcip_data_recebimento BETWEEN '"+dataInicio+"' "
                    + "AND "
                    + "'"+dataFinal+"' AND ppcip_situacao LIKE 'Re análise' ORDER BY ppcip_data_recebimento ASC");
            conexao.resultset.first();
                Table tabela = new Table(2,1);
                tabela.setBorderColor(Color.BLACK);
                tabela.setPadding(1);
                tabela.setSpacing(1);
                tabela.setBorderWidth(1);
                tabela.setAlignment(1);
                /*tamanho das celulas*/
                float fl [] = new float[2];
                fl[0] = (float) 150;//id
                fl[1] = (float) 60;//nome
                tabela.setWidths(fl);
                
                Paragraph titProp = new Paragraph("PROPRIETÁRIO: ",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cell1 = new Cell(titProp);
                cell1.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell1.setBorder(0);
                tabela.addCell(cell1);
                /********************************************************************************/
                Paragraph observacoes = new Paragraph("TAXA",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cell12 = new Cell(observacoes);
                cell12.setBorder(0);
                tabela.addCell(cell12);
                /********************************************************************************/
                
            double total = 0;
            int qtd = 0;
            do
            {
                total+=conexao.resultset.getDouble("ppcip_taxa");
                qtd ++;
                Paragraph titCel = new Paragraph("|"+conexao.resultset.getString("ppcip_data_recebimento")+"| - "+
                conexao.resultset.getString("ppcip_proprietario"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cellTit = new Cell(titCel);
                cellTit.setHorizontalAlignment(Cell.ALIGN_LEFT);
                tabela.addCell(cellTit);
                /********************************************************************************/
                Paragraph titCel2 = new Paragraph(conexao.resultset.getString("ppcip_taxa"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cellTit2 = new Cell(titCel2);
                cellTit2.setHorizontalAlignment(Cell.ALIGN_CENTER);
                tabela.addCell(cellTit2);
                /********************************************************************************/
            }
            while(conexao.resultset.next());
            DecimalFormat def = new DecimalFormat("#########.##");
                //titulo da pagina
                Paragraph Titulo2 = new Paragraph("\nQuantidade de PPCIP para RE ANÁLISE e Valores arrecadados",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
                Titulo2.setAlignment(1);
                document.add(Titulo2);
                
                //data escolhida
                Paragraph Titdata = new Paragraph("\nPesquisa feitas entre "+Data.alteraData(dataInicio)+" e "+Data.alteraData(dataFinal),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
                Titdata.setAlignment(1);
                document.add(Titdata);
                
                Paragraph Tit3 = new Paragraph("\nProjetos recebidos: "+qtd+"\nValor total arrecadado: "+def.format(total)+" R$",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0)));
                Tit3.setAlignment(0);
                /********************************************************************************/
                document.add(tabela);
                document.add(Tit3);
                document.newPage();
            
            return true;
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (Relatorio_Livro linha xx) Exception: "+ex.getMessage();
            new arquivoLog(log);
            Paragraph Tit3 = new Paragraph("\nQuantidade de PPCIP para RE ANÁLISE e Valores arrecadados",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
            Tit3.setAlignment(0);
            /********************************************************************************/
               
            Paragraph Titdata = new Paragraph("\nPESQUISA SEM RESULTADO",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
            Titdata.setAlignment(1);
            try
            {
                document.add(Tit3);
                document.add(Titdata);
                document.newPage();
            }
            catch (DocumentException ex1)
            {
                Logger.getLogger(RelatorioPDF.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }
    private boolean ppcip_aprovado(String dataInicio,String dataFinal)        
    {
        try
        {
            //Pesquisa no banco de dados
            //conexao.executeSQL("SELECT * from quartel_sst.certificado WHERE cert_data BETWEEN '"+Data.DateToString(dataInicial)+"' AND '"+Data.DateToString(dataFinal)+"'");
            conexao.executeSQL("SELECT * from quartel_sst.ppcip WHERE ppcip_data_recebimento BETWEEN '"+dataInicio+"' "
                    + "AND "
                    + "'"+dataFinal+"' AND ppcip_situacao LIKE 'Aprovado' ORDER BY ppcip_data_recebimento ASC");
            conexao.resultset.first();
                Table tabela = new Table(2,1);
                tabela.setBorderColor(Color.BLACK);
                tabela.setPadding(1);
                tabela.setSpacing(1);
                tabela.setBorderWidth(1);
                tabela.setAlignment(1);
                /*tamanho das celulas*/
                float fl [] = new float[2];
                fl[0] = (float) 150;//id
                fl[1] = (float) 60;//nome
                tabela.setWidths(fl);
                
                Paragraph titProp = new Paragraph("PROPRIETÁRIO: ",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cell1 = new Cell(titProp);
                cell1.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell1.setBorder(0);
                tabela.addCell(cell1);
                /********************************************************************************/
                Paragraph observacoes = new Paragraph("Data",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cell12 = new Cell(observacoes);
                cell12.setBorder(0);
                tabela.addCell(cell12);
                /********************************************************************************/
                
            double total = 0;
            int qtd = 0;
            do
            {
                total+=conexao.resultset.getDouble("ppcip_taxa");
                qtd ++;
                Paragraph titCel = new Paragraph("|"+conexao.resultset.getString("ppcip_data_recebimento")+"| - "+
                conexao.resultset.getString("ppcip_proprietario"),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new Color(0, 0, 0)));
                 Cell cellTit = new Cell(titCel);
                cellTit.setHorizontalAlignment(Cell.ALIGN_LEFT);
                tabela.addCell(cellTit);
                /********************************************************************************/
                Paragraph titCel2 = new Paragraph(Data.alteraData(conexao.resultset.getString("ppcip_data_recebimento")),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL, new Color(0, 0, 0)));
                Cell cellTit2 = new Cell(titCel2);
                cellTit2.setHorizontalAlignment(Cell.ALIGN_CENTER);
                tabela.addCell(cellTit2);
                /********************************************************************************/
            }
            while(conexao.resultset.next());
            DecimalFormat def = new DecimalFormat("#########.##");
                //titulo da pagina
                Paragraph Titulo2 = new Paragraph("\nQuantidade de PPCIP APROVADOS",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
                Titulo2.setAlignment(1);
                document.add(Titulo2);
                
                //data escolhida
                Paragraph Titdata = new Paragraph("\nPesquisa feitas entre "+Data.alteraData(dataInicio)+" e "+Data.alteraData(dataFinal),
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
                Titdata.setAlignment(1);
                document.add(Titdata);
                
                Paragraph Tit3 = new Paragraph("\nProjetos APROVADOS: "+qtd+"\nValor total arrecadado: "+def.format(total)+" R$",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0)));
                Tit3.setAlignment(0);
                /********************************************************************************/
                document.add(tabela);
                document.add(Tit3);
                document.newPage();
            
            return true;
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (Relatorio_Livro linha xx) Exception: "+ex.getMessage();
            new arquivoLog(log);
            Paragraph Tit3 = new Paragraph("\nQuantidade de PPCIP APROVADOS",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, new Color(0, 0, 0)));
            Tit3.setAlignment(0);
            /********************************************************************************/
               
            Paragraph Titdata = new Paragraph("\nPESQUISA SEM RESULTADO",
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0)));
            Titdata.setAlignment(1);
            try
            {
                document.add(Tit3);
                document.add(Titdata);
                document.newPage();
            }
            catch (DocumentException ex1)
            {
                Logger.getLogger(RelatorioPDF.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return false;
    }
    public void gerRelatorio(String dataI, String dataF)
    {
        primeiraPagina();
        certificado(dataI,dataF);
        requerimento(dataI,dataF);
        exigencia(dataI,dataF);
        ppcip_analise(dataI,dataF);
        ppcip_reanalise(dataI,dataF);
        ppcip_aprovado(dataI,dataF);
        document.close();
        File pdf = new File("Relatorio_PDF/Relatorio.pdf");
        try
        {
            Desktop.getDesktop().open(pdf);//visualizar arquivo .pdf
        } catch (IOException ex)
        {
        }
    }
    /**
     * <h3>Este metodo serve para chamar todos os privados para criação do arquivo .pdf (Livro.pdf)</h3>
     * @return <strong>true</strong>Se nao houver erros na criacao do arquivo, <strong>false</strong> caso algum erro aconteça.
     */
    public static void main(String args[])
    {
        Conexao con = new Conexao();
        con.conecta("localhost", "root", "suporte@guedes");
        RelatorioPDF rel = new RelatorioPDF(con);
        rel.primeiraPagina();
        rel.certificado("2013-03-01","2013-03-21");
        rel.requerimento("2013-03-01","2013-03-21");
        rel.exigencia("2013-03-01","2013-03-21");
        rel.ppcip_analise("2013-03-01","2013-03-21");
        rel.ppcip_reanalise("2013-03-01","2013-03-21");
        rel.ppcip_aprovado("2013-03-01","2013-03-21");
        rel.document.close();
        File pdf = new File("Relatorio_PDF/Relatorio.pdf");
        try
        {
            Desktop.getDesktop().open(pdf);//visualizar arquivo .pdf
        } catch (IOException ex)
        {
        }
    }
   
}
