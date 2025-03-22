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

    public Message getMessageByMessageId (int message){
        return this.messageDAO.retrieveMessageIdByMessageId(message);
    }

    public Message deleteMessageByMessageId (int message_id){
        Message existMessage = this.messageDAO.retrieveMessageIdByMessageId(message_id);
        if(existMessage != null){
            this.messageDAO.deleteMessageByMessageId(message_id);
            return existMessage;
        }else{
            return null;
        }
         
    }

    public Message updateMessage(Message message, int messageId){
        Message existMessage = this.messageDAO.retrieveMessageIdByMessageId(messageId);
        if(( existMessage != null) && 
        (message.getMessage_text() != "") && (message.getMessage_text().length() < 255)){
            this.messageDAO.updateMessageId(message, messageId);
            return this.messageDAO.retrieveMessageIdByMessageId(messageId);
        }
        else{
            return null;
        }
    }

    public List<Message> retrieveAllMessagesForUser (int posted_by){
        return this.messageDAO.retrieveAllMessagePostedBy(posted_by);
    }
}
