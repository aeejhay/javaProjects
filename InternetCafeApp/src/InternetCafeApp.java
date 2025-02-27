import javax.swing.SwingUtilities;

public class InternetCafeApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CafeGUI::new);
    }
}
