package DAO;

import java.sql.*;

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
                int generated_account_id =  (int) pkeyResultset.getLong(1);
                return new Account(generated_account_id, 
                                    account.getUsername(),
                                    account.getPassword());
            }  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // 
    public Account getAccount (Account userAccount) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT account_id, username, password FROM Account where username = ? AND password = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userAccount.getUsername());
            preparedStatement.setString(2, userAccount.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), 
                                                rs.getString("username"), 
                                                rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Boolean isRealAccount (int posted_by){
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Account WHERE account_id = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
}
