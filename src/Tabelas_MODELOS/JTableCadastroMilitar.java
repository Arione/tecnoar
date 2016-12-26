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
import JFrame_TecnoAr.Main;
import JFrame_MODELOS.jfCadastroMilitares;
import JFrame_AUX.jfProgressBar;
import JTableModels.TableModel;
import Log.arquivoLog;
import Uteis.CellRendererCenter;
import Uteis.CellRendererRight;
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

public class JTableCadastroMilitar
{
    public JTable tabela;
    public TableModel modelo = new TableModel();
    private Conexao conexao;
    private JPopupMenu popMenu;
    private JMenuItem jmAlterar,jmExcluir;
    private jfCadastroMilitares app;
    public JTableCadastroMilitar(Conexao con, jfCadastroMilitares ap)
    {
        this.conexao = con;
        this.app = ap;
        CriaJTABLET();
        criarMenu();
        tabela.setComponentPopupMenu(popMenu);
    }
    ActionListener visualizarListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            try
            {
                String iide = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                int id = Integer.parseInt(iide);
                conexao.executeSQL("SELECT * FROM bombeiro_19.militar WHERE id_militar = "+id);
                conexao.resultset.first();
                String nome = conexao.resultset.getString("nome");
                String matricula = conexao.resultset.getString("matricula");
                String senha = conexao.resultset.getString("senha");
                String graduacao = conexao.resultset.getString("graduacao");
                String nome_guerra = conexao.resultset.getString("nome_guerra");
                String quadro = conexao.resultset.getString("quadro");
                app.alterarMilitar(nome,matricula,senha,graduacao,nome_guerra,quadro,
                        conexao.resultset.getString("comum"),conexao.resultset.getString("chefe_dos_cov"),
                        conexao.resultset.getString("adjunto"),conexao.resultset.getString("supervisor_cov"),
                        conexao.resultset.getString("supervisor_adjunto"),conexao.resultset.getString("secao_material"),
                        conexao.resultset.getString("estatistica"),conexao.resultset.getString("darth_vader"));
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
                if(JOptionPane.showConfirmDialog(null, "Deseja Excluir?","Exclusão de Militar",JOptionPane.YES_NO_OPTION)==0)
                {
                    if(JOptionPane.showInputDialog("Digite 123 para excluir o registro:").equals("123"))
                    {
                        int id_militar = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
                        conexao.executeSQL("SELECT * FROM bombeiro_19.militar");
                        conexao.resultset.first();
                        String sqlDelete = "DELETE FROM bombeiro_19.militar WHERE id_militar="+id_militar;
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
        jmAlterar.addActionListener(visualizarListener);
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
        modelo.addColumn("Matrícula");
        modelo.addColumn("Graduação");
        modelo.addColumn("N. Guerra");
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
                                conexao.executeSQL("SELECT * FROM bombeiro_19.militar WHERE  id_militar="+indice+";");
                                conexao.resultset.first();
                                String nome = conexao.resultset.getString("nome");
                                String matricula = conexao.resultset.getString("matricula");
                                String senha = conexao.resultset.getString("senha");
                                String graduacao = conexao.resultset.getString("graduacao");
                                String nome_guerra = conexao.resultset.getString("nome_guerra");
                                String quadro = conexao.resultset.getString("quadro");
                                app.alterarMilitar(nome,matricula,senha,graduacao,nome_guerra,quadro,
                                conexao.resultset.getString("comum"),conexao.resultset.getString("chefe_dos_cov"),
                                conexao.resultset.getString("adjunto"),conexao.resultset.getString("supervisor_cov"),
                                conexao.resultset.getString("supervisor_adjunto"),conexao.resultset.getString("secao_material"),
                                conexao.resultset.getString("estatistica"),conexao.resultset.getString("darth_vader"));
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
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
        //ALINHAMENTO DAS CELULAS
        tabela.getColumnModel().getColumn(0).setCellRenderer(new CellRendererCenter());
        tabela.getColumnModel().getColumn(1).setCellRenderer(new CellRendererRight());
        tabela.getColumnModel().getColumn(2).setCellRenderer(new CellRendererCenter());
        tabela.getColumnModel().getColumn(3).setCellRenderer(new CellRendererCenter());
        tabela.getColumnModel().getColumn(4).setCellRenderer(new CellRendererCenter());
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
        conexao.executeSQL("SELECT * FROM bombeiro_19.militar ORDER BY nome ASC");
        try
        {
            conexao.resultset.first();
            do
            {
                int id = conexao.resultset.getInt("id_militar");
                String nome = conexao.resultset.getString("nome");
                String matricula = conexao.resultset.getString("matricula");
                String graduacao = conexao.resultset.getString("graduacao");
                String nome_guerra = conexao.resultset.getString("nome_guerra");
                modelo.addRow(new Object [] {id,nome,matricula,graduacao,nome_guerra});
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

