/*
 * ----------------------------------------------- *
 * @author: Arione Guedes dos Santos Junior
 * Grad. Sistemas de Informacao - U.F.M.S.
 * Email: arioneguedes@gmail.com
 * Website: arioneguedes.com.br
 * ----------------------------------------------- *
 */
/* @author Arione Guedes dos Santos Junior */

package Uteis;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Esta classe centraliza a celula de um JTable
 * @author Guedes
 */
public class CellRendererCenter extends DefaultTableCellRenderer
{
    private static final long   serialVersionUID    = 1L;
    public CellRendererCenter()
    {
        super();
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        this.setHorizontalAlignment(CENTER);
        return super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
    }
}