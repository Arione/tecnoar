package Uteis;
import javax.swing.*;  
import javax.swing.text.*; 
/**
 * @author Guedes
 */
public class JtextField_largura_fixa extends PlainDocument
{
	private int iMaxLength;
 
	public JtextField_largura_fixa(int maxlen) 
        {
		super();
		iMaxLength = maxlen;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException	
        {
		if (str == null) return;

		if (iMaxLength <= 0)        // aceitara qualquer no. de caracteres
		{
			super.insertString(offset, str, attr);
			return;
		}

		int ilen = (getLength() + str.length());
		if (ilen <= iMaxLength)    // se o comprimento final for menor...
			super.insertString(offset, str, attr);   // ...aceita str
		}
}