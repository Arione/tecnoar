/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tabelas_MODELOS;

import Conexao.Conexao;
import JFrame_TecnoAr.Main;
import JFrame_MODELOS.jfCadastrarCautela;
import JTableModels.TableModel;
import Log.arquivoLog;
import Renderers.CellRenderer;
import Uteis.Data;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

/**
 *
 * @author Guedes
 */
public class jfPesquisarCautela extends javax.swing.JFrame 
{
    public JTable tabela;
    public TableModel modelo = new TableModel();
    public CellRenderer cell_center,cell_2,cell_esquerda,cell_direita,cell_centro_branco_preto,cell_centro_vermelho_branco;
    private Conexao conexao;
    private JPopupMenu popMenu;
    private JMenuItem jmCautelar;
    private Main app;
    public jfPesquisarCautela(Conexao con, Main ap) 
    {
        this.conexao = con;
        this.app = ap;
        initComponents();
        criaMenu();
        CriaJTABLET();
        pesquisar();
    }
    private void criaMenu()
    {
        popMenu = new JPopupMenu();
        jmCautelar = new JMenuItem("Cautela do Material",new ImageIcon(this.getClass().getResource("/Imagens/personalizar.png")));
        jmCautelar.addActionListener(cautelarListener);
        popMenu.add(jmCautelar);
    }
    public ActionListener cautelarListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            if(JOptionPane.showConfirmDialog(null,"Deseja cautelar os equipamentos?", "Confirmação",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                    int selecao [] = tabela.getSelectedRows();
                    int [] id_material = new int[selecao.length];
                    for(int i=0;i<selecao.length;i++)
                    {
                        id_material[i] = Integer.parseInt(tabela.getValueAt(selecao[i], 0).toString());
//                        System.out.println("id do material: "+id_material[i]);
                    }
                    new jfCadastrarCautela(conexao,id_material).show(true);
            }
        }
    };
    private void CriaJTABLET()
    {
        modelo.setNumRows(0);
        modelo.addColumn("ID");
        modelo.addColumn("Data da Cautela");
        modelo.addColumn("Quem Cautelou");
        modelo.addColumn("Autorizado por");
        modelo.addColumn("Data da Devolução");
        modelo.setCellEditable(false);
        tabela = new JTable(modelo);
        //tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tabela.setAutoCreateRowSorter(true);
        tabela.setSelectionMode(0);
        tabela.setBackground(new Color(204,204,204));
        tabela.setSelectionBackground(new Color(0,102,102));
        tabela.setSelectionForeground(Color.WHITE);
        tabela.setFont(new Font(null, Font.PLAIN, 14));
        cell_center = new CellRenderer();
        cell_center.setHorizontalAlignment(JLabel.CENTER);
        cell_2 = new CellRenderer();
        cell_esquerda = new CellRenderer();
        cell_esquerda.setHorizontalAlignment(JLabel.LEFT);
        cell_direita = new CellRenderer();
        cell_direita.setHorizontalAlignment(JLabel.RIGHT);
        cell_centro_branco_preto = new CellRenderer();
        cell_centro_branco_preto.setHorizontalAlignment(JLabel.CENTER);
        cell_centro_branco_preto.setBackground(Color.GRAY);
        cell_centro_branco_preto.setForeground(Color.BLACK);
        cell_centro_vermelho_branco = new CellRenderer();
        cell_centro_vermelho_branco.setBackground(Color.RED);
        cell_centro_vermelho_branco.setForeground(Color.WHITE);
        cell_centro_vermelho_branco.setHorizontalAlignment(JLabel.RIGHT);
        CellRenderer cell_centro_preto_amarelo = new CellRenderer();
        cell_centro_preto_amarelo.setHorizontalAlignment(JLabel.CENTER);
        cell_centro_preto_amarelo.setBackground(Color.BLACK);
        cell_centro_preto_amarelo.setForeground(Color.YELLOW);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(0).setCellRenderer(cell_center);
        tabela.getColumnModel().getColumn(1).setCellRenderer(cell_center);
        tabela.getColumnModel().getColumn(2).setCellRenderer(cell_center);
        tabela.getColumnModel().getColumn(3).setCellRenderer(cell_esquerda);
        tabela.getColumnModel().getColumn(4).setCellRenderer(cell_centro_preto_amarelo);
        tabela.setDragEnabled(false);
        tabela.getTableHeader().setReorderingAllowed(false);//ESTE É PARA NÃO ARRASTAR AS COLUNAS COM O MOUSE
        tabela.setShowGrid(true);
        tabela.setAutoResizeMode(5);
        tabela.setGridColor(Color.lightGray);
        tabela.setComponentPopupMenu(popMenu);
        this.pack();
    }
    private void pesquisar()
    {
        try
        {
            modelo.setNumRows(0);
            conexao.executeSQL("select a.id_cautela,a.militar_liberou,a.militar_cautelou,a.data_saida,a.data_devolvida,"
            + "b.id_militar,b.nome,c.id_militar,c.nome " +
            "from " +
            "bombeiro_19.cautela a " +
            "inner join bombeiro_19.militar b on a.militar_liberou = b.id_militar " +
            "inner join bombeiro_19.militar c on a.militar_cautelou = c.id_militar ORDER BY a.id_cautela DESC;");
            conexao.resultset.first();
            if(conexao.resultset.getRow()==0)//verifica se o bd esta vazio
            {
                modelo.addRow(new Object[]{"","","","NENHUM CADASTRADO","",""});
            }
            else
            {
                do
                {
                        modelo.addRow(new Object[]{
                        conexao.resultset.getObject("a.id_cautela"),//id_cautela
                        Data.alteraData(conexao.resultset.getString("a.data_saida")),//data de saida
                        conexao.resultset.getObject("c.nome"),//nome militar cautelou
                        conexao.resultset.getObject("b.nome"),//nome militar liberou
                        conexao.resultset.getObject("a.data_devolvida")//data de devolucao
                    });
                }while(conexao.resultset.next());
            }
            jScrollPane1.setViewportView(tabela);
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
        this.pack();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relação de Cautelas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jfPesquisarCautela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfPesquisarCautela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfPesquisarCautela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfPesquisarCautela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                Conexao cone = new Conexao();
                cone.conecta("localhost","usuario", "sementes");
                new jfPesquisarCautela(cone,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
