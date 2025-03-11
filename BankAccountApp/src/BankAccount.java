import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// BankAccount class to manage balance and transactions
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

// GUI class
class BankGUI {
    private BankAccount account;
    private JLabel balanceLabel;
    private JTextField amountField;

    public BankGUI() {
        account = new BankAccount(1000.0); // Initial balance
        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame("Simple Bank System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Balance label
        balanceLabel = new JLabel("Balance: $" + account.getBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(balanceLabel, gbc);

        // Amount field
        amountField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(amountField, gbc);

        // Deposit button
        JButton depositButton = new JButton("Deposit");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(depositButton, gbc);

        // Withdraw button
        JButton withdrawButton = new JButton("Withdraw");
        gbc.gridx = 1;
        frame.add(withdrawButton, gbc);

        // Message label
        JLabel messageLabel = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(messageLabel, gbc);

        // Action Listeners
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    account.deposit(amount);
                    balanceLabel.setText("Balance: $" + account.getBalance());
                    messageLabel.setText("Deposit successful!");
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Invalid input!");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (account.withdraw(amount)) {
                        balanceLabel.setText("Balance: $" + account.getBalance());
                        messageLabel.setText("Withdrawal successful!");
                    } else {
                        messageLabel.setText("Insufficient funds!");
                    }
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Invalid input!");
                }
            }
        });

        frame.setVisible(true);
    }
}
