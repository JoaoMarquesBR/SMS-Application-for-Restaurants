import javax.swing.*;

public class MyClass {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame();
        Application.sendSMS("+15196154641","se fude");
        mainFrame.setTitle("SMS App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//prevents memory leak
        mainFrame.setLocationRelativeTo(null);//automatically centres our frame in the screen
        mainFrame.setSize(100,100);
        mainFrame.setVisible(true);

    }
}
