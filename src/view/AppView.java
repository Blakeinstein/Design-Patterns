package view;

import controller.Facade;
import controller.Login;
import models.ClassProductList;
import models.UserInformation;
import util.ProductIterator;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Application GUI entry point.
 * Instantiates an instance of a facade to control and
 * execute several actions in the app.
 */
public class AppView {
    private JButton loginButton;
    private JButton createNewUserButton;
    private JPanel mainFrame;
    private JTextPane loggedInUserInfo;
    private JButton refresh;
    private JList<String> productView;
    private JPanel toolbox;
    private JButton addTrading;
    private JButton addProduct;
    private JButton markOffering;
    private JButton createReminder;
    private JButton viewTrading;
    private JScrollPane borderText;

    private static AppView appInstance;

    private final JFrame frame;

    private final Facade facade;

    private boolean isUserLoggedIn = false;

    private final ArrayList<JButton> toolboxButtons;

    private AppView() {
        // Create a facade to control app actions.
        this.facade = new Facade();
        this.frame = new JFrame("PTBS");
        this.frame.setContentPane(this.mainFrame);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        loginButton.addActionListener(e -> AppView.this.changeLoginState());

        createNewUserButton.addActionListener(e -> AppView.this.createNewUserFlow());

        productView.addListSelectionListener(e -> {
            try {
                if (!e.getValueIsAdjusting()) {
                    AppView.this.facade.selectProduct(
                            AppView.this.productView.getSelectedValue()
                    );
                }
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(
                        AppView.this.frame,
                        ee.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        refresh.addActionListener(e -> AppView.this.facade.attachProductToUser());

        addProduct.addActionListener(e -> AppView.this.facade.productOperation());

        createReminder.addActionListener(e -> AppView.this.facade.remind());

        this.toolboxButtons = new ArrayList<>();
        this.toolboxButtons.add(this.addTrading);
        this.toolboxButtons.add(this.viewTrading);
        this.toolboxButtons.add(this.markOffering);
    }

    /**
     * Instantiate singleton if not already created and return the instance
     * @return AppView instance
     */
    public static AppView Get(){
        if (AppView.appInstance == null) {
            AppView.appInstance = new AppView();
        }
        return AppView.appInstance;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void createNewUserFlow() {
        LoginMenu dialog = new LoginMenu(
                true,
                new LoginMenu.LoginFormActions() {
                    public void onOk(String userName, String password, Login.USER_TYPE userType, boolean isRegister) throws Exception{
                        if (Login.GetInstance().hasUser(userName))
                            throw new Exception(String.format("User %s already exists", userName));
                        AppView.this.facade.createUser(
                                new UserInformation(userName, password, userType)
                        );
                        JOptionPane.showMessageDialog(
                                AppView.this.frame,
                                String.format("%s: %s created successfully", userType, userName),
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
        );
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void changeLoginState() {
        this.isUserLoggedIn = this.isUserLoggedIn ? this.facade.logout() : this.facade.login();
        this.loginButton.setText(this.isUserLoggedIn ? "Logout" : "Login");
        this.createNewUserButton.setVisible(!this.isUserLoggedIn);
        if (this.isUserLoggedIn) {
            this.loggedInUserInfo.setText(String.format("Logged in as %s", this.facade.getLoggedInUserName()));
        }
        this.loggedInUserInfo.setVisible(this.isUserLoggedIn);
        this.toolbox.setVisible(this.isUserLoggedIn);
        this.productView.setVisible(this.isUserLoggedIn);
    }

    public void SetProductList(ClassProductList products) {
        ProductIterator it = new ProductIterator(products);
        var newModel = new DefaultListModel<String>();
        while (it.hasNext()) {
            var prod = it.Next();
            newModel.addElement(prod.getName());
        }
        this.productView.setModel(newModel);
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public ArrayList<JButton> getToolboxButtons() {
        // clear action listeners
        for (var b : this.toolboxButtons) {
            for (var a : b.getActionListeners()) {
                b.removeActionListener(a);
            }
        }
        return this.toolboxButtons;
    }

    public JScrollPane getBorderText() {
        return this.borderText;
    }
}
