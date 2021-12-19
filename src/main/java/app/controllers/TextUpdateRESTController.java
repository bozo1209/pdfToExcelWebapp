//package app.controllers;
//
//import app.services.TextService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@RestController
//public class TextUpdateRESTController {
//
//    private TextService textService;
//
//    @Autowired
//    public TextUpdateRESTController(TextService textService){
//        this.textService = textService;
//    }
//
//
//    @GetMapping(path = "/resttext")
//    public TextService getRESTText(Model model){
//        System.out.println("tutaj");
//        List<String> textList = textService.getTextList();
////        model.addAttribute("textList", textService.getTextList());
//        return textService;
////        return textList;
//    }
//
//    @PostMapping(path = "/restupdate")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void updateList(@RequestParam(name = "text") String text, RedirectAttributes redirectAttributes, Model model){
//        System.out.println("*******start************");
//        textService.addElement(text);
//        System.out.println("******added to list**************");
//        model.addAttribute("textList", textService.getTextList());
//        redirectAttributes.addFlashAttribute("textList", textService.getTextList());
//        System.out.println("*************added to attribute***********");
////        return textService.getTextList();
////        return "redirect:/resttext";
//    }
//}
