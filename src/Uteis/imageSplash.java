/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Uteis;

import JFrame_TecnoAr.jfLogar;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class imageSplash extends JFrame
{
    public static JProgressBar jpBarra;
    public ImageIcon imag;
    public Image icon;
    public JLabel jlImage;
    public JLabel texto;
    public AbsoluteLayout absoluto = new AbsoluteLayout();
    public AbsoluteConstraints absImage,absTexto,absBarra,absLogar;
    public JPanel panel;
    public imageSplash()
    {
        jpBarra = new JProgressBar();
        jpBarra.setMaximum(100);
        jpBarra.setMinimum(0);
        jpBarra.setStringPainted(true);
        jpBarra.setForeground(Color.BLUE);
        imag = new ImageIcon(getClass().getResource("/Imagens/imagem_inicio.png"));
        jlImage = new JLabel();
        jlImage.setIcon(imag);
        texto = new JLabel("Guedes Corporation ™");
        texto.setForeground(Color.LIGHT_GRAY);
        panel = new jfLogar().jPanel1;
        panel.setMaximumSize(panel.getSize());
        icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagens/icon_main.png"));
        absImage = new AbsoluteConstraints(-1, -1);
        absTexto = new AbsoluteConstraints(31,380);
        absBarra = new AbsoluteConstraints(31, 411, 569,17);
        absLogar = new AbsoluteConstraints(280,200);
        this.setLayout(absoluto);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("TecnoAR");
        this.setAlwaysOnTop(true);
        this.getContentPane().add(texto,absTexto);
        this.getContentPane().add(jpBarra,absBarra);
        this.getContentPane().add(panel,absLogar,2);
        this.getContentPane().add(jlImage,absImage);
        this.getContentPane().getComponent(2).setVisible(false);
        this.setResizable(false);
        this.setIconImage(icon);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}