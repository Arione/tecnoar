/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame_MODELOS;

import JFrame_TecnoAr.Main;
import Conexao.Conexao;
import Log.arquivoLog;
import Tabelas_MODELOS.JTableCadastroMaterial;
import Uteis.Data;
import java.awt.Color;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Guedes
 */
public class jfCadastroMaterial extends javax.swing.JFrame {

    /**
     * Creates new form Militar
     */
    Conexao conexao = new Conexao();
    Main app;
    Data date = new Data();
    private Date dataAtual;
    private boolean alterarMaterial;
    private int indice;
    private String unidade;
    private String descricao;
    pesquisarUsuario user;
    public jfCadastroMaterial(Conexao con,Main ap) 
    {
        this.conexao = con;
        this.app = ap;
        this.indice = 0;
        this.alterarMaterial = false;
        this.unidade = "Und";
        user = new pesquisarUsuario(conexao);
        descricao = "";
        initComponents();
        dataAtual = jxDataEntrada.getDate();
        this.setLocationRelativeTo(null);
        //Hora.start();
        
        limparCampos();
    }
    public void limparCampos()
    {
        this.indice = 0;
        this.alterarMaterial = false;
        this.descricao = "";
        this.unidade = "Und";
        jbSalvarMaterial.setText("Cadastrar");
        listarOrigem();
        listarDestino();
        jxDataEntrada.setDate(dataAtual);
        jtCpCodigo.setText("");
        jcTipoPermanenteConsumo.setSelectedIndex(0);
        jcEstado.setSelectedIndex(0);
        jrUnd.setSelected(true);
        jsQtdMaterial.setValue((Object)1);
        jtNomeMaterial.setText("");
        jcOrigemMaterial.setSelectedIndex(0);
        jcDestinoMaterial.setSelectedIndex(0);
        jtaDescricao.setText("");
        jlStatusCadastro.setText("");
        jsListarMaterial.setViewportView(new JTableCadastroMaterial(conexao,this).getTabela());
        jlData.setText(date.getData()+", "+date.getHora()+"hs");
    }
    private boolean verificaCampos()
    {
        try
        {
            Date dataLivro;
            String auxDataMaterial="";
            dataLivro = jxDataEntrada.getDate();
            auxDataMaterial = new Uteis.Data().getData(dataLivro);
        }
        catch(NullPointerException npe1)
        {
            jxDataEntrada.requestFocus(); 
            jlStatusCadastro.setText("Necessário escolher DATA");
            return false;
        }
        if(jtNomeMaterial.getText().length()<5)
        {
            jtNomeMaterial.requestFocus(true);
            jtNomeMaterial.selectAll();
            jlStatusCadastro.setText("NOME do Material - necessário preencher");
            return false;
        }
        if(jcOrigemMaterial.getSelectedItem().toString().equals("ORIGEM"))
        {
            jcOrigemMaterial.requestFocus();
            jlStatusCadastro.setText("Necessário Escolher/Cadastrar ORIGEM");
            return false;
        }
        if(jcDestinoMaterial.getSelectedItem().toString().equals("DESTINO"))
        {
            jcDestinoMaterial.requestFocus();
            jlStatusCadastro.setText("Necessário Escolher/Cadastrar DESTINO");
            return false;
        }
        return true;
    }
    private void salvarRegistro()
    {
        try
        {
            Date dataD = jxDataEntrada.getDate();
            String dataEntrada = (1900+dataD.getYear())+"-"+(1+dataD.getMonth())+"-"+dataD.getDate();
            String cp_codigo = jtCpCodigo.getText();
            String tipo = jcTipoPermanenteConsumo.getSelectedItem().toString();
            String estado = jcEstado.getSelectedItem().toString();
            unidade = unidade;
            int quantidade = Integer.parseInt(jsQtdMaterial.getValue().toString());
            String nome = jtNomeMaterial.getText();
            String origem = jcOrigemMaterial.getSelectedItem().toString();
            String destino = jcDestinoMaterial.getSelectedItem().toString();
            descricao = jtaDescricao.getText().trim();
            String SqlInsert = "INSERT INTO bombeiro_19.material "
                    + "(data_entrada,cp_codigo,tipo,estado,unidade,quantidade,nome,origem,destino,descricao)"
                    + " VALUES "
                    + "('"+dataEntrada+"','"+cp_codigo+"','"+tipo+"','"+estado+"','"+unidade+"',"+quantidade+",'"+nome+"','"+origem+"','"+destino+"','"+descricao+"')";
            conexao.executeSQL("select * from bombeiro_19.material limit 1");
            conexao.statement.executeUpdate(SqlInsert);
            limparCampos();
            jlStatusCadastro.setText("Cadastro realizado com sucesso!");
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            er.printStackTrace();
            jlStatusCadastro.setText("SQLException "+er.getMessage());
        }
        catch(NumberFormatException nfe)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+nfe.getMessage();
            new arquivoLog(log);
            jlStatusCadastro.setText("NumberFormatException "+nfe.getMessage());
        }
         catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+ex.getMessage();
            new arquivoLog(log);
            jlStatusCadastro.setText("Exception "+ex.getMessage());
        }
    }
    private void updateRegistro()
    {
        try
        {
            Date dataD = jxDataEntrada.getDate();
            String dataEntrada = (1900+dataD.getYear())+"-"+(1+dataD.getMonth())+"-"+dataD.getDate();
            String cp_codigo = jtCpCodigo.getText();
            String tipo = jcTipoPermanenteConsumo.getSelectedItem().toString();
            String estado = jcEstado.getSelectedItem().toString();
            unidade = unidade;
            int quantidade = Integer.parseInt(jsQtdMaterial.getValue().toString());
            String nome = jtNomeMaterial.getText();
            String origem = jcOrigemMaterial.getSelectedItem().toString();
            String destino = jcDestinoMaterial.getSelectedItem().toString();
            descricao = jtaDescricao.getText().trim();
            String sqlUpdate = "UPDATE bombeiro_19.material SET "
                    + "data_entrada = '"+dataEntrada+"',"
                    + "cp_codigo = '"+cp_codigo+"',"
                    + "tipo = '"+tipo+"',"
                    + "estado = '"+estado+"',"
                    + "unidade = '"+unidade+"',"
                    + "quantidade = "+quantidade+","
                    + "nome = '"+nome+"',"
                    + "origem = '"+origem+"',"
                    + "destino = '"+destino+"',"
                    + "descricao = '"+descricao+"' "
                    + "where id_material = "+indice;
            conexao.statement.executeUpdate(sqlUpdate);
            limparCampos();
            jlStatusCadastro.setText("Cadastro ALTERADO com sucesso!");
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            er.printStackTrace();
            jlStatusCadastro.setText("SQLException "+er.getMessage());
        }
        catch(NumberFormatException nfe)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+nfe.getMessage();
            new arquivoLog(log);
            jlStatusCadastro.setText("NumberFormatException "+nfe.getMessage());
        }
         catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+ex.getMessage();
            new arquivoLog(log);
            jlStatusCadastro.setText("Exception "+ex.getMessage());
        }
    }
    private void listarOrigem()
    {
        jcOrigemMaterial.removeAllItems();
        conexao.executeSQL("SELECT * FROM bombeiro_19.material_origem ORDER BY nome ASC");
        try
        {
            conexao.resultset.first();
            if(conexao.resultset.getRow()==0)//verifica se o bd esta vazio
            {
                jcOrigemMaterial.addItem("ORIGEM");
            }
            else
            {
                do
                {
                    jcOrigemMaterial.addItem(conexao.resultset.getObject("nome"));
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
    private void listarDestino()
    {
        jcDestinoMaterial.removeAllItems();
        conexao.executeSQL("SELECT * FROM bombeiro_19.material_destino ORDER BY nome ASC");
        try
        {
            conexao.resultset.first();
            if(conexao.resultset.getRow()==0)//verifica se o bd esta vazio
            {
                jcDestinoMaterial.addItem("DESTINO");
            }
            else
            {
                do
                {
                    jcDestinoMaterial.addItem(conexao.resultset.getObject("nome"));
                }while(conexao.resultset.next());
            }
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  SQLException: "+er.getMessage();
            new arquivoLog(log);
            System.out.println(log);
        }
        catch(Exception ex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
            new arquivoLog(log);
        }
    }
    private void cadastroNovaOrigem(String novo)
    {
        try
        {
            String SqlInsert = "INSERT INTO bombeiro_19.material_origem "
                    + "(nome) VALUES ('"+novo+"')";
            conexao.executeSQL("select * from bombeiro_19.material_origem limit 1");
            conexao.statement.executeUpdate(SqlInsert);
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            er.printStackTrace();
            jlStatusCadastro.setText("SQLException "+er.getMessage());
        }
    }
    private void cadastroNovoDestino(String novo)
    {
        try
        {
            String SqlInsert = "INSERT INTO bombeiro_19.material_destino "
                    + "(nome) VALUES ('"+novo+"')";
            conexao.executeSQL("select * from bombeiro_19.material_destino limit 1");
            conexao.statement.executeUpdate(SqlInsert);
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            er.printStackTrace();
            jlStatusCadastro.setText("SQLException "+er.getMessage());
        }
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoUnidade = new javax.swing.ButtonGroup();
        jpCadastrOMaterial = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jsListarMaterial = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        jxDataEntrada = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jtCpCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jcTipoPermanenteConsumo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jtNomeMaterial = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jrUnd = new javax.swing.JRadioButton();
        jrCx = new javax.swing.JRadioButton();
        jrPct = new javax.swing.JRadioButton();
        jrMl = new javax.swing.JRadioButton();
        jrLitros = new javax.swing.JRadioButton();
        jrKg = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jsQtdMaterial = new javax.swing.JSpinner();
        jcEstado = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaDescricao = new javax.swing.JTextArea();
        jbCancelarLivroAdjunto = new javax.swing.JButton();
        jbSalvarMaterial = new javax.swing.JButton();
        jlData = new javax.swing.JLabel();
        jlStatusCadastro = new javax.swing.JLabel();
        jcOrigemMaterial = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jcDestinoMaterial = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jbAddOrigem = new javax.swing.JButton();
        jbAddDestino = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Materiais");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("DATA ENTRADA:");

        jToolBar1.setFloatable(false);

        jxDataEntrada.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jxDataEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jxDataEntradaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("CP/CÓDIGO:");

        jtCpCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtCpCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtCpCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtCpCodigoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("TIPO:");

        jcTipoPermanenteConsumo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcTipoPermanenteConsumo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Permanente", "Consumo" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("QUANTIDADE:");

        jtNomeMaterial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtNomeMaterial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtNomeMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNomeMaterialActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("UNIDADE:");

        grupoUnidade.add(jrUnd);
        jrUnd.setText("Und");
        jrUnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrUndActionPerformed(evt);
            }
        });

        grupoUnidade.add(jrCx);
        jrCx.setText("CX");
        jrCx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrCxActionPerformed(evt);
            }
        });

        grupoUnidade.add(jrPct);
        jrPct.setText("PCT");
        jrPct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrPctActionPerformed(evt);
            }
        });

        grupoUnidade.add(jrMl);
        jrMl.setText("ml");
        jrMl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrMlActionPerformed(evt);
            }
        });

        grupoUnidade.add(jrLitros);
        jrLitros.setText("litros");
        jrLitros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrLitrosActionPerformed(evt);
            }
        });

        grupoUnidade.add(jrKg);
        jrKg.setText("Kg");
        jrKg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrKgActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("NOME DO MATERIAL:");

        jsQtdMaterial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jsQtdMaterial.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jcEstado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bom", "Regular", "Inoperante" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("ESTADO:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("DESCRIÇÃO:");
        jLabel9.setToolTipText("ESCREVER DESCRIÇÃO OU OBSERVAÇÃO SOBRE O MATERIAL, PUBLICAÇÃO, ETC.");

        jtaDescricao.setColumns(20);
        jtaDescricao.setLineWrap(true);
        jtaDescricao.setRows(5);
        jtaDescricao.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jtaDescricao);

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

        jbSalvarMaterial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/save.png"))); // NOI18N
        jbSalvarMaterial.setText("Cadastrar");
        jbSalvarMaterial.setFocusable(false);
        jbSalvarMaterial.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbSalvarMaterial.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbSalvarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarMaterialActionPerformed(evt);
            }
        });

        jlData.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jlData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlData.setText(" ");

        jlStatusCadastro.setBackground(new java.awt.Color(204, 0, 0));
        jlStatusCadastro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlStatusCadastro.setForeground(new java.awt.Color(51, 51, 51));
        jlStatusCadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlStatusCadastro.setText("status");

        jcOrigemMaterial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcOrigemMaterial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ORIGEM" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("ORIGEM:");

        jcDestinoMaterial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcDestinoMaterial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DESTINO" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("DESTINO:");

        jbAddOrigem.setText("+");
        jbAddOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddOrigemActionPerformed(evt);
            }
        });

        jbAddDestino.setText("+");
        jbAddDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddDestinoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCadastrOMaterialLayout = new javax.swing.GroupLayout(jpCadastrOMaterial);
        jpCadastrOMaterial.setLayout(jpCadastrOMaterialLayout);
        jpCadastrOMaterialLayout.setHorizontalGroup(
            jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsListarMaterial)
                    .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                    .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jrMl)
                                        .addComponent(jrUnd))
                                    .addGap(18, 18, 18)
                                    .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jrLitros)
                                        .addComponent(jrCx))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jrPct)
                                        .addComponent(jrKg)))
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jxDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jtCpCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jcTipoPermanenteConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jcEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jsQtdMaterial))
                                        .addGap(30, 30, 30)
                                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(279, 279, 279)
                                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jtNomeMaterial)))))
                            .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcOrigemMaterial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jbAddOrigem)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcDestinoMaterial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jbAddDestino))))
                    .addComponent(jlData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlStatusCadastro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                .addComponent(jbSalvarMaterial)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbCancelarLivroAdjunto))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jpCadastrOMaterialLayout.setVerticalGroup(
            jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jxDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCpCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcTipoPermanenteConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrUnd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jrCx)
                                    .addComponent(jrPct))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jrLitros)
                                    .addComponent(jrKg)
                                    .addComponent(jrMl)))
                            .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtNomeMaterial, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jsQtdMaterial, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jcOrigemMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(jcDestinoMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbAddOrigem)
                                    .addComponent(jbAddDestino))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpCadastrOMaterialLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpCadastrOMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbSalvarMaterial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbCancelarLivroAdjunto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlStatusCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jsListarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlData)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 821, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpCadastrOMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 557, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jpCadastrOMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jxDataEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jxDataEntradaActionPerformed
//        String dataDoDia = new Uteis.Data().getDataAssuncao(jxDataEntrada.getDate());
//        System.out.println(dataDoDia);
//
//        jtaAssuncao.setText("Eu, "+user.graduacaoUsuario+" "+user.guerraUsuario+", assumi o serviço na função de Adjunto "
//            + "ao Oficial de Sobreaviso, no dia "+dataDoDia+", com todas as ordens em vigor e alterações constantes em livro");
//        jtPostoAdjunto.setText(user.graduacaoUsuario);
//        jtGuerraAdjunto.setText(user.guerraUsuario);
    }//GEN-LAST:event_jxDataEntradaActionPerformed

    private void jbCancelarLivroAdjuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarLivroAdjuntoActionPerformed
if(JOptionPane.showConfirmDialog(null, "Deseja LIMPAR TODOS OS CAMPOS?","Cadastro de Material",JOptionPane.YES_NO_OPTION)==0)
    limparCampos();
    }//GEN-LAST:event_jbCancelarLivroAdjuntoActionPerformed

    private void jbSalvarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarMaterialActionPerformed
if(verificaCampos())
{
    if(alterarMaterial)
    {
        if(JOptionPane.showConfirmDialog(null, "Deseja ALTERAR o registro?","ALTERANDO....",JOptionPane.YES_NO_OPTION)==0)
        {
            if(JOptionPane.showInputDialog("Digite 453 para ALTERAR o registro:").equals("453"))
            {
                updateRegistro();
            }
        }
    }
    else
    {
        if(JOptionPane.showConfirmDialog(null, "Deseja Salvar o registro?","SALVANDO....",JOptionPane.YES_NO_OPTION)==0)
        {
            if(JOptionPane.showInputDialog("Digite 987 para SALVAR o registro:").equals("987"))
            {
                salvarRegistro();
            }
        }
    }
}        
    }//GEN-LAST:event_jbSalvarMaterialActionPerformed

    private void jtCpCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtCpCodigoActionPerformed
jlStatusCadastro.setText("Procurar por CP ou CÓDIGO");
jsListarMaterial.setViewportView(new JTableCadastroMaterial(conexao,this).getTabela(jtCpCodigo.getText()));
    }//GEN-LAST:event_jtCpCodigoActionPerformed

    private void jtNomeMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNomeMaterialActionPerformed
jlStatusCadastro.setText("Procurar por Nome");
jsListarMaterial.setViewportView(new JTableCadastroMaterial(conexao,this).getTabelaNome(jtNomeMaterial.getText()));
    }//GEN-LAST:event_jtNomeMaterialActionPerformed

    private void jbAddOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddOrigemActionPerformed
String novo = "";
if((novo = JOptionPane.showInputDialog("Digite Novo:")).length()>1)
{
    if(JOptionPane.showConfirmDialog(null, "Cadastrar nova ORIGEM?","Cadastro",JOptionPane.YES_NO_OPTION)==0)
    {
        jcOrigemMaterial.addItem(novo);
        jcOrigemMaterial.setSelectedItem(novo);
        cadastroNovaOrigem(novo);
    }
}
    }//GEN-LAST:event_jbAddOrigemActionPerformed

    private void jbAddDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddDestinoActionPerformed
String novo = "";
if((novo = JOptionPane.showInputDialog("Digite Novo:")).length()>1)
{
    if(JOptionPane.showConfirmDialog(null, "Cadastrar novo DESTINO?","Cadastro",JOptionPane.YES_NO_OPTION)==0)
    {
        jcDestinoMaterial.addItem(novo);
        jcDestinoMaterial.setSelectedItem(novo);
        cadastroNovoDestino(novo);
    }
}
    }//GEN-LAST:event_jbAddDestinoActionPerformed

    private void jrUndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrUndActionPerformed
unidade = "Und";
    }//GEN-LAST:event_jrUndActionPerformed

    private void jrCxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrCxActionPerformed
unidade = "CX";
    }//GEN-LAST:event_jrCxActionPerformed

    private void jrPctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrPctActionPerformed
unidade = "PCT";        // TODO add your handling code here:
    }//GEN-LAST:event_jrPctActionPerformed

    private void jrMlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrMlActionPerformed
unidade = "ml";  
    }//GEN-LAST:event_jrMlActionPerformed

    private void jrLitrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrLitrosActionPerformed
unidade = "litros";  
    }//GEN-LAST:event_jrLitrosActionPerformed

    private void jrKgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrKgActionPerformed
unidade = "Kg";  
    }//GEN-LAST:event_jrKgActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoUnidade;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbAddDestino;
    private javax.swing.JButton jbAddOrigem;
    private javax.swing.JButton jbCancelarLivroAdjunto;
    private javax.swing.JButton jbSalvarMaterial;
    private javax.swing.JComboBox jcDestinoMaterial;
    private javax.swing.JComboBox jcEstado;
    private javax.swing.JComboBox jcOrigemMaterial;
    private javax.swing.JComboBox jcTipoPermanenteConsumo;
    private javax.swing.JLabel jlData;
    private javax.swing.JLabel jlStatusCadastro;
    private javax.swing.JPanel jpCadastrOMaterial;
    private javax.swing.JRadioButton jrCx;
    private javax.swing.JRadioButton jrKg;
    private javax.swing.JRadioButton jrLitros;
    private javax.swing.JRadioButton jrMl;
    private javax.swing.JRadioButton jrPct;
    private javax.swing.JRadioButton jrUnd;
    private javax.swing.JScrollPane jsListarMaterial;
    private javax.swing.JSpinner jsQtdMaterial;
    private javax.swing.JTextField jtCpCodigo;
    private javax.swing.JTextField jtNomeMaterial;
    private javax.swing.JTextArea jtaDescricao;
    private org.jdesktop.swingx.JXDatePicker jxDataEntrada;
    // End of variables declaration//GEN-END:variables

    public void alterarMaterial(int id) 
    {
        this.indice = id;
        this.alterarMaterial = true;
        jbSalvarMaterial.setText("Alterar");
        listarOrigem();
        listarDestino();
        try
        {
            conexao.executeSQL("SELECT * FROM bombeiro_19.material WHERE id_material = "+indice+" ORDER BY nome ASC");
            conexao.resultset.first();
            jxDataEntrada.setDate(Data.dataRequerimento(conexao.resultset.getString("data_entrada")));
            jtCpCodigo.setText(conexao.resultset.getString("cp_codigo"));
            jcTipoPermanenteConsumo.setSelectedItem(conexao.resultset.getObject("tipo"));
            jcEstado.setSelectedItem(conexao.resultset.getObject("estado"));
            unidade = conexao.resultset.getString("unidade");
            if(unidade.equals("Und"))
                jrUnd.setSelected(true);
            else if(unidade.equals("CX"))
                jrCx.setSelected(true);
            else if(unidade.equals("PCT"))
                jrPct.setSelected(true);
            else if(unidade.equals("ml"))
                jrMl.setSelected(true);
            else if(unidade.equals("litros"))
                jrLitros.setSelected(true);
            else if(unidade.equals("Kg"))
                jrKg.setSelected(true);
            jsQtdMaterial.setValue(conexao.resultset.getObject("quantidade"));
            jtNomeMaterial.setText(conexao.resultset.getString("nome"));
            jcOrigemMaterial.setSelectedItem(conexao.resultset.getObject("origem"));
            jcDestinoMaterial.setSelectedItem(conexao.resultset.getObject("destino"));
            descricao = conexao.resultset.getString("descricao")+"\n";
            jtaDescricao.setText(descricao);
            jlStatusCadastro.setText("Alterar Material");
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
    
}
