import view.AppView;

public class Main {
    public static void main(String[] args) {
        // Program entrypoint.
        // Create an instance of an application gui. which creates
        // an instance of its own facade to handle application actions.
        AppView app = AppView.Get();

        app.show();
    }
}