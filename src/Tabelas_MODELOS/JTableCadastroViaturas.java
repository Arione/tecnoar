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

import JFrame_MODELOS.jfCadastroViaturas;
import Conexao.Conexao;
import JFrame_TecnoAr.Main;
import JFrame_AUX.jfProgressBar;
import JTableModels.TableModel;
import Log.arquivoLog;
import Uteis.Data;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class JTableCadastroViaturas
{
    public JTable tabela;
    public TableModel modelo = new TableModel();
    private Conexao conexao;
    private JPopupMenu popMenu;
    private JMenuItem jmAlterar,jmExcluir;
    private jfCadastroViaturas app;
    public JTableCadastroViaturas(Conexao con, jfCadastroViaturas ap)
    {
        this.conexao = con;
        this.app = ap;
        CriaJTABLET();
        criarMenu();
        tabela.setComponentPopupMenu(popMenu);
    }
    ActionListener alterarListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            try
            {
                String iide = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                int id = Integer.parseInt(iide);
                System.out.println(id);
                conexao.executeSQL("SELECT * FROM bombeiro_19.viaturas WHERE id_viatura = "+id);
                conexao.resultset.first();
                System.out.println(""+conexao.resultset.getInt("id_viatura")+""+
                        conexao.resultset.getString("nome_viatura")+""+
                        conexao.resultset.getString("placa")+""+
                        conexao.resultset.getString("cartao"));
                app.alterarViatura(conexao.resultset.getInt("id_viatura"),
                        conexao.resultset.getString("nome_viatura"),
                        conexao.resultset.getString("placa"),
                        conexao.resultset.getString("cartao"));
                

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
    };
    ActionListener excluirListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            try
            {
                if(JOptionPane.showConfirmDialog(null, "Deseja Excluir?","Exclusão de Viatura",JOptionPane.YES_NO_OPTION)==0)
                {
                    if(JOptionPane.showInputDialog("Digite 123 para excluir o registro:").equals("123"))
                    {
                        int id_viatura = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
                        conexao.executeSQL("SELECT * FROM bombeiro_19.viaturas");
                        conexao.resultset.first();
                        String sqlDelete = "DELETE FROM bombeiro_19.viaturas WHERE id_viatura="+id_viatura;
                        conexao.statement.executeUpdate(sqlDelete);
                        criarLista();
                    }
                }
            }
            catch (Exception er)
            {
               String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  excluirListener: "+er.getMessage();
               new arquivoLog(log);

            }
        }
    };
    private void criarMenu()
    {
        popMenu = new JPopupMenu();
        jmAlterar = new JMenuItem("Alterar",new ImageIcon(this.getClass().getResource("/Imagens/personalizar.png")));
        jmAlterar.addActionListener(alterarListener);
        jmExcluir = new JMenuItem("Excluir",new ImageIcon(this.getClass().getResource("/Imagens/lixeira16.png")));
        jmExcluir.addActionListener(excluirListener);
        popMenu.add(jmAlterar);
        popMenu.add(jmExcluir);
    }
    private void CriaJTABLET()
    {
        modelo.setNumRows(0);
        modelo.addColumn("id");
        modelo.addColumn("Nome");
        modelo.addColumn("Placa");
        modelo.addColumn("Cartão");
        modelo.setCellEditable(false);
        tabela = new JTable(modelo);
        tabela.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                if(evt.getClickCount()==2)
                {
                    new Thread()
                    {
                        public void run()
                        {
                            jfProgressBar bar = new jfProgressBar();
                            try
                            {

                                String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                                conexao.executeSQL("SELECT * FROM bombeiro_19.viaturas WHERE  id_viatura="+indice+";");
                                conexao.resultset.first();
                                String nome = conexao.resultset.getString("nome_viatura");
                                String placa = conexao.resultset.getString("placa");
                                String cartao = conexao.resultset.getString("cartao");
                                System.out.println(nome+" ("+placa+"): "+cartao);
                                bar.dispose();
                                this.interrupt();
                            } 
                            catch (Exception er)
                            {
                               String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
                               new arquivoLog(log);
                               bar.dispose();
                                this.interrupt();
                            }
                        }
                    }.start();
                }
            }
        });
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tabela.setAutoCreateRowSorter(true);
        tabela.setSelectionMode(0);
        tabela.setFont(new Font(null, Font.PLAIN, 14));
        //ALINHAMENTO DAS CELULAS
        tabela.setDragEnabled(false);
        tabela.getTableHeader().setReorderingAllowed(false);//ESTE É PARA NÃO ARRASTAR AS COLUNAS COM O MOUSE
        tabela.setShowGrid(true);
        tabela.setGridColor(Color.lightGray);
        //tabela.setDefaultRenderer(Object.class, new CellRenderer());
    }
    /* Este Metodo faz a busca e insere os dados na JList */
    private void criarLista()
    {
        modelo.setNumRows(0);
        conexao.executeSQL("SELECT * FROM bombeiro_19.viaturas ORDER BY nome_viatura ASC");
        try
        {
            conexao.resultset.first();
            do
            {
                int id = conexao.resultset.getInt("id_viatura");
                String nome = conexao.resultset.getString("nome_viatura");
                String placa = conexao.resultset.getString("placa");
                String cartao = conexao.resultset.getString("cartao");
                modelo.addRow(new Object [] {id,nome,placa,cartao});
            }while(conexao.resultset.next());
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
        criarLista();
        return tabela;
    }

}

