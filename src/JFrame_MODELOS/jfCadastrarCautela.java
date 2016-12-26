/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JFrame_MODELOS;

import Conexao.Conexao;
import Log.arquivoLog;
import Tabelas_MODELOS.JTableCadastroCautela;
import Uteis.Data;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Guedes
 */
public class jfCadastrarCautela extends javax.swing.JFrame 
{

    /**
     * Creates new form jfCadastrarCautela
     */
    pesquisarUsuario user;
    private Conexao conexao;
    int [] materiais;
    private JTableCadastroCautela tabela;
    String dataDoDia = "";
    private Date dataAtual;
    public jfCadastrarCautela(Conexao con,int [] selecao) 
    {
        this.conexao = con;
        this.materiais = selecao;
        user = new pesquisarUsuario(conexao);
        initComponents();
        dataAtual = jxDataDevolver.getDate();
        limparCampos();
//        System.out.println("Material 1: "+materiais[0]);
        tabela = new JTableCadastroCautela(conexao, materiais);
        jScrollPane1.setViewportView(tabela.getTabela());
    }
    private void limparCampos()
    {
        jTextField1.setText(user.nomeUsuario);
        jxDataDevolver.setDate(dataAtual);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jtBuscaNome = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jbCadastrarCautela = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jxDataDevolver = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jlStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cautela de Material");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cautela de Materiais - Equipamentos"));

        jTextField1.setEditable(false);
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder("Adjunto"));

        jtBuscaNome.setToolTipText("digite nome e pressione enter");
        jtBuscaNome.setBorder(javax.swing.BorderFactory.createTitledBorder("Solicitação de Cautela"));
        jtBuscaNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtBuscaNomeActionPerformed(evt);
            }
        });

        jbCadastrarCautela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/save.png"))); // NOI18N
        jbCadastrarCautela.setText("Cadastrar");
        jbCadastrarCautela.setFocusable(false);
        jbCadastrarCautela.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbCadastrarCautela.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbCadastrarCautela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCadastrarCautelaActionPerformed(evt);
            }
        });

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cancel.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.setFocusable(false);
        jbCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jxDataDevolver.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jxDataDevolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jxDataDevolverActionPerformed(evt);
            }
        });

        jLabel1.setText("Devolver no dia:");

        jlStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlStatus.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1)
                    .addComponent(jtBuscaNome, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jxDataDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                        .addComponent(jbCadastrarCautela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar))
                    .addComponent(jlStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtBuscaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCadastrarCautela, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jxDataDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

    private void jtBuscaNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtBuscaNomeActionPerformed
buscarMilitar(jtBuscaNome.getText());
    }//GEN-LAST:event_jtBuscaNomeActionPerformed

    private void jbCadastrarCautelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCadastrarCautelaActionPerformed
if(verificaCampos())
    tabela.gerarDocumento(jtBuscaNome.getText(),dataDoDia);
    }//GEN-LAST:event_jbCadastrarCautelaActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Deseja LIMPAR TODOS OS CAMPOS?","Livro do Condutor Operador de Viaturas",JOptionPane.YES_NO_OPTION)==0)
        dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jxDataDevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jxDataDevolverActionPerformed
dataDoDia = new Uteis.Data().getDataAssuncao(jxDataDevolver.getDate());

    }//GEN-LAST:event_jxDataDevolverActionPerformed
private boolean verificaCampos()
{
    if(jtBuscaNome.getText().length()<5)
    {
        jtBuscaNome.requestFocus(true);
        jtBuscaNome.setText("Digite nome e pressione enter");
        jtBuscaNome.selectAll();
        return false;
    }
    try
    {
        Date dataLivro;
        String auxDataAdjunto="";
        dataLivro = jxDataDevolver.getDate();
        auxDataAdjunto = new Uteis.Data().getData(dataLivro);
    }
    catch(NullPointerException npe1)
    {
        jxDataDevolver.requestFocus(); 
        return false;
    }
    for(int i=0;i<tabela.getTabela().getRowCount();i++)
    {
        try
        {
            if(Integer.parseInt(tabela.getTabela().getValueAt(i,1).toString())<0)
            {
                 jlStatus.setText("Número inválido");
                return false;
            }
        }
        catch(NumberFormatException nfe)
        {
            jlStatus.setText("Número inválido");
            return false;
        }
    }
    return true;
}
private void buscarMilitar(String nome)
{
    conexao.executeSQL("SELECT * FROM bombeiro_19.militar WHERE nome LIKE '%"+nome+"%'");
        try
        {
            conexao.resultset.first();
            jtBuscaNome.setText(conexao.resultset.getString("nome"));
            jtBuscaNome.selectAll();
            jtBuscaNome.requestFocus(true);
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
//        this.pack();
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbCadastrarCautela;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JLabel jlStatus;
    private javax.swing.JTextField jtBuscaNome;
    private org.jdesktop.swingx.JXDatePicker jxDataDevolver;
    // End of variables declaration//GEN-END:variables

}