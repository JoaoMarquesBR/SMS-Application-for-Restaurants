import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class Application {

    public static final String ACCOUNT_SID = "put your own";
    public static final String AUTH_TOKEN = "put your own";

    public static void sendMessageToEmp(String message,String phoneNumber,String id,String name){
            sendSMS(phoneNumber,message);
    }

    public static void sendSMS(String phoneNumber,String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(""+phoneNumber),
                        "your own",
                        msg)
                .create();

        System.out.println(message.getSid());
    }

}


