/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame_TecnoAr;

import Conexao.Conexao;
import JFrame_AUX.jfProgressBar;
import JTableModels.TableModel;
import Log.arquivoLog;
import Renderers.CellRenderer;
import Uteis.Data;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 *
 * @author Guedes
 */
public class jfCadastroClientes extends javax.swing.JFrame {

    /**
     * Creates new form jfCadastroClientes
     */
    Conexao conexao;
    Data date = new Data();
    private Date dataAtual;
    Main appMain;
    private boolean alterarCadastro;
    private int indice;
    public JTable tabelaClientes;
    public TableModel modelo = new TableModel();
    public CellRenderer cell_1,cell_2,cell_3,cell_4,cell_5;
    private JPopupMenu popMenu;
    private JMenuItem jmExcluir;
    public jfCadastroClientes(Conexao con, Main app) 
    {
        this.conexao = con;
        this.appMain = app;
        this.alterarCadastro = false;
        this.indice = 0;
        initComponents();
        criaMenu();
        CriaJTABLET();
        //Hora.start();
        dataAtual = jxDataCadastro.getDate();
        limparTela();
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/Imagens/efetivo_ico.png")));
    }
    private void criaMenu()
    {
        popMenu = new JPopupMenu();
        jmExcluir = new JMenuItem("Excluir",new ImageIcon(this.getClass().getResource("/Imagens/lixeira16.png")));
        jmExcluir.addActionListener(excluirListener);
        
        popMenu.add(jmExcluir);
    }
    public ActionListener excluirListener = new ActionListener()
    {
         public void actionPerformed(ActionEvent e)
        {
            if(JOptionPane.showConfirmDialog(null,"Deseja excluir este registro?", "Confirmação",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                if(JOptionPane.showInputDialog("Digite 951 para excluir o registro:").equals("951"))
                {
                    try
                    {
                        String indice = tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 0).toString();
                        conexao.executeSQL("SELECT * FROM guedes_tecnoar.cliente WHERE  id="+indice+";");
                        conexao.resultset.first();
                        String sqlUpdate =  "DELETE FROM guedes_tecnoar.cliente WHERE  id="+indice+";";
                        conexao.statement.executeUpdate(sqlUpdate);
                        limparTela();
                        jlStatus.setText("Registro nº "+indice+" excluído do Banco de Dados");
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
                        JOptionPane.showMessageDialog(null,"Seleção inválida");
                        String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Exception: "+ex.getMessage();
                        new arquivoLog(log);
                    }
                }
            }
        }
    };
    private void CriaJTABLET()
    {
        modelo.setNumRows(0);
        modelo.addColumn("ID");
        modelo.addColumn("Nome completo");
        modelo.addColumn("CPF/CNPJ");
        modelo.addColumn("email");
        modelo.setCellEditable(false);
        tabelaClientes = new JTable(modelo);
        tabelaClientes.addMouseListener(new java.awt.event.MouseAdapter()
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

                                bar.jProgressBar1.setString("Recuperando registro ...");
                                bar.jProgressBar1.setEnabled(true);
                                bar.jProgressBar1.setMaximum(100);
                                bar.jProgressBar1.setMinimum(0);
                                bar.jProgressBar1.setIndeterminate(true);
                                bar.setTitle("Aguarde...");
                                bar.show(true);
                                int indice = Integer.parseInt(tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 0).toString());
                                if(indice==0)
                                {
                                    bar.dispose();
                                    JOptionPane.showMessageDialog(null,"Esse não Inocente!");
                                }
                                else
                                    editarCadastro(indice);
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
        tabelaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tabelaClientes.setAutoCreateRowSorter(true);
        tabelaClientes.setSelectionMode(0);
        tabelaClientes.setBackground(new Color(204,204,204));
        tabelaClientes.setSelectionBackground(new Color(0,102,102));
        tabelaClientes.setSelectionForeground(Color.WHITE);
        tabelaClientes.setFont(new Font(null, Font.PLAIN, 14));
        cell_1 = new CellRenderer();
        cell_1.setHorizontalAlignment(JLabel.CENTER);
        cell_2 = new CellRenderer();
        cell_2.setHorizontalAlignment(JLabel.LEFT);
        cell_3 = new CellRenderer();
        cell_3.setHorizontalAlignment(JLabel.CENTER);
        cell_4 = new CellRenderer();
        cell_4.setHorizontalAlignment(JLabel.CENTER);
        cell_5 = new CellRenderer();
        cell_1.setBackground(Color.BLACK);
        cell_1.setForeground(Color.white);
        tabelaClientes.getColumnModel().getColumn(0).setCellRenderer(cell_1);
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(15);
        tabelaClientes.getColumnModel().getColumn(1).setCellRenderer(cell_2);
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaClientes.getColumnModel().getColumn(2).setCellRenderer(cell_3);
        tabelaClientes.getColumnModel().getColumn(3).setCellRenderer(cell_4);
        tabelaClientes.setDragEnabled(false);
        tabelaClientes.getTableHeader().setReorderingAllowed(false);//ESTE É PARA NÃO ARRASTAR AS COLUNAS COM O MOUSE
        tabelaClientes.setShowGrid(true);
        tabelaClientes.setGridColor(Color.lightGray);
        tabelaClientes.setComponentPopupMenu(popMenu);
        jsClientesCadastrados.setViewportView(tabelaClientes);
    }
    private void lerRegistro()
    {
        modelo.setNumRows(0);
        conexao.executeSQL("select * from guedes_tecnoar.cliente ORDER BY id DESC LIMIT 6");
       try
        {
            conexao.resultset.first();
            if(conexao.resultset.getRow()==0)//verifica se o bd esta vazio
            {
                modelo.addRow(new Object[]{"","NENHUM CADASTRADO","",""});
            }
            else
            {
                do
                {
                        modelo.addRow(new Object[]{
                        conexao.resultset.getObject("id"),//id_cautela
                        conexao.resultset.getObject("nome"),//nome militar cautelou
                        conexao.resultset.getObject("cpf_cnpj"),//nome militar liberou
                        conexao.resultset.getObject("email")//data de devolucao
                    });
                }while(conexao.resultset.next());
            }
            jsClientesCadastrados.setViewportView(tabelaClientes);
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            if(er.getMessage().contains("empty"))
            {
                modelo.addRow(new Object[]{"","NÃO POSSUI NENHUM CADASTRO","","","","",""});
                jsClientesCadastrados.setViewportView(tabelaClientes);
            }
        }
       //tabela.setComponentPopupMenu(popMenu);
    }
    public void limparTela()
    {
        indice=0;
        alterarCadastro=false;
        jxDataCadastro.setDate(dataAtual);
        jtNome.setText("");
        jtCPF1.setText("");
        jtTelefoneFixo.setText("");
        jtTelefoneCelular.setText("");
        jtInscricao.setText("");
        jtEmail.setText("");
        jtRua.setText("");
        jtRuaNumero.setText("");
        jtBairro.setText("");
        jtCidade.setText("");
        lerRegistro();
        jlStatus.setText("");
    }
    public boolean verificaCampos()
    {
        try
        {
            Date dataLivro;
            String auxDataAdjunto="";
            dataLivro = jxDataCadastro.getDate();
            auxDataAdjunto = new Uteis.Data().getData(dataLivro);
        }
        catch(NullPointerException npe1)
        {
            jxDataCadastro.requestFocus(); 
            jlStatus.setText("Necessário escolher DATA");
            return false;
        }
        if(jtNome.getText().length()<10)
        {
            jtNome.requestFocus(true);
            jtNome.selectAll();
            jlStatus.setText("Nome - necessário preencher");
            return false;
        }
        jtNome.setText(jtNome.getText().toUpperCase());
        if(jtCPF1.getText().length()<5)
        {
            jtCPF1.requestFocus(true);
            jtCPF1.selectAll();
            jlStatus.setText("CPF/CNPJ - necessário preencher");
            return false;
        }
        return true;
    }
    public void salvarRegistro()
    {
        try
        {
            Date dataD = jxDataCadastro.getDate();
            String dataLivro = (1900+dataD.getYear())+"-"+(1+dataD.getMonth())+"-"+dataD.getDate();
            String nome = jtNome.getText();
            String cpf = jtCPF1.getText();
            String telefoneFixo = jtTelefoneFixo.getText();
            String telefoneCel = jtTelefoneCelular.getText();
            String inscricao = jtInscricao.getText();
            String email = jtEmail.getText();
            String rua = jtRua.getText();
            String numero = jtRuaNumero.getText();
            String bairro = jtBairro.getText();
            String cidade = jtCidade.getText();
            String SqlInsert = "INSERT INTO guedes_tecnoar.cliente "
                        + "(data_cadastro,nome,cpf_cnpj,inscricao,telefone_fixo,telefone_cel,email,rua,numero,bairro,cidade) "
                    + "VALUES ('"+dataLivro+"','"+nome+"','"+cpf+"','"+inscricao+"','"+telefoneFixo+"','"+telefoneCel+"','"+email+"','"+rua+"','"+numero+"','"+bairro+"','"+cidade+"')";
            conexao.executeSQL("select * from guedes_tecnoar.cliente limit 1");
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
    public void editarCadastro(int id)
    {
        try
        {
            indice = id;
            alterarCadastro = true;
            conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE id="+id);
            conexao.resultset.first();
            jxDataCadastro.setDate(Data.dataRequerimento(conexao.resultset.getString("data_cadastro")));
            jtNome.setText(conexao.resultset.getString("nome"));
            jtCPF1.setText(conexao.resultset.getString("cpf_cnpj"));
            jtTelefoneFixo.setText(conexao.resultset.getString("telefone_fixo"));
            jtTelefoneCelular.setText(conexao.resultset.getString("telefone_cel"));
            jtInscricao.setText(conexao.resultset.getString("inscricao"));
            jtEmail.setText(conexao.resultset.getString("email"));
            jtRua.setText(conexao.resultset.getString("rua"));
            jtRuaNumero.setText(conexao.resultset.getString("numero"));
            jtBairro.setText(conexao.resultset.getString("bairro"));
            jtCidade.setText(conexao.resultset.getString("cidade"));
            jlStatus.setText("Editando registro nº: "+id);
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
            Date dataD = jxDataCadastro.getDate();
            String dataLivro = (1900+dataD.getYear())+"-"+(1+dataD.getMonth())+"-"+dataD.getDate();
            String nome = jtNome.getText();
            String cpf = jtCPF1.getText();
            String telefoneFixo = jtTelefoneFixo.getText();
            String telefoneCel = jtTelefoneCelular.getText();
            String inscricao = jtInscricao.getText();
            String email = jtEmail.getText();
            String rua = jtRua.getText();
            String numero = jtRuaNumero.getText();
            String bairro = jtBairro.getText();
            String cidade = jtCidade.getText();

            String sqlUpdate = "UPDATE guedes_tecnoar.cliente SET "
                    +" data_cadastro = '"+dataLivro+"',"
                    +" nome = '"+nome+"',"
                    + "cpf_cnpj = '"+cpf+"',"
                    + "inscricao ='"+inscricao+"',"
                    + "telefone_fixo='"+telefoneFixo+"',"
                    + "telefone_cel='"+telefoneCel+"',"
                    + "email='"+email+"',"
                    + "rua='"+rua+"',"
                    + "numero='"+numero+"',"
                    + "bairro='"+bairro+"',"
                    + "cidade='"+cidade+"'"
                    + "  WHERE id="+indice;
            conexao.executeSQL("select * from guedes_tecnoar.cliente limit 1");
            conexao.statement.executeUpdate(sqlUpdate);
            limparTela();
            jlStatus.setText("Registro alterado com sucesso!");
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jxDataCadastro = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jpDadosPessoais = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtInscricao = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtTelefoneCelular = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtCPF1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtTelefoneFixo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtEmail = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jtRua = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtRuaNumero = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtBairro = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtCidade = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jbSalvarLivroAdjunto = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jbCancelarLivroAdjunto = new javax.swing.JButton();
        jlStatus = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jsClientesCadastrados = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TecnoAR - Cadastro de Clientes");
        setResizable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jxDataCadastro.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jxDataCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jxDataCadastroActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cadastro de Clientes");

        jpDadosPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados pessoais"));

        jLabel2.setText("Nome Completo:");

        jtNome.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNomeActionPerformed(evt);
            }
        });

        jLabel4.setText("Inscrição:");

        jtInscricao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtInscricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtInscricaoActionPerformed(evt);
            }
        });

        jLabel5.setText("Celular:");

        jtTelefoneCelular.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtTelefoneCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtTelefoneCelularActionPerformed(evt);
            }
        });

        jLabel6.setText("CNPJ/CPF:");

        jtCPF1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtCPF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtCPF1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Tel. Fixo:");

        jtTelefoneFixo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtTelefoneFixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtTelefoneFixoActionPerformed(evt);
            }
        });

        jLabel8.setText("Email:");

        jtEmail.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jtEmail.setForeground(new java.awt.Color(0, 0, 153));
        jtEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpDadosPessoaisLayout = new javax.swing.GroupLayout(jpDadosPessoais);
        jpDadosPessoais.setLayout(jpDadosPessoaisLayout);
        jpDadosPessoaisLayout.setHorizontalGroup(
            jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jtInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jtTelefoneFixo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jtTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jtEmail)
                                .addContainerGap())))
                    .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jtCPF1)
                                .addContainerGap())))))
        );
        jpDadosPessoaisLayout.setVerticalGroup(
            jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                                .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtCPF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jtInscricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDadosPessoaisLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jtTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtTelefoneFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        jtRua.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtRua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtRuaActionPerformed(evt);
            }
        });

        jLabel9.setText("Nome da Rua:");

        jtRuaNumero.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtRuaNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtRuaNumeroActionPerformed(evt);
            }
        });

        jLabel10.setText("Número:");

        jtBairro.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtBairroActionPerformed(evt);
            }
        });

        jLabel11.setText("Bairro:");

        jLabel12.setText("Cidade");

        jtCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtCidadeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jtRuaNumero)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtRuaNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        jlStatus.setText("                        ");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes cadastrados"));

        jsClientesCadastrados.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsClientesCadastrados)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsClientesCadastrados, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpDadosPessoais, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jxDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jxDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalvarLivroAdjuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarLivroAdjuntoActionPerformed
if(verificaCampos())
{
    if(alterarCadastro)
    {
        if(JOptionPane.showConfirmDialog(null, "Deseja ALTERAR o registro?","Cadastro de Cliente",JOptionPane.YES_NO_OPTION)==0)
        {
            updateRegistro();
        }
    }
    else
    {
        if(JOptionPane.showConfirmDialog(null, "Deseja Salvar o registro?","Cadastro de Cliente",JOptionPane.YES_NO_OPTION)==0)
        {
            salvarRegistro();
        }
    }
}        
    }//GEN-LAST:event_jbSalvarLivroAdjuntoActionPerformed

    private void jbCancelarLivroAdjuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarLivroAdjuntoActionPerformed
if(JOptionPane.showConfirmDialog(null, "Deseja LIMPAR TODOS OS CAMPOS?","Cadastro de Cliente",JOptionPane.YES_NO_OPTION)==0)
        limparTela();
    }//GEN-LAST:event_jbCancelarLivroAdjuntoActionPerformed

    private void jxDataCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jxDataCadastroActionPerformed
        
    }//GEN-LAST:event_jxDataCadastroActionPerformed

    private void jtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNomeActionPerformed
jtNome.setText(jtNome.getText().toUpperCase());
jtCPF1.requestFocus(true);
    }//GEN-LAST:event_jtNomeActionPerformed

    private void jtCPF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtCPF1ActionPerformed

jtInscricao.requestFocus(true);
    }//GEN-LAST:event_jtCPF1ActionPerformed

    private void jtInscricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtInscricaoActionPerformed
jtTelefoneFixo.requestFocus(true);
    }//GEN-LAST:event_jtInscricaoActionPerformed

    private void jtTelefoneFixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtTelefoneFixoActionPerformed
jtTelefoneCelular.requestFocus(true);
    }//GEN-LAST:event_jtTelefoneFixoActionPerformed

    private void jtTelefoneCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtTelefoneCelularActionPerformed
jtEmail.requestFocus(true);
    }//GEN-LAST:event_jtTelefoneCelularActionPerformed

    private void jtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtEmailActionPerformed
jtEmail.setText(jtEmail.getText().toLowerCase());
        jtRua.requestFocus(true);
    }//GEN-LAST:event_jtEmailActionPerformed

    private void jtRuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtRuaActionPerformed
jtRua.setText(jtRua.getText().toUpperCase());
jtRuaNumero.requestFocus(true);
    }//GEN-LAST:event_jtRuaActionPerformed

    private void jtRuaNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtRuaNumeroActionPerformed
jtBairro.requestFocus(true);
    }//GEN-LAST:event_jtRuaNumeroActionPerformed

    private void jtBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtBairroActionPerformed
jtBairro.setText(jtBairro.getText().toUpperCase());
        jtCidade.requestFocus(true);
    }//GEN-LAST:event_jtBairroActionPerformed

    private void jtCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtCidadeActionPerformed
jtCidade.setText(jtCidade.getText().toUpperCase());
        jbSalvarLivroAdjunto.requestFocus(true);
    }//GEN-LAST:event_jtCidadeActionPerformed
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(jfCadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(jfCadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(jfCadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(jfCadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new jfCadastroClientes().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbCancelarLivroAdjunto;
    private javax.swing.JButton jbSalvarLivroAdjunto;
    private javax.swing.JLabel jlStatus;
    private javax.swing.JPanel jpDadosPessoais;
    private javax.swing.JScrollPane jsClientesCadastrados;
    private javax.swing.JTextField jtBairro;
    private javax.swing.JTextField jtCPF1;
    private javax.swing.JTextField jtCidade;
    private javax.swing.JTextField jtEmail;
    private javax.swing.JTextField jtInscricao;
    private javax.swing.JTextField jtNome;
    private javax.swing.JTextField jtRua;
    private javax.swing.JTextField jtRuaNumero;
    private javax.swing.JTextField jtTelefoneCelular;
    private javax.swing.JTextField jtTelefoneFixo;
    private org.jdesktop.swingx.JXDatePicker jxDataCadastro;
    // End of variables declaration//GEN-END:variables
}
