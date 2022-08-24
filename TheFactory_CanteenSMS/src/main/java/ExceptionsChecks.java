import javax.swing.*;

public class ExceptionsChecks {
    static JFrame warning;

    public static void phoneCheck(){
        warning = new JFrame();
        JOptionPane.showMessageDialog(warning,"Number field hasn't been full filled.");
    }
    public static void tableNumberCheck(){
        warning = new JFrame();
        JOptionPane.showMessageDialog(warning,"Table hasn't been assigned.");
    }
}
