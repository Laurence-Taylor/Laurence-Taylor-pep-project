package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // Insert a new Message
    public Message insertMessage (Message message){
        // Open Connection
        Connection conn = ConnectionUtil.getConnection();
        // SQL statement
        String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultset = preparedStatement.getGeneratedKeys();
            if (pkeyResultset.next()){
                int generated_message_id =  (int) pkeyResultset.getLong(1);
                return new Message(generated_message_id, 
                                    message.getPosted_by(), 
                                    message.getMessage_text(), 
                                    message.getTime_posted_epoch());
            }  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // RETURN a list of all Messages
    public List<Message> getAllMessage(){
        //
        Connection conn = ConnectionUtil.getConnection();
        // Create SQL sentence
        String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message;";
        // Create New array List of messages
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
    // RETURN a message given its message_id
    public Message getMessageId(int message_id){
        Connection conn = ConnectionUtil.getConnection();
        String sql =  "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE message_id = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                                rs.getInt("posted_by"), 
                                                rs.getString("message_text"), 
                                                rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    //DELETE a message given its message_id
    public Message deleteMessageId(int message_id){
        Connection conn = ConnectionUtil.getConnection();
        String sqlSelect = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE message_id = ?;";
        String sqlDelete = "DELETE message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE message_id = ?;";
        try {
            PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, message_id);
            ResultSet rs = psSelect.executeQuery();
            Message msgToDelete = new Message();
            while (rs.next()){
                msgToDelete = new Message(rs.getInt("message_id"), 
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"), 
                                            rs.getLong("time_posted_epoch"));
                                        }
            PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
            psDelete.setInt(1,message_id);
            psDelete.execute();
            return msgToDelete;
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    // UPDATE a given Message
    public Message updateMessageId(Message newMessage){
        Connection conn = ConnectionUtil.getConnection();
        String sql =  "UPDATE Message SET posted_by=?, message_text=?, time_posted_epoch=? WHERE message_id = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, newMessage.getPosted_by());
            preparedStatement.setString(2, newMessage.getMessage_text());
            preparedStatement.setLong(3, newMessage.getTime_posted_epoch());
            preparedStatement.setInt(4, newMessage.getMessage_id());
            preparedStatement.execute();
            return new Message(newMessage.getMessage_id(), 
                                newMessage.getPosted_by(), 
                                newMessage.getMessage_text(), 
                                newMessage.getTime_posted_epoch());
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Return all messages posted by an user account
    public List<Message> retrieveAllMessagePostedBy(int posted_by){
        
        Connection conn = ConnectionUtil.getConnection();
        // Create SQL sentence
        String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE posted_by = ?;";
        // Create New array List of messages
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                                rs.getInt("posted_by"),
                                                rs.getString("message_text"),
                                                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
}
