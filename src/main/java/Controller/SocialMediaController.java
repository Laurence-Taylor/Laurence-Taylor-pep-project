package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postUserRegistrationHandler);
        app.post("/login", this::postUserLoginHandler);
        app.post("/messages", this::postCreateMessageHandler);
        app.get("/messages", this::getRetrieveAllMessagesHandler);
        //app.get("/messages/{message_id}", this::getRetrieveMessageByMessageIdHandler);
        //app.delete("messages/{message_id}", this::deleteMessageByMessageIdHandler);
        //app.patch("messages/{message_id}", this::patchMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::getRetrieveAllMessagesForUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

     //OKOKOKOKOKOKKOKOKOK
    private void postUserRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    //OKOKOKOKOKOKOKOKOKOKOK
    private void postUserLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.getStartSession(account);
        if (loginAccount != null) {
            ctx.json(mapper.writeValueAsString(loginAccount));
        }else{
            ctx.status(401);
        }
    }

    //OKOKOKOKOKOKOKOKOKOKOKOK 
    private void postCreateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    //OKOKOKOKOKOKOKOKOKOKOKOK
    private void getRetrieveAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessage();
        ctx.json(messages);
    }


    private void getRetrieveMessageByMessageIdHandler(Context ctx) throws JsonProcessingException {
     //   ObjectMapper mapper = new ObjectMapper();
     //   int message_id = mapper.readValue(ctx.body(),Message.class.getField("message_id"));
     //   int message_id = mapper
     //   Message returnedMessage = messageService.getMessageByMessageId(message_id);
     //   ctx.json(returnedMessage);
    }

    private void deleteMessageByMessageIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        Message returnedMessage = messageService.deleteMessage(message.getMessage_id());
        ctx.json(returnedMessage);

    }

    private void patchMessageIdHandler(Context ctx) {
        ctx.json("sample text");
    }

    private void getRetrieveAllMessagesForUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        List<Message> messages = messageService.retrieveAllMessagesForUser(message.getPosted_by());
        ctx.json(messages);
    }
}