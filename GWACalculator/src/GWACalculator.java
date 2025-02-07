public class GWACalculator {
    private float grade;
    private int credit;
    private float totalGradePoints = 0;
    private int totalCredits = 0;

    // Default Constructor
    public GWACalculator() {}

    // Compute method to accumulate weighted grade points
    public void compute() {
        totalGradePoints = grade * credit; // Compute grade points
        totalCredits = credit; // Store credit
    }

    // Setter for grade
    public void setGrade(float grade) {
        this.grade = grade;
    }

    // Setter for credit
    public void setCredit(int credit) {
        this.credit = credit;
    }

    // Getter for total grade points
    public float getTotalGradePoints() {
        return totalGradePoints;
    }

    // Getter for total credits
    public int getTotalCredits() {
        return totalCredits;
    }
}
