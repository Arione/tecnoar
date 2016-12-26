package Threads;

import Conexao.Conexao;
import JFrame_MODELOS.pesquisarUsuario;
import Log.arquivoLog;
import Uteis.Data;
import Uteis.XMLGenerator;
import Uteis.imageSplash;

/**
 * @author Guedes
 */
public class splash extends Thread
{
    public static int i = 0;
    public static Conexao conexao;
    public static imageSplash im;
    private String log;
    private XMLGenerator get = new XMLGenerator();
    public splash()
    {

    }
    @Override
    public void run()
    {
        im = new imageSplash();
        while(i<101)
        {
            try{sleep(5);}catch(InterruptedException ex){}
            imageSplash.jpBarra.setValue(i);
            if(i==10)
                im.texto.setText("TecnoAR");
            else if(i==30)
                im.texto.setText("Excelência em serviços de Ar Condicionados");
            else if(i==60)
                im.texto.setText("Iniciando área Administrativa...");
            if(i==70)
            {
                log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Esperando Login no Banco de Dados...";
                new arquivoLog(log);
                im.texto.setText("Esperando Login de Usuário");
                im.getContentPane().getComponent(2).setVisible(true);
            }
            if(i<71)
            {
                i++;
            }
            if(i==72)
            {
                im.texto.setText("Houve um erro, por favor Repita o Login!");
            }
            if(i==73)
            {
                log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...Login efetuado com Sucesso";
                new arquivoLog(log);
                i++;
            }
            if(i==95)
            {
                im.texto.setText("Carregando configurações .xml");
                //get.loadXml("config.xml");
            }
            if(i==99)
            {
                im.texto.setText("Inicializando aplicativo... (Aguarde um momento)");
            }
            if(i>72)
            {
                i++;
            }
        }
        new JFrame_TecnoAr.Main(conexao).setVisible(true);
        im.dispose();
        log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Iniciando Aplicativo...";
        new arquivoLog(log);
    }
}

