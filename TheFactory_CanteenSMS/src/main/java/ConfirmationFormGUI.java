import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationFormGUI extends JFrame {

    private static Label idLbl,nameLbl,phoneNumberLbl;

    private static JButton confirm;

    public  ConfirmationFormGUI(Guest guest){
        super("Confirmation");
        this.setSize(260,200);//width, height...change to desired dimensions.
        this.setLocationRelativeTo(null);//automatically centres our frame in the screen
        this.setLayout(new GridLayout(3,1));

        this.nameLbl = new Label("Name: "+ guest.getName());
        this.phoneNumberLbl =new Label("PhoneNumber: "+guest.getPhoneNumber());
        this.idLbl = new Label("ID :"+guest.getId());
        this.setLayout(new GridLayout(6,1,5,5));

//        this.add(idLbl);
        this.add(nameLbl);
        this.add(phoneNumberLbl);
        this.add(idLbl);

        confirm = new JButton("Confirm");
        this.add(confirm);
        confirm.setBackground(Color.GREEN);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainLayout.addGuestOrder(guest);
                MainLayout.totalOrders++;
                DatabaseCommands.insertQuery(guest.getName(),guest.getPhoneNumber(),String.valueOf( guest.getId()));
                FormGUI.mainFrame.dispose();
                ConfirmationFormGUI.this.dispose();
            }
        });
        this.setVisible(true);
    }




}
