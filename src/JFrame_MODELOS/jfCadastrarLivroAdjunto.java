/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame_MODELOS;

import JFrame_TecnoAr.Main;
import Conexao.Conexao;
import Log.arquivoLog;
import Uteis.Data;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Guedes
 */
public class jfCadastrarLivroAdjunto extends javax.swing.JFrame {

    /**
     * Creates new form jfCadastrarLivro
     */
    Conexao conexao;
    pesquisarUsuario user;
    Data date = new Data();
    private Date dataAtual;
    Main appMain;
    private boolean alterarLivro;
    private int indice;
    public jfCadastrarLivroAdjunto(Conexao con, Main app) 
    {
        this.conexao = con;
        this.appMain = app;
        this.alterarLivro = false;
        this.indice = 0;
        user = new pesquisarUsuario(conexao);
        initComponents();
        //Hora.start();
        dataAtual = jxEscalaData.getDate();
        limparTela();
        this.jScrollPane1.getVerticalScrollBar().setUnitIncrement(50);//rolagem rapida 
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/Imagens/Query.png")));
        //setExtendedState(MAXIMIZED_BOTH);
    }
    public static void main(String args[]) 
    {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                Conexao conect = new Conexao();
                conect.conecta("localhost","root","semente");
                new jfCadastrarLivroAdjunto(conect,null).setVisible(true);
            }
        });
    }
    public void limparTela()
    {
        alterarLivro = false;
        indice = 0;
        jxEscalaData.setDate(dataAtual);
        jtPostoOficial.setText("");
        jtGuerraOficial.setText("");
        jtPostoAdjunto.setText("");
        jtGuerraAdjunto.setText("");
        jtPostoOpCom.setText("");
        jtGuerraOpComuni.setText("");
        jtPostoAuxiliarABR.setText("");
        jtGuerraAuxiliarABR.setText("");
        jtPostoCmtABR.setText("");
        jtGuerraCmtABR.setText("");
        jtPostoCondutorABR.setText("");
        jtGuerraCondutorABR.setText("");
        jtPostoCmtABT.setText("");
        jtGuerraCmtABT.setText("");
        jtPostoAuxiliarABT.setText("");
        jtGuerraAuxiliarABT.setText("");
        jtPostoCondutorABT.setText("");
        jtGuerraCondutorABT.setText("");
        jtPostoCmtUR.setText("");
        jtGuerraCmtUR.setText("");
        jtPostoAuxiliarUR.setText("");
        jtGuerraAuxiliarUR.setText("");
        jtPostoCondutorUR.setText("");
        jtGuerraCondutorUR.setText("");
        jtPostoCondutorAS.setText("");
        jtGuerraCondutorAS.setText("");
        jtPostoGuarnicaoAS.setText("");
        jtGuerraGuarnicaoAS.setText("");
        jtaAlteracaoPessoal.setText("SEM ALTERAÇÃO");
        jtaAlteracaoMaterial.setText("SEM ALTERAÇÃO");
        jfLeituraAguaFinal.setValue(0);
        jfLeituraAguaConsumo.setValue(0);
        jfLeituraEnergiaFinal.setValue(0);
        jfLeituraEnergiaConsumo.setValue(0);
        jtAlteracaoInstalacao.setText("");
        jtaInstrucao.setText("SEM ALTERAÇÃO");
        jtaDocumentosEquEtc.setText("SEM ALTERAÇÃO");
        jtOcorrenciasSigoSalvamento.setText("");
        jtOcorrenciasOcSalvamento.setValue(0);
        jfOcorrenciasSalvNaoFatal.setValue(0);
        jfOcorrenciasSalvFatal.setValue(0);
        jtOcorrenciasSigoIncendio.setText("");
        jtOcorrenciasOcIncendio.setValue(0);
        jfOcorrencioIncNaoFatal.setValue(0);
        jfOcorrencioIncNaoFatal.setValue(0);
        jtOcorrenciasSigoPalestra.setText("");
        jtOcorrenciasOcPalestra.setValue(0);
        jtOcorrenciasSigoPalestraNaoFatal.setValue(0);
        jtOcorrenciasSigoPalestraFatal.setValue(0);
        jtOcorrenciasSigoAuxilio.setText("");
        jtOcorrenciasOcAuxilio.setValue(0);
        jtOcorrenciasSigoAuxilioNaoFatal.setValue(0);
        jtOcorrenciasSigoAuxilioFatal.setValue(0);
        jlStatus.setText("");
        assuncaoServico();
        leiturasIniciais();
        passagemServico();
        jlData.setText(date.getData()+", "+date.getHora()+"hs");
    }
    private Thread Hora = new Thread()
    {
        @Override
        public void run()
        {
          try
          {
            while(true)
            {
                jlData.setText(date.getData()+", "+date.getHora()+"hs");
            }
          }catch(Exception s){}
        }
    };
     private void assuncaoServico()
     {
         jtaAssuncao.setText("Eu, "+user.graduacaoUsuario+" "+user.guerraUsuario+", assumi o serviço na função de Adjunto "
                 + "ao Oficial de Sobreaviso, neste dia, com todas as ordens em vigor e alterações constantes em livro");
         jtPostoAdjunto.setText(user.graduacaoUsuario);
         jtGuerraAdjunto.setText(user.guerraUsuario);
     }
     private void passagemServico()
     {
         jtaPassagemServico.setText("Fiz ao meu Substituto legal, o XXXXXXXX , onde transmiti todas as"
                 + " ordens em vigor e alterações constantes em livro.");
         jlNomeCompleto.setText(user.nomeUsuario);
         jlMatricula.setText("Matrícula: "+user.matriculaUsuario);
     }
     private String pGraduacao(String guerra)
     {
         try
         {
             conexao.executeSQL("SELECT * FROM bombeiro_19.militar WHERE nome_guerra LIKE '"+guerra+"' limit 1");
             conexao.resultset.first();
             return conexao.resultset.getString("graduacao");
         }
         catch (SQLException sqle)
         {
             new arquivoLog(sqle.getMessage());
             return "XXX";
         }
     }
     private void leiturasIniciais()
     {
         try
         {
             conexao.executeSQL("SELECT * FROM bombeiro_19.livroadjunto ORDER BY id_livro_adjunto DESC LIMIT 1");
             conexao.resultset.first();
             jfLeituraAguaInicial.setValue(conexao.resultset.getObject("leitura_agua_final"));
             jfLeituraEnergiaInicial.setValue(conexao.resultset.getObject("leitura_energia_final"));
         }
         catch (SQLException sqle)
         {
             new arquivoLog(sqle.getMessage());
             jfLeituraAguaInicial.setValue(0);
             jfLeituraEnergiaInicial.setValue(0);
         }
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaAssuncao = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jtPostoOficial = new javax.swing.JTextField();
        jtGuerraOficial = new javax.swing.JTextField();
        jtPostoAdjunto = new javax.swing.JTextField();
        jtGuerraAdjunto = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jtPostoOpCom = new javax.swing.JTextField();
        jtGuerraOpComuni = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jtPostoAuxiliarUR = new javax.swing.JTextField();
        jtPostoCondutorUR = new javax.swing.JTextField();
        jtPostoCmtUR = new javax.swing.JTextField();
        jtGuerraCmtUR = new javax.swing.JTextField();
        jtGuerraCondutorUR = new javax.swing.JTextField();
        jtGuerraAuxiliarUR = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jtPostoAuxiliarABT = new javax.swing.JTextField();
        jtPostoCondutorABT = new javax.swing.JTextField();
        jtPostoCmtABT = new javax.swing.JTextField();
        jtGuerraCmtABT = new javax.swing.JTextField();
        jtGuerraCondutorABT = new javax.swing.JTextField();
        jtGuerraAuxiliarABT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jtPostoAuxiliarABR = new javax.swing.JTextField();
        jtPostoCondutorABR = new javax.swing.JTextField();
        jtPostoCmtABR = new javax.swing.JTextField();
        jtGuerraCmtABR = new javax.swing.JTextField();
        jtGuerraCondutorABR = new javax.swing.JTextField();
        jtGuerraAuxiliarABR = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jtPostoGuarnicaoAS = new javax.swing.JTextField();
        jtPostoCondutorAS = new javax.swing.JTextField();
        jtGuerraCondutorAS = new javax.swing.JTextField();
        jtGuerraGuarnicaoAS = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtaAlteracaoPessoal = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtaAlteracaoMaterial = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jfLeituraAguaInicial = new javax.swing.JFormattedTextField();
        jtGuerraCmtABR3 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jfLeituraAguaFinal = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jfLeituraAguaConsumo = new javax.swing.JFormattedTextField();
        jtGuerraCmtABR4 = new javax.swing.JTextField();
        jfLeituraEnergiaFinal = new javax.swing.JFormattedTextField();
        jfLeituraEnergiaConsumo = new javax.swing.JFormattedTextField();
        jfLeituraEnergiaInicial = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jtAlteracaoInstalacao = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtaInstrucao = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtaDocumentosEquEtc = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jtOcorrenciasSigoSalvamento = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jtOcorrenciasOcSalvamento = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jfOcorrenciasSalvNaoFatal = new javax.swing.JFormattedTextField();
        jtOcorrenciasSigoIncendio = new javax.swing.JTextField();
        jtOcorrenciasOcIncendio = new javax.swing.JFormattedTextField();
        jfOcorrencioIncNaoFatal = new javax.swing.JFormattedTextField();
        jfOcorrencioIncFatal = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jfOcorrenciasSalvFatal = new javax.swing.JFormattedTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jtOcorrenciasOcAuxilio = new javax.swing.JFormattedTextField();
        jtOcorrenciasSigoAuxilioNaoFatal = new javax.swing.JFormattedTextField();
        jtOcorrenciasSigoAuxilioFatal = new javax.swing.JFormattedTextField();
        jtOcorrenciasSigoPalestraFatal = new javax.swing.JFormattedTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jtOcorrenciasSigoPalestra = new javax.swing.JTextField();
        jtOcorrenciasOcPalestra = new javax.swing.JFormattedTextField();
        jtOcorrenciasSigoPalestraNaoFatal = new javax.swing.JFormattedTextField();
        jtOcorrenciasSigoAuxilio = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtaPassagemServico = new javax.swing.JTextArea();
        jlNomeCompleto = new javax.swing.JLabel();
        jlMatricula = new javax.swing.JLabel();
        jlPostoAdjunto = new javax.swing.JLabel();
        jlData = new javax.swing.JLabel();
        jxEscalaData = new org.jdesktop.swingx.JXDatePicker();
        jToolBar1 = new javax.swing.JToolBar();
        jbSalvarLivroAdjunto = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jbCancelarLivroAdjunto = new javax.swing.JButton();
        jlStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar livro do Adjunto ao Oficial de Sobreaviso");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Livro de Alterações Diárias de Serviço");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("I - ASSUNÇÃO DO SERVIÇO"));

        jtaAssuncao.setColumns(20);
        jtaAssuncao.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        jtaAssuncao.setLineWrap(true);
        jtaAssuncao.setRows(3);
        jtaAssuncao.setEnabled(false);
        jScrollPane2.setViewportView(jtaAssuncao);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("II - ESCALA DE SERVIÇO"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("FUNÇÃO");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("POSTO/GRAD.");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("MILITAR (nome de Guerra)");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField1.setText("Oficial de Sobreaviso");
        jTextField1.setEnabled(false);

        jtPostoOficial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoOficial.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoOficial.setEnabled(false);

        jtGuerraOficial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraOficialActionPerformed(evt);
            }
        });

        jtPostoAdjunto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoAdjunto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoAdjunto.setEnabled(false);

        jtGuerraAdjunto.setEnabled(false);

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField6.setText("Adjunto ao Oficial de Sobreaviso");
        jTextField6.setEnabled(false);

        jtPostoOpCom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoOpCom.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoOpCom.setEnabled(false);

        jtGuerraOpComuni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraOpComuniActionPerformed(evt);
            }
        });

        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField9.setText("Op. de Comunicação");
        jTextField9.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("UR - 59");

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField2.setText("Comandante de Guarnição");
        jTextField2.setEnabled(false);

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField7.setText("Condutor Operador de Viatura");
        jTextField7.setEnabled(false);

        jTextField10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField10.setText("Auxiliar de saúde");
        jTextField10.setEnabled(false);

        jtPostoAuxiliarUR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoAuxiliarUR.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoAuxiliarUR.setEnabled(false);

        jtPostoCondutorUR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoCondutorUR.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoCondutorUR.setEnabled(false);

        jtPostoCmtUR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoCmtUR.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoCmtUR.setEnabled(false);

        jtGuerraCmtUR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCmtURActionPerformed(evt);
            }
        });

        jtGuerraCondutorUR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCondutorURActionPerformed(evt);
            }
        });

        jtGuerraAuxiliarUR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraAuxiliarURActionPerformed(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField3.setText("Comandante de Guarnição");
        jTextField3.setEnabled(false);

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField8.setText("Condutor Operador de Viatura");
        jTextField8.setEnabled(false);

        jTextField11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField11.setText("Auxiliar de linha");
        jTextField11.setEnabled(false);

        jtPostoAuxiliarABT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoAuxiliarABT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoAuxiliarABT.setEnabled(false);

        jtPostoCondutorABT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoCondutorABT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoCondutorABT.setEnabled(false);

        jtPostoCmtABT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoCmtABT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoCmtABT.setEnabled(false);

        jtGuerraCmtABT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCmtABTActionPerformed(evt);
            }
        });

        jtGuerraCondutorABT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCondutorABTActionPerformed(evt);
            }
        });

        jtGuerraAuxiliarABT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraAuxiliarABTActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("ABT - 29");

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField4.setText("Comandante de Guarnição");
        jTextField4.setEnabled(false);

        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField12.setText("Condutor Operador de Viatura");
        jTextField12.setEnabled(false);

        jTextField13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField13.setText("Auxiliar de linha");
        jTextField13.setEnabled(false);

        jtPostoAuxiliarABR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoAuxiliarABR.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoAuxiliarABR.setEnabled(false);

        jtPostoCondutorABR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoCondutorABR.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoCondutorABR.setEnabled(false);

        jtPostoCmtABR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoCmtABR.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoCmtABR.setEnabled(false);

        jtGuerraCmtABR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCmtABRActionPerformed(evt);
            }
        });

        jtGuerraCondutorABR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCondutorABRActionPerformed(evt);
            }
        });

        jtGuerraAuxiliarABR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraAuxiliarABRActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("ABR - 28");

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField5.setText("Condutor da Viatura");
        jTextField5.setEnabled(false);

        jTextField14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField14.setText("Guarnição");
        jTextField14.setEnabled(false);

        jtPostoGuarnicaoAS.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoGuarnicaoAS.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoGuarnicaoAS.setEnabled(false);

        jtPostoCondutorAS.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtPostoCondutorAS.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPostoCondutorAS.setEnabled(false);

        jtGuerraCondutorAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCondutorASActionPerformed(evt);
            }
        });

        jtGuerraGuarnicaoAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraGuarnicaoASActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("AS - 50");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jtPostoOficial, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jtGuerraOficial)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoAdjunto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraAdjunto))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoOpCom, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraOpComuni))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoCmtUR, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraCmtUR))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoCondutorUR, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraCondutorUR))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoAuxiliarUR, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraAuxiliarUR))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoCmtABT, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraCmtABT))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoCondutorABT, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraCondutorABT))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoAuxiliarABT, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraAuxiliarABT))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoCmtABR, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraCmtABR))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoCondutorABR, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraCondutorABR))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoAuxiliarABR, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraAuxiliarABR))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoCondutorAS, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraCondutorAS))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jtPostoGuarnicaoAS, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jtGuerraGuarnicaoAS))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoOficial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraOficial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoAdjunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraAdjunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoOpCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraOpComuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoCmtUR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraCmtUR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoCondutorUR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraCondutorUR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoAuxiliarUR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraAuxiliarUR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoCmtABT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraCmtABT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoCondutorABT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraCondutorABT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoAuxiliarABT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraAuxiliarABT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoCmtABR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraCmtABR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoCondutorABR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraCondutorABR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoAuxiliarABR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraAuxiliarABR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoCondutorAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraCondutorAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtPostoGuarnicaoAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGuerraGuarnicaoAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("III - ALTERAÇÃO DE PESSOAL"));

        jtaAlteracaoPessoal.setColumns(20);
        jtaAlteracaoPessoal.setRows(3);
        jtaAlteracaoPessoal.setText("SEM ALTERAÇÃO");
        jScrollPane3.setViewportView(jtaAlteracaoPessoal);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("IV - ALTERAÇÃO DE MATERIAL"));

        jtaAlteracaoMaterial.setColumns(20);
        jtaAlteracaoMaterial.setRows(3);
        jtaAlteracaoMaterial.setText("SEM ALTERAÇÃO");
        jScrollPane4.setViewportView(jtaAlteracaoMaterial);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("V - ALTERAÇÃO DE INSTALAÇÃO"));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("LEITURA");

        jfLeituraAguaInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfLeituraAguaInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jtGuerraCmtABR3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtGuerraCmtABR3.setText("ÁGUA");
        jtGuerraCmtABR3.setEnabled(false);
        jtGuerraCmtABR3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCmtABR3ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("INICIAL");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("FINAL");

        jfLeituraAguaFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfLeituraAguaFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfLeituraAguaFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jfLeituraAguaFinalActionPerformed(evt);
            }
        });
        jfLeituraAguaFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jfLeituraAguaFinalKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("CONSUMO");

        jfLeituraAguaConsumo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfLeituraAguaConsumo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfLeituraAguaConsumo.setEnabled(false);

        jtGuerraCmtABR4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtGuerraCmtABR4.setText("ENERGIA");
        jtGuerraCmtABR4.setEnabled(false);
        jtGuerraCmtABR4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtGuerraCmtABR4ActionPerformed(evt);
            }
        });

        jfLeituraEnergiaFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfLeituraEnergiaFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfLeituraEnergiaFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jfLeituraEnergiaFinalActionPerformed(evt);
            }
        });

        jfLeituraEnergiaConsumo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfLeituraEnergiaConsumo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfLeituraEnergiaConsumo.setEnabled(false);

        jfLeituraEnergiaInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfLeituraEnergiaInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel14.setText("Outras alterações de instalações:");

        jtAlteracaoInstalacao.setText(" ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtAlteracaoInstalacao)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtGuerraCmtABR4, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                    .addComponent(jtGuerraCmtABR3)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jfLeituraAguaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jfLeituraAguaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jfLeituraAguaConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jfLeituraEnergiaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jfLeituraEnergiaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jfLeituraEnergiaConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfLeituraAguaConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfLeituraAguaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jfLeituraAguaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtGuerraCmtABR3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jfLeituraEnergiaConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jfLeituraEnergiaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jfLeituraEnergiaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtGuerraCmtABR4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtAlteracaoInstalacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("VI - INSTRUÇÃO"));

        jtaInstrucao.setColumns(20);
        jtaInstrucao.setRows(3);
        jtaInstrucao.setText("SEM ALTERAÇÃO");
        jScrollPane5.setViewportView(jtaInstrucao);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("VII - DOCUMENTOS / EQUIPAMENTOS / APARELHOS / ETC"));

        jtaDocumentosEquEtc.setColumns(20);
        jtaDocumentosEquEtc.setRows(3);
        jtaDocumentosEquEtc.setText("SEM ALTERAÇÃO");
        jScrollPane6.setViewportView(jtaDocumentosEquEtc);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("VIII - OCORRÊNCIAS"));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("SIGO (123,124,125)");

        jtOcorrenciasSigoSalvamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtOcorrenciasSigoSalvamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasSigoSalvamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtOcorrenciasSigoSalvamentoActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("TIPO");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("OCORRÊNCIAS");

        jtOcorrenciasOcSalvamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtOcorrenciasOcSalvamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasOcSalvamento.setText("0");
        jtOcorrenciasOcSalvamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtOcorrenciasOcSalvamentoActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("NÃO FATAL");

        jfOcorrenciasSalvNaoFatal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfOcorrenciasSalvNaoFatal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfOcorrenciasSalvNaoFatal.setText("0");
        jfOcorrenciasSalvNaoFatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jfOcorrenciasSalvNaoFatalActionPerformed(evt);
            }
        });

        jtOcorrenciasSigoIncendio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtOcorrenciasSigoIncendio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasSigoIncendio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtOcorrenciasSigoIncendioActionPerformed(evt);
            }
        });

        jtOcorrenciasOcIncendio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtOcorrenciasOcIncendio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasOcIncendio.setText("0");

        jfOcorrencioIncNaoFatal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfOcorrencioIncNaoFatal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfOcorrencioIncNaoFatal.setText("0");

        jfOcorrencioIncFatal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfOcorrencioIncFatal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfOcorrencioIncFatal.setText("0");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("FATAL");

        jfOcorrenciasSalvFatal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jfOcorrenciasSalvFatal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfOcorrenciasSalvFatal.setText("0");
        jfOcorrenciasSalvFatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jfOcorrenciasSalvFatalActionPerformed(evt);
            }
        });

        jTextField15.setText("SALVAMENTO");
        jTextField15.setEnabled(false);

        jTextField16.setText("INCÊNDIO");
        jTextField16.setEnabled(false);

        jtOcorrenciasOcAuxilio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtOcorrenciasOcAuxilio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasOcAuxilio.setText("0");

        jtOcorrenciasSigoAuxilioNaoFatal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtOcorrenciasSigoAuxilioNaoFatal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasSigoAuxilioNaoFatal.setText("0");

        jtOcorrenciasSigoAuxilioFatal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtOcorrenciasSigoAuxilioFatal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasSigoAuxilioFatal.setText("0");

        jtOcorrenciasSigoPalestraFatal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtOcorrenciasSigoPalestraFatal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasSigoPalestraFatal.setText("0");

        jTextField17.setText("PALESTRA");
        jTextField17.setEnabled(false);

        jTextField18.setText("AUXÍLIO");
        jTextField18.setEnabled(false);

        jtOcorrenciasSigoPalestra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtOcorrenciasSigoPalestra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasSigoPalestra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtOcorrenciasSigoPalestraActionPerformed(evt);
            }
        });

        jtOcorrenciasOcPalestra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtOcorrenciasOcPalestra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasOcPalestra.setText("0");

        jtOcorrenciasSigoPalestraNaoFatal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtOcorrenciasSigoPalestraNaoFatal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasSigoPalestraNaoFatal.setText("0");

        jtOcorrenciasSigoAuxilio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtOcorrenciasSigoAuxilio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtOcorrenciasSigoAuxilio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtOcorrenciasSigoAuxilioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtOcorrenciasSigoIncendio, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addComponent(jtOcorrenciasSigoSalvamento)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField16)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jTextField15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtOcorrenciasOcIncendio)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtOcorrenciasOcSalvamento, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jfOcorrenciasSalvNaoFatal, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jfOcorrencioIncNaoFatal)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(284, 284, 284)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtOcorrenciasOcAuxilio)
                                    .addComponent(jtOcorrenciasOcPalestra, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtOcorrenciasSigoAuxilio)
                                    .addComponent(jtOcorrenciasSigoPalestra, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField17, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                    .addComponent(jTextField18))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtOcorrenciasSigoPalestraNaoFatal, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jtOcorrenciasSigoAuxilioNaoFatal, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jfOcorrencioIncFatal, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jfOcorrenciasSalvFatal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jtOcorrenciasSigoPalestraFatal)
                    .addComponent(jtOcorrenciasSigoAuxilioFatal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jfOcorrenciasSalvNaoFatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtOcorrenciasOcSalvamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtOcorrenciasSigoSalvamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jfOcorrencioIncNaoFatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtOcorrenciasOcIncendio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jtOcorrenciasSigoIncendio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtOcorrenciasSigoPalestraNaoFatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jtOcorrenciasSigoPalestra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtOcorrenciasSigoAuxilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jtOcorrenciasOcPalestra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtOcorrenciasOcAuxilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtOcorrenciasSigoAuxilioNaoFatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtOcorrenciasSigoAuxilioFatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfOcorrenciasSalvFatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jfOcorrencioIncFatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtOcorrenciasSigoPalestraFatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("IX - PASSAGEM DE SERVIÇO"));

        jtaPassagemServico.setColumns(20);
        jtaPassagemServico.setLineWrap(true);
        jtaPassagemServico.setRows(3);
        jScrollPane7.setViewportView(jtaPassagemServico);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jlNomeCompleto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlNomeCompleto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNomeCompleto.setText("Nome - Graduação");

        jlMatricula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlMatricula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlMatricula.setText("Matrícula: ");

        jlPostoAdjunto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlPostoAdjunto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPostoAdjunto.setText("Adjunto ao Oficial de Sobreaviso");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlNomeCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlMatricula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlPostoAdjunto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlNomeCompleto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlPostoAdjunto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jlData.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jlData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlData.setText(" ");

        jxEscalaData.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jxEscalaData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jxEscalaDataActionPerformed(evt);
            }
        });

        jToolBar1.setFloatable(false);

        jbSalvarLivroAdjunto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/save.png"))); // NOI18N
        jbSalvarLivroAdjunto.setText("Cadastrar");
        jbSalvarLivroAdjunto.setFocusable(false);
        jbSalvarLivroAdjunto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbSalvarLivroAdjunto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbSalvarLivroAdjunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarLivroAdjuntoActionPerformed(evt);
            }
        });
        jToolBar1.add(jbSalvarLivroAdjunto);

        jLabel3.setText("     ");
        jToolBar1.add(jLabel3);

        jbCancelarLivroAdjunto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cancel.png"))); // NOI18N
        jbCancelarLivroAdjunto.setText("Cancelar");
        jbCancelarLivroAdjunto.setFocusable(false);
        jbCancelarLivroAdjunto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbCancelarLivroAdjunto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbCancelarLivroAdjunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarLivroAdjuntoActionPerformed(evt);
            }
        });
        jToolBar1.add(jbCancelarLivroAdjunto);

        jlStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlStatus.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jxEscalaData, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlData, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jxEscalaData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jlStatus)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlData)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtGuerraOpComuniActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraOpComuniActionPerformed
    {//GEN-HEADEREND:event_jtGuerraOpComuniActionPerformed
String graduacao =  pGraduacao(jtGuerraOpComuni.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraOpComuni.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraOpComuni.setText(jtGuerraOpComuni.getText().toUpperCase());
    jtPostoOpCom.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraCmtUR.requestFocus(true);
}

    }//GEN-LAST:event_jtGuerraOpComuniActionPerformed

    private void jtGuerraOficialActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraOficialActionPerformed
    {//GEN-HEADEREND:event_jtGuerraOficialActionPerformed
String graduacao =  pGraduacao(jtGuerraOficial.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraOficial.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraOficial.setText(jtGuerraOficial.getText().toUpperCase());
    jtPostoOficial.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraOpComuni.requestFocus(true);
}
    }//GEN-LAST:event_jtGuerraOficialActionPerformed

    private void jtGuerraCmtURActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCmtURActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCmtURActionPerformed
String graduacao =  pGraduacao(jtGuerraCmtUR.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraCmtUR.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraCmtUR.setText(jtGuerraCmtUR.getText().toUpperCase());
    jtPostoCmtUR.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraCondutorUR.requestFocus(true);
    jScrollPane1.getVerticalScrollBar().setValue(220);
}
    }//GEN-LAST:event_jtGuerraCmtURActionPerformed

    private void jtGuerraAuxiliarURActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraAuxiliarURActionPerformed
    {//GEN-HEADEREND:event_jtGuerraAuxiliarURActionPerformed
String graduacao =  pGraduacao(jtGuerraAuxiliarUR.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraAuxiliarUR.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraAuxiliarUR.setText(jtGuerraAuxiliarUR.getText().toUpperCase());
    jtPostoAuxiliarUR.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraCmtABT.requestFocus(true);
    jScrollPane1.getVerticalScrollBar().setValue(320);
}
    }//GEN-LAST:event_jtGuerraAuxiliarURActionPerformed

    private void jtGuerraCmtABTActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCmtABTActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCmtABTActionPerformed
String graduacao =  pGraduacao(jtGuerraCmtABT.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraCmtABT.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraCmtABT.setText(jtGuerraCmtABT.getText().toUpperCase());
    jtPostoCmtABT.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraCondutorABT.requestFocus(true);
}
    }//GEN-LAST:event_jtGuerraCmtABTActionPerformed

    private void jtGuerraAuxiliarABTActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraAuxiliarABTActionPerformed
    {//GEN-HEADEREND:event_jtGuerraAuxiliarABTActionPerformed
String graduacao =  pGraduacao(jtGuerraAuxiliarABT.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraAuxiliarABT.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraAuxiliarABT.setText(jtGuerraAuxiliarABT.getText().toUpperCase());
    jtPostoAuxiliarABT.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraCmtABR.requestFocus(true);
    jScrollPane1.getVerticalScrollBar().setValue(420);
}
    }//GEN-LAST:event_jtGuerraAuxiliarABTActionPerformed

    private void jtGuerraCmtABRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCmtABRActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCmtABRActionPerformed
String graduacao =  pGraduacao(jtGuerraCmtABR.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraCmtABR.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraCmtABR.setText(jtGuerraCmtABR.getText().toUpperCase());
    jtPostoCmtABR.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraCondutorABR.requestFocus(true);
}
    }//GEN-LAST:event_jtGuerraCmtABRActionPerformed

    private void jtGuerraAuxiliarABRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraAuxiliarABRActionPerformed
    {//GEN-HEADEREND:event_jtGuerraAuxiliarABRActionPerformed
String graduacao =  pGraduacao(jtGuerraAuxiliarABR.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraAuxiliarABR.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraAuxiliarABR.setText(jtGuerraAuxiliarABR.getText().toUpperCase());
    jtPostoAuxiliarABR.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraCondutorAS.requestFocus(true);
}
    }//GEN-LAST:event_jtGuerraAuxiliarABRActionPerformed

    private void jtGuerraCondutorASActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCondutorASActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCondutorASActionPerformed
String graduacao =  pGraduacao(jtGuerraCondutorAS.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraCondutorAS.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraCondutorAS.setText(jtGuerraCondutorAS.getText().toUpperCase());
    jtPostoCondutorAS.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraGuarnicaoAS.requestFocus(true);
}
    }//GEN-LAST:event_jtGuerraCondutorASActionPerformed

    private void jtGuerraCmtABR3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCmtABR3ActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCmtABR3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtGuerraCmtABR3ActionPerformed

    private void jtGuerraCmtABR4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCmtABR4ActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCmtABR4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtGuerraCmtABR4ActionPerformed

    private void jtGuerraCondutorURActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCondutorURActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCondutorURActionPerformed
String graduacao =  pGraduacao(jtGuerraCondutorUR.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraCondutorUR.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraCondutorUR.setText(jtGuerraCondutorUR.getText().toUpperCase());
    jtPostoCondutorUR.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraAuxiliarUR.requestFocus(true);
}
    }//GEN-LAST:event_jtGuerraCondutorURActionPerformed

    private void jtGuerraCondutorABTActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCondutorABTActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCondutorABTActionPerformed
String graduacao =  pGraduacao(jtGuerraCondutorABT.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraCondutorABT.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraCondutorABT.setText(jtGuerraCondutorABT.getText().toUpperCase());
    jtPostoCondutorABT.setText(graduacao);
    jtGuerraCondutorABR.setText(jtGuerraCondutorABT.getText().toUpperCase());
    jtPostoCondutorABR.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraAuxiliarABT.requestFocus(true);
}
    }//GEN-LAST:event_jtGuerraCondutorABTActionPerformed

    private void jtGuerraCondutorABRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraCondutorABRActionPerformed
    {//GEN-HEADEREND:event_jtGuerraCondutorABRActionPerformed
String graduacao =  pGraduacao(jtGuerraCondutorABR.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraCondutorABR.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraCondutorABR.setText(jtGuerraCondutorABR.getText().toUpperCase());
    jtPostoCondutorABR.setText(graduacao);
    jlStatus.setText(" ");
    jtGuerraAuxiliarABR.requestFocus(true);
}
    }//GEN-LAST:event_jtGuerraCondutorABRActionPerformed

    private void jtGuerraGuarnicaoASActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtGuerraGuarnicaoASActionPerformed
    {//GEN-HEADEREND:event_jtGuerraGuarnicaoASActionPerformed
String graduacao =  pGraduacao(jtGuerraGuarnicaoAS.getText());
if(graduacao.equals("XXX"))
{
    jtGuerraGuarnicaoAS.selectAll();
    jlStatus.setText("MILITAR NÃO ENCONTRADO");
}
else
{
    jtGuerraGuarnicaoAS.setText(jtGuerraGuarnicaoAS.getText().toUpperCase());
    jtPostoGuarnicaoAS.setText(graduacao);
    jlStatus.setText(" ");
    jtaAlteracaoPessoal.requestFocus(true);
    jtaAlteracaoPessoal.selectAll();
}
    }//GEN-LAST:event_jtGuerraGuarnicaoASActionPerformed

    private void jtOcorrenciasSigoSalvamentoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtOcorrenciasSigoSalvamentoActionPerformed
    {//GEN-HEADEREND:event_jtOcorrenciasSigoSalvamentoActionPerformed
String aux = jtOcorrenciasSigoSalvamento.getText();
if(aux.length()>0)
{
    aux = aux.trim();
    jtOcorrenciasOcSalvamento.setValue(aux.split(",").length);
}
    jtOcorrenciasOcSalvamento.requestFocus(true);
//jtOcorrenciasOcSalvamento.selectAll();
    }//GEN-LAST:event_jtOcorrenciasSigoSalvamentoActionPerformed

    private void jtOcorrenciasSigoIncendioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtOcorrenciasSigoIncendioActionPerformed
    {//GEN-HEADEREND:event_jtOcorrenciasSigoIncendioActionPerformed
String aux = jtOcorrenciasSigoIncendio.getText();
if(aux.length()>0)
{
    jtOcorrenciasOcIncendio.setValue(aux.split(",").length);
}
jtOcorrenciasOcIncendio.requestFocus(true);
//jtOcorrenciasOcIncendio.selectAll();
    }//GEN-LAST:event_jtOcorrenciasSigoIncendioActionPerformed

    private void jtOcorrenciasSigoPalestraActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtOcorrenciasSigoPalestraActionPerformed
    {//GEN-HEADEREND:event_jtOcorrenciasSigoPalestraActionPerformed
String aux = jtOcorrenciasSigoPalestra.getText();
if(aux.length()>0)
{
    jtOcorrenciasOcPalestra.setValue(aux.split(",").length);
}
jtOcorrenciasOcPalestra.requestFocus(true);
    }//GEN-LAST:event_jtOcorrenciasSigoPalestraActionPerformed

    private void jtOcorrenciasSigoAuxilioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtOcorrenciasSigoAuxilioActionPerformed
    {//GEN-HEADEREND:event_jtOcorrenciasSigoAuxilioActionPerformed
String aux = jtOcorrenciasSigoAuxilio.getText();
if(aux.length()>0)
{
    jtOcorrenciasOcAuxilio.setValue(aux.split(",").length);
}
jtOcorrenciasOcAuxilio.requestFocus(true);
    }//GEN-LAST:event_jtOcorrenciasSigoAuxilioActionPerformed

    private void jfLeituraAguaFinalKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jfLeituraAguaFinalKeyTyped
    {//GEN-HEADEREND:event_jfLeituraAguaFinalKeyTyped

    }//GEN-LAST:event_jfLeituraAguaFinalKeyTyped

    private void jfLeituraAguaFinalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jfLeituraAguaFinalActionPerformed
    {//GEN-HEADEREND:event_jfLeituraAguaFinalActionPerformed
try
{
    int inicial = Integer.parseInt(jfLeituraAguaInicial.getText());
    int lfinal = Integer.parseInt(jfLeituraAguaFinal.getText());
    int media = lfinal - inicial;
    jfLeituraAguaConsumo.setValue(media);
}
catch(NumberFormatException nfe)
{
    jlStatus.setText("Numero de leitura inválido");
}
catch(Exception exc)
{
    jlStatus.setText("Numero de leitura inválido");
}
    }//GEN-LAST:event_jfLeituraAguaFinalActionPerformed

    private void jfLeituraEnergiaFinalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jfLeituraEnergiaFinalActionPerformed
    {//GEN-HEADEREND:event_jfLeituraEnergiaFinalActionPerformed
try
{
    int inicial = Integer.parseInt(jfLeituraEnergiaInicial.getText());
    int lfinal = Integer.parseInt(jfLeituraEnergiaFinal.getText());
    int media = lfinal - inicial;
    jfLeituraEnergiaConsumo.setValue(media);
}
catch(NumberFormatException nfe)
{
    jlStatus.setText("Numero de leitura inválido");
}
catch(Exception exc)
{
    jlStatus.setText("Numero de leitura inválido");
}
    }//GEN-LAST:event_jfLeituraEnergiaFinalActionPerformed

    private void jbSalvarLivroAdjuntoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbSalvarLivroAdjuntoActionPerformed
    {//GEN-HEADEREND:event_jbSalvarLivroAdjuntoActionPerformed
if(verificaCampos())
{
    if(alterarLivro)
    {
        if(JOptionPane.showConfirmDialog(null, "Deseja ALTERAR o registro?","Livro do Adjunto ao Oficial de Sobreaviso",JOptionPane.YES_NO_OPTION)==0)
        {
            if(JOptionPane.showInputDialog("Digite 258 para ALTERAR o registro:").equals("258"))
            {
                updateRegistro();
            }
        }
    }
    else
    {
        if(JOptionPane.showConfirmDialog(null, "Deseja Salvar o registro?","Livro do Adjunto ao Oficial de Sobreaviso",JOptionPane.YES_NO_OPTION)==0)
        {
            if(JOptionPane.showInputDialog("Digite 123 para SALVAR o registro:").equals("123"))
            {
                salvarRegistro();
            }
        }
    }
}
    }//GEN-LAST:event_jbSalvarLivroAdjuntoActionPerformed

    private void jtOcorrenciasOcSalvamentoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtOcorrenciasOcSalvamentoActionPerformed
    {//GEN-HEADEREND:event_jtOcorrenciasOcSalvamentoActionPerformed
jfOcorrenciasSalvNaoFatal.requestFocus(true);
jfOcorrenciasSalvNaoFatal.setValue(jtOcorrenciasOcSalvamento.getValue());
//jfOcorrenciasSalvNaoFatal.selectAll();
    }//GEN-LAST:event_jtOcorrenciasOcSalvamentoActionPerformed

    private void jfOcorrenciasSalvNaoFatalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jfOcorrenciasSalvNaoFatalActionPerformed
    {//GEN-HEADEREND:event_jfOcorrenciasSalvNaoFatalActionPerformed
jfOcorrenciasSalvFatal.requestFocus(true);
    }//GEN-LAST:event_jfOcorrenciasSalvNaoFatalActionPerformed

    private void jfOcorrenciasSalvFatalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jfOcorrenciasSalvFatalActionPerformed
    {//GEN-HEADEREND:event_jfOcorrenciasSalvFatalActionPerformed
jtOcorrenciasSigoIncendio.requestFocus(true);
    }//GEN-LAST:event_jfOcorrenciasSalvFatalActionPerformed

    private void jbCancelarLivroAdjuntoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbCancelarLivroAdjuntoActionPerformed
    {//GEN-HEADEREND:event_jbCancelarLivroAdjuntoActionPerformed
if(JOptionPane.showConfirmDialog(null, "Deseja LIMPAR TODOS OS CAMPOS?","Livro do Adjunto ao Oficial de Sobreaviso",JOptionPane.YES_NO_OPTION)==0)
        limparTela();
    }//GEN-LAST:event_jbCancelarLivroAdjuntoActionPerformed

    private void jxEscalaDataActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jxEscalaDataActionPerformed
    {//GEN-HEADEREND:event_jxEscalaDataActionPerformed
String dataDoDia = new Uteis.Data().getDataAssuncao(jxEscalaData.getDate());
        System.out.println(dataDoDia);
        
        jtaAssuncao.setText("Eu, "+user.graduacaoUsuario+" "+user.guerraUsuario+", assumi o serviço na função de Adjunto "
                 + "ao Oficial de Sobreaviso, no dia "+dataDoDia+", com todas as ordens em vigor e alterações constantes em livro");
         jtPostoAdjunto.setText(user.graduacaoUsuario);
         jtGuerraAdjunto.setText(user.guerraUsuario);
    }//GEN-LAST:event_jxEscalaDataActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbCancelarLivroAdjunto;
    private javax.swing.JButton jbSalvarLivroAdjunto;
    private javax.swing.JFormattedTextField jfLeituraAguaConsumo;
    private javax.swing.JFormattedTextField jfLeituraAguaFinal;
    private javax.swing.JFormattedTextField jfLeituraAguaInicial;
    private javax.swing.JFormattedTextField jfLeituraEnergiaConsumo;
    private javax.swing.JFormattedTextField jfLeituraEnergiaFinal;
    private javax.swing.JFormattedTextField jfLeituraEnergiaInicial;
    private javax.swing.JFormattedTextField jfOcorrenciasSalvFatal;
    private javax.swing.JFormattedTextField jfOcorrenciasSalvNaoFatal;
    private javax.swing.JFormattedTextField jfOcorrencioIncFatal;
    private javax.swing.JFormattedTextField jfOcorrencioIncNaoFatal;
    private javax.swing.JLabel jlData;
    private javax.swing.JLabel jlMatricula;
    private javax.swing.JLabel jlNomeCompleto;
    private javax.swing.JLabel jlPostoAdjunto;
    private javax.swing.JLabel jlStatus;
    private javax.swing.JTextField jtAlteracaoInstalacao;
    private javax.swing.JTextField jtGuerraAdjunto;
    private javax.swing.JTextField jtGuerraAuxiliarABR;
    private javax.swing.JTextField jtGuerraAuxiliarABT;
    private javax.swing.JTextField jtGuerraAuxiliarUR;
    private javax.swing.JTextField jtGuerraCmtABR;
    private javax.swing.JTextField jtGuerraCmtABR3;
    private javax.swing.JTextField jtGuerraCmtABR4;
    private javax.swing.JTextField jtGuerraCmtABT;
    private javax.swing.JTextField jtGuerraCmtUR;
    private javax.swing.JTextField jtGuerraCondutorABR;
    private javax.swing.JTextField jtGuerraCondutorABT;
    private javax.swing.JTextField jtGuerraCondutorAS;
    private javax.swing.JTextField jtGuerraCondutorUR;
    private javax.swing.JTextField jtGuerraGuarnicaoAS;
    private javax.swing.JTextField jtGuerraOficial;
    private javax.swing.JTextField jtGuerraOpComuni;
    private javax.swing.JFormattedTextField jtOcorrenciasOcAuxilio;
    private javax.swing.JFormattedTextField jtOcorrenciasOcIncendio;
    private javax.swing.JFormattedTextField jtOcorrenciasOcPalestra;
    private javax.swing.JFormattedTextField jtOcorrenciasOcSalvamento;
    private javax.swing.JTextField jtOcorrenciasSigoAuxilio;
    private javax.swing.JFormattedTextField jtOcorrenciasSigoAuxilioFatal;
    private javax.swing.JFormattedTextField jtOcorrenciasSigoAuxilioNaoFatal;
    private javax.swing.JTextField jtOcorrenciasSigoIncendio;
    private javax.swing.JTextField jtOcorrenciasSigoPalestra;
    private javax.swing.JFormattedTextField jtOcorrenciasSigoPalestraFatal;
    private javax.swing.JFormattedTextField jtOcorrenciasSigoPalestraNaoFatal;
    private javax.swing.JTextField jtOcorrenciasSigoSalvamento;
    private javax.swing.JTextField jtPostoAdjunto;
    private javax.swing.JTextField jtPostoAuxiliarABR;
    private javax.swing.JTextField jtPostoAuxiliarABT;
    private javax.swing.JTextField jtPostoAuxiliarUR;
    private javax.swing.JTextField jtPostoCmtABR;
    private javax.swing.JTextField jtPostoCmtABT;
    private javax.swing.JTextField jtPostoCmtUR;
    private javax.swing.JTextField jtPostoCondutorABR;
    private javax.swing.JTextField jtPostoCondutorABT;
    private javax.swing.JTextField jtPostoCondutorAS;
    private javax.swing.JTextField jtPostoCondutorUR;
    private javax.swing.JTextField jtPostoGuarnicaoAS;
    private javax.swing.JTextField jtPostoOficial;
    private javax.swing.JTextField jtPostoOpCom;
    private javax.swing.JTextArea jtaAlteracaoMaterial;
    private javax.swing.JTextArea jtaAlteracaoPessoal;
    private javax.swing.JTextArea jtaAssuncao;
    private javax.swing.JTextArea jtaDocumentosEquEtc;
    private javax.swing.JTextArea jtaInstrucao;
    private javax.swing.JTextArea jtaPassagemServico;
    private org.jdesktop.swingx.JXDatePicker jxEscalaData;
    // End of variables declaration//GEN-END:variables
public void alterarLivro(int id)
{
    jlStatus.setText("Alterar Indice: "+id);
    alterarLivro = true;
    indice = id;
    try
    {
        conexao.executeSQL("select * from bombeiro_19.livroadjunto WHERE id_livro_adjunto="+indice+"");
        conexao.resultset.first();
        jxEscalaData.setDate(Data.dataRequerimento(conexao.resultset.getString("data_livro")));
        jtaAssuncao.setText(conexao.resultset.getString("assuncao"));
        jtPostoOficial.setText(conexao.resultset.getString("posto_oficial"));
        jtGuerraOficial.setText(conexao.resultset.getString("nome_guerra_oficial"));
        jtPostoAdjunto.setText(conexao.resultset.getString("posto_adjunto"));
        jtGuerraAdjunto.setText(conexao.resultset.getString("nome_guerra_adjunto"));
        jtPostoOpCom.setText(conexao.resultset.getString("posto_op_comunicacao"));
        jtGuerraOpComuni.setText(conexao.resultset.getString("nome_guerra_op_comunicacao"));
        jtPostoAuxiliarABR.setText(conexao.resultset.getString("posto_auxiliar"));
        jtGuerraAuxiliarABR.setText(conexao.resultset.getString("nome_guerra_auxiliar"));
        jtPostoCmtABR.setText(conexao.resultset.getString("posto_cmt_abr"));
        jtGuerraCmtABR.setText(conexao.resultset.getString("nome_guerra_cmt_abr"));
        jtPostoCondutorABR.setText(conexao.resultset.getString("posto_condutor_abr"));
        jtGuerraCondutorABR.setText(conexao.resultset.getString("nome_guerra_condutor_abr"));
        jtPostoCmtABT.setText(conexao.resultset.getString("posto_cmt_abr"));
        jtGuerraCmtABT.setText(conexao.resultset.getString("nome_guerra_cmt_abr"));
        jtPostoAuxiliarABT.setText(conexao.resultset.getString("posto_auxiliar_abt"));
        jtGuerraAuxiliarABT.setText(conexao.resultset.getString("nome_guerra_auxiliar_abt"));
        jtPostoCondutorABT.setText(conexao.resultset.getString("posto_condutor_abt"));
        jtGuerraCondutorABT.setText(conexao.resultset.getString("nome_guerra_condutor_abt"));
        jtPostoCmtUR.setText(conexao.resultset.getString("posto_cmt_ur"));
        jtGuerraCmtUR.setText(conexao.resultset.getString("nome_guerra_cmt_ur"));
        jtPostoAuxiliarUR.setText(conexao.resultset.getString("posto_auxiliar_ur"));
        jtGuerraAuxiliarUR.setText(conexao.resultset.getString("nome_guerra_auxiliar_ur"));
        jtPostoCondutorUR.setText(conexao.resultset.getString("posto_condutor_ur"));
        jtGuerraCondutorUR.setText(conexao.resultset.getString("nome_guerra_condutor_ur"));
        jtPostoCondutorAS.setText(conexao.resultset.getString("posto_condutor_as"));
        jtGuerraCondutorAS.setText(conexao.resultset.getString("nome_guerra_condutor_as"));
        jtPostoGuarnicaoAS.setText(conexao.resultset.getString("posto_guarnicao_as"));
        jtGuerraGuarnicaoAS.setText(conexao.resultset.getString("nome_guerra_guarnicao_as"));
        jtaAlteracaoPessoal.setText(conexao.resultset.getString("alteracao_pessoal"));
        jtaAlteracaoMaterial.setText(conexao.resultset.getString("alteracao_material"));
        jfLeituraAguaInicial.setValue(conexao.resultset.getObject("leitura_agua_inicial"));
        jfLeituraAguaFinal.setValue(conexao.resultset.getObject("leitura_agua_final"));
        jfLeituraAguaConsumo.setValue(conexao.resultset.getObject("leitura_agua_consumo"));
        jfLeituraEnergiaInicial.setValue(conexao.resultset.getObject("leitura_energia_inicial"));
        jfLeituraEnergiaFinal.setValue(conexao.resultset.getObject("leitura_energia_final"));
        jfLeituraEnergiaConsumo.setValue(conexao.resultset.getObject("leitura_energia_consumo"));
        jtAlteracaoInstalacao.setText(conexao.resultset.getString("alteracao_instalacao"));
        jtaInstrucao.setText(conexao.resultset.getString("alteracao_instrucao"));
        jtaDocumentosEquEtc.setText(conexao.resultset.getString("alteracao_documentos"));
        jtOcorrenciasSigoSalvamento.setText(conexao.resultset.getString("ocorrencias_sigo_salvamento"));
        jtOcorrenciasOcSalvamento.setValue(conexao.resultset.getObject("qtd_ocorrencia_salvamento"));
        jfOcorrenciasSalvNaoFatal.setValue(conexao.resultset.getObject("qtd_ocorrencia_salvamento_nfatal"));
        jfOcorrenciasSalvFatal.setValue(conexao.resultset.getObject("qtd_ocorrencias_salvamento_fatal"));
        jtOcorrenciasSigoIncendio.setText(conexao.resultset.getString("ocorrencias_sigo_incendio"));
        jtOcorrenciasOcIncendio.setValue(conexao.resultset.getObject("qtd_ocorrencia_incendio"));
        jfOcorrencioIncNaoFatal.setValue(conexao.resultset.getObject("qtd_ocorrencia_incendio_nfatal"));
        jfOcorrencioIncFatal.setValue(conexao.resultset.getObject("qtd_ocorrencias_incendio_fatal"));
        jtOcorrenciasSigoPalestra.setText(conexao.resultset.getString("ocorrencias_sigo_palestra"));
        jtOcorrenciasOcPalestra.setValue(conexao.resultset.getObject("qtd_ocorrencias_palestra"));
        jtOcorrenciasSigoPalestraNaoFatal.setValue(conexao.resultset.getObject("qtd_ocorrencias_palestra_nfatal"));
        jtOcorrenciasSigoPalestraFatal.setValue(conexao.resultset.getObject("qtd_ocorrencias_palestra_fatal"));
        jtOcorrenciasSigoAuxilio.setText(conexao.resultset.getString("ocorrencias_sigo_auxilio"));
        jtOcorrenciasOcAuxilio.setValue(conexao.resultset.getObject("qtd_ocorrencias_auxilio"));
        jtOcorrenciasSigoAuxilioNaoFatal.setValue(conexao.resultset.getObject("qtd_ocorrencias_auxilio_nfatal"));
        jtOcorrenciasSigoAuxilioFatal.setValue(conexao.resultset.getObject("qtd_ocorrencias_auxilio_fatal"));
        jtaPassagemServico.setText(conexao.resultset.getString("passagem_servico"));
        jlNomeCompleto.setText(user.nomeUsuario);
        jlMatricula.setText("Matrícula: "+user.matriculaUsuario);
        jlStatus.setText("Alterar livro da data: "+Data.alteraData(conexao.resultset.getString("data_livro")));
    }
    catch(SQLException er)
    {
        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
        new arquivoLog(log);
        er.printStackTrace();
        jlStatus.setText("SQLException "+er.getMessage());
    }
    catch(NumberFormatException nfe)
    {
        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+nfe.getMessage();
        new arquivoLog(log);

        jlStatus.setText("NumberFormatException "+nfe.getMessage());
    }
     catch(Exception ex)
    {
        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+ex.getMessage();
        new arquivoLog(log);
        jlStatus.setText("Exception "+ex.getMessage());
    }
}
    private boolean verificaCampos()
    {
        try
        {
            Date dataLivro;
            String auxDataAdjunto="";
            dataLivro = jxEscalaData.getDate();
            auxDataAdjunto = new Uteis.Data().getData(dataLivro);
        }
        catch(NullPointerException npe1)
        {
            jxEscalaData.requestFocus(); 
            jlStatus.setText("Necessário escolher DATA");
            return false;
        }
        if(jtaAssuncao.getText().length()<50)
        {
            jtaAssuncao.requestFocus(true);
            jtaAssuncao.selectAll();
            jlStatus.setText("Assunção do Serviço - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(1);
            return false;
        }
        if(jtPostoOficial.getText().length()<2)
        {
            jtGuerraOficial.requestFocus(true);
            jtGuerraOficial.selectAll();
            jlStatus.setText("Oficial de Sobreaviso - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(1);
            return false;
        }
        if(jtPostoOpCom.getText().length()<2)
        {
            jtGuerraOpComuni.requestFocus(true);
            jtGuerraOpComuni.selectAll();
            jlStatus.setText("Operador de comunicação - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(1);
            return false;
        }
        if(jtPostoCmtUR.getText().length()<2)
        {
            jtGuerraCmtUR.requestFocus(true);
            jtGuerraCmtUR.selectAll();
            jlStatus.setText("UR - Comandante de Guarnição - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(75);
            return false;
        }
        if(jtPostoCondutorUR.getText().length()<2)
        {
            jtGuerraCondutorUR.requestFocus(true);
            jtGuerraCondutorUR.selectAll();
            jlStatus.setText("UR - Condutor operador - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(75);
            return false;
        }
//        if(jtPostoAuxiliarUR.getText().length()<2)
//        {
//            jtGuerraAuxiliarUR.requestFocus(true);
//            jtGuerraAuxiliarUR.selectAll();
//            jlStatus.setText("UR - Auxiliar de Saúde - necessário preencher");
//            jScrollPane1.getVerticalScrollBar().setValue(75);
//            return false;
//        }
        if(jtaAlteracaoPessoal.getText().length()<2)
        {
            jtaAlteracaoPessoal.requestFocus(true);
            jtaAlteracaoPessoal.selectAll();
            jlStatus.setText("Alteração de Pessoal - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(750);
            return false;
        }
        if(jtaAlteracaoMaterial.getText().length()<2)
        {
            jtaAlteracaoMaterial.requestFocus(true);
            jtaAlteracaoMaterial.selectAll();
            jlStatus.setText("Alteração de Material - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(800);
            return false;
        }
        if(jfLeituraAguaInicial.getText().length()<1)
        {
            jfLeituraAguaInicial.requestFocus(true);
            jfLeituraAguaInicial.selectAll();
            jlStatus.setText("ÁGUA Leitura inicial - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if(jfLeituraAguaFinal.getText().length()<1)
        {
            jfLeituraAguaFinal.requestFocus(true);
            jfLeituraAguaFinal.selectAll();
            jlStatus.setText("ÁGUA Leitura final - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if(Integer.parseInt(jfLeituraAguaInicial.getText())>Integer.parseInt(jfLeituraAguaFinal.getText()))
        {
            jfLeituraAguaFinal.requestFocus(true);
            jfLeituraAguaFinal.selectAll();
            jlStatus.setText("LEITURAS INCORRETAS");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if((Integer.parseInt(jfLeituraAguaInicial.getText())<0)||(Integer.parseInt(jfLeituraAguaFinal.getText())<0)||(Integer.parseInt(jfLeituraAguaConsumo.getText())<0))
        {
            jlStatus.setText("ÁGUA - LEITURAS INCORRETAS");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if(jfLeituraAguaConsumo.getText().length()<1)
        {
            jfLeituraAguaConsumo.requestFocus(true);
            jfLeituraAguaConsumo.selectAll();
            jlStatus.setText("ÁGUA consumo - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if(Integer.parseInt(jfLeituraAguaFinal.getText())>Integer.parseInt(jfLeituraAguaInicial.getText()))
        {
            int inicial = Integer.parseInt(jfLeituraAguaInicial.getText());
            int lfinal = Integer.parseInt(jfLeituraAguaFinal.getText());
            int media = lfinal - inicial;
            jfLeituraAguaConsumo.setValue(media);
        }
        if(jfLeituraEnergiaInicial.getText().length()<1)
        {
            jfLeituraEnergiaInicial.requestFocus(true);
            jfLeituraEnergiaInicial.selectAll();
            jlStatus.setText("ÁGUA Leitura inicial - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if(jfLeituraEnergiaFinal.getText().length()<1)
        {
            jfLeituraEnergiaFinal.requestFocus(true);
            jfLeituraEnergiaFinal.selectAll();
            jlStatus.setText("ENERGIA Leitura final - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if(Integer.parseInt(jfLeituraEnergiaInicial.getText())>Integer.parseInt(jfLeituraEnergiaFinal.getText()))
        {
            jfLeituraEnergiaFinal.requestFocus(true);
            jfLeituraEnergiaFinal.selectAll();
            jlStatus.setText("LEITURAS INCORRETAS");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if((Integer.parseInt(jfLeituraEnergiaInicial.getText())<0)||(Integer.parseInt(jfLeituraEnergiaFinal.getText())<0)||(Integer.parseInt(jfLeituraEnergiaConsumo.getText())<0))
        {
            jlStatus.setText("ENERGIA - LEITURAS INCORRETAS");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if(jfLeituraEnergiaConsumo.getText().length()<1)
        {
            jfLeituraEnergiaConsumo.requestFocus(true);
            jfLeituraEnergiaConsumo.selectAll();
            jlStatus.setText("ENERGIA consumo - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(900);
            return false;
        }
        if(Integer.parseInt(jfLeituraEnergiaFinal.getText())>Integer.parseInt(jfLeituraEnergiaInicial.getText()))
        {
            int inicial = Integer.parseInt(jfLeituraEnergiaInicial.getText());
            int lfinal = Integer.parseInt(jfLeituraEnergiaFinal.getText());
            int media = lfinal - inicial;
            jfLeituraEnergiaConsumo.setValue(media);
        }
        if(jtaInstrucao.getText().length()<1)
        {
            jtaInstrucao.requestFocus(true);
            jtaInstrucao.selectAll();
            jlStatus.setText("INSTRUÇÃO - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(1000);
            return false;
        }
        if(jtaDocumentosEquEtc.getText().length()<1)
        {
            jtaDocumentosEquEtc.requestFocus(true);
            jtaDocumentosEquEtc.selectAll();
            jlStatus.setText("DOCUMENTOS - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(1200);
            return false;
        }
        if(jtaPassagemServico.getText().length()<1)
        {
            jtaPassagemServico.requestFocus(true);
            jtaPassagemServico.selectAll();
            jlStatus.setText("Passagem de Serviço - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(1600);
            return false;
        }
            
        if(jtaPassagemServico.getText().contains("XX"))
        {
            jlStatus.setText("Passagem de Serviço - necessário preencher");
            jScrollPane1.getVerticalScrollBar().setValue(1600);
            return false;
        }
        jtaAssuncao.setText(jtaAssuncao.getText().toUpperCase());
        jtaAlteracaoPessoal.setText(jtaAlteracaoPessoal.getText().toUpperCase());
        jtaAlteracaoMaterial.setText(jtaAlteracaoMaterial.getText().toUpperCase());
        jtaInstrucao.setText(jtaInstrucao.getText().toUpperCase());
        jtAlteracaoInstalacao.setText(jtAlteracaoInstalacao.getText().toUpperCase());
        jtaDocumentosEquEtc.setText(jtaDocumentosEquEtc.getText().toUpperCase());
        jtaPassagemServico.setText(jtaPassagemServico.getText().toUpperCase());
        return true;
    }
    private void salvarRegistro()
    {
        int firstTopico = 1;
        try
        {
            conexao.executeSQL("select * from bombeiro_19.livroadjunto ORDER BY id_livro_adjunto DESC limit 1");
            conexao.resultset.first();
            firstTopico = conexao.resultset.getInt("topico_9") + 1;
        }
        catch(SQLException er)
        {
            if(er.getMessage().contains("empty"))
                firstTopico = 1;
        }
        try
        {
            Date dataD = jxEscalaData.getDate();
            String dataLivro = (1900+dataD.getYear())+"-"+(1+dataD.getMonth())+"-"+dataD.getDate();
            if((dataD.getMonth()==0)&&(dataD.getDate()==1))
                firstTopico = 1;
            int segundoTopico =  firstTopico + 1;
            int terceiroTopico = segundoTopico + 1;
            int quartoTopico = terceiroTopico + 1;
            int quintoTopico = quartoTopico + 1;
            int sextoTopico = quintoTopico + 1;
            int setimoTopico = sextoTopico + 1;
            int oitavoTopico = setimoTopico + 1;
            int nonoTopico = oitavoTopico + 1;
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataD); // Objeto Date() do usuário
            cal.add(cal.DAY_OF_MONTH, +1);
            Date auxDataProxima = cal.getTime();
            String dataDoDia = new Uteis.Data().getData(dataD);
            String dataDiaSeguinte = new Uteis.Data().getData(auxDataProxima);
            String assuncao = jtaAssuncao.getText();
            String postoOficial = jtPostoOficial.getText();
            String nomeGuerraOficial = jtGuerraOficial.getText();
            String postoAdjunto = jtPostoAdjunto.getText();
            String nomeGuerraAdjunto = jtGuerraAdjunto.getText();
            String postOpComunicacao = jtPostoOpCom.getText();
            String nomeGuerraOpComunicacao = jtGuerraOpComuni.getText();
            String postoAuxiliarABR = jtPostoAuxiliarABR.getText();
            String nomeGuerraAuxiliarABR = jtGuerraAuxiliarABR.getText();
            String postoCmtAbr = jtPostoCmtABR.getText();
            String nomeGuerraCmtAbr = jtGuerraCmtABR.getText();
            String postoCondutorAbr = jtPostoCondutorABR.getText();
            String nomeGuerraCondutorAbr = jtGuerraCondutorABR.getText();
            String postoCmtAbt = jtPostoCmtABT.getText();
            String nomeGuerraCmtAbt = jtGuerraCmtABT.getText();
            String postoAuxiliarAbt = jtPostoAuxiliarABT.getText();
            String nomeGuerraAuxiliarAbt = jtGuerraAuxiliarABT.getText();
            String postoCondutorAbt = jtPostoCondutorABT.getText();
            String nomeGuerraCondutorAbt = jtGuerraCondutorABT.getText();
            String postoCmtUR = jtPostoCmtUR.getText();
            String nomeGuerraCmtUr = jtGuerraCmtUR.getText();
            String postoAuxiliarUr = jtPostoAuxiliarUR.getText();
            String nomeGuerraAuxiliarUr = jtGuerraAuxiliarUR.getText();
            String postoCondutorUr = jtPostoCondutorUR.getText();
            String nomeGuerraCondutorUr = jtGuerraCondutorUR.getText();
            String postoCondutorAs = jtPostoCondutorAS.getText();
            String nomeGuerraCondutorAs = jtGuerraCondutorAS.getText();;
            String postoGuarnicaoAS = jtPostoGuarnicaoAS.getText();
            String nomeGuerraGuarnicaoAs = jtGuerraGuarnicaoAS.getText();
            String alteracaoPessoal = jtaAlteracaoPessoal.getText();
            String alteracaoMaterial = jtaAlteracaoMaterial.getText();
            int leituraAguaInicial = Integer.parseInt(jfLeituraAguaInicial.getText());
            int leituraFinalAgua = Integer.parseInt(jfLeituraAguaFinal.getText());
            int leituraAguaConsumo = Integer.parseInt(jfLeituraAguaConsumo.getText());
            int leituraEnergiaInicial = Integer.parseInt(jfLeituraEnergiaInicial.getText());
            int leituraEnergiaFinal = Integer.parseInt(jfLeituraEnergiaFinal.getText());
            int leituraEnergiaConsumo = Integer.parseInt(jfLeituraEnergiaConsumo.getText());
            String alteracaoInstalacao = jtAlteracaoInstalacao.getText();
            String alteracaoInstrucao = jtaInstrucao.getText();
            String alteracaoDocumentos = jtaDocumentosEquEtc.getText();
            String ocorrenciaSigoSalvamento = jtOcorrenciasSigoSalvamento.getText();
            int ocorrenciaOcSalvamento = Integer.parseInt(jtOcorrenciasOcSalvamento.getText());
            int ocorrenciaSalvNaoFatal = Integer.parseInt(jfOcorrenciasSalvNaoFatal.getText());
            int ocorrenciaSalvFatal = Integer.parseInt(jfOcorrenciasSalvFatal.getText());
            String ocorrenciaSigoIncendio = jtOcorrenciasSigoIncendio.getText();
            int ocorrenciaOcIncendio = Integer.parseInt(jtOcorrenciasOcIncendio.getText());
            int ocorrenciaIncNaoFatal = Integer.parseInt(jfOcorrencioIncNaoFatal.getText());
            int ocorrenciaIncFatal = Integer.parseInt(jfOcorrencioIncFatal.getText());
            String ocorrenciaSigoPalestra = jtOcorrenciasSigoPalestra.getText();
            int ocorrenciaOcPalestra = Integer.parseInt(jtOcorrenciasOcPalestra.getText());
            int ocorrenciaSigoPalestraNaoFatal = Integer.parseInt(jtOcorrenciasSigoPalestraNaoFatal.getText());
            int ocorrenciaSigoPalestraFatal = Integer.parseInt(jtOcorrenciasSigoPalestraFatal.getText());
            String ocorrenciaSigoAuxilio = jtOcorrenciasSigoAuxilio.getText();
            int ocorrenciaOcAuxilio = Integer.parseInt(jtOcorrenciasOcAuxilio.getText());
            int ocorrenciaSigoAuxiliarNaoFatal = Integer.parseInt(jtOcorrenciasSigoAuxilioNaoFatal.getText());
            int ocorrenciaSigoAuxiliarFatal = Integer.parseInt(jtOcorrenciasSigoAuxilioFatal.getText());
            String passagemServico = jtaPassagemServico.getText();
            String nomeCompletoAssinatura = jlNomeCompleto.getText();
            String matriculaAssinatura = jlMatricula.getText();
            String postoAdjuntoAssinatura = jlPostoAdjunto.getText();
            String SqlInsert = "INSERT INTO bombeiro_19.livroadjunto "
                    + "(data_livro,"
                    + "data_dia,"
                    + "data_amanha,"
                    + "topico_1,"
                    + "topico_2,"
                    + "topico_3,"
                    + "topico_4,"
                    + "topico_5,"
                    + "topico_6,"
                    + "topico_7,"
                    + "topico_8,"
                    + "topico_9,"
                    + "assuncao,"
                    + "posto_oficial,"
                    + "nome_guerra_oficial,"
                    + "posto_adjunto,"
                    + "nome_guerra_adjunto,"
                    + "posto_op_comunicacao,"
                    + "nome_guerra_op_comunicacao,"
                    + "posto_auxiliar,nome_guerra_auxiliar,posto_cmt_abr,nome_guerra_cmt_abr,posto_condutor_abr,"
                    + "nome_guerra_condutor_abr,posto_cmt_abt,nome_guerra_cmt_abt,posto_auxiliar_abt,"
                    + "nome_guerra_auxiliar_abt,posto_condutor_abt,nome_guerra_condutor_abt,posto_cmt_ur,"
                    + "nome_guerra_cmt_ur,posto_auxiliar_ur,nome_guerra_auxiliar_ur,posto_condutor_ur,"
                    + "nome_guerra_condutor_ur,posto_condutor_as,nome_guerra_condutor_as,posto_guarnicao_as,"
                    + "nome_guerra_guarnicao_as,alteracao_pessoal,alteracao_material,leitura_agua_inicial,"
                    + "leitura_agua_final,leitura_agua_consumo,leitura_energia_inicial,leitura_energia_final,"
                    + "leitura_energia_consumo,alteracao_instalacao,alteracao_instrucao,alteracao_documentos,"
                    + "ocorrencias_sigo_salvamento,qtd_ocorrencia_salvamento,qtd_ocorrencia_salvamento_nfatal,"
                    + "qtd_ocorrencias_salvamento_fatal,ocorrencias_sigo_incendio,qtd_ocorrencia_incendio,qtd_ocorrencia_incendio_nfatal,"
                    + "qtd_ocorrencias_incendio_fatal,ocorrencias_sigo_palestra,qtd_ocorrencias_palestra,qtd_ocorrencias_palestra_nfatal,"
                    + "qtd_ocorrencias_palestra_fatal,ocorrencias_sigo_auxilio,qtd_ocorrencias_auxilio,qtd_ocorrencias_auxilio_nfatal,"
                    + "qtd_ocorrencias_auxilio_fatal,passagem_servico,nome_completo_assinatura,matricula_assinatura,posto_adjunto_assinatura"
                    + " ) VALUES "
                    + "('"+dataLivro+"',"
                    + "'"+dataDoDia+"',"
                    + "'"+dataDiaSeguinte+"',"
                    + ""+firstTopico+","
                    + ""+segundoTopico+","
                    + ""+terceiroTopico+","
                    + ""+quartoTopico+","
                    + ""+quintoTopico+","
                    + ""+sextoTopico+","
                    + ""+setimoTopico+","
                    + ""+oitavoTopico+","
                    + ""+nonoTopico+","
                    + "'"+assuncao +"',"
                    + "'"+postoOficial +"',"
                    + "'"+nomeGuerraOficial +"',"
                    + "'"+postoAdjunto +"',"
                    + "'"+nomeGuerraAdjunto +"',"
                    + "'"+postOpComunicacao +"',"
                    + "'"+nomeGuerraOpComunicacao +"',"
                    + "'"+postoAuxiliarABR +"',"
                    + "'"+nomeGuerraAuxiliarABR +"',"
                    + "'"+postoCmtAbr +"',"
                    + "'"+nomeGuerraCmtAbr +"',"
                    + "'"+postoCondutorAbr +"',"
                    + "'"+nomeGuerraCondutorAbr +"',"
                    + "'"+postoCmtAbt +"',"
                    + "'"+nomeGuerraCmtAbt +"',"
                    + "'"+postoAuxiliarAbt +"',"
                    + "'"+nomeGuerraAuxiliarAbt +"',"
                    + "'"+postoCondutorAbt +"',"
                    + "'"+nomeGuerraCondutorAbt +"',"
                    + "'"+postoCmtUR +"',"
                    + "'"+nomeGuerraCmtUr +"',"
                    + "'"+postoAuxiliarUr +"',"
                    + "'"+nomeGuerraAuxiliarUr +"',"
                    + "'"+postoCondutorUr +"',"
                    + "'"+nomeGuerraCondutorUr +"',"
                    + "'"+postoCondutorAs +"',"
                    + "'"+nomeGuerraCondutorAs +"',"
                    + "'"+postoGuarnicaoAS +"',"
                    + "'"+nomeGuerraGuarnicaoAs +"',"
                    + "'"+alteracaoPessoal +"',"
                    + "'"+alteracaoMaterial +"',"
                    + ""+leituraAguaInicial +","
                    + ""+leituraFinalAgua +","
                    + ""+leituraAguaConsumo +","
                    + ""+leituraEnergiaInicial +","
                    + ""+leituraEnergiaFinal +","
                    + ""+leituraEnergiaConsumo +","
                    + "'"+alteracaoInstalacao +"',"
                    + "'"+alteracaoInstrucao +"',"
                    + "'"+alteracaoDocumentos +"',"
                    + "'"+ocorrenciaSigoSalvamento +"',"
                    + ""+ocorrenciaOcSalvamento +","
                    + ""+ocorrenciaSalvNaoFatal +","
                    + ""+ocorrenciaSalvFatal +","
                    + "'"+ocorrenciaSigoIncendio +"',"
                    + ""+ocorrenciaOcIncendio +","
                    + ""+ocorrenciaIncNaoFatal +","
                    + ""+ocorrenciaIncFatal +","
                    + "'"+ocorrenciaSigoPalestra +"',"
                    + ""+ocorrenciaOcPalestra +","
                    + ""+ocorrenciaSigoPalestraNaoFatal +","
                    + ""+ocorrenciaSigoPalestraFatal +","
                    + "'"+ocorrenciaSigoAuxilio +"',"
                    + ""+ocorrenciaOcAuxilio +","
                    + ""+ocorrenciaSigoAuxiliarNaoFatal +","
                    + ""+ocorrenciaSigoAuxiliarFatal +","
                    + "'"+passagemServico +"',"
                    + "'"+nomeCompletoAssinatura +"',"
                    + "'"+matriculaAssinatura +"',"
                    + "'"+postoAdjuntoAssinatura +"')";
            conexao.executeSQL("select * from bombeiro_19.livroadjunto limit 1");
            conexao.statement.executeUpdate(SqlInsert);
            limparTela();
            jlStatus.setText("Cadastro realizado com sucesso!");
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            er.printStackTrace();
            jlStatus.setText("SQLException "+er.getMessage());
        }
        catch(NumberFormatException nfe)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+nfe.getMessage();
            new arquivoLog(log);
            
            jlStatus.setText("NumberFormatException "+nfe.getMessage());
        }
         catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+ex.getMessage();
            new arquivoLog(log);
            jlStatus.setText("Exception "+ex.getMessage());
        }
    }

    private void updateRegistro() 
    {
        try
        {
            Date dataD = jxEscalaData.getDate();
            String dataLivro = (1900+dataD.getYear())+"-"+(1+dataD.getMonth())+"-"+dataD.getDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataD); // Objeto Date() do usuário
            cal.add(cal.DAY_OF_MONTH, +1);
            Date auxDataProxima = cal.getTime();
            String dataDoDia = new Uteis.Data().getData(dataD);
            String dataDiaSeguinte = new Uteis.Data().getData(auxDataProxima);
            String assuncao = jtaAssuncao.getText();
            String postoOficial = jtPostoOficial.getText();
            String nomeGuerraOficial = jtGuerraOficial.getText();
            String postOpComunicacao = jtPostoOpCom.getText();
            String nomeGuerraOpComunicacao = jtGuerraOpComuni.getText();
            String postoAuxiliarABR = jtPostoAuxiliarABR.getText();
            String nomeGuerraAuxiliarABR = jtGuerraAuxiliarABR.getText();
            String postoCmtAbr = jtPostoCmtABR.getText();
            String nomeGuerraCmtAbr = jtGuerraCmtABR.getText();
            String postoCondutorAbr = jtPostoCondutorABR.getText();
            String nomeGuerraCondutorAbr = jtGuerraCondutorABR.getText();
            String postoCmtAbt = jtPostoCmtABT.getText();
            String nomeGuerraCmtAbt = jtGuerraCmtABT.getText();
            String postoAuxiliarAbt = jtPostoAuxiliarABT.getText();
            String nomeGuerraAuxiliarAbt = jtGuerraAuxiliarABT.getText();
            String postoCondutorAbt = jtPostoCondutorABT.getText();
            String nomeGuerraCondutorAbt = jtGuerraCondutorABT.getText();
            String postoCmtUR = jtPostoCmtUR.getText();
            String nomeGuerraCmtUr = jtGuerraCmtUR.getText();
            String postoAuxiliarUr = jtPostoAuxiliarUR.getText();
            String nomeGuerraAuxiliarUr = jtGuerraAuxiliarUR.getText();
            String postoCondutorUr = jtPostoCondutorUR.getText();
            String nomeGuerraCondutorUr = jtGuerraCondutorUR.getText();
            String postoCondutorAs = jtPostoCondutorAS.getText();
            String nomeGuerraCondutorAs = jtGuerraCondutorAS.getText();;
            String postoGuarnicaoAS = jtPostoGuarnicaoAS.getText();
            String nomeGuerraGuarnicaoAs = jtGuerraGuarnicaoAS.getText();
            String alteracaoPessoal = jtaAlteracaoPessoal.getText();
            String alteracaoMaterial = jtaAlteracaoMaterial.getText();
            int leituraAguaInicial = Integer.parseInt(jfLeituraAguaInicial.getText());
            int leituraFinalAgua = Integer.parseInt(jfLeituraAguaFinal.getText());
            int leituraAguaConsumo = Integer.parseInt(jfLeituraAguaConsumo.getText());
            int leituraEnergiaInicial = Integer.parseInt(jfLeituraEnergiaInicial.getText());
            int leituraEnergiaFinal = Integer.parseInt(jfLeituraEnergiaFinal.getText());
            int leituraEnergiaConsumo = Integer.parseInt(jfLeituraEnergiaConsumo.getText());
            String alteracaoInstalacao = jtAlteracaoInstalacao.getText();
            String alteracaoInstrucao = jtaInstrucao.getText();
            String alteracaoDocumentos = jtaDocumentosEquEtc.getText();
            String ocorrenciaSigoSalvamento = jtOcorrenciasSigoSalvamento.getText();
            int ocorrenciaOcSalvamento = Integer.parseInt(jtOcorrenciasOcSalvamento.getText());
            int ocorrenciaSalvNaoFatal = Integer.parseInt(jfOcorrenciasSalvNaoFatal.getText());
            int ocorrenciaSalvFatal = Integer.parseInt(jfOcorrenciasSalvFatal.getText());
            String ocorrenciaSigoIncendio = jtOcorrenciasSigoIncendio.getText();
            int ocorrenciaOcIncendio = Integer.parseInt(jtOcorrenciasOcIncendio.getText());
            int ocorrenciaIncNaoFatal = Integer.parseInt(jfOcorrencioIncNaoFatal.getText());
            int ocorrenciaIncFatal = Integer.parseInt(jfOcorrencioIncFatal.getText());
            String ocorrenciaSigoPalestra = jtOcorrenciasSigoPalestra.getText();
            int ocorrenciaOcPalestra = Integer.parseInt(jtOcorrenciasOcPalestra.getText());
            int ocorrenciaSigoPalestraNaoFatal = Integer.parseInt(jtOcorrenciasSigoPalestraNaoFatal.getText());
            int ocorrenciaSigoPalestraFatal = Integer.parseInt(jtOcorrenciasSigoPalestraFatal.getText());
            String ocorrenciaSigoAuxilio = jtOcorrenciasSigoAuxilio.getText();
            int ocorrenciaOcAuxilio = Integer.parseInt(jtOcorrenciasOcAuxilio.getText());
            int ocorrenciaSigoAuxiliarNaoFatal = Integer.parseInt(jtOcorrenciasSigoAuxilioNaoFatal.getText());
            int ocorrenciaSigoAuxiliarFatal = Integer.parseInt(jtOcorrenciasSigoAuxilioFatal.getText());
            String passagemServico = jtaPassagemServico.getText();
            String sqlUpdate = "UPDATE bombeiro_19.livroadjunto SET "
                    + "data_livro="+ "'"+dataLivro+"',"
                    + "data_dia="+ "'"+dataDoDia+"',"
                    + "data_amanha="+ "'"+dataDiaSeguinte+"',"
                    + "assuncao="+ "'"+assuncao +"',"
                    + "posto_oficial=" + "'"+postoOficial +"',"
                    + "nome_guerra_oficial="+ "'"+nomeGuerraOficial +"',"
                    + "posto_op_comunicacao="+ "'"+postOpComunicacao +"',"
                    + "nome_guerra_op_comunicacao="+ "'"+nomeGuerraOpComunicacao +"',"
                    + "posto_auxiliar=" + "'"+postoAuxiliarABR +"',"
                    + "nome_guerra_auxiliar="+ "'"+nomeGuerraAuxiliarABR +"',"
                    + "posto_cmt_abr=" + "'"+postoCmtAbr +"',"
                    + "nome_guerra_cmt_abr=" + "'"+nomeGuerraCmtAbr +"',"
                    + "posto_condutor_abr=" + "'"+postoCondutorAbr +"',"
                    + "nome_guerra_condutor_abr="+ "'"+nomeGuerraCondutorAbr +"',"
                    + "posto_cmt_abt=" + "'"+postoCmtAbt +"',"
                    + "nome_guerra_cmt_abt="+ "'"+nomeGuerraCmtAbt +"',"
                    + "posto_auxiliar_abt="+ "'"+postoAuxiliarAbt +"',"
                    + "nome_guerra_auxiliar_abt="+ "'"+nomeGuerraAuxiliarAbt +"',"
                    + "posto_condutor_abt="+ "'"+postoCondutorAbt +"',"
                    + "nome_guerra_condutor_abt="+ "'"+nomeGuerraCondutorAbt +"',"
                    + "posto_cmt_ur=" + "'"+postoCmtUR +"',"
                    + "nome_guerra_cmt_ur="+ "'"+nomeGuerraCmtUr +"',"
                    + "posto_auxiliar_ur="+ "'"+postoAuxiliarUr +"',"
                    + "nome_guerra_auxiliar_ur="+ "'"+nomeGuerraAuxiliarUr +"',"
                    + "posto_condutor_ur="+ "'"+postoCondutorUr +"',"
                    + "nome_guerra_condutor_ur="+ "'"+nomeGuerraCondutorUr +"',"
                    + "posto_condutor_as="+ "'"+postoCondutorAs +"',"
                    + "nome_guerra_condutor_as=" + "'"+nomeGuerraCondutorAs +"',"
                    + "posto_guarnicao_as="+ "'"+postoGuarnicaoAS +"',"
                    + "nome_guerra_guarnicao_as="+ "'"+nomeGuerraGuarnicaoAs +"',"
                    + "alteracao_pessoal="+ "'"+alteracaoPessoal +"',"
                    + "alteracao_material="+ "'"+alteracaoMaterial +"',"
                    + "leitura_agua_inicial=" + ""+leituraAguaInicial +","
                    + "leitura_agua_final="+ ""+leituraFinalAgua +","
                    + "leitura_agua_consumo="+ ""+leituraAguaConsumo +","
                    + "leitura_energia_inicial="+ ""+leituraEnergiaInicial +","
                    + "leitura_energia_final="+ ""+leituraEnergiaFinal +","
                    + "leitura_energia_consumo="+ ""+leituraEnergiaConsumo +","
                    + "alteracao_instalacao="+ "'"+alteracaoInstalacao +"',"
                    + "alteracao_instrucao="+ "'"+alteracaoInstrucao +"',"
                    + "alteracao_documentos="+ "'"+alteracaoDocumentos +"',"
                    + "ocorrencias_sigo_salvamento="+ "'"+ocorrenciaSigoSalvamento +"',"
                    + "qtd_ocorrencia_salvamento="+ ""+ocorrenciaOcSalvamento +","
                    + "qtd_ocorrencia_salvamento_nfatal=" + ""+ocorrenciaSalvNaoFatal +","
                    + "qtd_ocorrencias_salvamento_fatal=" + ""+ocorrenciaSalvFatal +","
                    + "ocorrencias_sigo_incendio=" + "'"+ocorrenciaSigoIncendio +"',"
                    + "qtd_ocorrencia_incendio="+ ""+ocorrenciaOcIncendio +","
                    + "qtd_ocorrencia_incendio_nfatal="+ ""+ocorrenciaIncNaoFatal +","
                    + "qtd_ocorrencias_incendio_fatal="+ ""+ocorrenciaIncFatal +","
                    + "ocorrencias_sigo_palestra="+ "'"+ocorrenciaSigoPalestra +"',"
                    + "qtd_ocorrencias_palestra=" + ""+ocorrenciaOcPalestra +","
                    + "qtd_ocorrencias_palestra_nfatal="+ ""+ocorrenciaSigoPalestraNaoFatal +","
                    + "qtd_ocorrencias_palestra_fatal=" + ""+ocorrenciaSigoPalestraFatal +","
                    + "ocorrencias_sigo_auxilio=" + "'"+ocorrenciaSigoAuxilio +"',"
                    + "qtd_ocorrencias_auxilio="+ ""+ocorrenciaOcAuxilio +","
                    + "qtd_ocorrencias_auxilio_nfatal=" + ""+ocorrenciaSigoAuxiliarNaoFatal +","
                    + "qtd_ocorrencias_auxilio_fatal="+ ""+ocorrenciaSigoAuxiliarFatal +","
                    + "passagem_servico="  + "'"+passagemServico +"'"
                    + "  WHERE id_livro_adjunto="+indice;
            conexao.executeSQL("select * from bombeiro_19.livroadjunto limit 1");
            conexao.statement.executeUpdate(sqlUpdate);
            jlStatus.setText("Livro alterado com sucesso!");
            String sqlAcesso = "INSERT INTO bombeiro_19.acessos (usuario_login,operacao ) "
                    + "VALUES ('"+user.graduacaoUsuario+" "+user.guerraUsuario+"','Alterou livro do Adjunto do dia: "+dataLivro+" e indice: "+indice+"')";
            conexao.executeSQL("select * from bombeiro_19.acessos order by id desc limit 1");
            conexao.statement.executeUpdate(sqlAcesso);
            limparTela();   
            jlStatus.setText("Alterado com sucesso!");
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            er.printStackTrace();
            jlStatus.setText("SQLException "+er.getMessage());
        }
        catch(NumberFormatException nfe)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+nfe.getMessage();
            new arquivoLog(log);
            
            jlStatus.setText("NumberFormatException "+nfe.getMessage());
        }
         catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+ex.getMessage();
            new arquivoLog(log);
            jlStatus.setText("Exception "+ex.getMessage());
        }
    }
}
//String userOperacao = "Livro do Adjunto - Alteracao indice: "+Indice+" ("+DATA+")";
//File logado = new File("log.temp");
//FileReader fileReader = new FileReader(logado);
//BufferedReader bufferedReader = new BufferedReader(fileReader);
//String userLogado = bufferedReader.readLine();
//bufferedReader.close();
//String sqlAcesso = "insert into bombeiro_19.acessos (usuario_login,operacao ) VALUES ('"+userLogado+"','"+userOperacao+"')";
//conexao.executeSQL("select * from bombeiro_19.acessos order by id desc limit 1");
//conexao.statement.executeUpdate(sqlAcesso);