package view;

import controller.Facade;
import controller.Login;
import models.ClassProductList;
import models.UserInformation;
import util.ProductIterator;

import javax.swing.*;

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
    private JButton viewTrading;
    private JButton markOffering;
    private JButton submitOffering;
    private JButton createReminder;
    private JButton viewOffering;

    private static AppView appInstance;

    private final JFrame frame;

    private final Facade facade;

    private boolean isUserLoggedIn = false;

    private AppView() {
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

        addTrading.addActionListener(e -> AppView.this.facade.addTrading());
        viewTrading.addActionListener(e -> AppView.this.facade.viewTrading());
        addProduct.addActionListener(e -> AppView.this.facade.productOperation());
        viewOffering.addActionListener(e -> AppView.this.facade.viewOffering());
        markOffering.addActionListener(e -> AppView.this.facade.markOffering());
        submitOffering.addActionListener(e -> AppView.this.facade.submitOffering());
        createReminder.addActionListener(e -> AppView.this.facade.remind());
    }

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

    public void SetProductList(ClassProductList associatedProducts) {
        ProductIterator it = new ProductIterator(associatedProducts);
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
}
