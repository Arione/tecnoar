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
public class pesquisarUsuario
{
    Conexao conexao;
    public String nomeUsuario;
    public String graduacaoUsuario;
    public String guerraUsuario;
    public String matriculaUsuario;
    public int idUsuario;
    public int levelUsuario;
    public boolean comum;
    public boolean chefeDosCov;
    public boolean adjunto;
    public boolean supervisorCov;
    public boolean supervisorAdjunto;
    public boolean secaoMaterial;
    public boolean estatistica;
    public boolean darthVader;
    public pesquisarUsuario(Conexao con)
    {
        this.conexao = con;
        matriculaUsuario();
        levelUsuario();
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
    
    private void levelUsuario()
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
            graduacaoUsuario = (conexao.resultset.getString("graduacao"));
            guerraUsuario = (conexao.resultset.getString("nome_guerra"));
            idUsuario = (conexao.resultset.getInt("id_militar"));
            comum = new Boolean(conexao.resultset.getString("comum"));
            chefeDosCov = new Boolean(conexao.resultset.getString("chefe_dos_cov"));
            adjunto = new Boolean(conexao.resultset.getString("adjunto"));
            supervisorCov = new Boolean(conexao.resultset.getString("supervisor_cov"));
            supervisorAdjunto = new Boolean(conexao.resultset.getString("supervisor_adjunto"));
            secaoMaterial = new Boolean(conexao.resultset.getString("secao_material"));
            estatistica = new Boolean(conexao.resultset.getString("estatistica"));
            darthVader = new Boolean(conexao.resultset.getString("darth_vader"));
        }
        catch (Exception exc)
        {
            new arquivoLog(exc.getMessage());
            levelUsuario = 1;
        }
    }
}
