package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

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
                int generatedMessageId =  (int) pkeyResultset.getLong(1);
                return new Message(generatedMessageId, message.getUsername(),account.password);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
