/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tabelas_MODELOS;

import Conexao.Conexao;
import JFrame_TecnoAr.Main;
import JFrame_MODELOS.jfCadastrarCautela;
import JFrame_MODELOS.pesquisarUsuario;
import JTableModels.TableModel;
import Log.arquivoLog;
import Renderers.CellRenderer;
import Uteis.Data;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
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
public class jfPesquisarMaterial extends javax.swing.JFrame {

    public JTable tabela;
    public TableModel modelo = new TableModel();
    public CellRenderer cell_center,cell_2,cell_esquerda,cell_direita,cell_centro_branco_preto,cell_centro_vermelho_branco;
    private Conexao conexao;
    private JPopupMenu popMenu;
    private JMenuItem jmCautelar;
    private Main appMain;
    pesquisarUsuario user;
    public jfPesquisarMaterial(Conexao con, Main ap) 
    {
        this.conexao = con;
        this.appMain = ap;
        user = new pesquisarUsuario(conexao);
        initComponents();
        criaMenu();
        CriaJTABLET();
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/Imagens/pesq_ico.png")));
    }
    public void mostraTela()
    {
        pesquisar("SELECT * FROM bombeiro_19.material WHERE tipo LIKE 'Permanente' ORDER BY nome ASC");
        conexao.executeSQL("SELECT * FROM bombeiro_19.material_destino");
        try
        {
            conexao.resultset.first();
            jComboBox1.removeAllItems();
            if(conexao.resultset.getRow()==0)//verifica se o bd esta vazio
            {
                jComboBox1.addItem("NENHUM CADASTRADO");
            }
            else
            {
                jComboBox1.addItem("TODOS");
                do
                {
                    jComboBox1.addItem(conexao.resultset.getString("nome"));
                }while(conexao.resultset.next());
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
    private void criaMenu()
    {
        popMenu = new JPopupMenu();
        jmCautelar = new JMenuItem("Cautela do Material",new ImageIcon(this.getClass().getResource("/Imagens/personalizar.png")));
        jmCautelar.addActionListener(cautelarListener);
        if(user.adjunto)
        {
            popMenu.add(jmCautelar);
        }
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
        modelo.addColumn("CP do Material");
        modelo.addColumn("Data de Recebimento");
        modelo.addColumn("Nome do Material");
        modelo.addColumn("Acondicionado em:");
        modelo.addColumn("Qtd");
        modelo.setCellEditable(false);
        tabela = new JTable(modelo);
        //tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tabela.setAutoCreateRowSorter(true);
        tabela.setSelectionMode(2);
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
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);//protocolo
        tabela.getColumnModel().getColumn(1).setPreferredWidth(10);//data_registro
        tabela.getColumnModel().getColumn(2).setPreferredWidth(20);//graduacao
        tabela.getColumnModel().getColumn(3).setPreferredWidth(120);//nome adjujnto
        tabela.getColumnModel().getColumn(4).setPreferredWidth(40);//visto
        tabela.getColumnModel().getColumn(5).setPreferredWidth(10);//dta_visto
        tabela.getColumnModel().getColumn(0).setCellRenderer(cell_center);//protocolo
        tabela.getColumnModel().getColumn(1).setCellRenderer(cell_center);//data_registro
        tabela.getColumnModel().getColumn(2).setCellRenderer(cell_center);//graduacao
        tabela.getColumnModel().getColumn(3).setCellRenderer(cell_esquerda);//nome adjunto
        tabela.getColumnModel().getColumn(4).setCellRenderer(cell_centro_preto_amarelo);//visto
        tabela.getColumnModel().getColumn(5).setCellRenderer(cell_center);//data_visto
        tabela.setDragEnabled(false);
        tabela.getTableHeader().setReorderingAllowed(false);//ESTE É PARA NÃO ARRASTAR AS COLUNAS COM O MOUSE
        tabela.setShowGrid(true);
        tabela.setAutoResizeMode(5);
        tabela.setGridColor(Color.lightGray);
        tabela.setComponentPopupMenu(popMenu);
        this.pack();
    }
    private void pesquisar(String sql)
    {
         modelo.setNumRows(0);
        conexao.executeSQL(sql);
        try
        {
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
                        conexao.resultset.getObject("id_material"),
                        conexao.resultset.getObject("cp_codigo"),
                        Data.alteraData(conexao.resultset.getString("data_entrada")),
                        conexao.resultset.getObject("nome"),
                        conexao.resultset.getObject("destino"),
                        conexao.resultset.getObject("quantidade")
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtDigiteNome = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar Material");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nome do Equipamento:");

        jtDigiteNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtDigiteNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtDigiteNomeActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Local de Armazenamento:");

        jButton1.setText("filtrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtDigiteNome, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)))
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtDigiteNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtDigiteNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtDigiteNomeActionPerformed
pesquisar("SELECT * FROM bombeiro_19.material WHERE nome LIKE '%"+jtDigiteNome.getText()+"%' AND tipo LIKE 'Permanente' ORDER BY nome ASC");
    }//GEN-LAST:event_jtDigiteNomeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
String filtro = jComboBox1.getSelectedItem().toString();
if(filtro.equals("TODOS"))
    pesquisar("SELECT * FROM bombeiro_19.material WHERE tipo LIKE 'Permanente' ORDER BY nome ASC");
else
    pesquisar("SELECT * FROM bombeiro_19.material WHERE destino LIKE '%"+filtro+"%' ORDER BY nome ASC");
    }//GEN-LAST:event_jButton1ActionPerformed

   public static void main(String args[]) 
    {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                Conexao conect = new Conexao();
                conect.conecta("localhost","usuario","sementes");
                new jfPesquisarMaterial(conect,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtDigiteNome;
    // End of variables declaration//GEN-END:variables
}
