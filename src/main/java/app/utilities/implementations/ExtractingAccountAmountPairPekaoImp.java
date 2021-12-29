package app.utilities.implementations;

import app.utilities.interfaces.CustomPair;
import app.utilities.interfaces.ExtractingAccountAmountPair;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractingAccountAmountPairPekaoImp implements ExtractingAccountAmountPair<String, BigDecimal> {
    @Override
    public CustomPair<String, BigDecimal> getCustomPair(String[] lines){
        CustomPair<String, BigDecimal> pair = new CustomPairImp<>();
        String account = "account";
        String amount = "amount";
//        String accountRegex = "BIEŻĄCY-PODMIOTY GOSP (?<" + account + ">\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4})\\s[A-Z]{3}\\s*";
        String accountRegex = ".*(?<" + account + ">\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4})\\s[A-Z]{3}\\s*";
        String valueRegex = "SALDO KOŃCOWE (?<" + amount + ">-?\\d{0,3}\\.?\\d{0,3}\\.?\\d{0,3}\\.?\\d{0,3},\\d{2})\\s*";
        for (String line : lines){
            Matcher matcher = Pattern.compile(accountRegex + "|" + valueRegex).matcher(line);
            if (matcher.matches()){
                if (matcher.group(account) != null){
                    pair.setAccount(matcher.group(account));
                }else if (matcher.group(amount) != null){
                    pair.setAmount(new BigDecimal(matcher.group(amount).replace(".", "").replace(",", ".")));
                }
            }
            if (pair.getAccount() != null && pair.getAmount() != null){
                break;
            }
        }
        return pair;
    }
}
