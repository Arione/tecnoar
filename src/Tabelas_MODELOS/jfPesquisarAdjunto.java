/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tabelas_MODELOS;

import Conexao.Conexao;
import JFrame_TecnoAr.Main;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import JFrame_AUX.jfProgressBar;
import Relatorios_MODELOS.jasperLivroAdjunto;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import javax.swing.KeyStroke;

/**
 *
 * @author Guedes
 */
public class jfPesquisarAdjunto extends javax.swing.JFrame
{
    public static void main(String args[]) 
    {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                Conexao conect = new Conexao();
                conect.conecta("localhost","usuario","sementes");
                new jfPesquisarAdjunto(conect,null).setVisible(true);
            }
        });
    }
    public JTable tabela;
    public TableModel modelo = new TableModel();
    public CellRenderer cell_center,cell_2,cell_esquerda,cell_direita,cell_centro_branco_preto,cell_centro_vermelho_branco;
    Conexao conexao;
    private JPopupMenu popMenu;
    private JMenuItem jmVisualizar,jmAlterar;
    private JMenuItem jmExcluir;
    private JMenuItem jmVistoFeito;
    private Main appMain;
    pesquisarUsuario user;
    public jfPesquisarAdjunto(Conexao con,Main app)
    {
        
        this.conexao = con;
        user = new pesquisarUsuario(conexao);
        this.appMain = app;
        this.addWindowListener(winListener);
        initComponents();
        criaMenu();
        CriaJTABLET();
//        buscaNomeAdjunto("", 10);
//        buscaNomeAdjunto(jtPesquisarNome.getText(),Integer.parseInt(jcResultados.getSelectedItem().toString()),jcVistoFeito.getSelectedItem().toString());
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/Imagens/pesq_ico.png")));
    }
 WindowAdapter winListener = new WindowAdapter()
    {
        public void windowClosing(WindowEvent event)
        {
            dispose();
        }
        public void windowIconified(WindowEvent e)
        {

        }
    };          
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        alphaPainter1 = new org.jdesktop.swingx.painter.AlphaPainter();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jcResultados = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jcVistoFeito = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jxDataLivro = new org.jdesktop.swingx.JXDatePicker();
        jbGo = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jtPesquisarNome = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Pesquisar e Visualizar Livro do Adjunto");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Data:");

        jcResultados.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jcResultados.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "30", "50", "80", "100", "150", "200", "300", "500" }));
        jcResultados.setToolTipText("Ele mostra a quantidade de resultados escolhida (Busca Proprietario)");
        jcResultados.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcResultadosItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Resultados");

        jcVistoFeito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jcVistoFeito.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "Sim", "Não" }));
        jcVistoFeito.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcVistoFeitoItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Visto");

        jxDataLivro.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jbGo.setText("OK");
        jbGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Nome de Guerra do Adjunto");

        jtPesquisarNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPesquisarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtPesquisarNomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jxDataLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbGo)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcResultados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcVistoFeito, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(241, 241, 241))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtPesquisarNome, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcVistoFeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jxDataLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbGo))
                    .addComponent(jtPesquisarNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
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

    private void jcResultadosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcResultadosItemStateChanged
buscaNomeAdjunto(jtPesquisarNome.getText(),Integer.parseInt(jcResultados.getSelectedItem().toString()),jcVistoFeito.getSelectedItem().toString());
    }//GEN-LAST:event_jcResultadosItemStateChanged

    private void jcVistoFeitoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcVistoFeitoItemStateChanged
buscaNomeAdjunto(jtPesquisarNome.getText(),Integer.parseInt(jcResultados.getSelectedItem().toString()),jcVistoFeito.getSelectedItem().toString());
    }//GEN-LAST:event_jcVistoFeitoItemStateChanged

    private void jtPesquisarNomeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtPesquisarNomeActionPerformed
    {//GEN-HEADEREND:event_jtPesquisarNomeActionPerformed
        buscaNomeAdjunto(jtPesquisarNome.getText(),Integer.parseInt(jcResultados.getSelectedItem().toString()));
    }//GEN-LAST:event_jtPesquisarNomeActionPerformed

    private void jbGoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbGoActionPerformed
    {//GEN-HEADEREND:event_jbGoActionPerformed
jcVistoFeito.setSelectedIndex(0);
buscarDataLivro(Integer.parseInt(jcResultados.getSelectedItem().toString()));
    }//GEN-LAST:event_jbGoActionPerformed
    private void CriaJTABLET()
    {
        modelo.setNumRows(0);
        modelo.addColumn("Protocolo");
        modelo.addColumn("Data do Registro");
        modelo.addColumn("Graduação");
        modelo.addColumn("Nome do Adjunto");
        modelo.addColumn("Visto");
        modelo.addColumn("Data do Visto");
        modelo.addColumn("Resp. Visto");
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

                                bar.jProgressBar1.setString("Preparando documento ...");
                                bar.jProgressBar1.setEnabled(true);
                                bar.jProgressBar1.setMaximum(100);
                                bar.jProgressBar1.setMinimum(0);
                                bar.jProgressBar1.setIndeterminate(true);
                                bar.setTitle("Aguarde...");
                                bar.show(true);
                                String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                                if(indice.equals("0"))
                                {
                                    bar.dispose();
                                    JOptionPane.showMessageDialog(null,"Esse não Inocente!");
                                }
                                else
                                    new jasperLivroAdjunto(indice, conexao);
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
        tabela.getColumnModel().getColumn(0).setPreferredWidth(15);//protocolo
        tabela.getColumnModel().getColumn(1).setPreferredWidth(40);//data_registro
        tabela.getColumnModel().getColumn(2).setPreferredWidth(20);//graduacao
        tabela.getColumnModel().getColumn(3).setPreferredWidth(120);//nome adjujnto
        tabela.getColumnModel().getColumn(4).setPreferredWidth(20);//visto
        tabela.getColumnModel().getColumn(5).setPreferredWidth(40);//dta_visto
        tabela.getColumnModel().getColumn(6).setPreferredWidth(80);//resp. visto
        tabela.getColumnModel().getColumn(0).setCellRenderer(cell_center);//protocolo
        tabela.getColumnModel().getColumn(1).setCellRenderer(cell_center);//data_registro
        tabela.getColumnModel().getColumn(2).setCellRenderer(cell_direita);//graduacao
        tabela.getColumnModel().getColumn(3).setCellRenderer(cell_center);//nome adjunto
        tabela.getColumnModel().getColumn(4).setCellRenderer(cell_centro_vermelho_branco);//visto
        tabela.getColumnModel().getColumn(5).setCellRenderer(cell_center);//data_visto
        tabela.getColumnModel().getColumn(6).setCellRenderer(cell_direita);//resp. visto
        tabela.setDragEnabled(false);
        tabela.getTableHeader().setReorderingAllowed(false);//ESTE É PARA NÃO ARRASTAR AS COLUNAS COM O MOUSE
        tabela.setShowGrid(true);
        tabela.setAutoResizeMode(5);
        tabela.setGridColor(Color.lightGray);
    }
    
    public void buscaNomeAdjunto(String nome, int limit)
    {
        modelo.setNumRows(0);
        conexao.executeSQL("select * from bombeiro_19.livroadjunto WHERE nome_guerra_adjunto LIKE '%"+nome+"%' ORDER BY id_livro_adjunto DESC limit "+limit);
       try
        {
            conexao.resultset.first();
            do
            {
                   modelo.addRow(new Object[]{
                   conexao.resultset.getObject("id_livro_adjunto"),
                   conexao.resultset.getObject("data_livro"),
                   conexao.resultset.getObject("posto_adjunto"),
                   conexao.resultset.getObject("nome_guerra_adjunto"),
                   conexao.resultset.getObject("visto"),
                   conexao.resultset.getObject("data_visto"),
                   conexao.resultset.getObject("resp_visto")
                });
            }while(conexao.resultset.next());
            jScrollPane1.setViewportView(tabela);
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
        }
       tabela.setComponentPopupMenu(popMenu);
    }
    public void buscaNomeAdjunto(String nomeAdjunto, int limit, String visto)
    {
        if(visto.equals("Todas"))
            visto = "%";
        modelo.setNumRows(0);
       conexao.executeSQL("select * from bombeiro_19.livroadjunto WHERE (nome_guerra_adjunto LIKE '%"+nomeAdjunto+"%')AND(visto LIKE '"+visto+"') ORDER BY id_livro_adjunto DESC limit "+limit);
       try
        {
            conexao.resultset.first();
            int contador = 0;
            double taxaSoma = 0;
            do
            {
                modelo.addRow(new Object[]{
                   conexao.resultset.getObject("id_livro_adjunto"),
                   conexao.resultset.getObject("data_livro"),
                   conexao.resultset.getObject("posto_adjunto"),
                   conexao.resultset.getObject("nome_guerra_adjunto"),
                   conexao.resultset.getObject("visto"),
                   conexao.resultset.getObject("data_visto"),
                   conexao.resultset.getObject("resp_visto")
                });
                contador++;
            }while(conexao.resultset.next());
            jScrollPane1.setViewportView(tabela);
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
        }
       catch(Exception exe)
       {
           System.out.println(exe.getMessage());
       }
       tabela.setComponentPopupMenu(popMenu);
    }
    private void buscarDataLivro(int limit)
    {
        
        try
        {
            Date date;
            date = jxDataLivro.getDate();
            String dataLivro = (1900+date.getYear())+"-"+(1+date.getMonth())+"-"+date.getDate();
        
            modelo.setNumRows(0);
            conexao.executeSQL("select * from bombeiro_19.livroadjunto WHERE data_livro='"+dataLivro+"' ORDER BY id_livro_adjunto DESC limit "+limit);
            conexao.resultset.first();
            int contador = 0;
            double taxaSoma = 0;
            do
            {
                modelo.addRow(new Object[]{
                   conexao.resultset.getObject("id_livro_adjunto"),
                   conexao.resultset.getObject("data_livro"),
                   conexao.resultset.getObject("posto_adjunto"),
                   conexao.resultset.getObject("nome_guerra_adjunto"),
                   conexao.resultset.getObject("visto"),
                   conexao.resultset.getObject("data_visto"),
                   conexao.resultset.getObject("resp_visto")
                });
                contador++;
            }while(conexao.resultset.next());
            jScrollPane1.setViewportView(tabela);
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
        }
       catch(NullPointerException npe1)
        {
            jxDataLivro.requestFocus();
        }
       catch(Exception exe)
       {
           System.out.println(exe.getMessage());
       }
       tabela.setComponentPopupMenu(popMenu);
    }
    private void criaMenu()
    {
        popMenu = new JPopupMenu();
        jmVisualizar = new JMenuItem("Visualizar",new ImageIcon(this.getClass().getResource("/Imagens/print-preview.png")));
        jmVisualizar.addActionListener(visualizarListener);
        jmVisualizar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_DOWN_MASK));
        jmAlterar = new JMenuItem("Alterar",new ImageIcon(this.getClass().getResource("/Imagens/personalizar.png")));
        jmAlterar.addActionListener(editarListener);
        jmExcluir = new JMenuItem("Excluir",new ImageIcon(this.getClass().getResource("/Imagens/lixeira16.png")));
        jmExcluir.addActionListener(excluirListener);
        jmVistoFeito = new JMenuItem("Realizar VISTO",new ImageIcon(this.getClass().getResource("/Imagens/Properties.png")));
        jmVistoFeito.addActionListener(actionVistoFeito);
        
        if(user.comum)
        {
            popMenu.add(jmVisualizar);
        }
        if(user.adjunto)
        {
            popMenu.add(jmAlterar);
        }
        if(user.supervisorAdjunto)
        {
            popMenu.add(jmVistoFeito);
        }
        if(user.darthVader)
        {
            popMenu.add(jmExcluir);
        }
    }
    public ActionListener actionVistoFeito = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            if(JOptionPane.showConfirmDialog(null,"O livro foi conferido?", "Confirmação",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                if(JOptionPane.showInputDialog("Digite 243 para confirmação:").equals("243"))
                {
                    try
                    {
                        String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Visto realizado "+indice;
                        new arquivoLog(log);
                        conexao.executeSQL("SELECT * FROM bombeiro_19.livroadjunto WHERE  id_livro_adjunto="+indice+";");
                        conexao.resultset.first();
                        Data date = new Data();
                        String data_visto =""+ date.getDataF();
                        String data_livro = new Data().getData(conexao.resultset.getDate("data_livro"));
                        String sqlUpdate =  
                        "UPDATE bombeiro_19.livroadjunto SET  visto='Sim', data_visto = '"+data_visto+"', resp_visto='"+user.graduacaoUsuario+" "+user.guerraUsuario+"' WHERE id_livro_adjunto="+indice+";";
                        conexao.statement.executeUpdate(sqlUpdate);
                        buscaNomeAdjunto("",Integer.parseInt(jcResultados.getSelectedItem().toString()));
                        String SqlAcesso = "insert into bombeiro_19.acessos (usuario_login,operacao ) VALUES ('"+user.graduacaoUsuario+" "+user.guerraUsuario+"','Realizou visto do livro do dia: "+data_livro+"')";
                            conexao.executeSQL("select * from bombeiro_19.acessos");
                            conexao.statement.executeUpdate(SqlAcesso);
                    } 
                    catch(SQLException er)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  SQLException: "+er.getMessage();
                        new arquivoLog(log);
                    }
                    catch(NumberFormatException nfe)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  NumberFormatException: "+nfe.getMessage();
                        new arquivoLog(log);
                    }
                    catch(Exception ex)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
                        new arquivoLog(log);
                    }
                }
            }
            else 
            {
                if(JOptionPane.showInputDialog("(NÃO) - Digite 213 para confirmação:").equals("213"))
                {
                    try
                    {
                        String indice = tabela.getValueAt(tabela.getSelectedRow(), 5).toString();
                        System.out.println("Vistoria realizada: "+indice);
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Vistoria Realizada "+indice;
                        new arquivoLog(log);
                        conexao.executeSQL("SELECT * FROM arione_cbm.requerimento WHERE  indice="+indice+";");
                        conexao.resultset.first();
                        Data date = new Data();
                        String data_vistoria =""+ date.getDataSalv();
                        String sqlUpdate =  
                        "UPDATE arione_cbm.requerimento SET  vistoria='Não', data_vistoria = '"+data_vistoria+"' WHERE indice="+indice+";";
                        conexao.statement.executeUpdate(sqlUpdate);
                        buscaNomeAdjunto("",Integer.parseInt(jcResultados.getSelectedItem().toString()));
                    } 
                    catch(SQLException er)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  SQLException: "+er.getMessage();
                        new arquivoLog(log);
                    }
                    catch(NumberFormatException nfe)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  NumberFormatException: "+nfe.getMessage();
                        new arquivoLog(log);
                    }
                    catch(Exception ex)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
                        new arquivoLog(log);
                    }
                }
            }
        }
    };
    public ActionListener visualizarListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            new Thread()
            {
                public void run()
                {
                    jfProgressBar bar = new jfProgressBar();
                    try
                    {
                        
                        bar.jProgressBar1.setString("Preparando documento ...");
                        bar.jProgressBar1.setEnabled(true);
                        bar.jProgressBar1.setMaximum(100);
                        bar.jProgressBar1.setMinimum(0);
                        bar.jProgressBar1.setIndeterminate(true);
                        bar.setTitle("Aguarde...");
                        bar.show(true);
                        String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                        new jasperLivroAdjunto(indice, conexao);
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
    };
    public ActionListener excluirListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            if(JOptionPane.showConfirmDialog(null,"Deseja excluir este registro?", "Confirmação",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                if(JOptionPane.showInputDialog("Digite 123 para excluir o registro:").equals("123"))
                {
                    try
                    {
                        String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                        String userOperacao = "LIVRO DO ADJUNTO - Excluir Registro de indice: "+indice+" ("+tabela.getValueAt(tabela.getSelectedRow(), 1).toString()+")";
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Excluir LIVRO ADJUNTO "+indice;
                        new arquivoLog(log);
                        conexao.executeSQL("SELECT * FROM bombeiro_19.livroadjunto WHERE  id_livro_adjunto="+indice+";");
                        conexao.resultset.first();
                        System.out.println(conexao.resultset.getString("visto"));
                        if(conexao.resultset.getString("visto").equals("Sim"))
                        {
                            JOptionPane.showMessageDialog(null,"Exclusão não autorizada");
                        }
                        else
                        {
                            String sqlUpdate =  "DELETE FROM bombeiro_19.livroadjunto WHERE  id_livro_adjunto="+indice+";";
                            conexao.statement.executeUpdate(sqlUpdate);
                                File logado = new File("log.temp");
                                FileReader fileReader = new FileReader(logado);
                                BufferedReader bufferedReader = new BufferedReader(fileReader);
                                String userLogado = bufferedReader.readLine();
                                bufferedReader.close();
                                String sqlAcesso = "insert into bombeiro_19.acessos (usuario_login,operacao ) VALUES ('"+userLogado+"','"+userOperacao+"')";
                                conexao.executeSQL("select * from bombeiro_19.acessos order by id desc limit 1");
                                conexao.statement.executeUpdate(sqlAcesso);
                            buscaNomeAdjunto("",Integer.parseInt(jcResultados.getSelectedItem().toString()));
                        }
                    } 
                    catch(SQLException er)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  SQLException: "+er.getMessage();
                        new arquivoLog(log);
                    }
                    catch(NumberFormatException nfe)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  NumberFormatException: "+nfe.getMessage();
                        new arquivoLog(log);
                    }
                    catch(Exception ex)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
                        new arquivoLog(log);
                    }
                }
            }
        }
    };
    public ActionListener editarListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            new Thread()
            {
                public void run()
                {
                    jfProgressBar bar = new jfProgressBar();
                    try
                    {
                        String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                        conexao.executeSQL("SELECT * FROM bombeiro_19.livroadjunto WHERE  id_livro_adjunto="+indice+";");
                        conexao.resultset.first();
                        System.out.println(user.guerraUsuario+" --> "+conexao.resultset.getString("nome_guerra_adjunto"));
                        if(conexao.resultset.getString("visto").equals("Sim"))
                        {
                            JOptionPane.showMessageDialog(null,"Alteração não autorizada");
                        }
                        else if(conexao.resultset.getString("nome_guerra_adjunto").equals(user.guerraUsuario)==false)
                        {
                            JOptionPane.showMessageDialog(null,"Alteração não autorizada");
                        }
                        else
                        {
                            bar.jProgressBar1.setString("Criando documento...");
                            bar.jProgressBar1.setEnabled(true);
                            bar.jProgressBar1.setMaximum(100);
                            bar.jProgressBar1.setMinimum(0);
                            bar.jProgressBar1.setIndeterminate(true);
                            bar.setTitle("Aguarde...");
                            bar.show(true);
                            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Alterar Registro: "+indice;
                            new arquivoLog(log);
//                            appMain.cadastroLivro.limparTela();
//                            appMain.cadastroLivro.alterarLivro(Integer.parseInt(indice));
//                            appMain.cadastroLivro.show(true);
                            bar.dispose();
//                          show(false);
                            dispose();
                        }
                            this.interrupt();
                        
                    } 
                    catch(NumberFormatException nfe)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  NumberFormatException: "+nfe.getMessage();
                        new arquivoLog(log);
                        bar.dispose();
                        this.interrupt();
                    }
                    catch(Exception ex)
                    {
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
                        new arquivoLog(log);
                        bar.dispose();
                        this.interrupt();
                    }
                }
            }.start();
        }
    };
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.painter.AlphaPainter alphaPainter1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbGo;
    private javax.swing.JComboBox jcResultados;
    private javax.swing.JComboBox jcVistoFeito;
    private javax.swing.JTextField jtPesquisarNome;
    private org.jdesktop.swingx.JXDatePicker jxDataLivro;
    // End of variables declaration//GEN-END:variables

    
}
