package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message newMessage){

        AccountDAO userAccountDAO = new AccountDAO();
        if ((newMessage.getMessage_text()!="") && (newMessage.getMessage_text().length()<255) && (userAccountDAO.isRealAccount(newMessage.getPosted_by()))){
            return this.messageDAO.insertMessage(newMessage);
        }
        else{
            return null;
        }
    }

    public List<Message> getAllMessage(){
        return this.messageDAO.getAllMessage();
    }

    public Message getMessageId (int message_id){
        return this.messageDAO.getMessageId(message_id);
    }

    public Message deleteMessage (int message_id){
        return this.deleteMessage(message_id);
    }

    public Message updateMessage(Message newMessage){
        if((this.messageDAO.getMessageId(newMessage.getMessage_id()) != null) && (newMessage.getMessage_text() != "") && (newMessage.getMessage_text().length()<255)){
            return this.messageDAO.updateMessageId(newMessage);
        }
        else{
            return null;
        }
    }

    public List<Message> getAllMessageAccount (int posted_by){
        return this.messageDAO.getAllMessagePostedBy(posted_by);
    }
}
