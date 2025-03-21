package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message addMessage(Message newMessage){
        if ((newMessage.getMessage_text()!="") && 
        (newMessage.getMessage_text().length()<255) && 
        (accountDAO.isRealAccount(newMessage.getPosted_by()))){
            return this.messageDAO.insertMessage(newMessage);
        }
        else{
            return null;
        }
    }

    public List<Message> getAllMessage(){
        return this.messageDAO.getAllMessage();
    }

    public Message getMessageByMessageId (Message message){
        return this.messageDAO.getMessageId(message.getMessage_id());
    }

    public Message deleteMessage (int message_id){
        return this.deleteMessage(message_id);
    }

    public Message updateMessage(Message newMessage){
        if((this.messageDAO.getMessageId(newMessage.getMessage_id()) != null) && 
        (newMessage.getMessage_text() != "") && 
        (newMessage.getMessage_text().length()<255)){
            return this.messageDAO.updateMessageId(newMessage);
        }
        else{
            return null;
        }
    }

    public List<Message> retrieveAllMessagesForUser (int posted_by){
        return this.messageDAO.retrieveAllMessagePostedBy(posted_by);
    }
}
