package app.pdf;

import app.bankNames.BankNames;
import app.utilities.implementations.ExtractingAccountAmountPairPekaoImp;
import app.utilities.interfaces.CustomPair;
import app.utilities.interfaces.ExtractingAccountAmountPair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ExtractsAccountsAndAmounts {


    public ArrayList<CustomPair<String, BigDecimal>> getAccountsAndAmountsList(File[] files){
        ArrayList<CustomPair<String, BigDecimal>> accountsAndAmountsList = new ArrayList<>();
        for (File file : files){
            accountsAndAmountsList.add(checkBankName(file));
        }
        return accountsAndAmountsList;
    }

    public void printAccountsAndAmounts(ArrayList<CustomPair<String, BigDecimal>> list){
        System.out.println("*****************");
        list.forEach(System.out::println);
//        System.out.println(list.isEmpty());
//        System.out.println(list.get(0));
    }

    private CustomPair<String, BigDecimal> checkBankName(File file){
        CustomPair<String, BigDecimal> pair = null;
        try(PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String text = pdfTextStripper.getText(document);
            String[] lines = text.split("\n");
            for (String line : lines){
                if (Pattern.compile("[a-zA-Z]* " + BankNames.PEKAO.name() + " S.A.\\s*").matcher(line.toUpperCase()).matches()){
                    pair = getCustomPair(lines, new ExtractingAccountAmountPairPekaoImp());
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return pair;
    }

    private CustomPair<String, BigDecimal> getCustomPair(String[] lines, ExtractingAccountAmountPair<String, BigDecimal> extracting){
        return extracting.getCustomPair(lines);
    }

}
