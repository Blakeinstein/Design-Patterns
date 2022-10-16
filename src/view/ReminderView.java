package view;

import models.Reminder;
import util.Utils;

import javax.swing.*;
import java.util.Date;

public class ReminderView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JList overdueList;
    private JList pendingList;

    public ReminderView(Reminder reminder) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> dispose());

        var overdue = new DefaultListModel<String>();
        for (var t : reminder.getOverdue()) {
            overdue.addElement(
                    Utils.FormatTimeDifference(new Date(), t.getDueDate())
            );
        }
        this.overdueList.setModel(overdue);

        var pending = new DefaultListModel<String>();
        for (var t : reminder.getPending()) {
            pending.addElement(
                    Utils.FormatTimeDifference(t.getDueDate(), new Date())
            );
        }
        this.pendingList.setModel(pending);
    }

}
