package Uteis;
/**
 * @by Guedes
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
public class Data
{
    private String dia,mes,ano,dia_semana,hora;
    public String DATA;
    public String DataF;
    public String DataSalv;
    SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm:ss");
    public Data()
    {
       DATA="";
       DataF="";
       DataSalv="";
    }
    /**
     *
     * @return HH:mm:ss
     */
    public String getHora()
    {
        le_hora();
        return hora;
    }
    /**
     *
     * @return Segunda-feira, 21 de julho de 2011;
     */
    public String getData()
    {
        le_data();
        DiaSemana();
        Mes();
        DATA = dia_semana+" "+dia+" de "+mes+" de "+ano;
        return DATA;
    }
    /**
     *
     * @return 2011
     */
    public String getAno()
    {
        le_data();
        return this.ano;
    }
    /**
     *
     * @return 20/07/2011
     */
    public String getDataF()
    {
        Date data = new Date();
        mes=""+(1+data.getMonth());
        dia=""+data.getDate();
        ano=""+(1900+data.getYear());
        int m = Integer.parseInt(mes);
        if(m<=9)
            mes="0"+mes;
        DataF = dia+"/"+mes+"/"+ano;
        return DataF;
    }
    /**
     *
     * @return 2011-07-20
     */
    public String getDataSalv()
    {
        le_data();
        return DataSalv;
    }
    private void le_hora()
    {
     Date horaAtual = new Date();
     horaAtual.setHours(horaAtual.getHours());//retirar durante horário de verão
     hora=horaFormatada.format(horaAtual);
    }
    private void le_data()
    {
        Date data = new Date();
        mes=""+(1+data.getMonth());
        dia=""+data.getDate();
        ano=""+(1900+data.getYear());
        dia_semana=""+data.getDay();
        DataF = dia+"/"+mes+"/"+ano;
        DataSalv = ano+"-"+mes+"-"+dia;
    }
    private void DiaSemana()
{
        if(dia_semana.compareTo("1")==0){dia_semana="Segunda-feira,";}
        else if (dia_semana.compareTo("2")==0){dia_semana="Terça-feira,";}
        else if (dia_semana.compareTo("3")==0){dia_semana="Quarta-feira,";}
        else if (dia_semana.compareTo("4")==0){dia_semana="Quinta-feira,";}
        else if (dia_semana.compareTo("5")==0){dia_semana="Sexta-feira,";}
        else if (dia_semana.compareTo("6")==0){dia_semana="Sabado,";}
        else{dia_semana="Domingo,";}
    }
    private void Mes()
    {
            if(mes.compareTo("1")==0){mes="Janeiro";}
            else if(mes.compareTo("2")==0){mes="Fevereiro";}
            else if(mes.compareTo("3")==0){mes="Março";}
            else if(mes.compareTo("4")==0){mes="Abril";}
            else if(mes.compareTo("5")==0){mes="Maio";}
            else if(mes.compareTo("6")==0){mes="Junho";}
            else if(mes.compareTo("7")==0){mes="Julho";}
            else if(mes.compareTo("8")==0){mes="Agosto";}
            else if(mes.compareTo("9")==0){mes="Setembro";}
            else if(mes.compareTo("10")==0){mes="Outubro";}
            else if(mes.compareTo("11")==0){mes="Novembro";}
            else{mes="Dezembro";}
        }
    public String getMes(String mes)
    {
            if(mes.compareTo("01")==0){mes="Janeiro";}
            else if(mes.compareTo("02")==0){mes="Fevereiro";}
            else if(mes.compareTo("03")==0){mes="Março";}
            else if(mes.compareTo("04")==0){mes="Abril";}
            else if(mes.compareTo("05")==0){mes="Maio";}
            else if(mes.compareTo("06")==0){mes="Junho";}
            else if(mes.compareTo("07")==0){mes="Julho";}
            else if(mes.compareTo("08")==0){mes="Agosto";}
            else if(mes.compareTo("09")==0){mes="Setembro";}
            else if(mes.compareTo("10")==0){mes="Outubro";}
            else if(mes.compareTo("11")==0){mes="Novembro";}
            else{mes="Dezembro";}
            return mes;
    }
    /**
     *
     * @param data
     * @return Segunda-feira, 21 de julho de 2011;
     */
    public String getData(Date data)
    {
        mes=""+(1+data.getMonth());
        dia=""+data.getDate();
        ano=""+(1900+data.getYear());
        dia_semana=""+data.getDay();
        if(dia_semana.compareTo("1")==0){dia_semana="Segunda-feira,";}
        else if (dia_semana.compareTo("2")==0){dia_semana="Terça-feira,";}
        else if (dia_semana.compareTo("3")==0){dia_semana="Quarta-feira,";}
        else if (dia_semana.compareTo("4")==0){dia_semana="Quinta-feira,";}
        else if (dia_semana.compareTo("5")==0){dia_semana="Sexta-feira,";}
        else if (dia_semana.compareTo("6")==0){dia_semana="Sabado,";}
        else{dia_semana="Domingo,";}
        if(mes.compareTo("1")==0){mes="Janeiro";}
        else if(mes.compareTo("2")==0){mes="Fevereiro";}
        else if(mes.compareTo("3")==0){mes="Março";}
        else if(mes.compareTo("4")==0){mes="Abril";}
        else if(mes.compareTo("5")==0){mes="Maio";}
        else if(mes.compareTo("6")==0){mes="Junho";}
        else if(mes.compareTo("7")==0){mes="Julho";}
        else if(mes.compareTo("8")==0){mes="Agosto";}
        else if(mes.compareTo("9")==0){mes="Setembro";}
        else if(mes.compareTo("10")==0){mes="Outubro";}
        else if(mes.compareTo("11")==0){mes="Novembro";}
        else{mes="Dezembro";}
        DATA = dia_semana+" "+dia+" de "+mes+" de "+ano;
        //DATA = dia+" de "+mes+" de "+ano;
        return DATA;
    }
    public String getDataAssuncao(Date data)
    {
        mes=""+(1+data.getMonth());
        dia=""+data.getDate();
        ano=""+(1900+data.getYear());
        dia_semana=""+data.getDay();
        if(dia_semana.compareTo("1")==0){dia_semana="Segunda-feira";}
        else if (dia_semana.compareTo("2")==0){dia_semana="Terça-feira";}
        else if (dia_semana.compareTo("3")==0){dia_semana="Quarta-feira";}
        else if (dia_semana.compareTo("4")==0){dia_semana="Quinta-feira";}
        else if (dia_semana.compareTo("5")==0){dia_semana="Sexta-feira";}
        else if (dia_semana.compareTo("6")==0){dia_semana="Sabado";}
        else{dia_semana="Domingo";}
        if(mes.compareTo("1")==0){mes="Janeiro";}
        else if(mes.compareTo("2")==0){mes="Fevereiro";}
        else if(mes.compareTo("3")==0){mes="Março";}
        else if(mes.compareTo("4")==0){mes="Abril";}
        else if(mes.compareTo("5")==0){mes="Maio";}
        else if(mes.compareTo("6")==0){mes="Junho";}
        else if(mes.compareTo("7")==0){mes="Julho";}
        else if(mes.compareTo("8")==0){mes="Agosto";}
        else if(mes.compareTo("9")==0){mes="Setembro";}
        else if(mes.compareTo("10")==0){mes="Outubro";}
        else if(mes.compareTo("11")==0){mes="Novembro";}
        else{mes="Dezembro";}
        DATA = dia+" de "+mes+" de "+ano+" ("+dia_semana+")";
        //DATA = dia+" de "+mes+" de "+ano;
        return DATA;
    }
    public Date DataExtensoToDate(String data)
    {
        //17 de janeiro de 2015
        StringTokenizer temp = new StringTokenizer(data);
        String dia = temp.nextToken();
        temp.nextToken();
        String mes = temp.nextToken();
        temp.nextToken();
        String ano = temp.nextToken();
        
        if(mes.compareTo("Janeiro")==0){mes="0";}
        else if(mes.compareToIgnoreCase("Fevereiro")==0){mes="1";}
        else if(mes.compareToIgnoreCase("Março")==0){mes="2";}
        else if(mes.compareToIgnoreCase("Abril")==0){mes="3";}
        else if(mes.compareToIgnoreCase("Maio")==0){mes="4";}
        else if(mes.compareToIgnoreCase("Junho")==0){mes="5";}
        else if(mes.compareToIgnoreCase("Julho")==0){mes="6";}
        else if(mes.compareToIgnoreCase("Agosto")==0){mes="7";}
        else if(mes.compareToIgnoreCase("Setembro")==0){mes="8";}
        else if(mes.compareToIgnoreCase("Outubro")==0){mes="9";}
        else if(mes.compareToIgnoreCase("Novembro")==0){mes="10";}
        else{mes="11";}
        int day = Integer.parseInt(dia);
        int month = Integer.parseInt(mes);
        int year = (Integer.parseInt(ano)-1900);
        System.out.println(year+1900);
        return new Date(year,month,day);
    }
     /**
     * Entra 20/07/1979 e sai 1979/07/20
     * @param data
     * @return 
     */
    public String formatData(String data)
    {
        String date="";
        if(data.equalsIgnoreCase("__/__/____"))
        {
            date = formatData(new Data().getDataF());
        }
        else
        {
            date = date + data.substring(6, 10);
            date = date + "-";
            date = date + data.substring(3, 5);
            date = date + "-";
            date = date + data.substring(0,2);
        }
        return date;
    }
    /**
     * Entra 2013-01-21 e sai 21/01/2013
     * @param data
     * @return 
     */
    public static String alteraData(String data)
    {
        String date="";
            date = date + data.substring(8,10);
            date = date + "/";
            date = date + data.substring(5, 7);
            date = date + "/";
            date = date + data.substring(0, 4);
        return date;
    }
    /**
     * Este método transforma a data do BD 2013-07-20 no objeto Date
     * @param data
     * @return Date
     */
    public static Date dataRequerimento(String data)
    {
        Date dataNova = new Date();
        String s_ano = data.substring(0, 4);
        String s_mes = data.substring(5,7);
        String s_dia = data.substring(8,10);
        int ano = (Integer.parseInt(s_ano)-1900);
        int mes = (Integer.parseInt(s_mes)-1);
        int dia = Integer.parseInt(s_dia);
        dataNova.setDate(dia);
        dataNova.setMonth(mes);
        dataNova.setYear(ano);
        System.out.println(ano+"-"+mes+"-"+dia);
        return dataNova;
    }
    /**
     * Este método transforma o objeto Date para cadastro no BD
     * Date to 2012-07-20
     * @param data
     * @return 
     */
    public static String DateToString(Date data)
    {
        String auxData = (1900+data.getYear())+"-"+(1+data.getMonth())+"-"+data.getDate();
        return auxData;
    }
    public static String dataBuscaRelatorio(Date data)
    {
        String dataCompleta = "";
        int ano = 1900+data.getYear();
        dataCompleta = ""+ano;
        int mes = 1+data.getMonth();
        if(mes<10)
            dataCompleta += "-0"+mes;
        else
            dataCompleta += "-"+mes;
        int dia = data.getDate();
        if(dia<10)
            dataCompleta += "-0"+dia;
        else
            dataCompleta += "-"+dia;
        
        return dataCompleta;
    }
}
