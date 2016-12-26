/*
 * ----------------------------------------------- *
 * @author: Arione Guedes dos Santos Junior
 * Grad. Sistemas de Informação - U.F.M.S.
 * Email: arioneguedes@gmail.com
 * Website: arioneguedes.com.br
 * ----------------------------------------------- *
 */

package Uteis;
/*
 * ADICIONAR NO CONSTRUTOR ONDE IRÁ SER ADICIONADO  O JTOOLTIP
 * EXEMPLO:
 * JButton jb = new JButton("botao")
 * {
 *    public JToolTip createToolTip()
 *     {
 *           JToolTipWithIcon tip = null ;
 *       try
 *       {
 *         tip = new JToolTipWithIcon(new ImageIcon("user.png"));
 *       }
 *       catch (Exception ex)
 *       {
 *         ex.printStackTrace();
 *       }
 *       tip.setComponent( this ) ;
 *       return tip ;
 *     }
 * };
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalToolTipUI;

public class JToolTipWithIcon extends JToolTip
{
  ImageIcon icon ;

  public JToolTipWithIcon( ImageIcon icon )
  {
    this.icon = icon ;
    setUI( new IconToolTipUI() ) ;
  }

  public JToolTipWithIcon( MetalToolTipUI toolTipUI )
  {
    setUI( toolTipUI ) ;
  }

  private class IconToolTipUI extends MetalToolTipUI
  {
    public void paint(Graphics g, JComponent c)
    {
      FontMetrics metrics = c.getFontMetrics( c.getFont() ) ;
      Dimension size = c.getSize() ;
      g.setColor( c.getBackground() ) ;
      g.fillRect( 0, 0, size.width, size.height ) ;
      int x = 3 ;
      if( icon != null )
      {
        icon.paintIcon( c, g, 0, 0 ) ;
        x += icon.getIconWidth() + 1 ;
      }
      g.setColor( c.getForeground() ) ;
      g.drawString( ((JToolTip)c).getTipText(), x, metrics.getHeight() ) ;
    }

    public Dimension getPreferredSize(JComponent c)
    {
      FontMetrics metrics = c.getFontMetrics(c.getFont());
      String tipText = ((JToolTip)c).getTipText() ;
      if( tipText == null )
      {
        tipText = "";
      }
      int width = SwingUtilities.computeStringWidth( metrics, tipText ) ;
      int height = metrics.getHeight() ;
      if( icon != null )
      {
        width += icon.getIconWidth() + 1 ;
        height = icon.getIconHeight() > height ? icon.getIconHeight() : height + 4 ;
      }
      return new Dimension( width + 6, height ) ;
    }
  }
}