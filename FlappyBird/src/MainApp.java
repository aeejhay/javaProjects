import javax.swing.*;

public class MainApp {
    public static void main(String[] args) throws Exception{
        int bordWidth = 360;
        int bordHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        //frame.setVisible(true);
        frame.setSize(bordWidth, bordHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();//so that the title bar not included in the height
        flappyBird.requestFocus();
        frame.setVisible(true);


    }
}
