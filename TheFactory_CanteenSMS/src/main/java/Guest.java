import java.nio.charset.StandardCharsets;

public class Guest {
    private int id;
    private String name;
    private String phoneNumber;
    private static final String UTF8_NAME = StandardCharsets.UTF_8.name();
    public int timesMessaged=0;


    public Guest(String name, String phoneNumber,String guestID) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = Integer.valueOf(guestID);

    }
    public Guest(String name, String phoneNumber, int guestID,int timesMesseged) {
        this.timesMessaged = timesMesseged;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = guestID;
    }

    public int getTimesMessaged() {
        return timesMessaged;
    }

    public void setTimesMessaged(int timesMessaged) {
        this.timesMessaged = timesMessaged;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name+" - "+ timesMessaged;
    }

    /**
     * @return elements that will be added to the JTable row
     */
    public Object[]guestData(){
        Object[]myObject = {id,name,phoneNumber,timesMessaged};
        return  myObject;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }




    /**
     * Increases for amount of times the person received the msg.
     */
    public void addMessaged(){
        timesMessaged++;
    }

}