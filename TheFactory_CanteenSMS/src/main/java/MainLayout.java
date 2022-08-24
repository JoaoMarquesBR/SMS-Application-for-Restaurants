import com.twilio.type.PhoneNumber;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class MainLayout {
    private static DefaultListModel demoList;
    private static JList list;
    private static JTable table;
    private static DefaultTableModel model;
    private static String message;
    private static File messageFile = new File("C:\\TheFactory_Canteen_SMS\\Message.txt");

    private static JButton addOrder, notify, delete;

    public static ArrayList<Guest> guestList = new ArrayList<>();
    public static int totalOrders = 0;
    private static int indexSelected;

    /**
     * Initializes the Main GUI and sets up the application of phonenumber to be used.
     */
    public static void main(String[] args) {
        new MainLayout();
        message = fileToString();
        DatabaseCommands.setPathConnection();
        DatabaseCommands.queryResults();
        DatabaseCommands.refresh();
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DatabaseCommands.refresh();
        }

    }

    public MainLayout() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("SMS App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//prevents memory leak
        mainFrame.setSize(800, 650);//width, height...change to desired dimensions.
        mainFrame.setLocationRelativeTo(null);//automatically centres our frame in the screen
        GridBagLayout myLayout = new GridBagLayout();
        mainFrame.setLayout(new BorderLayout());

//        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\TheFactory_Clubhouse_SMS\\img\\logo.png");
//        mainFrame.setIconImage(icon);

        initialize();

        Panel listPanel = new Panel();

        model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("PhoneNumber");
        model.addColumn("Times Messaged");
        //actual data for the table in a 2d array
        //create table with data
        table = new JTable(model);
        table.setPreferredSize(new Dimension(900,800));
        //add the table to the frame
        listPanel.add(new JScrollPane(table));
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
//                System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());

                //System.out.println("row "+ table.getSelectedRow());
            }
        });


        Panel functionalPanel = new Panel();
        functionalPanel.setLayout(new GridLayout(3,1,5,5));
        Dimension btnDimension = new Dimension(140,140);
        addOrder.setPreferredSize(btnDimension);
        notify.setPreferredSize(btnDimension);
        delete.setPreferredSize(btnDimension);

//        listPanel.setPreferredSize(new Dimension(800,1000));

        functionalPanel.add(addOrder);
        functionalPanel.add(notify);
        functionalPanel.add(delete);


        JPanel container = new JPanel();
        container.add(listPanel);
        container.add(functionalPanel);

        JPanel topPanel = new JPanel();
        JLabel title = new JLabel("The Factory Guest Notification");
        title.setFont(new Font("Serif",Font.BOLD,40));
        topPanel.add(title);

        mainFrame.add(topPanel,BorderLayout.NORTH);

        mainFrame.add(container,BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }


    /**
     * Adds Guest to the list and calls updateList
     */
    public static void addGuestOrder(Guest guest) {
        guestList.add(guest);
        updateList();
    }

    /**
     * Everytime it's called It will update all rows of JTable
     */
    public static void updateList() {
        indexSelected=-1;
        model.setRowCount(0);
        System.out.println(guestList.size());
        for(int i=0;i<guestList.size();i++){
            ;
            Object[] data = new Object[][] {
                    {guestList.get(0).getId(),guestList.get(0).getName(),guestList.get(0).getPhoneNumber(),guestList.get(0).getTimesMessaged()}
            };
            model.addRow(guestList.get(i).guestData());
        }
    }

    /**
     * Initializes elements for main layout.
     */
    public void initialize(){
        MyListener listener = new MyListener();

        addOrder = new JButton("AddOrder");
        addOrder.addActionListener(listener );

        notify = new JButton("Notify");
        notify.addActionListener(listener);

        delete = new JButton("Delete");
        delete.addActionListener(listener);
    }

    private class MyListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            indexSelected =  table.getSelectedRow();
                switch (e.getActionCommand()){
                    case "AddOrder":
                        new FormGUI();
                        break;
                    case "Notify":
                        if(indexSelected!=-1){
                            indexSelected =  table.getSelectedRow();
//
                            Guest notifyingGuest = guestList.get(indexSelected);

                            String modifiedMessage = message;

                            modifiedMessage = modifiedMessage.replace("phone",notifyingGuest.getPhoneNumber());
                            modifiedMessage = modifiedMessage.replace("guest_id",String.valueOf(notifyingGuest.getId()) );
                            modifiedMessage = modifiedMessage.replace("guest_name",notifyingGuest.getName());
                            System.out.println("message is actually "+ modifiedMessage);
                            System.out.println("phone number was "+ notifyingGuest.getPhoneNumber());
                            System.out.println("notified "+ notifyingGuest.getName()+" - "+ notifyingGuest.getPhoneNumber());
                            Application.sendMessageToEmp( modifiedMessage  ,notifyingGuest.getPhoneNumber(), String.valueOf(notifyingGuest.getId()),notifyingGuest.getName());
                            DatabaseCommands.updateTimes(guestList.get(indexSelected).getPhoneNumber());
                        }
                        break;
                    case "Delete":
                        if(indexSelected!=-1){
                            new DeleteConfirmation(guestList.get(indexSelected));
                        }
                        break;
                }

        }
    }
    private static String fileToString(){
        String myFileTxt="";
        try (FileReader fr = new FileReader(messageFile);
             BufferedReader br = new BufferedReader(fr);) {
            int ch;
            while ((ch = br.read()) != -1) {
                myFileTxt += ""+Character.toString(ch);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Missing file");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Empty file");
            System.exit(0);
        }
        return myFileTxt;
    }
}
