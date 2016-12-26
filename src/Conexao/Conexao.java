package Conexao;
/**
 * @by Guedes
 */
import Log.arquivoLog;
import java.sql.*;
import javax.swing.*;
import Uteis.Data;
public class Conexao
{
    //final private String ipServidor="localhost";
    //final private String ipServidor="coximemergencia.com.br:3306";
    final private String driver ="com.mysql.jdbc.Driver";
    //final private String url="jdbc:mysql://"+ipServidor+"/coximeme_bd";
    //final private String usuario="root";
    //final private String usuario="coximeme_root";
    //final private String senha="suporte@guedes";
    private Connection Conexao;
    public Statement statement;
    public ResultSet resultset;
    private String usuario, url, senha;
    /**
     * Este método serve para fazer a conexão ao banco de dados.
     * @param url Este parametro é o endereço do banco de dados: "localhost"
     * @param usuario Este é o usuário de acesso: "root"
     * @param senha Esta é a senha de acesso: 
     * @return 
     */
    public boolean conecta(String url, String usuario, String senha)
    {
        if((usuario.length()<1)||(senha.length()<1))
            return false;
        new arquivoLog("|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Conectando "+url+"...");
        try
        {
            Class.forName(driver);
            Conexao=DriverManager.getConnection("jdbc:mysql://"+url+":3306",usuario,senha);
            new arquivoLog("|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...Sucesso");
            this.usuario = usuario;
            this.url = url;
            this.senha = senha;
            return true;
        }
        catch(ClassNotFoundException Driver)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...(Driver)Falha = "+Driver.getMessage();
            new arquivoLog(log);
            System.out.println(log);
            return false;
            //System.exit(0);
        }
        catch(SQLException Fonte)
        {
           String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...(Fonte)Falha = "+Fonte.getMessage();
            new arquivoLog(log);
            System.out.println(log);
            return false;
           //System.exit(0);
        }
        catch(Exception ef)
        {
           String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...(ef)Falha = "+ef.getMessage();
           new arquivoLog(log);
           System.out.println(log);
           return false;
        }
    }
    public boolean desconecta()
    {
        new arquivoLog("|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Banco de dados Desconectando...");
        boolean result = true;
        try
        {
           Conexao.close();
           new arquivoLog("|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...Sucesso");
        }
        catch(SQLException fecha)
        {
           result=false;
          String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...Falha = "+fecha.getMessage();
            new arquivoLog(log);
        }
        return result;
    }

    public void executeSQL(String sql)
    {
        new arquivoLog("|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  Executando SQL ("+sql+")...");
        try
        {
            statement=Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultset=statement.executeQuery(sql);
           new arquivoLog("|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...Sucesso");
        }
        catch(SQLException sqlex)
        {
            String log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...(sqlex)Falha = "+sqlex.getMessage();
            //quando desconecta do banco de dados por acidente, ou não, esta parte do código
            //utiliza a ultima conexao para reconectar ao banco de dados.
            if(sqlex.getMessage().contains("closed")||(sqlex.getMessage().contains("connection")))
            {
                log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...(sqlex)Falha = Conexão ao BD perdida, necessário Reiniciar conexão!";
                conecta(url, usuario, senha);
            }
            else
                log = "|"+new Data().getDataF()+" ás "+new Data().getHora()+"|  ...(sqlex)Falha = "+sqlex.getMessage();
            new arquivoLog(log);
        }
    }
    public Connection getConection()
    {
        return this.Conexao;
    }
}
