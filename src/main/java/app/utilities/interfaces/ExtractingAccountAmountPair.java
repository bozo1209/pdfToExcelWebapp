package app.utilities.interfaces;

public interface ExtractingAccountAmountPair<Account,Amount> {

    CustomPair<Account, Amount> getCustomPair(String[] lines);
}
