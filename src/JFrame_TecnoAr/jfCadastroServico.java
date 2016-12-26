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
import Relatorio_TecnoAr.jasperHistoricoCliente;
import Relatorio_TecnoAr.jasperRegistroDia;
import Renderers.CellRenderer;
import Uteis.Data;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
public class jfCadastroServico extends javax.swing.JFrame 
{

    /**
     * Creates new form Servicos
     */
    Conexao conexao;
    Data date = new Data();
    private Date dataAtual;
    Main appMain;
    private boolean alterarCadastro;
    private int id_cliente;
    private int id_servico;
    public JTable tabelaServicos;
    public TableModel modelo = new TableModel();
    public CellRenderer cell_1,cell_2,cell_3,cell_4,cell_5;
    
     private JPopupMenu popMenu;
    private JMenuItem jmVisualizar;
    public jfCadastroServico(Conexao con, Main app) 
    {
        this.conexao = con;
        this.appMain = app;
        this.alterarCadastro = false;
        this.id_cliente = 0;
        this.id_servico = 0;
        initComponents();
        criaMenu();
        CriaJTABLET();
        dataAtual = jxDataServico.getDate();
        limparTela();
        this.jScrollPane2.getVerticalScrollBar().setUnitIncrement(50);//rolagem rapida 
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/Imagens/Query.png")));
    }
    private void criaMenu()
    {
        popMenu = new JPopupMenu();
        jmVisualizar = new JMenuItem("Visualizar Serviços desta Data",new ImageIcon(this.getClass().getResource("/Imagens/print-preview.png")));
        jmVisualizar.addActionListener(VerServicoListener);
        jmVisualizar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_DOWN_MASK));
        popMenu.add(jmVisualizar);
    }
    public ActionListener VerServicoListener = new ActionListener()
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
                        String idservico = tabelaServicos.getValueAt(tabelaServicos.getSelectedRow(), 0).toString();
                        conexao.executeSQL("select * from guedes_tecnoar.servicos WHERE id="+idservico+" limit 1");
                        conexao.resultset.first();
                        verRegistro(id_cliente+"",conexao.resultset.getString("data_servico"));
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
    private void CriaJTABLET()
    {
        modelo.setNumRows(0);
        modelo.addColumn("ID");
        modelo.addColumn("Tipo Serviço");
        modelo.addColumn("Data");
        modelo.addColumn("Pagamento");
        modelo.addColumn("R$ Total");
        modelo.setCellEditable(false);
        tabelaServicos = new JTable(modelo);
        tabelaServicos.addMouseListener(new java.awt.event.MouseAdapter()
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
                                int indice = Integer.parseInt(tabelaServicos.getValueAt(tabelaServicos.getSelectedRow(), 0).toString());
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
        tabelaServicos.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tabelaServicos.setAutoCreateRowSorter(true);
        tabelaServicos.setSelectionMode(0);
        tabelaServicos.setBackground(new Color(204,204,204));
        tabelaServicos.setSelectionBackground(new Color(0,102,102));
        tabelaServicos.setSelectionForeground(Color.WHITE);
        tabelaServicos.setFont(new Font(null, Font.PLAIN, 14));
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
        tabelaServicos.getColumnModel().getColumn(0).setCellRenderer(cell_1);
        tabelaServicos.getColumnModel().getColumn(0).setPreferredWidth(15);
        tabelaServicos.getColumnModel().getColumn(1).setCellRenderer(cell_2);
        tabelaServicos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaServicos.getColumnModel().getColumn(2).setCellRenderer(cell_3);
        tabelaServicos.getColumnModel().getColumn(3).setCellRenderer(cell_4);
        tabelaServicos.setDragEnabled(false);
        tabelaServicos.getTableHeader().setReorderingAllowed(false);//ESTE É PARA NÃO ARRASTAR AS COLUNAS COM O MOUSE
        tabelaServicos.setShowGrid(true);
        tabelaServicos.setGridColor(Color.lightGray);
        //tabelaServicos.setComponentPopupMenu(popMenu);
        jsHistoricoServicos.setViewportView(tabelaServicos);
    }
    private void lerRegistro()
    {
        modelo.setNumRows(0);
        conexao.executeSQL("select * from guedes_tecnoar.servicos WHERE id_cliente="+id_cliente+" ORDER BY id DESC LIMIT 5");
       try
        {
            conexao.resultset.first();
            if(conexao.resultset.getRow()==0)//verifica se o bd esta vazio
            {
                modelo.addRow(new Object[]{"","NENHUM CADASTRADO","","",""});
            }
            else
            {
                do
                {
                        modelo.addRow(new Object[]{
                        conexao.resultset.getObject("id"),//id_cautela
                        conexao.resultset.getObject("tipo_servico"),//nome militar cautelou
                        Data.alteraData(conexao.resultset.getString("data_servico")),
                        conexao.resultset.getObject("forma_pagamento"),//nome militar liberou
                        conexao.resultset.getObject("valor_total")//data de devolucao
                    });
                        System.out.println(conexao.resultset.getString("tipo_servico"));
                }while(conexao.resultset.next());
            }
            jsHistoricoServicos.setViewportView(tabelaServicos);
            tabelaServicos.setComponentPopupMenu(popMenu);
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            if(er.getMessage().contains("empty"))
            {
                modelo.addRow(new Object[]{"","NÃO POSSUI NENHUM CADASTRO","","","","",""});
                System.out.println("NENHUM SERVIÇO");
                jsHistoricoServicos.setViewportView(tabelaServicos);
            }
        }
       //tabela.setComponentPopupMenu(popMenu);
    }
    public void limparTela()
    {
        alterarCadastro = false;
        jxDataServico.setDate(dataAtual);
        jtIdCliente.setText("");
        id_cliente=0;
        id_servico=0;
        jtNomecliente.setText("");
        jtCPFcliente.setText("");
        jcTipoServico.setSelectedIndex(0);
        jsQtdServico.setValue(1);
        jfValorUnitario.setText("0");
        jfValorTotal.setText("0");
        bgFormaPagamento.clearSelection();
        jrEmDinheiro.setSelected(true);
        jxDataVencimento.setDate(dataAtual);
        jtaObservacoes.setText("");
        //jsHistoricoServicos.setVisible(false);
        lerRegistro();
        jlStatus.setText("");
    }
    public void limparTelaEditada()
    {
        alterarCadastro = false;
        jxDataServico.setDate(dataAtual);
        jcTipoServico.setSelectedIndex(0);
        jsQtdServico.setValue(1);
        jfValorUnitario.setText("0");
        jfValorTotal.setText("0");
        bgFormaPagamento.clearSelection();
        jrEmDinheiro.setSelected(true);
        jxDataVencimento.setDate(dataAtual);
        jtaObservacoes.setText("");
        //jsHistoricoServicos.setVisible(false);
        lerRegistro();
    }
    private boolean verificaCampos()
    {
        try
        {
            Date dataLivro;
            String auxDataAdjunto="";
            dataLivro = jxDataServico.getDate();
            auxDataAdjunto = new Uteis.Data().getData(dataLivro);
        }
        catch(NullPointerException npe1)
        {
            jxDataServico.requestFocus(); 
            jlStatus.setText("Necessário escolher DATA");
            return false;
        }
        if(id_cliente==0)
        {
            jtNomecliente.requestFocus(true);
            jtNomecliente.selectAll();
            jlStatus.setText("Necessário escolher Cliente");
            return false;
        }
        try
        {
            String troca = jfValorUnitario.getText().replace(',','.');
            jfValorUnitario.setText(troca);
            double unitario = Double.parseDouble(jfValorUnitario.getText());
            if(unitario < 1)
            {
                jfValorUnitario.selectAll();
                jfValorUnitario.requestFocus(true);
                jlStatus.setText("Valor inválido!");
                jScrollPane2.getVerticalScrollBar().setValue(1);
                return false;
            }
            double total = unitario * Integer.parseInt(jsQtdServico.getValue().toString());
            DecimalFormat def = new DecimalFormat("#########.##");
            total = Double.parseDouble(def.format(total).replace(',','.'));
            jfValorTotal.setText(""+total);
        }
        catch(NumberFormatException nfe1)
        {
             jfValorUnitario.requestFocus();
             jfValorUnitario.setSelectionStart(0);
             jfValorUnitario.setSelectionEnd(jfValorUnitario.getText().length());
             jlStatus.setText("Valor Unitário - Valor inválido ex: 12.45");
             jScrollPane2.getVerticalScrollBar().setValue(1);
             return false;
        }
        
        try
        {
            Date dataVencimento;
            String auxDataVencimento="";
            dataVencimento = jxDataVencimento.getDate();
            auxDataVencimento = new Uteis.Data().getData(dataVencimento);
        }
        catch(NullPointerException npe1)
        {
            jxDataVencimento.requestFocus(); 
            jlStatus.setText("Necessário escolher DATA de vencimento");
            jScrollPane2.getVerticalScrollBar().setValue(1);
            return false;
        }
        return true;
    }
    public void editarCadastro(int id)
    {
        try
        {
            id_servico = id;
            alterarCadastro = true;
            conexao.executeSQL("select * from guedes_tecnoar.servicos WHERE id="+id);
            conexao.resultset.first();
            id_cliente = conexao.resultset.getInt("id_cliente");
            //buscarCliente();
            jxDataServico.setDate(Data.dataRequerimento(conexao.resultset.getString("data_servico")));
            jcTipoServico.setSelectedItem(conexao.resultset.getObject("tipo_servico"));
            jsQtdServico.setValue(conexao.resultset.getObject("qtd_servico"));
            jfValorUnitario.setText(conexao.resultset.getString("valor_unitario"));
            jfValorTotal.setText(conexao.resultset.getString("valor_total"));
            String formaPagamento = conexao.resultset.getString("forma_pagamento");
            if(formaPagamento.equals("Em dinheiro"))
                jrEmDinheiro.setSelected(true);
            else if(formaPagamento.equals("Cheque"))
                jrCheque.setSelected(true);
            else if(formaPagamento.equals("Boleto bancário"))
                jrBoleto.setSelected(true);
            else if(formaPagamento.equals("Promissória"))
                jrPromissoria.setSelected(true);
            else if(formaPagamento.equals("Cartão de Crédito"))
                jrCartaoCredito.setSelected(true);
            else if(formaPagamento.equals("Transfer. Bancária"))
                jrTransferencia.setSelected(true);
            jxDataVencimento.setDate(Data.dataRequerimento(conexao.resultset.getString("data_vencimento")));
            jtaObservacoes.setText(conexao.resultset.getString("observacoes"));
            jScrollPane2.getVerticalScrollBar().setValue(1);
            //buscandoHistorico(id_cliente);
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
    private void salvarRegistro()
    {
        try
        {
            Date dataServico = jxDataServico.getDate();
            String data_servico = (1900+dataServico.getYear())+"-"+(1+dataServico.getMonth())+"-"+dataServico.getDate();
            String tipo_servico = jcTipoServico.getSelectedItem().toString();
            int qtd_servico = (int)jsQtdServico.getValue();
            double valor_unitario = Double.parseDouble(jfValorUnitario.getText());
            double valor_total = Double.parseDouble(jfValorTotal.getText());
            String forma_pagamento = "";
            if(jrEmDinheiro.isSelected())
                forma_pagamento = "Em dinheiro";
            else if(jrCheque.isSelected())
                forma_pagamento = "Cheque";
            else if(jrBoleto.isSelected())
                forma_pagamento = "Boleto bancário";
            else if(jrPromissoria.isSelected())
                forma_pagamento = "Promissória";
            else if(jrCartaoCredito.isSelected())
                forma_pagamento = "Cartão de Crédito";
            else if(jrTransferencia.isSelected())
                forma_pagamento = "Transfer. Bancária";
            Date dataVencimento = jxDataVencimento.getDate();
            String data_vencimento = (1900+dataVencimento.getYear())+"-"+(1+dataVencimento.getMonth())+"-"+dataVencimento.getDate();
            String observacoes = jtaObservacoes.getText();
            String SqlInsert = "INSERT INTO guedes_tecnoar.servicos "
                        + "(id_cliente,data_servico,tipo_servico,qtd_servico,valor_unitario,valor_total,forma_pagamento,data_vencimento,observacoes) "
                    + "VALUES ("+id_cliente+",'"+data_servico+"','"+tipo_servico+"',"+qtd_servico+","+valor_unitario+","+valor_total+",'"+forma_pagamento+"','"+data_vencimento+"','"+observacoes+"')";
            conexao.executeSQL("select * from guedes_tecnoar.servicos limit 1");
            conexao.statement.executeUpdate(SqlInsert);
            limparTelaEditada();
            jlStatus.setText("Cadastro realizado com sucesso!");
            jScrollPane2.getVerticalScrollBar().setValue(200);
            
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
    private void verRegistro(String id,String data)
    {
        new jasperRegistroDia(id, data, conexao);
    }
    private void updateRegistro()
    {
        try
        {
            Date dataServico = jxDataServico.getDate();
            String data_servico = (1900+dataServico.getYear())+"-"+(1+dataServico.getMonth())+"-"+dataServico.getDate();
            String tipo_servico = jcTipoServico.getSelectedItem().toString();
            int qtd_servico = (int)jsQtdServico.getValue();
            double valor_unitario = Double.parseDouble(jfValorUnitario.getText());
            double valor_total = Double.parseDouble(jfValorTotal.getText());
            String forma_pagamento = "";
            if(jrEmDinheiro.isSelected())
                forma_pagamento = "Em dinheiro";
            else if(jrCheque.isSelected())
                forma_pagamento = "Cheque";
            else if(jrBoleto.isSelected())
                forma_pagamento = "Boleto bancário";
            else if(jrPromissoria.isSelected())
                forma_pagamento = "Promissória";
            else if(jrCartaoCredito.isSelected())
                forma_pagamento = "Cartão de Crédito";
            else if(jrTransferencia.isSelected())
                forma_pagamento = "Transfer. Bancária";
            Date dataVencimento = jxDataVencimento.getDate();
            String data_vencimento = (1900+dataVencimento.getYear())+"-"+(1+dataVencimento.getMonth())+"-"+dataVencimento.getDate();
            String observacoes = jtaObservacoes.getText();
            
            String sqlUpdate = "UPDATE guedes_tecnoar.servicos SET "
                    +" data_servico = '"+data_servico+"',"
                    +" tipo_servico = '"+tipo_servico+"',"
                    + "qtd_servico = "+qtd_servico+","
                    + "valor_unitario ="+valor_unitario+","
                    + "valor_total="+valor_total+","
                    + "forma_pagamento='"+forma_pagamento+"',"
                    + "data_vencimento='"+data_vencimento+"',"
                    + "observacoes='"+observacoes+"'"
                    + "  WHERE id="+id_servico;
            conexao.executeSQL("select * from guedes_tecnoar.servicos limit 1");
            conexao.statement.executeUpdate(sqlUpdate);
            limparTelaEditada();
            jlStatus.setText("Registro nº "+id_servico+" alterado!");
            
            
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgFormaPagamento = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jxDataServico = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jpDadosPessoais = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtNomecliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtCPFcliente = new javax.swing.JTextField();
        jtIdCliente = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jbSalvarLivroAdjunto = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jbCancelarLivroAdjunto = new javax.swing.JButton();
        jlStatus = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jcTipoServico = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jsQtdServico = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaObservacoes = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jrEmDinheiro = new javax.swing.JRadioButton();
        jrCheque = new javax.swing.JRadioButton();
        jrBoleto = new javax.swing.JRadioButton();
        jrCartaoCredito = new javax.swing.JRadioButton();
        jrPromissoria = new javax.swing.JRadioButton();
        jrTransferencia = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jxDataVencimento = new org.jdesktop.swingx.JXDatePicker();
        jfValorUnitario = new javax.swing.JTextField();
        jfValorTotal = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jsHistoricoServicos = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TecnoAR - Cadastro de Serviços");
        setResizable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jxDataServico.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jxDataServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jxDataServicoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cadastro de Serviço");

        jpDadosPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados pessoais"));

        jLabel2.setText("Nome Cliente:");

        jtNomecliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtNomecliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNomeclienteActionPerformed(evt);
            }
        });

        jLabel6.setText("CNPJ/CPF:");

        jtCPFcliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jtIdCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtIdCliente.setText("ID");
        jtIdCliente.setEnabled(false);

        javax.swing.GroupLayout jpDadosPessoaisLayout = new javax.swing.GroupLayout(jpDadosPessoais);
        jpDadosPessoais.setLayout(jpDadosPessoaisLayout);
        jpDadosPessoaisLayout.setHorizontalGroup(
            jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtNomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                        .addComponent(jtCPFcliente)
                        .addContainerGap())))
        );
        jpDadosPessoaisLayout.setVerticalGroup(
            jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtNomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jtCPFcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jlStatus.setText("               ");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Serviço Realizado"));

        jLabel4.setText("Tipo de Serviço:");

        jcTipoServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Instalação", "Limpeza", "Manutenção", "Troca de placa", "Carga de gás", "Conserto de instalação", "Serviço técnico" }));

        jLabel5.setText("Qtd:");

        jsQtdServico.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel7.setText("Valor Unitário R$:");

        jLabel8.setText("Valor Total R$:");

        jLabel13.setText("Observações:");

        jtaObservacoes.setColumns(20);
        jtaObservacoes.setRows(5);
        jScrollPane1.setViewportView(jtaObservacoes);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Forma de Pagamento"));

        bgFormaPagamento.add(jrEmDinheiro);
        jrEmDinheiro.setSelected(true);
        jrEmDinheiro.setText("Em dinheiro");

        bgFormaPagamento.add(jrCheque);
        jrCheque.setText("Cheque");

        bgFormaPagamento.add(jrBoleto);
        jrBoleto.setText("Boleto bancário");

        bgFormaPagamento.add(jrCartaoCredito);
        jrCartaoCredito.setText("Cartão de Crédito");

        bgFormaPagamento.add(jrPromissoria);
        jrPromissoria.setText("Promissória");

        bgFormaPagamento.add(jrTransferencia);
        jrTransferencia.setText("Transfer. Bancária");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Data vencimento:");

        jxDataVencimento.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jxDataVencimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jxDataVencimentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrEmDinheiro)
                    .addComponent(jrCheque)
                    .addComponent(jrBoleto))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrTransferencia)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrPromissoria)
                            .addComponent(jrCartaoCredito))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jxDataVencimento, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrEmDinheiro)
                    .addComponent(jrPromissoria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrCheque)
                    .addComponent(jrCartaoCredito))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrBoleto)
                    .addComponent(jrTransferencia)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jxDataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jfValorUnitario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfValorUnitario.setText(" ");
        jfValorUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jfValorUnitarioActionPerformed(evt);
            }
        });

        jfValorTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jfValorTotal.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jsQtdServico, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel7))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jfValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(26, 26, 26))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jcTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jsQtdServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jfValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Histórico de serviços"));

        jsHistoricoServicos.setBorder(null);
        jsHistoricoServicos.setPreferredSize(new java.awt.Dimension(126, 568));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsHistoricoServicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsHistoricoServicos, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jPanel5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpDadosPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jxDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jxDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jxDataServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jxDataServicoActionPerformed

    }//GEN-LAST:event_jxDataServicoActionPerformed

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
        if(JOptionPane.showConfirmDialog(null, "Deseja Salvar o registro?","Cadastro de Serviço",JOptionPane.YES_NO_OPTION)==0)
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

    private void jxDataVencimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jxDataVencimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jxDataVencimentoActionPerformed

    private void jtNomeclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNomeclienteActionPerformed
buscarCliente(jtNomecliente.getText());
    }//GEN-LAST:event_jtNomeclienteActionPerformed

    private void jfValorUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jfValorUnitarioActionPerformed
    try
        {
            String troca = jfValorUnitario.getText().replace(',','.');
            jfValorUnitario.setText(troca);
            jfValorUnitario.setCaretPosition(jfValorUnitario.getText().length());
            double unitario = Double.parseDouble(jfValorUnitario.getText());
            if(unitario < 1)
            {
                jfValorUnitario.selectAll();
                jfValorUnitario.requestFocus(true);
                jlStatus.setText("Valor inválido!");
            }
            double total = unitario * Integer.parseInt(jsQtdServico.getValue().toString());
            DecimalFormat def = new DecimalFormat("#########.##");
            total = Double.parseDouble(def.format(total).replace(',','.'));
            jfValorTotal.setText(""+total);
        }
        catch(NumberFormatException nfe1)
        {
             jfValorUnitario.requestFocus();
             jfValorUnitario.setSelectionStart(0);
             jfValorUnitario.setSelectionEnd(jfValorUnitario.getText().length());
             jlStatus.setText("Valor Unitário - Valor inválido ex: 12.45");
        }
    }//GEN-LAST:event_jfValorUnitarioActionPerformed
public void buscarCliente(String nome)
{
    try
        {
            limparTela();
            conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE nome LIKE '%"+nome+"%'");
            conexao.resultset.first();
            jtNomecliente.setText(conexao.resultset.getString("nome"));
            jtCPFcliente.setText(conexao.resultset.getString("cpf_cnpj"));
            jtIdCliente.setText(conexao.resultset.getString("id"));
            id_cliente = Integer.parseInt(jtIdCliente.getText());
            lerRegistro();
        }
        catch(SQLException er)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ERRO: "+er.getMessage();
            new arquivoLog(log);
            er.printStackTrace();
            jlStatus.setText("CLIENTE NÃO CADASTRADO");
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
public void buscarCliente(int id)
{
    try
        {
            limparTela();
            conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE id="+id+"");
            conexao.resultset.first();
            jtNomecliente.setText(conexao.resultset.getString("nome"));
            jtCPFcliente.setText(conexao.resultset.getString("cpf_cnpj"));
            jtIdCliente.setText(conexao.resultset.getString("id"));
            id_cliente = Integer.parseInt(jtIdCliente.getText());
            lerRegistro();
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
private void buscarCliente()
{
    try
        {
            conexao.executeSQL("select * from guedes_tecnoar.cliente WHERE id="+id_cliente+"");
            conexao.resultset.first();
            jtNomecliente.setText(conexao.resultset.getString("nome"));
            jtCPFcliente.setText(conexao.resultset.getString("cpf_cnpj"));
            jtIdCliente.setText(conexao.resultset.getString("id"));
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
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgFormaPagamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbCancelarLivroAdjunto;
    private javax.swing.JButton jbSalvarLivroAdjunto;
    private javax.swing.JComboBox<String> jcTipoServico;
    private javax.swing.JTextField jfValorTotal;
    private javax.swing.JTextField jfValorUnitario;
    private javax.swing.JLabel jlStatus;
    private javax.swing.JPanel jpDadosPessoais;
    private javax.swing.JRadioButton jrBoleto;
    private javax.swing.JRadioButton jrCartaoCredito;
    private javax.swing.JRadioButton jrCheque;
    private javax.swing.JRadioButton jrEmDinheiro;
    private javax.swing.JRadioButton jrPromissoria;
    private javax.swing.JRadioButton jrTransferencia;
    private javax.swing.JScrollPane jsHistoricoServicos;
    private javax.swing.JSpinner jsQtdServico;
    private javax.swing.JTextField jtCPFcliente;
    private javax.swing.JTextField jtIdCliente;
    private javax.swing.JTextField jtNomecliente;
    private javax.swing.JTextArea jtaObservacoes;
    private org.jdesktop.swingx.JXDatePicker jxDataServico;
    private org.jdesktop.swingx.JXDatePicker jxDataVencimento;
    // End of variables declaration//GEN-END:variables
}
