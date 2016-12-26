/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

/**
 *
 * @author Guedes
 */
public class testa_conexao
{
    public static void main(String args[])
    {
        Conexao conexao = new Conexao();
        if(conexao.conecta("localhost","root","semente"))
            System.out.println("conectou");
        else
            System.out.println("não funcionou");
    }
}
