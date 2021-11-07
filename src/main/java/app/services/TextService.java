package app.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextService {

    private List<String> textList = new ArrayList<>();

    public void addElement(String text){
        textList.add(text);
    }

    public List<String> getTextList() {
        return textList;
    }
}
