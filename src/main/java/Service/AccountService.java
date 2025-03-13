package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // Logic add new account 
    public Account addAccount(Account account){
        if ((account.getUsername() != "") && (account.getPassword().length()>3) && (this.accountDAO.getAccount(account)==null)){
            return this.accountDAO.insertAccount(account);
        }
        else{
            return null;
        }
    }

    public Account getStartSession(Account account){
        if (this.accountDAO.getAccount(account) != null){
            return this.accountDAO.getAccount(account);
        }
        else{
            return null;
        }
    }

}
