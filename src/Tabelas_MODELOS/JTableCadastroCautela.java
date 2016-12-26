/*
 * ----------------------------------------------- *
 * @author: Arione Guedes dos Santos Junior
 * Grad. Sistemas de Informação - U.F.M.S.
 * Email: arioneguedes@gmail.com
 * Website: arioneguedes.com.br
 * ----------------------------------------------- *
 */
/* @author Arione Guedes dos Santos Junior */

package Tabelas_MODELOS;

import Conexao.Conexao;
import JFrame_MODELOS.pesquisarUsuario;
import JTableModels.TableModelNaoEditavel;
import Log.arquivoLog;
import Renderers.CellRenderer;
import Uteis.Data;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class JTableCadastroCautela
{
    pesquisarUsuario user;
    public JTable tabela;
    public TableModelNaoEditavel modelo = new TableModelNaoEditavel();
    public CellRenderer cell_center;
    private Conexao conexao;
    private int [] equipamentos;
    Document document;
    String nome = "",matricula="",cargo="";
    int id_militar=0;
    private JPopupMenu popMenu;
    private JMenuItem jmRetirar;
    private int id_cautela = 0;
    public JTableCadastroCautela(Conexao con, int [] selecao)
    {
        this.conexao = con;
        this.equipamentos = selecao;
        user = new pesquisarUsuario(conexao);
        criaMenu();
        CriaJTABLET();
        criarLista();
    }
    private void criaMenu()
    {
        popMenu = new JPopupMenu();
        jmRetirar = new JMenuItem("Retirar item",new ImageIcon(this.getClass().getResource("/Imagens/lixeira16.png")));
        jmRetirar.addActionListener(retirarListener);
        popMenu.add(jmRetirar);
    }
    public ActionListener retirarListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            modelo.removeRow(tabela.getSelectedRow());
        }
    };
    private void CriaJTABLET()
    {
        modelo.setNumRows(0);
        modelo.addColumn("NOME");//2
        modelo.addColumn("QTD");//3
        modelo.addColumn("Estado");
        tabela = new JTable(modelo);
        cell_center = new CellRenderer();
        cell_center.setHorizontalAlignment(JLabel.CENTER);
        cell_center.setBackground(Color.red);
        cell_center.setForeground(Color.white);
        tabela.setFont(new Font(null, Font.PLAIN, 14));
        tabela.getColumnModel().getColumn(0).setPreferredWidth(140);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(20);
        //ALINHAMENTO DAS CELULAS
        tabela.getColumnModel().getColumn(1).setCellRenderer(cell_center);
        tabela.setDragEnabled(false);
        tabela.getTableHeader().setReorderingAllowed(false);//ESTE É PARA NÃO ARRASTAR AS COLUNAS COM O MOUSE
        tabela.setShowGrid(true);
        tabela.setComponentPopupMenu(popMenu);
    }
    /* Este Metodo faz a busca e insere os dados na JList */
    private void criarLista()
    {
        System.out.println("criar lista: ");
        modelo.setNumRows(0);
        try
        {
            for(int i=0;i<equipamentos.length;i++)
            {
                conexao.executeSQL("SELECT * FROM bombeiro_19.material WHERE id_material="+equipamentos[i]);
                conexao.resultset.first();
                modelo.addRow(new Object []{conexao.resultset.getObject("nome"),1,conexao.resultset.getObject("estado")});
            }
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  SQLException: "+er.getMessage();
            new arquivoLog(log);
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
            new arquivoLog(log);
        }
    }
    /**
     *
     * @return JTable criado com listagem da seleção dos valores do banco de dados,
     * ordenado pelo nome do militar
     */
    public JTable getTabela()
    {
        //criarLista();
        return tabela;
    }
    private void buscarMilitar(String nome)
    {
        conexao.executeSQL("SELECT * FROM bombeiro_19.militar WHERE nome LIKE '%"+nome+"%'");
            try
            {
                conexao.resultset.first();
                this.nome = conexao.resultset.getString("nome");
                this.matricula = conexao.resultset.getString("matricula");
                this.cargo = conexao.resultset.getString("graduacao");
                this.id_militar = conexao.resultset.getInt("id_militar");
            }
            catch(SQLException er)
            {
                String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  SQLException: "+er.getMessage();
                new arquivoLog(log);
    //            JOptionPane.showMessageDialog(null, "Houve erro, verificar arquivo .log");
            }
            catch(Exception ex)
            {
                String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
                new arquivoLog(log);
    //            JOptionPane.showMessageDialog(null, "Erro na consulta, verifique arquivo de log ou conexão ao BD");
            }
    }
    public void gerarDocumento(String nom,String dataDia)
    {
        buscarMilitar(nom);
        document = new Document(new Rectangle(595, 842),60,20,30,20);
        try
        {
            inserirViaSql(dataDia);
            new File("CAUTELA_PDF").mkdir();
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("CAUTELA_PDF/Cautela-"+nom+"-"+new Data().getDataSalv()+"-ID "+id_cautela+".pdf"));
            document.open();
            
            Paragraph Titulo2 = new Paragraph("\nCORPO DE BOMBEIROS MILITAR\n19º SGBM/IND\nCautela de Materiais - nº: "+id_cautela,
            FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, com.lowagie.text.Font.BOLD, new Color(0, 0, 0)));
            Titulo2.setAlignment(1);
            document.add(Titulo2);
            
            document.add(new Paragraph("NOME:",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
            document.add(new Paragraph(nome,FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0))));
            document.add(new Paragraph("MATRÍCULA:",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
            document.add(new Paragraph(matricula,FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0))));
            document.add(new Paragraph("\nDeclaro ter recebido do 19ºSGBM/IND no dia "+new Data().getDataF()+", os seguintes materiais:\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0))));
            
                Table tabelaDocument = new Table(3,1);
//                tabela.setBorderColor(Color.BLACK);
                tabelaDocument.setPadding(2);
                tabelaDocument.setSpacing(1);
                tabelaDocument.setBorderWidth(1);
                tabelaDocument.setAlignment(1);
                /*tamanho das celulas*/
                float fl [] = new float[3];
                fl[0] = (float) 350;//id
                fl[1] = (float) 30;//nome
                fl[2] = (float) 40;//nome
                tabelaDocument.setWidths(fl);
                
                Paragraph titProp = new Paragraph("EQUIPAMENTO: ",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, com.lowagie.text.Font.BOLD, new Color(0, 0, 0)));
                Cell cell1 = new Cell(titProp);
                cell1.setHorizontalAlignment(Cell.ALIGN_LEFT);
//                cell1.setBorder(0);
                tabelaDocument.addCell(cell1);
                /********************************************************************************/
                Paragraph observacoes = new Paragraph("QTD",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0)));
                Cell cell12 = new Cell(observacoes);
                cell1.setHorizontalAlignment(Cell.ALIGN_CENTER);
//                cell12.setBorder(0);
                tabelaDocument.addCell(cell12);
                /********************************************************************************/   
                Paragraph estado = new Paragraph("ESTADO",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0)));
                Cell cellestado = new Cell(estado);
                cellestado.setHorizontalAlignment(Cell.ALIGN_CENTER);
//                cell12.setBorder(0);
                tabelaDocument.addCell(cellestado);
                /********************************************************************************/   
                
                for(int i=0;i<modelo.getRowCount();i++)
                {
                    Paragraph titCel = new Paragraph(modelo.getValueAt(i,0).toString(),
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0)));
                     Cell cellTit = new Cell(titCel);
                    cellTit.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    tabelaDocument.addCell(cellTit);
                    /********************************************************************************/
                    Paragraph titCel2 = new Paragraph(modelo.getValueAt(i,1).toString(),
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0)));
                    Cell cellTit2 = new Cell(titCel2);
                    cellTit2.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    tabelaDocument.addCell(cellTit2);
                    /********************************************************************************/
                    /********************************************************************************/
                    Paragraph titCel3 = new Paragraph(modelo.getValueAt(i,2).toString(),
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0)));
                    Cell cellTit3 = new Cell(titCel3);
                    cellTit3.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    tabelaDocument.addCell(cellTit3);
                    /********************************************************************************/
                }
            document.add(tabelaDocument);
            document.add(new Paragraph("\nComprometendo-me devolver-lo(s) na(s) mesma(s) condições em: "+dataDia,FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0))));
            
            Table tabelaAssinatura = new Table(2,1);
            tabelaAssinatura.setPadding(2);
            tabelaAssinatura.setSpacing(1);
            tabelaAssinatura.setBorderWidth(0);
            tabelaAssinatura.setAlignment(1);
            /*tamanho das celulas*/
            float fls [] = new float[2];
            fls[0] = (float) 120;//id
            fls[1] = (float) 120;//nome
            tabelaAssinatura.setWidths(fls);
            
            Paragraph pAdjunto = new Paragraph("\n\n\n"+user.nomeUsuario+"\n"+user.graduacaoUsuario+" BM - Matrícula: "+user.matriculaUsuario+"\nResponsável pela cautela",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0)));
                Cell celAdjunto = new Cell(pAdjunto);
//                celAdjunto.setHorizontalAlignment(Cell.ALIGN_LEFT);
                celAdjunto.setBorder(0);
                celAdjunto.setHorizontalAlignment(1);
                tabelaAssinatura.addCell(celAdjunto);
                
            Paragraph pMilitar = new Paragraph("\n\n\n"+nome+"\n"+cargo+" BM - Matrícula: "+matricula+"\nResponsável pelo uso do material",
                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL, new Color(0, 0, 0)));
                Cell celMilitar = new Cell(pMilitar);
//                celMilitar.setHorizontalAlignment(Cell.ALIGN_LEFT);
                celMilitar.setBorder(0);
                celMilitar.setHorizontalAlignment(1);
                tabelaAssinatura.addCell(celMilitar);
            document.add(tabelaAssinatura);
            
            document.add(new Paragraph("\nRecebi os Materiais na data: ______/_______/____________, nas mesmas condições de sua Cautela.",FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, com.lowagie.text.Font.BOLD, new Color(0, 0, 0))));
            
            Table tabelaRecebimento = new Table(2,1);
            tabelaRecebimento.setPadding(2);
            tabelaRecebimento.setSpacing(1);
            tabelaRecebimento.setBorderWidth(0);
            tabelaRecebimento.setAlignment(0);
            /*tamanho das celulas*/
            float flr [] = new float[2];
            flr[0] = (float) 120;//id
            flr[1] = (float) 120;//nome
            tabelaRecebimento.setWidths(flr);
            
            Paragraph pRecebeu = new Paragraph("\nNome:\nMatrícula: \nResponsável pelo recebimento do Material",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.BOLD, new Color(0, 0, 0)));
                Cell celRecebeu = new Cell(pRecebeu);
                celRecebeu.setHorizontalAlignment(Cell.ALIGN_LEFT);
                celRecebeu.setBorder(0);
                celRecebeu.setHorizontalAlignment(0);
                tabelaRecebimento.addCell(celRecebeu);
                
            Paragraph pEntregou = new Paragraph("\nNome: \nMatrícula: \nResponsável pela entrega do Material",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.BOLD, new Color(0, 0, 0)));
                Cell celEntregou = new Cell(pEntregou);
                celEntregou.setHorizontalAlignment(Cell.ALIGN_LEFT);
                celEntregou.setBorder(0);
                celEntregou.setHorizontalAlignment(0);
                tabelaRecebimento.addCell(celEntregou);
            document.add(tabelaRecebimento);
            
            document.close();
            File pdf = new File("CAUTELA_PDF/Cautela-"+nom+"-"+new Data().getDataSalv()+"-ID "+id_cautela+".pdf");
            Desktop.getDesktop().open(pdf);//visualizar arquivo .pdf
            
        }
        catch (Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (fudeu aki) Exception: "+ex.getMessage();
            new arquivoLog(log);
        } 
    }
    private void inserirViaSql(String dta)
    {
        try
        {
            int militar_liberou = user.idUsuario;
            int militar_cautelou = id_militar;
            String data_saida = new Data().getDataSalv();
            String data_devolvida = dta;
            String SqlInsert = "INSERT INTO bombeiro_19.cautela "
                    + "(militar_liberou,militar_cautelou,data_saida,data_devolvida)"
                    + " VALUES "
                    + "("+militar_liberou+","+militar_cautelou+",'"+data_saida+"','"+data_devolvida+"')";
            conexao.executeSQL("select * from bombeiro_19.cautela");
            conexao.statement.executeUpdate(SqlInsert);
            conexao.executeSQL("select * from bombeiro_19.cautela ORDER BY id_cautela DESC limit 1");
            conexao.resultset.first();
            id_cautela = conexao.resultset.getInt("id_cautela");

            for(int i=0;i<modelo.getRowCount();i++)
            {
                String nome_equipamento = modelo.getValueAt(i,0).toString();//equipamento (nome)
                conexao.executeSQL("select * from bombeiro_19.material WHERE nome LIKE '"+nome_equipamento+"' ORDER BY nome DESC limit 1");
                conexao.resultset.first();
                int id_equipamento = conexao.resultset.getInt("id_material");
                int quantidade = Integer.parseInt(modelo.getValueAt(i,1).toString());//qtd
                String SqlMaterial = "INSERT INTO bombeiro_19.cautela_material "
                    + "(id_cautela,id_equipamento,quantidade)"
                    + " VALUES "
                    + "("+id_cautela+","+id_equipamento+","+quantidade+")";
                conexao.executeSQL("select * from bombeiro_19.cautela_material");
                conexao.statement.executeUpdate(SqlMaterial);
            }
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            er.printStackTrace();
        }
        catch(NumberFormatException nfe)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+nfe.getMessage();
            new arquivoLog(log);
        }
        catch (Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  (fudeu aki) Exception: "+ex.getMessage();
            new arquivoLog(log);
        } 
    }
}

