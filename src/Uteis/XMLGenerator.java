package Uteis;

import java.awt.Color;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JLabel;

public class XMLGenerator
{
	public Color corBack;
        public Color corForg;
        public XMLGenerator(){}
        public void setCorBack(Color back){this.corBack = back;}
        public Color getCorBack(){return corBack;}
        public void setCorForg(Color back){this.corForg = back;}
        public Color getCorForg(){return corForg;}
        /*comeca implementacao swing*/
        public void setAll()
        {
            this.corBack = new Color(240,240,240);
            this.corForg = new Color(0,0,0);
        }
        @Override
	public String toString(){return getClass().getName();}
	public void saveXml(String path)
	{
		XMLEncoder e=null;
		try
		{
			e = new XMLEncoder(	new BufferedOutputStream(  new FileOutputStream(path) )	);
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
	    e.writeObject(this);
	    e.close();
	}
	public Object loadXml(String path)
	{
		XMLDecoder d=null;
		try
		{
			d = new XMLDecoder(  new BufferedInputStream(  new FileInputStream(path)));
                        Object result = d.readObject();
                        d.close();
                        return result;
		}
		catch (FileNotFoundException e)
		{
                    setAll();
                    saveXml(path);
                    loadXml(path);
		}
	   
	    return null;
	}
	
}