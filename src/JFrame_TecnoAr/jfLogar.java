/**
 *
 *  Esta classe serve para Logar o Uusuario do Banco de dados,
 *  este login eh feito somente uma vez, esta conexao sera passada
 *  como parametro para as outras classes.
 * @author Arione
 */
package JFrame_TecnoAr;

import Conexao.Conexao;
import Log.arquivoLog;
import Threads.splash;
import Uteis.Data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class jfLogar extends javax.swing.JFrame
{
    private String User;
    private String pwd;
    private String Url;
    private Conexao conexao;/*Conexao ao banco de dados*/
    public jfLogar()
    {
        initComponents();
        jbProgressLogar.setVisible(false);
        jbProgressLogar.setStringPainted(true);
        conexao = new Conexao();
    }
    private void goConexao()
    {
        jbLogar.setEnabled(true);
        jbProgressLogar.setString("Conectando...");
        jbProgressLogar.setIndeterminate(true);
        jbProgressLogar.setVisible(true);
        setUser(jtNomeLogar.getText());
        setPwd(jpSenhaLogar.getText());
        new Thread()
        {
            public void run()
            {
                jtNomeLogar.setEnabled(false);
                jpSenhaLogar.setEnabled(false);
                boolean entrou = false;
//                if(conexao.conecta("localhost","guedes_tecnoar","semente"))
                if(conexao.conecta("arioneguedes.com.br","guedes_tecnoar","semente"))
                {    
                    conexao.executeSQL("SELECT * FROM guedes_tecnoar.usuario");
                    try
                        
                    {
                        conexao.resultset.first();
                        do
                        {
                            if(jtNomeLogar.getText().equals(conexao.resultset.getString("usuario_login")))
                            {
                                entrou = true;
                                if(jpSenhaLogar.getText().equals(conexao.resultset.getString("usuario_senha")))
                                {
                                    jbProgressLogar.setIndeterminate(false);
                                    int i=3;
                                    while(i>=0)
                                    {
                                        try{sleep(600);}catch(InterruptedException err){}
                                        jbProgressLogar.setString("Sucesso... "+i);
                                        i--;
                                    }
//                                    String SqlInsert = "insert into bombeiro_19.acessos (usuario_login,operacao ) VALUES ('"+jtNomeLogar.getText()+"','Logou no sistema')";
//                                    conexao.executeSQL("select * from bombeiro_19.acessos");
//                                    conexao.statement.executeUpdate(SqlInsert);
                                    try
                                    {
                                        File logado = new File("log.temp");
                                        FileWriter fileWriter = new FileWriter(logado);
                                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                        bufferedWriter.write(""+jtNomeLogar.getText());
                                        bufferedWriter.close();
                                    }
                                    catch(Exception ex)
                                    {
                                        new arquivoLog(ex.getMessage());
                                    }
                                    splash.i=73;
                                    splash.conexao=conexao;
                                    splash.im.getContentPane().getComponent(2).setVisible(false);
                                    splash.im.texto.setText("Sucesso na Conexão... Aguarde!");
                                    String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  LOGOU COM SUCESSO: "+jtNomeLogar.getText();
                                    new arquivoLog(log);
                                    this.interrupt();
                                }
                                else
                                {
                                    jtNomeLogar.setEnabled(true);
                                    jpSenhaLogar.setEnabled(true);
                                    jbProgressLogar.setString("Senha não confere");
                                    jpSenhaLogar.selectAll();
                                    jpSenhaLogar.requestFocus(true);
                                    jbLogar.setEnabled(true);
                                    this.interrupt();
                                }
                            }
                        }while(conexao.resultset.next());
                        jtNomeLogar.setEnabled(true);
                        jpSenhaLogar.setEnabled(true);
                        if(!entrou)
                        {
                            jbProgressLogar.setString("Usuário não confere");
                            jtNomeLogar.selectAll();
                            jtNomeLogar.requestFocus(true);
                        }
                        jbLogar.setEnabled(true);
                        this.interrupt();
                    }
                    catch (SQLException ex)
                    {
                        new arquivoLog(ex.getMessage());
                    }
                    //conexao.resultset.getString("vistoriantes_nome");
                }
                else
                {
                    jtNomeLogar.setEnabled(true);
                    jpSenhaLogar.setEnabled(true);
                    jbProgressLogar.setString("CONEXÃO INVÁLIDA");
                    jbLogar.setEnabled(true);
                    splash.i=72;
                    this.interrupt();
                }
                    
            }
        }.start();
    }
    private void setUser(String us){User = us;}
    private void setPwd(String p){pwd = p;}
    private void setUrl(String u){Url =u;}
    public String getUser(){return User;}
    public String getPwd(){return pwd;}
    public String getUrl(){return Url;}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbLogar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jlLoginName = new javax.swing.JLabel();
        jbProgressLogar = new javax.swing.JProgressBar();
        jpSenhaLogar = new javax.swing.JPasswordField();
        jtNomeLogar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Coxim Emergência");
        setAlwaysOnTop(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jbLogar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/user.png"))); // NOI18N
        jbLogar.setText("Logar");
        jbLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLogarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Senha.:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Usuário.:");

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        jlLoginName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlLoginName.setForeground(new java.awt.Color(255, 255, 255));
        jlLoginName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlLoginName.setText("Acesso ao Banco de Dados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlLoginName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jlLoginName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jbProgressLogar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jbProgressLogar.setForeground(new java.awt.Color(0, 0, 0));

        jpSenhaLogar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jpSenhaLogar.setForeground(new java.awt.Color(51, 0, 0));
        jpSenhaLogar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jpSenhaLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpSenhaLogarActionPerformed(evt);
            }
        });

        jtNomeLogar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jtNomeLogar.setForeground(new java.awt.Color(51, 0, 0));
        jtNomeLogar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtNomeLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNomeLogarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel2))
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jpSenhaLogar)
                                    .addComponent(jtNomeLogar)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jbProgressLogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbLogar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtNomeLogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jpSenhaLogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbProgressLogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbLogar, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbLogarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbLogarActionPerformed
    {//GEN-HEADEREND:event_jbLogarActionPerformed
goConexao();
    }//GEN-LAST:event_jbLogarActionPerformed

    private void jpSenhaLogarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jpSenhaLogarActionPerformed
    {//GEN-HEADEREND:event_jpSenhaLogarActionPerformed
goConexao();
    }//GEN-LAST:event_jpSenhaLogarActionPerformed

    private void jtNomeLogarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtNomeLogarActionPerformed
    {//GEN-HEADEREND:event_jtNomeLogarActionPerformed
jpSenhaLogar.requestFocus(true);
    }//GEN-LAST:event_jtNomeLogarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbLogar;
    private javax.swing.JProgressBar jbProgressLogar;
    private javax.swing.JLabel jlLoginName;
    private javax.swing.JPasswordField jpSenhaLogar;
    javax.swing.JTextField jtNomeLogar;
    // End of variables declaration//GEN-END:variables

}
