/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTableModels;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Guedes
 */
public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor, KeyListener {
    private JTextField text;
    public Object getCellEditorValue() {
        return text;
    }
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        text = new JTextField();
        text.addKeyListener(this);
        text.setText((String)value);
        return text;
    }
    public void keyTyped(KeyEvent e) {
        if (e.getSource()==text){
            if (e.getKeyChar()<'0' || e.getKeyChar()>'9'){
                JOptionPane.showMessageDialog(null, "Valor inválido");
                e.setKeyChar(' ');
            }
        }
    }
    public void keyPressed(KeyEvent e) {
        // Evento não utilizado, porém precisa ser declarado
    }
    public void keyReleased(KeyEvent e) {
        // Evento não utilizado, porém precisa ser declarado
    }
}