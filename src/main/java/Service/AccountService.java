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
        if ((account.getUsername() != "") && (account.getPassword().length()>3) && (accountDAO.getAccountUserName(account.getUsername())!=null)){
            return this.accountDAO.insertAccount(account);
        }
        else{
            return null;
        }
    }

    

}
