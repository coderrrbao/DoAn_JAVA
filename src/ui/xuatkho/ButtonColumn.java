package ui.xuatkho;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class ButtonColumn extends AbstractCellEditor
    implements TableCellRenderer, TableCellEditor, ActionListener {
  private JButton renderButton;
  private JButton editButton;
  private JTable table;
  private Action action;

  public ButtonColumn(JTable table, Action action, int column) {
    this.table = table;
    this.action = action;

    FlatSVGIcon trashIcon = new FlatSVGIcon("assets/icon/xoa.svg", 45, 45);

    renderButton = new JButton(trashIcon);
    renderButton.setBorderPainted(false);
    renderButton.setContentAreaFilled(false);

    editButton = new JButton(trashIcon);
    editButton.setBorderPainted(false);
    editButton.setContentAreaFilled(false);
    editButton.setFocusable(false);
    editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    editButton.addActionListener(this);

    TableColumnModel columnModel = table.getColumnModel();
    columnModel.getColumn(column).setCellRenderer(this);
    columnModel.getColumn(column).setCellEditor(this);

    table.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
            int col = table.columnAtPoint(e.getPoint());
            if (col == column && row != -1) {
              if (table.isEditing())
                table.getCellEditor().stopCellEditing();
              editButton.doClick();
            }
          }
        });
  }

  @Override
  public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    return renderButton;
  }

  @Override
  public Component getTableCellEditorComponent(
      JTable table, Object value, boolean isSelected, int row, int column) {
    return editButton;
  }

  @Override
  public Object getCellEditorValue() {
    return "";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    int row = table.convertRowIndexToModel(table.getEditingRow());
    fireEditingStopped();
    if (row == -1)
      row = table.getSelectedRow();
    if (row != -1) {
      ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + row);
      action.actionPerformed(event);
    }
  }
}
