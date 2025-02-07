import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        float totalGradePoints = 0;
        int totalCredits = 0;
        int ask;

        do {
            GWACalculator calculate = new GWACalculator();

            // Ask for user input
            float grade = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter your grade: "));
            int credit = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter credit: "));

            // Set values in the GWACalculator object
            calculate.setGrade(grade);
            calculate.setCredit(credit);

            // Compute grade weighted points
            calculate.compute();

            // Accumulate total
            totalGradePoints += calculate.getTotalGradePoints();
            totalCredits += calculate.getTotalCredits();

            // Ask if the user wants to continue
            ask = JOptionPane.showInternalConfirmDialog(null, "Would you like to enter another input? (yes/no)");

        } while (ask!=1);  // Loop until user enters "no"

        // Compute and display final GWA
        if (totalCredits > 0) {
            float gwa = totalGradePoints / totalCredits; // Compute GWA
            JOptionPane.showMessageDialog(null, "Your Grade Weighted Average (GWA) is: " + gwa);
        } else {
            JOptionPane.showMessageDialog(null, "No valid credits entered. Cannot compute GWA.");
        }
    }
}
