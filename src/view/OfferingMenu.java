package view;

import controller.Login;
import models.OfferingList;
import util.OfferingIterator;

import javax.swing.*;

public class OfferingMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JList<String> offeringList;

    public OfferingMenu(Login.USER_TYPE userType, OfferingList offerings) {
        setContentPane(contentPane);
        setModal(true);
        setTitle(String.format("%s offerings", userType == Login.USER_TYPE.Buyer ? "Buyer" : "Seller"));
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        var model = new DefaultListModel<String>();
        var it = new OfferingIterator(offerings);
        while (it.hasNext()) {
            var next = it.Next();
            model.addElement(
                    String.format(
                            "%s - %s",
                            next.getProduct().getName(),
                            next.getPerson().getName()
                    )
            );
        }
        this.offeringList.setModel(model);
    }

    private void onOK() {
        dispose();
    }
}
