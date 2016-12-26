/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame_TecnoAr;

import Conexao.Conexao;
import Log.arquivoLog;
import Tabelas_TecnoAr.jfPesquisarClientes;
import Uteis.Data;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Guedes
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    Conexao conexao;
    jPanelMain painel;
    Data date = new Data();
    private KeyStroke keySair,keyCadastrarOrcamento, keyCadastrarCliente,keyCadastrarServico;
    private KeyStroke keyPesquisarServicos,keyPesquisarOrcamentos,keyPesquisarClientes;
    public Main(Conexao con) 
    {
        painel = new jPanelMain();
        this.conexao = con;
        initComponents();
        teclasAtalho();
        jlData.setText(date.getData()+", "+date.getHora()+"hs");
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/Imagens/icon_main.png")));
    }
    private void teclasAtalho()
    {
        /*Teclas de atalho (CTRL + X)*/
        keySair = KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK);
        /*Teclas de Atalho (CTRL + C)*/
        keyCadastrarCliente = KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK);
        /*Teclas de Atalho (CTRL + S)*/
        keyCadastrarServico = KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK);
        /*Teclas de Atalho (CTRL + O)*/
        keyCadastrarOrcamento = KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK);
        /*Teclas de Atalho (CTRL + SHIFT + C )*/
        keyPesquisarClientes = KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK |InputEvent.SHIFT_DOWN_MASK);
        /*Teclas de Atalho (CTRL + SHIFT + S)*/
        keyPesquisarServicos = KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK |InputEvent.SHIFT_DOWN_MASK);
        /*Teclas de Atalho (CTRL + SHIFT + O)*/
        keyPesquisarOrcamentos = KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK |InputEvent.SHIFT_DOWN_MASK);
        jmSair.setAccelerator(keySair);
        jmCadastrarCliente.setAccelerator(keyCadastrarCliente);
        jmCadastrarServico.setAccelerator(keyCadastrarServico);
        jmCadastrarOrcamento.setAccelerator(keyCadastrarOrcamento);
        jmPesquisarServicos.setAccelerator(keyPesquisarServicos);
        jmPesquisarOrcamentos.setAccelerator(keyPesquisarOrcamentos);
        jmPesquisarClientes.setAccelerator(keyPesquisarClientes);
    }

public static void main(String args[]) 
{
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                try
        {
            new Main(null).setVisible(true);
            UIManager.setLookAndFeel(new NimRODLookAndFeel());
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException ex1)
            {
                Logger.getLogger(init.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (InstantiationException ex1)
            {
                Logger.getLogger(init.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IllegalAccessException ex1)
            {
                Logger.getLogger(init.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (UnsupportedLookAndFeelException ex1)
            {
                Logger.getLogger(init.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
                
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlData = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmFile = new javax.swing.JMenu();
        jmSair = new javax.swing.JMenuItem();
        jmCadastrar = new javax.swing.JMenu();
        jmCadastrarCliente = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmCadastrarServico = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jmCadastrarOrcamento = new javax.swing.JMenuItem();
        jmPesquisar = new javax.swing.JMenu();
        jmPesquisarClientes = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jmPesquisarServicos = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jmPesquisarOrcamentos = new javax.swing.JMenuItem();
        jmRelatorio = new javax.swing.JMenu();
        jmRelatorioPeriodo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jmRelatorioCliente = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jmGerenciar = new javax.swing.JMenu();
        jmGerenciarUsuario = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmGerenciarServicos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TecnoAR - Sistema Administrativo");
        setResizable(false);

        jlData.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jlData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlData.setText("data");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/imagem_main.png"))); // NOI18N

        jMenuBar1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/personalizar.png"))); // NOI18N
        jmFile.setText("File   ");
        jmFile.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N

        jmSair.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmSair.setText("Sair do programa");
        jmSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSairActionPerformed(evt);
            }
        });
        jmFile.add(jmSair);

        jMenuBar1.add(jmFile);

        jmCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cad_ico.png"))); // NOI18N
        jmCadastrar.setText("Cadastrar   ");
        jmCadastrar.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N

        jmCadastrarCliente.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmCadastrarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/efetivo_ico.png"))); // NOI18N
        jmCadastrarCliente.setText("Clientes");
        jmCadastrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCadastrarClienteActionPerformed(evt);
            }
        });
        jmCadastrar.add(jmCadastrarCliente);
        jmCadastrar.add(jSeparator1);

        jmCadastrarServico.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmCadastrarServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/sync.png"))); // NOI18N
        jmCadastrarServico.setText("Serviços");
        jmCadastrarServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCadastrarServicoActionPerformed(evt);
            }
        });
        jmCadastrar.add(jmCadastrarServico);
        jmCadastrar.add(jSeparator7);

        jmCadastrarOrcamento.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmCadastrarOrcamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/edit file_16x16.png"))); // NOI18N
        jmCadastrarOrcamento.setText("Orçamentos");
        jmCadastrarOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCadastrarOrcamentoActionPerformed(evt);
            }
        });
        jmCadastrar.add(jmCadastrarOrcamento);

        jMenuBar1.add(jmCadastrar);

        jmPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/buscar.png"))); // NOI18N
        jmPesquisar.setText("Pesquisar   ");
        jmPesquisar.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N

        jmPesquisarClientes.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmPesquisarClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/find_16x16.png"))); // NOI18N
        jmPesquisarClientes.setText("Clientes");
        jmPesquisarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmPesquisarClientesActionPerformed(evt);
            }
        });
        jmPesquisar.add(jmPesquisarClientes);
        jmPesquisar.add(jSeparator8);

        jmPesquisarServicos.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmPesquisarServicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/find_16x16.png"))); // NOI18N
        jmPesquisarServicos.setText("Serviços");
        jmPesquisarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmPesquisarServicosActionPerformed(evt);
            }
        });
        jmPesquisar.add(jmPesquisarServicos);
        jmPesquisar.add(jSeparator9);

        jmPesquisarOrcamentos.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmPesquisarOrcamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/zoom+.png"))); // NOI18N
        jmPesquisarOrcamentos.setText("Orçamentos");
        jmPesquisarOrcamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmPesquisarOrcamentosActionPerformed(evt);
            }
        });
        jmPesquisar.add(jmPesquisarOrcamentos);

        jMenuBar1.add(jmPesquisar);

        jmRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/rel_ico.png"))); // NOI18N
        jmRelatorio.setText("Relatório   ");
        jmRelatorio.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N

        jmRelatorioPeriodo.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmRelatorioPeriodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/rel_ico.png"))); // NOI18N
        jmRelatorioPeriodo.setText("Relatório por Período");
        jmRelatorioPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmRelatorioPeriodoActionPerformed(evt);
            }
        });
        jmRelatorio.add(jmRelatorioPeriodo);
        jmRelatorio.add(jSeparator3);

        jmRelatorioCliente.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmRelatorioCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/rel_ico.png"))); // NOI18N
        jmRelatorioCliente.setText("Relatório por Cliente");
        jmRelatorioCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmRelatorioClienteActionPerformed(evt);
            }
        });
        jmRelatorio.add(jmRelatorioCliente);
        jmRelatorio.add(jSeparator4);

        jMenuBar1.add(jmRelatorio);

        jmGerenciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/vist_ico.png"))); // NOI18N
        jmGerenciar.setText("Gerenciar");
        jmGerenciar.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N

        jmGerenciarUsuario.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmGerenciarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/efetivo_ico.png"))); // NOI18N
        jmGerenciarUsuario.setText("Usuários");
        jmGerenciarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmGerenciarUsuarioActionPerformed(evt);
            }
        });
        jmGerenciar.add(jmGerenciarUsuario);
        jmGerenciar.add(jSeparator2);

        jmGerenciarServicos.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jmGerenciarServicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/truck.png"))); // NOI18N
        jmGerenciarServicos.setText("Serviços");
        jmGerenciarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmGerenciarServicosActionPerformed(evt);
            }
        });
        jmGerenciar.add(jmGerenciarServicos);

        jMenuBar1.add(jmGerenciar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlData, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlData)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSairActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jmSairActionPerformed

    private void jmCadastrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCadastrarClienteActionPerformed
new jfCadastroClientes(conexao, this).show(true);
    }//GEN-LAST:event_jmCadastrarClienteActionPerformed

    private void jmGerenciarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmGerenciarUsuarioActionPerformed

    }//GEN-LAST:event_jmGerenciarUsuarioActionPerformed

    private void jmGerenciarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmGerenciarServicosActionPerformed

    }//GEN-LAST:event_jmGerenciarServicosActionPerformed

    private void jmPesquisarOrcamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmPesquisarOrcamentosActionPerformed

    }//GEN-LAST:event_jmPesquisarOrcamentosActionPerformed

    private void jmPesquisarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmPesquisarClientesActionPerformed
new jfPesquisarClientes(conexao, this).show(true);
    }//GEN-LAST:event_jmPesquisarClientesActionPerformed

    private void jmRelatorioPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRelatorioPeriodoActionPerformed

    }//GEN-LAST:event_jmRelatorioPeriodoActionPerformed

    private void jmRelatorioClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRelatorioClienteActionPerformed

    }//GEN-LAST:event_jmRelatorioClienteActionPerformed

    private void jmCadastrarServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCadastrarServicoActionPerformed
new jfCadastroServico(conexao,this).show(true);
    }//GEN-LAST:event_jmCadastrarServicoActionPerformed

    private void jmCadastrarOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCadastrarOrcamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmCadastrarOrcamentoActionPerformed

    private void jmPesquisarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmPesquisarServicosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmPesquisarServicosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JLabel jlData;
    private javax.swing.JMenu jmCadastrar;
    private javax.swing.JMenuItem jmCadastrarCliente;
    private javax.swing.JMenuItem jmCadastrarOrcamento;
    private javax.swing.JMenuItem jmCadastrarServico;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmGerenciar;
    private javax.swing.JMenuItem jmGerenciarServicos;
    private javax.swing.JMenuItem jmGerenciarUsuario;
    private javax.swing.JMenu jmPesquisar;
    private javax.swing.JMenuItem jmPesquisarClientes;
    private javax.swing.JMenuItem jmPesquisarOrcamentos;
    private javax.swing.JMenuItem jmPesquisarServicos;
    private javax.swing.JMenu jmRelatorio;
    private javax.swing.JMenuItem jmRelatorioCliente;
    private javax.swing.JMenuItem jmRelatorioPeriodo;
    private javax.swing.JMenuItem jmSair;
    // End of variables declaration//GEN-END:variables
}
