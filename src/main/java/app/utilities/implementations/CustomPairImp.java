package app.utilities.implementations;

import app.utilities.interfaces.CustomPair;

public class CustomPairImp<Account,Amount> implements CustomPair<Account, Amount> {

    private Account account;
    private Amount amount;

    public CustomPairImp() {
    }

    @Override
    public String toString() {
        return "CustomPairImp{" +
                "account=" + account +
                ", amount=" + amount +
                '}';
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public Amount getAmount() {
        return amount;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
