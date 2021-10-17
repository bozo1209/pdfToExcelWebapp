package app.utilities.interfaces;

public interface CustomPair<Account,Amount> {
    Account getAccount();
    Amount getAmount();
    void setAccount(Account account);
    void setAmount(Amount amount);
}
