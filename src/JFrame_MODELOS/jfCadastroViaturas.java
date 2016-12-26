/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame_MODELOS;

import JFrame_TecnoAr.Main;
import Conexao.Conexao;
import Log.arquivoLog;
import Tabelas_MODELOS.JTableCadastroViaturas;
import Uteis.Data;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Guedes
 */
public class jfCadastroViaturas extends javax.swing.JFrame
{

    Conexao conexao = new Conexao();
    Data date = new Data();
    Main app;
    boolean alterarRegistro;
    int indice;
    public jfCadastroViaturas(Conexao con,Main ap)
    {
        this.conexao = con;
        this.app = ap;
        this.alterarRegistro = false;
        this.indice = 0;
        initComponents();
        limparCamposCadastroViaturas();
        this.setLocationRelativeTo(null);
    }
    public void alterarViatura(int id,String nome,String placa,String cartao)
    {
        alterarRegistro = true;
        indice = id;
        jtViaturaNome.setText(nome);
        jtViaturaPlaca.setText(placa);
        jtViaturaNumeroCartao.setText(cartao);
    }
    private void salvarViatura()
    {
        String nome = jtViaturaNome.getText().toUpperCase();
        String placa = jtViaturaPlaca.getText();
        String cartao = jtViaturaNumeroCartao.getText();
        try
        {
            if(alterarRegistro)
            {
                String sqlUpdate = "UPDATE bombeiro_19.viaturas SET "
                            + "nome_viatura='" + nome + "', "
                            + "placa='" + placa + "', "
                            + "cartao='" + cartao + "' "
                            + "WHERE  id_viatura = " + indice;
                    conexao.executeSQL("select * from bombeiro_19.viaturas ORDER BY nome_viatura ASC limit 10");
                    conexao.statement.executeUpdate(sqlUpdate);
            }
            else
            {
                conexao.executeSQL("SELECT * FROM bombeiro_19.viaturas ORDER BY nome_viatura ASC");
                conexao.resultset.first();
                int compare = 0;
                int idd = 0;
                if (conexao.resultset.getRow() > 0)
                {
                    do
                    {
                        if (nome.compareToIgnoreCase(conexao.resultset.getString("nome_viatura")) == 0)
                        {
                            idd = conexao.resultset.getInt("id_viatura");
                            compare = 1;
                            break;
                        }

                    } while (conexao.resultset.next());
                }
                if (compare == 0)
                {
                    String sqlInsert = "INSERT INTO bombeiro_19.viaturas "
                            + "(nome_viatura,placa,cartao) VALUES "
                            + "('" + nome + "','" + placa + "','" + cartao + "')";
                    conexao.statement.executeUpdate(sqlInsert);
                } else
                {
                    String sqlUpdate = "UPDATE bombeiro_19.viaturas SET "
                            + "nome_viatura='" + nome + "', "
                            + "placa='" + placa + "', "
                            + "cartao='" + cartao + "' "
                            + "WHERE  id_viatura = " + idd;
                    conexao.executeSQL("select * from bombeiro_19.viaturas ORDER BY nome_viatura ASC limit 10");
                    conexao.statement.executeUpdate(sqlUpdate);
                }
            }
            limparCamposCadastroViaturas();
        } catch (SQLException er)
        {
            String log = "|" + new Data().getDataF() + " ás " + new Data().getHora() + "|  SQLException: " + er.getMessage();
            new arquivoLog(log);
        } catch (NumberFormatException nfe)
        {
            String log = "|" + new Data().getDataF() + " ás " + new Data().getHora() + "|  NumberFormatException: " + nfe.getMessage();
            new arquivoLog(log);
        } catch (Exception ex)
        {
            String log = "|" + new Data().getDataF() + " ás " + new Data().getHora() + "|  Exception: " + ex.getMessage();
            new arquivoLog(log);
        }
    }
    
    public void limparCamposCadastroViaturas()
    {
        alterarRegistro = false;
        indice = 0;
        jtViaturaNome.setText("");
        jtViaturaPlaca.setText("");
        jtViaturaNumeroCartao.setText("");
        jsListarViaturas.setViewportView(new JTableCadastroViaturas(conexao, this).getTabela());
    }

    private boolean validaViatura()
    {
        if (jtViaturaNome.getText().length() < 3)
        {
            jtViaturaNome.requestFocus(true);
            jtViaturaNome.selectAll();
            jlstatusViatura.setText("Minimo 3");
            return false;
        }
        if (jtViaturaPlaca.getText().length() < 3)
        {
            jtViaturaPlaca.requestFocus(true);
            jtViaturaPlaca.selectAll();
            jlstatusViatura.setText("Minimo 7");
            return false;
        }
        if (jtViaturaNumeroCartao.getText().length() < 5)
        {
            jtViaturaNumeroCartao.requestFocus();
            jtViaturaNumeroCartao.selectAll();
            jlstatusViatura.setText("Minimo 5");
            return false;
        }
        jlstatusViatura.setText(" ");
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpCadastrarViaturas = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtViaturaNome = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtViaturaPlaca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtViaturaNumeroCartao = new javax.swing.JTextField();
        jToolBar2 = new javax.swing.JToolBar();
        jbSalvarViatura = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jsListarViaturas = new javax.swing.JScrollPane();
        jlstatusViatura = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Viaturas");
        setResizable(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Nome Viatura");

        jtViaturaNome.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtViaturaNome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtViaturaNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtViaturaNomeActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Placa");

        jtViaturaPlaca.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtViaturaPlaca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtViaturaPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtViaturaPlacaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Nº Cartão Abastecimento");

        jtViaturaNumeroCartao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtViaturaNumeroCartao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtViaturaNumeroCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtViaturaNumeroCartaoActionPerformed(evt);
            }
        });

        jToolBar2.setFloatable(false);

        jbSalvarViatura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/save.png"))); // NOI18N
        jbSalvarViatura.setFocusable(false);
        jbSalvarViatura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbSalvarViatura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbSalvarViatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarViaturaActionPerformed(evt);
            }
        });
        jToolBar2.add(jbSalvarViatura);

        jLabel9.setText("   ");
        jToolBar2.add(jLabel9);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cancel.png"))); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        jlstatusViatura.setText(" ");

        javax.swing.GroupLayout jpCadastrarViaturasLayout = new javax.swing.GroupLayout(jpCadastrarViaturas);
        jpCadastrarViaturas.setLayout(jpCadastrarViaturasLayout);
        jpCadastrarViaturasLayout.setHorizontalGroup(
            jpCadastrarViaturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastrarViaturasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastrarViaturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtViaturaNumeroCartao)
                    .addComponent(jtViaturaPlaca)
                    .addComponent(jtViaturaNome)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCadastrarViaturasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlstatusViatura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpCadastrarViaturasLayout.createSequentialGroup()
                        .addGroup(jpCadastrarViaturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jsListarViaturas, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpCadastrarViaturasLayout.setVerticalGroup(
            jpCadastrarViaturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastrarViaturasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastrarViaturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsListarViaturas)
                    .addGroup(jpCadastrarViaturasLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtViaturaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtViaturaPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtViaturaNumeroCartao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jlstatusViatura)
                        .addGap(0, 226, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 821, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jpCadastrarViaturas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jpCadastrarViaturas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtViaturaNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtViaturaNomeActionPerformed
        jtViaturaNome.setText(jtViaturaNome.getText().toUpperCase());
        jtViaturaPlaca.requestFocus(true);
    }//GEN-LAST:event_jtViaturaNomeActionPerformed

    private void jtViaturaPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtViaturaPlacaActionPerformed
        jtViaturaPlaca.setText(jtViaturaPlaca.getText().toUpperCase());
        jtViaturaNumeroCartao.requestFocus(true);// TODO add your handling code here:
    }//GEN-LAST:event_jtViaturaPlacaActionPerformed

    private void jtViaturaNumeroCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtViaturaNumeroCartaoActionPerformed
        jbSalvarViatura.requestFocus(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jtViaturaNumeroCartaoActionPerformed

    private void jbSalvarViaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarViaturaActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Deseja SALVAR?", "SALVAR REGISTRO", JOptionPane.YES_NO_OPTION) == 0)
        {
            if (validaViatura())
            {
                salvarViatura();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jbSalvarViaturaActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Deseja limpar os campos?", "Limpar Campos", JOptionPane.YES_NO_OPTION) == 0)
        {
            limparCamposCadastroViaturas();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(jfCadastroViaturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(jfCadastroViaturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(jfCadastroViaturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(jfCadastroViaturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new jfCadastroViaturas(null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton jbSalvarViatura;
    private javax.swing.JLabel jlstatusViatura;
    private javax.swing.JPanel jpCadastrarViaturas;
    private javax.swing.JScrollPane jsListarViaturas;
    private javax.swing.JTextField jtViaturaNome;
    private javax.swing.JTextField jtViaturaNumeroCartao;
    private javax.swing.JTextField jtViaturaPlaca;
    // End of variables declaration//GEN-END:variables
}
