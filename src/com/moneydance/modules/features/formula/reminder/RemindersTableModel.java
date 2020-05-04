package com.moneydance.modules.features.formula.reminder;

import com.infinitekind.moneydance.model.Reminder;
import com.moneydance.modules.features.formula.MDApi;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RemindersTableModel extends AbstractTableModel {

    private final Reminder NEW_ENTRY = new Reminder(null);

    private List<Reminder> reminders;

    public RemindersTableModel(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    Reminder getReminder(int index) {
        return reminders.get(index);
    }

    int insert() {
        int insertIndex = reminders.size();
        reminders.add(insertIndex, NEW_ENTRY);
        fireTableRowsInserted(insertIndex, insertIndex);
        return insertIndex;
    }

    @Override
    public int getRowCount() {
        return reminders.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 1:
                return "Next payment";
            default:
                return "Name";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reminder reminder = reminders.get(rowIndex);
        switch (columnIndex) {
            case 1:
                return reminder.getNextOccurance(29991231);
            default:
                return reminder.getDescription();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 && NEW_ENTRY.equals(reminders.get(rowIndex));
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (!isCellEditable(rowIndex, columnIndex)) {
            return;
        }

        Reminder reminder = (Reminder)aValue;
        MDApi.enableReminder(reminder);

        reminders.set(rowIndex, reminder);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}
