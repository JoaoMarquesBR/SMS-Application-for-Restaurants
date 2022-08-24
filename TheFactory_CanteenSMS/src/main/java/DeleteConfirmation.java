import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteConfirmation extends JFrame {
        private static Label idLbl,nameLbl,phoneNumberLbl,deletingLbl;

        private static JButton confirm;

    public static void main(String[] args) {
//        new DeleteConfirmation(new Guest("Joao","591931","4034"));
    }


        public  DeleteConfirmation(Guest guest){
            super("Deleting Confirmation");
            this.setSize(260,200);//width, height...change to desired dimensions.
            this.setLocationRelativeTo(null);//automatically centres our frame in the screen
            this.setLayout(new GridLayout(3,1));

            this.deletingLbl = new Label("                           DELETING");
            this.nameLbl = new Label("Name: "+ guest.getName());
            this.phoneNumberLbl =new Label("PhoneNumber: "+guest.getPhoneNumber());
            this.idLbl = new Label("ID :"+guest.getId());
            this.setLayout(new GridLayout(6,1,5,5));
            this.add(deletingLbl);
            this.add(nameLbl);
            this.add(phoneNumberLbl);
            this.add(idLbl);

            confirm = new JButton("Confirm");
            this.add(confirm);
            confirm.setBackground(Color.RED);
            confirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DatabaseCommands.removeGuest(guest.getPhoneNumber());
                    MainLayout.guestList.remove(guest);
                   MainLayout.updateList();
                    DeleteConfirmation.this.dispose();
                }
            });
            this.setVisible(true);
        }
}
