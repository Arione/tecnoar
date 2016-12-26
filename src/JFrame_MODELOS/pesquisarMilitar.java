/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrame_MODELOS;

import Conexao.Conexao;
import Log.arquivoLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Guedes
 */
public class pesquisarMilitar
{
    Conexao conexao;
    public String nomeUsuario;
    public String graduacaoUsuario;
    public String guerraUsuario;
    public String matriculaUsuario;
    public int idUsuario;
    public pesquisarMilitar (Conexao con)
    {
        this.conexao = con;
        nomeUsuario();
        graduacaoUsuario();
        guerraUsuario();
        matriculaUsuario();
        idUsuario();
    }
    /* Pesquisar nome completo do usuario do sistema
    * @return nome
    */
    private void nomeUsuario()
    {
        try
        {
            File logado = new File("log.temp");
            FileReader fileReader = new FileReader(logado);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String userLogado = bufferedReader.readLine();
            bufferedReader.close();
            conexao.executeSQL("select * from bombeiro_19.militar WHERE matricula="+userLogado);
            conexao.resultset.first();
            nomeUsuario = (conexao.resultset.getString("nome"));
        }
        catch (Exception exc)
        {
            new arquivoLog(exc.getMessage());
            nomeUsuario = "Usuario nao encontrado";
        }
    }
    private void graduacaoUsuario()
    {
        try
        {
            File logado = new File("log.temp");
            FileReader fileReader = new FileReader(logado);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String userLogado = bufferedReader.readLine();
            bufferedReader.close();
            conexao.executeSQL("select * from bombeiro_19.militar WHERE matricula="+userLogado);
            conexao.resultset.first();
            graduacaoUsuario = (conexao.resultset.getString("graduacao"));
        }
        catch (Exception exc)
        {
            new arquivoLog(exc.getMessage());
            graduacaoUsuario = "Usuario nao encontrado";
        }
    }
    private void guerraUsuario()
    {
        try
        {
            File logado = new File("log.temp");
            FileReader fileReader = new FileReader(logado);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String userLogado = bufferedReader.readLine();
            bufferedReader.close();
            conexao.executeSQL("select * from bombeiro_19.militar WHERE matricula="+userLogado);
            conexao.resultset.first();
            guerraUsuario = (conexao.resultset.getString("nome_guerra"));
        }
        catch (Exception exc)
        {
            new arquivoLog(exc.getMessage());
            guerraUsuario = "Usuario nao encontrado";
        }
    }
    private void matriculaUsuario()
    {
        try
        {
            File logado = new File("log.temp");
            FileReader fileReader = new FileReader(logado);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String userLogado = bufferedReader.readLine();
            bufferedReader.close();
            matriculaUsuario = userLogado;
        }
        catch (Exception exc)
        {
            new arquivoLog(exc.getMessage());
            matriculaUsuario = "Usuario nao encontrado";
        }
    }
    private void idUsuario()
    {
        try
        {
            File logado = new File("log.temp");
            FileReader fileReader = new FileReader(logado);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String userLogado = bufferedReader.readLine();
            bufferedReader.close();
            conexao.executeSQL("select * from bombeiro_19.militar WHERE matricula="+userLogado);
            conexao.resultset.first();
            idUsuario = (conexao.resultset.getInt("id_militar"));
        }
        catch (Exception exc)
        {
            new arquivoLog(exc.getMessage());
            guerraUsuario = "Usuario nao encontrado";
        }
    }   
    
}
