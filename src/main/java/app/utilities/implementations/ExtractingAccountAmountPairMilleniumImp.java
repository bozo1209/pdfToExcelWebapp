package app.utilities.implementations;

import app.bankNames.BankNames;
import app.utilities.interfaces.CustomPair;
import app.utilities.interfaces.ExtractingAccountAmountPair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractingAccountAmountPairMilleniumImp implements ExtractingAccountAmountPair<String, BigDecimal> {
    @Override
    public CustomPair<String, BigDecimal> getCustomPair(String[] lines) {
        CustomPair<String, BigDecimal> pair = new CustomPairImp<>();
        String account = "account";
        String amount = "amount";
        String accountRegex = "[a-zA-Z]*:\\s*(?<" + account + ">\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4})\\s*";
        String valueRegex = "SALDO KOŃCOWE\\s*(?<" + amount + ">-?\\d{0,3},?\\d{0,3},?\\d{0,3},?\\d{0,3}.\\d{2})\\s*";
        for (String line : lines){
            Matcher matcher = Pattern.compile(accountRegex + "|" + valueRegex).matcher(line);
            if (matcher.matches()){
                if (matcher.group(account) != null){
                    pair.setAccount(matcher.group(account));
                }else if (matcher.group(amount) != null){
                    pair.setAmount(new BigDecimal(matcher.group(amount).replace(",", "")));
                }
            }
        }
        return pair;
    }

//    public static void main(String[] args) {
////        try(PDDocument document = PDDocument.load(new File("C:\\Users\\mateu\\Desktop\\Nowy folder\\pdf to excel\\pdf\\testMill.pdf"))) {
////            PDFTextStripper pdfTextStripper = new PDFTextStripper();
////            pdfTextStripper.setEndPage(1);
////            String text = pdfTextStripper.getText(document);
//////            System.out.println(text);
////            CustomPair<String, BigDecimal> pair = new CustomPairImp<>();
////            String[] lines = text.split("\n");
//////            String[] lines = text.split("\\s");
//////            String[] lines = text.split("\\s*");
//////            String[] lines = text.split("\\t");
//////            String[] lines = text.split("\\t*");
//////            String[] lines = text.split("\\R");
//////            String[] lines = text.split("\\r");
////            int count = 0;
////            String account = "account";
////            String amount = "amount";
//////            String regex = "Bank Millennium S A".toUpperCase() + "\\s*";
//////            String regex = "Bank ".toUpperCase() + BankNames.MILLENNIUM.name() + " S A".toUpperCase() + "\\s*";
////            String regex = "[a-zA-Z]*\\s" + BankNames.MILLENNIUM.name() + " S A\\s*";
////            String regex2 = "[a-zA-Z]*:\\s*(?<" + account + ">\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4})\\s*";
////            String regex3 = "SALDO KOŃCOWE\\s*(?<" + amount + ">-?\\d{0,3},?\\d{0,3},?\\d{0,3},?\\d{0,3}.\\d{2})\\s*";
////            for (String line : lines){
////                count++;
////                System.out.println(count + ": " + line);
//////                if (Pattern.compile("[a-zA-Z]* " + "Millennium" + " S A\\R*").matcher(line.toUpperCase()).matches()){
//////                    System.out.println("************* in ***********************");
//////                }
//////                if (Pattern.compile("\\s*B\\s*a\\s*n\\s*k\\s*M\\s*i\\s*l\\s*l\\s*e\\s*n\\s*n\\s*i\\s*u\\s*m\\s*S\\s*A\\s*").matcher(line.toUpperCase()).matches()){
//////                    System.out.println("************* in ***********************");
//////                }
////                if (Pattern.compile(regex).matcher(line.toUpperCase()).matches()){
////                    System.out.println("************* in ***********************");
////                }
////                if (Pattern.compile(regex2).matcher(line.toUpperCase()).matches()){
////                    System.out.println("************* in2 ***********************");
////                }
////                if (Pattern.compile(regex3).matcher(line.toUpperCase()).matches()){
////                    System.out.println("************* in3 ***********************");
////                }
//////                if (count == 501){
//////                    System.out.println(line.toUpperCase());
//////                    char[] charArray = line.toCharArray();
//////                    for (char el : charArray){
//////                        System.out.println(el);
//////                    }
//////                }
////            }
////        }catch (IOException e){
////            System.out.println("IOException: " + e);
////            e.printStackTrace();
////        }
//
//        String s = "q";
//        int i = 0;
//        switch (s){
//            case "q" -> {
//                i = 1;
//                System.out.println(s + " " + i);
//            }
//            case "w" -> {
//                i = 2;
//                System.out.println(s + " " + i);
//            }
//            default -> {
//                i = 3;
//                System.out.println(s + " " + i);
//            }
//        }
//
//        switch (s){
//            case "q" : {
//                i = 1;
//                System.out.println(s + " " + i);
//            }
//            case "w" : {
//                i = 2;
//                System.out.println(s + " " + i);
//            }
//            default : {
//                i = 3;
//                System.out.println(s + " " + i);
//            }
//        }
//    }

}
