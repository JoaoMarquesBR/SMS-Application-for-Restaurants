import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FormGUI extends JFrame{

//     private String name;
//    private String phoneNumber;
//    private int orderNumber;
//    private int tableNumber;//2 digits

    private static JTextField nameTF,idTF;
    private static JPhoneNumberFormattedTextField phoneNumberTF;

    private static JLabel nameLbl,phoneNumberLbl,idLabel;


    private static JButton submit,clear;

    static JFrame mainFrame;



    public FormGUI(){
        mainFrame = new JFrame();
        mainFrame.setTitle("Add Customer");
        mainFrame.setSize(270,200);//width, height...change to desired dimensions.
        mainFrame.setLocationRelativeTo(null);//automatically centres our frame in the screen
        mainFrame.setLayout(new FlowLayout() );

        initializeElements();

        Panel inputPanel = new Panel();
        inputPanel.setLayout(new GridLayout(3,2,5,5));
        inputPanel.add(nameLbl);
        inputPanel.add(nameTF);

        inputPanel.add(phoneNumberLbl);
        inputPanel.add(phoneNumberTF);
        inputPanel.add(idLabel);
        inputPanel.add(idTF);

        mainFrame.add(inputPanel);
        mainFrame.add(clear);
        mainFrame.add(submit);



        mainFrame.setVisible(true);


    }



    private static void initializeElements()  {

        nameTF = new JTextField("");
        try {
            phoneNumberTF = new JPhoneNumberFormattedTextField();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        nameTF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phoneNumberTF.requestFocus();
                System.out.println("at phoneNUmber");
            }
        });

        phoneNumberTF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idTF.requestFocus();
                System.out.println("at id textfield.");
            }
        });

        idTF = new JTextField("");
        nameLbl = new JLabel("Name :");
        phoneNumberLbl = new JLabel("PhoneNumber :");
        idLabel = new JLabel("ID :");

        submit = new JButton("Submit");
        clear = new JButton("Clear");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = MainLayout.totalOrders;
                String name = nameTF.getText();
                String phoneNumber = phoneNumberTF.getText();
                String userID = idTF.getText();
                int numberCount = 0;
                for(int i=0;i<phoneNumber.length();i++){
                    char ch;
                    ch = phoneNumber.charAt(i);
                    if(Character.isDigit(ch)){
                        numberCount++;
                    }
                }

                if(numberCount!=11) {
                    ExceptionsChecks.phoneCheck();
                }else{
                    Guest formInputs = new Guest(name,phoneNumber,userID);
                    new ConfirmationFormGUI(formInputs);
                }


            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }


    /**
     * Clear all TextFields
     */
    private static void clear(){
        nameTF.setText("");
        phoneNumberTF.setText("");
    }

    private static final class JPhoneNumberFormattedTextField extends JFormattedTextField{
        private static final long serialVersionUID = 8997075146338662662L;
        public JPhoneNumberFormattedTextField() throws ParseException {
            super(new MaskFormatter("1(###) ###-####"));
            setColumns(9);
        }
    }


}
