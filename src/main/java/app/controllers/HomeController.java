package app.controllers;

import app.pdf.ExtractsAccountsAndAmounts;
import app.utilities.interfaces.CustomPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

//    private Model model;
//    private RedirectAttributes model;
//
//    @Autowired
//    public HomeController(RedirectAttributes model){
//        this.model = model;
//    }

//    @Autowired
//    public HomeController(Model model){
//        this.model = model;
//    };


//    Map<String, List<MultipartFile>> filesMapWithSessionId;
    Map<String, List<File>> filesMapWithSessionId;
//    Map<String, MultipartFile[]> filesMapWithSessionId;
//    Map<String, String[]> filesMapWithSessionId;

    @GetMapping(path = {"/", "/home"})
    public String getHome(HttpSession session
            , Model model
    ){
        if (filesMapWithSessionId == null){
            System.out.println("filesMapWithSessionId == null");
            filesMapWithSessionId = new HashMap<>();
        }
        if (filesMapWithSessionId.get(session.getId()) == null
//                || filesMapWithSessionId.get(session.getId())[0].isEmpty()
        ){
            System.out.println("filesMapWithSessionId.get(session.getId()) == null");
            filesMapWithSessionId.put(session.getId(), new ArrayList<>());
//            model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
//            model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
//            session.setAttribute("files", filesMapWithSessionId.get(session.getId()));
        }

//        model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
//        model.getFlashAttributes();
//        System.out.println("model.getFlashAttributes() " + model.getFlashAttributes());
        System.out.println("session.getId() = " + session.getId());
        if (filesMapWithSessionId.get(session.getId()) != null){
            System.out.println("ffffffffff");
            for (int i = 0; i < filesMapWithSessionId.get(session.getId()).size(); i++){
                System.out.println("multipartFile[" + i + "] " + filesMapWithSessionId.get(session.getId()).get(i).getName());
//                System.out.println("multipartFile[" + i + "] " + filesMapWithSessionId.get(session.getId()).get(i).getOriginalFilename());
            }
//            if (!arrayIsEmptyOrNull(filesMapWithSessionId.get(session.getId()).toArray((new MultipartFile[0])))){
//                model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
//            }
            System.out.println("lllllllllll");
            model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
//            model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
//            model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
        }
        //        filesMapWithSessionId.put(session.getId(), null);
//        String[] text = {"jeden", "dwa"};
//        filesMapWithSessionId.put(session.getId(), text);
//        String[] test = null;
//        System.out.println(test[0]);
//        if (test == null){
//            System.out.println("asdfsfdsf");
//        }
//        if (filesMapWithSessionId.get(session.getId()) == null){
//            model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
//        }


        return "/home.html";
//        return new ModelAndView("/home.html", model)
    }

    @PostMapping(path = "/uploadFile")
    public String submit(
            @RequestParam("files") MultipartFile[] multipartFile
//            @RequestParam("files") File[] multipartFile
            , HttpSession session
            , RedirectAttributes model
    ){
        System.out.println("lklllllllllllll");
        for (int i = 0; i < multipartFile.length; i++){
            System.out.println("multipartFile[" + i + "] " + multipartFile[i].getName());
            System.out.println(multipartFile[i].getName());
            System.out.println(session.getServletContext().getRealPath(multipartFile[i].getOriginalFilename()));
        }
//        for (int i = 0; i < multipartFile.length; i++){
//            System.out.println("multipartFile[" + i + "] " + multipartFile[i].getOriginalFilename());
//
//        }
//        if (multipartFile[0].getName().equals("")){
//            return "redirect:/home";
//        }
        if (multipartFile[0].getOriginalFilename().equals("")){
            return "redirect:/home";
        }
        File[] files = new File[multipartFile.length];
        for (int i = 0; i < multipartFile.length; i++){
            files[i] = new File(session.getServletContext().getRealPath(multipartFile[i].getOriginalFilename()));
//            files[i] = new File("src/main/resources/temp/" + multipartFile[i].getOriginalFilename());
//            files[i] = new File("C:\\Users\\mateu\\Desktop\\Nowy folder\\pdf to excel\\t\\" + multipartFile[i].getOriginalFilename());
//            files[i] = new File(multipartFiles[i].getOriginalFilename());
            try {
                multipartFile[i].transferTo(files[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("*****************");
//        if (!arrayIsEmptyOrNull(multipartFile)) {
            if (filesMapWithSessionId.get(session.getId()) != null) {
//            if (!arrayIsEmptyOrNull(filesMapWithSessionId.get(session.getId()).toArray(new MultipartFile[0]))) {
                System.out.println("11111111111");
//            MultipartFile[] multipartFilesCombined = combineTwoArrays(filesMapWithSessionId.get(session.getId()), multipartFile);
//            filesMapWithSessionId.replace(session.getId(), multipartFilesCombined);
//                File[] testFile = new File[multipartFile.length];
//                for (int i = 0; i < multipartFile.length; i++){
//                    testFile[i] = new File(session.getServletContext().getRealPath(multipartFile[i].getOriginalFilename()));
//                }
//                filesMapWithSessionId.replace(session.getId(), addsTwoLists(filesMapWithSessionId.get(session.getId()), arrayToList(multipartFile)));
//                filesMapWithSessionId.replace(session.getId(), addsTwoLists(filesMapWithSessionId.get(session.getId()), arrayToList(multipartFile)));
                filesMapWithSessionId.replace(session.getId(), addsTwoLists(filesMapWithSessionId.get(session.getId()), arrayToList(files)));

            } else {
                System.out.println("222222222");
//            filesMapWithSessionId.replace(session.getId(), multipartFile);
//                filesMapWithSessionId.replace(session.getId(), arrayToList(multipartFile));
                filesMapWithSessionId.replace(session.getId(), arrayToList(files));
            }
//            model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
//        }
//        File[] testFile = new File[multipartFile.length];
//        for (int i = 0; i < multipartFile.length; i++){
//            testFile[i] = new File(session.getServletContext().getRealPath(multipartFile[i].getOriginalFilename()));
//        }
//        model.addFlashAttribute("files", testFile);
        model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
//        filesMapWithSessionId.replace(session.getId(), multipartFile);
//        filesMapWithSessionId.replace(session.getId(), multipartFile);
//        model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
//        model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
//        session.setAttribute("files", filesMapWithSessionId.get(session.getId()));
        for (int i = 0; i < multipartFile.length; i++){
//            System.out.println("multipartFile[" + i + "] " + multipartFile[i].getOriginalFilename());
            System.out.println(multipartFile[i].getName());
        }

        for (int i = 0; i < filesMapWithSessionId.get(session.getId()).size(); i++){
            System.out.println("filesMapWithSessionId.get(session.getId()).get(" + i + ")" + filesMapWithSessionId.get(session.getId()).get(i).getName());
//            System.out.println("filesMapWithSessionId.get(session.getId()).get(" + i + ")" + filesMapWithSessionId.get(session.getId()).get(i).getOriginalFilename());
        }


        return "redirect:/home";
//        return "/home.html";
    }

    @PostMapping(path = "/generateExcel")
    public String downloadTest(HttpSession session){
        ExtractsAccountsAndAmounts extractsAccountsAndAmounts = new ExtractsAccountsAndAmounts();
//        MultipartFile[] multipartFiles = filesMapWithSessionId.get(session.getId()).toArray(new MultipartFile[0]);
//        File[] files = new File[multipartFiles.length];
//        for (int i = 0; i < multipartFiles.length; i++){
////            files[i] = new File(session.getServletContext().getRealPath(multipartFiles[i].getOriginalFilename()));
////            files[i] = new File("src/main/resources/temp/" + multipartFiles[i].getOriginalFilename());
////            files[i] = new File("C:\\Users\\mateu\\Desktop\\Nowy folder\\pdf to excel\\t\\" + multipartFiles[i].getOriginalFilename());
////            files[i] = new File(multipartFiles[i].getOriginalFilename());
//            try {
//                multipartFiles[i].transferTo(files[i]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(files[i].getAbsolutePath());
//        }
        File[] files = filesMapWithSessionId.get(session.getId()).toArray(new File[0]);
        ArrayList<CustomPair<String, BigDecimal>> accountsAndAmountsList = extractsAccountsAndAmounts.getAccountsAndAmountsList(files);
        extractsAccountsAndAmounts.printAccountsAndAmounts(accountsAndAmountsList);
        return "redirect:/home";
    }

    public List<File> arrayToList(File[] multipartFiles){
//    public List<MultipartFile> arrayToList(MultipartFile[] multipartFiles){
//        List<MultipartFile> list = new ArrayList<>();
        List<File> list = new ArrayList<>();
        Collections.addAll(list, multipartFiles);
        return list;
    }

    /**
     * thinks about what methods use to delete duplicate
     */
    public List<File> addsTwoLists(List<File> first, List<File> second){
//    public List<MultipartFile> addsTwoLists(List<MultipartFile> first, List<MultipartFile> second){
//        first.addAll(second);
        first.addAll(removeDuplicatedFiles(first, second));
        return first;
//        return first.stream().distinct().collect(Collectors.toList());
    }

    public List<File> removeDuplicatedFiles(List<File> first, List<File> second){
        for (int i = 0; i < first.size(); i++){
            for (int j = 0; j < second.size(); j++){
                System.out.println(first.get(i) + " " + second.get(j));
                if (first.get(i).equals(second.get(j))){
                    second.remove(j);
                }
            }
        }
        return second;
    }

    public boolean arrayIsEmptyOrNull(MultipartFile[] array){
        if (array == null){
            System.out.println("arr 1");
            return true;
        }
        if (array.length == 0){
            System.out.println("arr 2");
            return true;
        }
        for(MultipartFile file : array){
            System.out.println("arr 3");
            if (file == null || file.isEmpty()){
                System.out.println(file.getOriginalFilename());
                System.out.println("arr 4");
                return true;
            }
        }
        System.out.println("arr 5");
        return false;
    }

    public <T> T[] combineTwoArrays(T[] first, T[] second){
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
