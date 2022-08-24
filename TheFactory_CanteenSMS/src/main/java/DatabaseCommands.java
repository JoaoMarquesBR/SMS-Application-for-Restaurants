import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseCommands {
    private static String url;
//    private static String url = "jdbc:sqlite:C:\\MyDatabases\\myDatabase.db";

    private static  Connection conn;
    private static int refreshCount=0;

    public static void queryResults(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            String phoneNumber="";
            String name="";
            int timesMesseged=0;
            String tableNumber="";
            try(Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery("select id,name,phoneNumber,timesMesseged from MyOrders")) {
                while(results.next()) {
                    tableNumber = results.getString(1);
                    name= results.getString(2);
                    phoneNumber = results.getString(3);
                    timesMesseged = results.getInt(4);
                    MainLayout.addGuestOrder(new Guest(name,phoneNumber,Integer.valueOf(tableNumber),timesMesseged));
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void insertQuery(String name,String phoneNumber,String id){
        String query="";
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            query = "insert into myOrders ('name','phoneNumber','timesMesseged','id') VALUES ('"+name+"',"+"'"+phoneNumber+"',"+ 0+",'"+id+"' );";

            Statement statement = conn.createStatement();
            System.out.println("query "+ query);
            statement.executeUpdate(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void removeGuest(String phoneNumber){
        String query;
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            query = "delete from myorders where phoneNumber LIKE '%"+phoneNumber+"%';";
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public  static void updateTimes(String phoneNumber){
        String query;
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            query = "update myorders set timesmesseged = (timesMesseged+1) where phoneNumber LIKE '%"+phoneNumber+"%';";

            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void refresh(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            String phoneNumber="";
            String name="";
            int timesMesseged=0;
            int tableNumber=0;
            int guestID=0;

            ArrayList<Guest>databaseGuest = new ArrayList<>();
            try(Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery("select id,name,phoneNumber,timesMesseged,id from MyOrders")) {
                databaseGuest.clear();
                while(results.next()) {
                    int id=0;
                    guestID = results.getInt(5);
                    name= results.getString(2);
                    phoneNumber = results.getString(3);
                    timesMesseged = results.getInt(4);
                    Guest guest= new Guest(name,phoneNumber,guestID,timesMesseged);
                    guest.setTimesMessaged(timesMesseged);
                    databaseGuest.add(guest);

                }
            }

            //check if guest of DB is in the software yet
            for(int i=0;i<databaseGuest.size();i++) {
                boolean exist = false;
                //check for each index of the list.
                for(int j=0;j<MainLayout.guestList.size();j++){


                    if(databaseGuest.get(i).getPhoneNumber().equals(MainLayout.guestList.get(j).getPhoneNumber())) {

                        //in that IF we confirm that the DATABASE GUEST exists in our LIST.
                        exist=true;
                        //now we just check if there's any update needed on that certain guest.
                        if(databaseGuest.get(i).timesMessaged!=MainLayout.guestList.get(j).getTimesMessaged()){
                            System.out.println("We have to update because "+ databaseGuest.get(i).timesMessaged+" is not "+ MainLayout.guestList.get(j).timesMessaged);
                            MainLayout.guestList.get(j).timesMessaged = databaseGuest.get(i).timesMessaged;
                            MainLayout.updateList();
                        }

                    }
                }

                //if the DB guest doesnt exist, then we gotta add in our end.
                if(!exist){
                    System.out.println("doesnt exist, so lets add");
                    MainLayout.addGuestOrder(new Guest(name,phoneNumber,guestID,timesMesseged));
                }
            }

                //if exists in the list but not in the DB, we gotta REMOVE from our point.
            for(int i=0;i<MainLayout.guestList.size();i++){
                boolean exist=false;
                for(int j=0;j<databaseGuest.size();j++){
                    if(databaseGuest.get(j).getPhoneNumber().equals(MainLayout.guestList.get(i).getPhoneNumber())){
                        exist = true;
                    }
                }

                //if It exists in the List but not in the database, we need to remove from our end.
                if(!exist){
                    System.out.println("removing guest");
                    MainLayout.guestList.remove(i);
                    MainLayout.updateList();
                }

            }


            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /*
        Reads the URL to connect.
     */
    public static void setPathConnection(){
        String pathConnection="";
        try (FileReader fr = new FileReader(new File("C:\\TheFactory_Canteen_SMS\\Configs\\urlConnection.txt"));
             BufferedReader br = new BufferedReader(fr);) {
            int ch;
            while ((ch = br.read()) != -1) {
                pathConnection += ""+Character.toString(ch);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Missing file");
            JFrame frame = new JFrame();
            System.exit(0);
        } catch (IOException e) {
            JFrame frame = new JFrame();
            System.exit(0);
        }

        url = pathConnection;

    }

}
