/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tabelas_TecnoAr;

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
import JFrame_TecnoAr.jfCadastroClientes;
import JFrame_TecnoAr.jfCadastroServico;
import Relatorio_TecnoAr.jasperHistoricoCliente;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.KeyStroke;

/**
 *
 * @author Guedes
 */
public class jfPesquisarClientes extends javax.swing.JFrame
{
    public static void main(String args[]) 
    {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                Conexao conect = new Conexao();
                conect.conecta("localhost","root","semente");
                new jfPesquisarClientes(conect,null).setVisible(true);
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
    private JMenuItem jmOrcamento;
    private JMenuItem jmIncluirServico;
    private Main appMain;
    pesquisarUsuario user;
    public jfPesquisarClientes(Conexao con,Main app)
    {
        
        this.conexao = con;
        this.appMain = app;
        this.addWindowListener(winListener);
        initComponents();
        criaMenu();
        CriaJTABLET();
        buscaNomeCliente("", 10);
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
        jxDataRegistro = new org.jdesktop.swingx.JXDatePicker();
        jbGo = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jtPesquisarNome = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtPesquisarCPF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Pesquisar e Visualizar Livro do COV");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Data registro:");

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

        jxDataRegistro.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jbGo.setText("OK");
        jbGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Nome do Cliente");

        jtPesquisarNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPesquisarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtPesquisarNomeActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("CPF/CNPJ:");

        jtPesquisarCPF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPesquisarCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtPesquisarCPFActionPerformed(evt);
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
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jxDataRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbGo)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcResultados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                .addGap(94, 94, 94))
                            .addComponent(jtPesquisarNome, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtPesquisarCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(97, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jxDataRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbGo)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtPesquisarNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(27, 27, 27))
                        .addComponent(jtPesquisarCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
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
buscaNomeResultadoCpf(jtPesquisarNome.getText(),Integer.parseInt(jcResultados.getSelectedItem().toString()),jtPesquisarCPF.getText());
    }//GEN-LAST:event_jcResultadosItemStateChanged

    private void jtPesquisarNomeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtPesquisarNomeActionPerformed
    {//GEN-HEADEREND:event_jtPesquisarNomeActionPerformed
        buscaNomeCliente(jtPesquisarNome.getText(),Integer.parseInt(jcResultados.getSelectedItem().toString()));
    }//GEN-LAST:event_jtPesquisarNomeActionPerformed

    private void jbGoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbGoActionPerformed
    {//GEN-HEADEREND:event_jbGoActionPerformed
buscaDataCadastro(Integer.parseInt(jcResultados.getSelectedItem().toString()));
    }//GEN-LAST:event_jbGoActionPerformed

    private void jtPesquisarCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtPesquisarCPFActionPerformed
        buscaCPFCliente(jtPesquisarCPF.getText(), Integer.parseInt(jcResultados.getSelectedItem().toString()));
    }//GEN-LAST:event_jtPesquisarCPFActionPerformed
private void CriaJTABLET()
    {
        modelo.setNumRows(0);
        modelo.addColumn("ID");
        modelo.addColumn("Data Registro");
        modelo.addColumn("Nome completo");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("Tel. Celular");
        modelo.addColumn("Tel. Fixo");
        modelo.addColumn("Email");
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
                                    //new jasperLivroCov(indice, conexao);
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
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150);//graduacao
        tabela.getColumnModel().getColumn(3).setPreferredWidth(40);//nome adjujnto
        tabela.getColumnModel().getColumn(4).setPreferredWidth(40);//visto
        tabela.getColumnModel().getColumn(5).setPreferredWidth(40);//dta_visto
        tabela.getColumnModel().getColumn(6).setPreferredWidth(40);//resp. visto
        tabela.getColumnModel().getColumn(0).setCellRenderer(cell_center);//protocolo
        tabela.getColumnModel().getColumn(1).setCellRenderer(cell_center);//data_registro
        tabela.getColumnModel().getColumn(2).setCellRenderer(cell_esquerda);//graduacao
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
public void buscaNomeResultadoCpf(String nome,int limit,String cpf)
{
    modelo.setNumRows(0);
        conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE nome LIKE '%"+nome+"%' AND cpf_cnpj LIKE '%"+cpf+"%' order by nome ASC limit "+limit);
       try
        {
            conexao.resultset.first();
            do
            {
                   modelo.addRow(new Object[]{
                   conexao.resultset.getObject("id"),
                   Data.alteraData(conexao.resultset.getString("data_cadastro")),
                   conexao.resultset.getObject("nome"),
                   conexao.resultset.getObject("cpf_cnpj"),
                   conexao.resultset.getObject("telefone_cel"),
                   conexao.resultset.getObject("telefone_fixo"),
                   conexao.resultset.getObject("email")
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
    public void buscaCPFCliente(String cpf, int limit)
    {
        modelo.setNumRows(0);
        conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE cpf_cnpj LIKE '%"+cpf+"%' order by nome ASC limit "+limit);
       try
        {
            conexao.resultset.first();
            do
            {
                   modelo.addRow(new Object[]{
                   conexao.resultset.getObject("id"),
                   Data.alteraData(conexao.resultset.getString("data_cadastro")),
                   conexao.resultset.getObject("nome"),
                   conexao.resultset.getObject("cpf_cnpj"),
                   conexao.resultset.getObject("telefone_cel"),
                   conexao.resultset.getObject("telefone_fixo"),
                   conexao.resultset.getObject("email")
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
    public void buscaNomeCliente(String nome, int limit)
    {
        modelo.setNumRows(0);
        conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE nome LIKE '%"+nome+"%' order by nome ASC limit "+limit);
       try
        {
            conexao.resultset.first();
            do
            {
                   modelo.addRow(new Object[]{
                   conexao.resultset.getObject("id"),
                   Data.alteraData(conexao.resultset.getString("data_cadastro")),
                   conexao.resultset.getObject("nome"),
                   conexao.resultset.getObject("cpf_cnpj"),
                   conexao.resultset.getObject("telefone_cel"),
                   conexao.resultset.getObject("telefone_fixo"),
                   conexao.resultset.getObject("email")
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
    private void buscaDataCadastro(int limit)
    {
        
        try
        {
            Date date;
            date = jxDataRegistro.getDate();
            String dataLivro = (1900+date.getYear())+"-"+(1+date.getMonth())+"-"+date.getDate();
            System.out.println(dataLivro);
            modelo.setNumRows(0);
            conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE data_cadastro='"+dataLivro+"' ORDER BY nome ASC limit "+limit);
            conexao.resultset.first();
            int contador = 0;
            double taxaSoma = 0;
            do
            {
               modelo.addRow(new Object[]{
                   conexao.resultset.getObject("id"),
                   Data.alteraData(conexao.resultset.getString("data_cadastro")),
                   conexao.resultset.getObject("nome"),
                   conexao.resultset.getObject("cpf_cnpj"),
                   conexao.resultset.getObject("telefone_cel"),
                   conexao.resultset.getObject("telefone_fixo"),
                   conexao.resultset.getObject("email")
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
            jxDataRegistro.requestFocus();
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
        jmVisualizar = new JMenuItem("Visualizar Histórico",new ImageIcon(this.getClass().getResource("/Imagens/print-preview.png")));
        jmVisualizar.addActionListener(visualizarHistoricoListener);
        jmVisualizar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_DOWN_MASK));
        jmAlterar = new JMenuItem("Alterar",new ImageIcon(this.getClass().getResource("/Imagens/personalizar.png")));
        jmAlterar.addActionListener(editarClienteListener);
        jmExcluir = new JMenuItem("Excluir",new ImageIcon(this.getClass().getResource("/Imagens/lixeira16.png")));
        jmExcluir.addActionListener(excluirClienteListener);
        jmIncluirServico = new JMenuItem("Incluir Serviço",new ImageIcon(this.getClass().getResource("/Imagens/Properties.png")));
        jmIncluirServico.addActionListener(actionIncluirServico);
        jmOrcamento = new JMenuItem("Criar Orçamento",new ImageIcon(this.getClass().getResource("/Imagens/Properties.png")));
        jmOrcamento.addActionListener(actionIncluirOrcamento);
        popMenu.add(jmVisualizar);
        popMenu.add(jmAlterar);
        popMenu.add(jmIncluirServico);
        popMenu.add(jmOrcamento);
        popMenu.add(jmExcluir);//OK
    }
    public ActionListener actionIncluirServico = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            if(JOptionPane.showConfirmDialog(null,"Deseja incluir um serviço para este Cliente?", "Confirmação",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                if(JOptionPane.showInputDialog("Digite 243 para confirmação:").equals("243"))
                {
                    new Thread()
                    {
                        public void run()
                        {
                            jfProgressBar bar = new jfProgressBar();
                            try
                            {
                                String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                                conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE  id="+indice+";");
                                conexao.resultset.first();
                                    bar.jProgressBar1.setString("Incluir Serviço...");
                                    bar.jProgressBar1.setEnabled(true);
                                    bar.jProgressBar1.setMaximum(100);
                                    bar.jProgressBar1.setMinimum(0);
                                    bar.jProgressBar1.setIndeterminate(true);
                                    bar.setTitle("Aguarde...");
                                    bar.show(true);
                                    String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Incluir Serviço: "+indice;
                                    new arquivoLog(log);
                                    jfCadastroServico app = new jfCadastroServico(conexao, appMain);
                                    app.buscarCliente(Integer.parseInt(indice));
                                    app.show(true);
                                    bar.dispose();
//                                    dispose();
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
            }
        }
    };
    public ActionListener actionIncluirOrcamento = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            if(JOptionPane.showConfirmDialog(null,"Deseja realizar um ORÇAMENTO para este Cliente?", "Confirmação",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                if(JOptionPane.showInputDialog("Digite 516 para confirmação:").equals("516"))
                {
                    try
                    {
                        String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                        conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE  id="+indice+";");
                        conexao.resultset.first();
                        
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
    public ActionListener visualizarHistoricoListener = new ActionListener()
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
                        new jasperHistoricoCliente(indice, conexao);
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
    public ActionListener excluirClienteListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            if(JOptionPane.showConfirmDialog(null,"Deseja excluir este registro?", "Confirmação",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                if(JOptionPane.showInputDialog("Digite 1194 para excluir o registro:").equals("1194"))
                {
                    try
                    {
                        String indice = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                        conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE  id="+indice+";");
                        conexao.resultset.first();
                            String sqlUpdate =  "DELETE FROM guedes_tecnoar.cliente WHERE  id="+indice+";";
                            conexao.statement.executeUpdate(sqlUpdate);
                            buscaNomeCliente("",Integer.parseInt(jcResultados.getSelectedItem().toString()));
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
    public ActionListener editarClienteListener = new ActionListener()
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
                        conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE  id="+indice+";");
                        conexao.resultset.first();
                            bar.jProgressBar1.setString("Alterando registro...");
                            bar.jProgressBar1.setEnabled(true);
                            bar.jProgressBar1.setMaximum(100);
                            bar.jProgressBar1.setMinimum(0);
                            bar.jProgressBar1.setIndeterminate(true);
                            bar.setTitle("Aguarde...");
                            bar.show(true);
                            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Alterar Registro: "+indice;
                            new arquivoLog(log);
                            jfCadastroClientes app = new jfCadastroClientes(conexao, appMain);
                            app.editarCadastro(Integer.parseInt(indice));
                            app.show(true);
                            bar.dispose();
//                            dispose();
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbGo;
    private javax.swing.JComboBox jcResultados;
    private javax.swing.JTextField jtPesquisarCPF;
    private javax.swing.JTextField jtPesquisarNome;
    private org.jdesktop.swingx.JXDatePicker jxDataRegistro;
    // End of variables declaration//GEN-END:variables

    
}
