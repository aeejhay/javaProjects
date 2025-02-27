import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CafeGUI extends JFrame {
    private JTextField usernameField;
    private JComboBox<String> pcSelector;
    private JButton loginButton, logoutButton;

    private HashMap<String, UserSession> sessions = new HashMap<>();
    private HashMap<String, JLabel[]> sessionLabels = new HashMap<>();

    public CafeGUI() {
        setTitle("Internet Cafe System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Internet Cafe Login System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(titleLabel, gbc);

        JLabel pcLabel = new JLabel("Select PC:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(pcLabel, gbc);

        pcSelector = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            pcSelector.addItem("PC" + i);
        }
        gbc.gridx = 1;
        add(pcSelector, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(loginButton, gbc);

        logoutButton = new JButton("Logout");
        gbc.gridx = 1;
        add(logoutButton, gbc);

        // Labels for session information
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        add(new JLabel("PC Status Overview:", SwingConstants.CENTER), gbc);

        // Creating session labels dynamically for each PC
        int row = 5;
        for (int i = 1; i <= 10; i++) {
            String pcName = "PC" + i;

            JLabel pcTitle = new JLabel(pcName + " Status: ");
            JLabel loginTimeLabel = new JLabel("Login: -");
            JLabel sessionDurationLabel = new JLabel("Duration: -");
            JLabel logoutTimeLabel = new JLabel("Logout: -");

            gbc.gridy = row++;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            add(pcTitle, gbc);

            gbc.gridx = 1;
            add(loginTimeLabel, gbc);

            gbc.gridx = 2;
            add(sessionDurationLabel, gbc);

            gbc.gridx = 3;
            add(logoutTimeLabel, gbc);

            sessionLabels.put(pcName, new JLabel[]{loginTimeLabel, sessionDurationLabel, logoutTimeLabel});
        }

        loginButton.addActionListener(new LoginAction());
        logoutButton.addActionListener(new LogoutAction());

        setVisible(true);
    }

    // Get selected PC
    private String getSelectedPC() {
        return (String) pcSelector.getSelectedItem();
    }

    // Login Action
    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String selectedPC = getSelectedPC();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a username.");
                return;
            }

            if (sessions.containsKey(selectedPC)) {
                JOptionPane.showMessageDialog(null, selectedPC + " is already in use. Logout first.");
                return;
            }

            UserSession newSession = new UserSession(username);
            sessions.put(selectedPC, newSession);

            JLabel[] labels = sessionLabels.get(selectedPC);
            labels[0].setText("Login: " + newSession.getLoginTime());
            labels[1].setText("Duration: Ongoing...");
            labels[2].setText("Logout: -");

            usernameField.setText("");
        }
    }

    // Logout Action
    private class LogoutAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedPC = getSelectedPC();

            if (!sessions.containsKey(selectedPC)) {
                JOptionPane.showMessageDialog(null, "No active session on " + selectedPC);
                return;
            }

            UserSession session = sessions.get(selectedPC);
            session.logout();

            JLabel[] labels = sessionLabels.get(selectedPC);
            labels[1].setText("Duration: " + session.getSessionDuration());
            labels[2].setText("Logout: " + session.getLogoutTime());

            sessions.remove(selectedPC);
        }
    }
}
