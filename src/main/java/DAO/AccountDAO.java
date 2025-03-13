package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    // INSERT a new Account
    public Account insertAccount (Account account){
        // Open Connection
        Connection conn = ConnectionUtil.getConnection();
        // SQL sentence
        String sql = "INSERT INTO Account (username, password) VALUES (?,?);";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultset = preparedStatement.getGeneratedKeys();
            if (pkeyResultset.next()){
                int generatedAccountId =  (int) pkeyResultset.getLong(1);
                return new Account(generatedAccountId, account.getUsername(),account.getPassword());
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // 
    public Account getAccountUserName (String username) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT (username, password) FROM Account where username = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                return account;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
    
}
