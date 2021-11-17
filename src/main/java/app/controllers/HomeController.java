package app.controllers;

import app.excel.SaveAccountsAndAmountsInExcel;
import app.pdf.ExtractsAccountsAndAmounts;
import app.services.HomeService;
import app.utilities.interfaces.CustomPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class HomeController {

    private HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService){
        this.homeService = homeService;
    }

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
//    Map<String, List<File>> filesMapWithSessionId;
//    Map<String, MultipartFile[]> filesMapWithSessionId;
//    Map<String, String[]> filesMapWithSessionId;

    @GetMapping(path = {"/", "/home"})
    public String getHome(HttpSession session
            , Model model
    ){
        homeService.getHomeService(session, model);
//        if (filesMapWithSessionId == null){
//            System.out.println("filesMapWithSessionId == null");
//            filesMapWithSessionId = new HashMap<>();
//        }
//        if (filesMapWithSessionId.get(session.getId()) == null
////                || filesMapWithSessionId.get(session.getId())[0].isEmpty()
//        ){
//            System.out.println("filesMapWithSessionId.get(session.getId()) == null");
//            filesMapWithSessionId.put(session.getId(), new ArrayList<>());
////            model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
////            model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
////            session.setAttribute("files", filesMapWithSessionId.get(session.getId()));
//        }
//
////        model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
////        model.getFlashAttributes();
////        System.out.println("model.getFlashAttributes() " + model.getFlashAttributes());
//        System.out.println("session.getId() = " + session.getId());
//        if (filesMapWithSessionId.get(session.getId()) != null){
//            System.out.println("ffffffffff");
//            for (int i = 0; i < filesMapWithSessionId.get(session.getId()).size(); i++){
//                System.out.println("multipartFile[" + i + "] " + filesMapWithSessionId.get(session.getId()).get(i).getName());
////                System.out.println("multipartFile[" + i + "] " + filesMapWithSessionId.get(session.getId()).get(i).getOriginalFilename());
//            }
////            if (!arrayIsEmptyOrNull(filesMapWithSessionId.get(session.getId()).toArray((new MultipartFile[0])))){
////                model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
////            }
//            System.out.println("lllllllllll");
//            model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
////            model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
////            model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
//        }
//        //        filesMapWithSessionId.put(session.getId(), null);
////        String[] text = {"jeden", "dwa"};
////        filesMapWithSessionId.put(session.getId(), text);
////        String[] test = null;
////        System.out.println(test[0]);
////        if (test == null){
////            System.out.println("asdfsfdsf");
////        }
////        if (filesMapWithSessionId.get(session.getId()) == null){
////            model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
////        }


        return "home.html";
//        return new ModelAndView("/home.html", model)
    }

//    public String totylkotest(){
//        System.out.println("totylkotest()");
//        return "redirect:/home";
//    }

//    @PostMapping(path = "/uploadFile")
//    public String submit(
//            @RequestParam("files") MultipartFile[] multipartFile
////            @RequestParam("files") File[] multipartFile
//            , HttpSession session
//            , RedirectAttributes model
//    ){
//        homeService.submitService(multipartFile, session, model);
//        homeService.test(multipartFile, session);
//        homeService.getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(multipartFile, session);
//        totylkotest();
//        String pathToFile;
//        System.out.println("lklllllllllllll");
//        for (int i = 0; i < multipartFile.length; i++){
//            System.out.println("multipartFile[" + i + "] " + multipartFile[i].getName());
//            System.out.println(multipartFile[i].getName());
//            System.out.println(session.getServletContext().getRealPath(multipartFile[i].getOriginalFilename()));
//        }
////        for (int i = 0; i < multipartFile.length; i++){
////            System.out.println("multipartFile[" + i + "] " + multipartFile[i].getOriginalFilename());
////
////        }
////        if (multipartFile[0].getName().equals("")){
////            return "redirect:/home";
////        }
//        System.out.println("multipartFile.length = " + multipartFile.length);
//        System.out.println("multipartFile[0] = " + multipartFile[0].isEmpty());
//        System.out.println("multipartFile[0] = " );
//        if (multipartFile.length == 0){
//            System.out.println("multipartFile.length = " + multipartFile.length);
//            return "redirect:/home";
//        }
//        if (multipartFile[0].getOriginalFilename().equals("")){
//            return "redirect:/home";
//        }
//        pathToFile = session.getServletContext().getRealPath(multipartFile[0].getOriginalFilename());
//        System.out.println("pathToFile = " + pathToFile);
//        String pathToFolder = pathToFile.replaceAll(multipartFile[0].getOriginalFilename(), "") + session.getId();
//        System.out.println("pathToFolder = " + pathToFolder);
//        if (!Files.isDirectory(Paths.get(pathToFolder + session.getId()))){
////            new File(pathToFolder + session.getId()).mkdir();
//            System.out.println("nie ma folderu");
//        }else {
//            System.out.println("jest folder");
//        }
////        new File(pathToFolder + session.getId()).mkdir();
//        new File(pathToFolder).mkdir();
//        System.out.println("pathToFolder = " + pathToFolder);
//        File[] files = new File[multipartFile.length];
//        for (int i = 0; i < multipartFile.length; i++){
////            files[i] = new File(session.getServletContext().getRealPath(multipartFile[i].getOriginalFilename()));
//            files[i] = Paths.get(pathToFolder, multipartFile[i].getOriginalFilename()).toFile();
//            System.out.println("files path " + files[i]);
////            files[i] = new File("src/main/resources/temp/" + multipartFile[i].getOriginalFilename());
////            files[i] = new File("C:\\Users\\mateu\\Desktop\\Nowy folder\\pdf to excel\\t\\" + multipartFile[i].getOriginalFilename());
////            files[i] = new File(multipartFiles[i].getOriginalFilename());
//            try {
//                multipartFile[i].transferTo(files[i]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("*****************");
////        if (!arrayIsEmptyOrNull(multipartFile)) {
//            if (filesMapWithSessionId.get(session.getId()) != null) {
////            if (!arrayIsEmptyOrNull(filesMapWithSessionId.get(session.getId()).toArray(new MultipartFile[0]))) {
//                System.out.println("11111111111");
////            MultipartFile[] multipartFilesCombined = combineTwoArrays(filesMapWithSessionId.get(session.getId()), multipartFile);
////            filesMapWithSessionId.replace(session.getId(), multipartFilesCombined);
////                File[] testFile = new File[multipartFile.length];
////                for (int i = 0; i < multipartFile.length; i++){
////                    testFile[i] = new File(session.getServletContext().getRealPath(multipartFile[i].getOriginalFilename()));
////                }
////                filesMapWithSessionId.replace(session.getId(), addsTwoLists(filesMapWithSessionId.get(session.getId()), arrayToList(multipartFile)));
////                filesMapWithSessionId.replace(session.getId(), addsTwoLists(filesMapWithSessionId.get(session.getId()), arrayToList(multipartFile)));
//                filesMapWithSessionId.replace(session.getId(), addsTwoLists(filesMapWithSessionId.get(session.getId()), arrayToList(files)));
//
//            } else {
//                System.out.println("222222222");
////            filesMapWithSessionId.replace(session.getId(), multipartFile);
////                filesMapWithSessionId.replace(session.getId(), arrayToList(multipartFile));
//                filesMapWithSessionId.replace(session.getId(), arrayToList(files));
//            }
////            model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
////        }
////        File[] testFile = new File[multipartFile.length];
////        for (int i = 0; i < multipartFile.length; i++){
////            testFile[i] = new File(session.getServletContext().getRealPath(multipartFile[i].getOriginalFilename()));
////        }
////        model.addFlashAttribute("files", testFile);
//        model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
////        filesMapWithSessionId.replace(session.getId(), multipartFile);
////        filesMapWithSessionId.replace(session.getId(), multipartFile);
////        model.addAttribute("files", filesMapWithSessionId.get(session.getId()));
////        model.addFlashAttribute("files", filesMapWithSessionId.get(session.getId()));
////        session.setAttribute("files", filesMapWithSessionId.get(session.getId()));
//        for (int i = 0; i < multipartFile.length; i++){
////            System.out.println("multipartFile[" + i + "] " + multipartFile[i].getOriginalFilename());
//            System.out.println(multipartFile[i].getName());
//        }
//
//        for (int i = 0; i < filesMapWithSessionId.get(session.getId()).size(); i++){
//            System.out.println("filesMapWithSessionId.get(session.getId()).get(" + i + ")" + filesMapWithSessionId.get(session.getId()).get(i).getName());
////            System.out.println("filesMapWithSessionId.get(session.getId()).get(" + i + ")" + filesMapWithSessionId.get(session.getId()).get(i).getOriginalFilename());
//        }
//
//
//        return "redirect:/home";
////        return "/fileTable.html";
////        return "/home.html";
//    }

    @GetMapping(path = "/generateExcel")
    public ResponseEntity<Resource> downloadExcel(HttpSession session
//            , HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        System.out.println("*****************");
        System.out.println("downloadTest(HttpSession session)");
//        ExtractsAccountsAndAmounts extractsAccountsAndAmounts = new ExtractsAccountsAndAmounts();
//        SaveAccountsAndAmountsInExcel saveAccountsAndAmountsInExcel = new SaveAccountsAndAmountsInExcel();
////        MultipartFile[] multipartFiles = filesMapWithSessionId.get(session.getId()).toArray(new MultipartFile[0]);
////        File[] files = new File[multipartFiles.length];
////        for (int i = 0; i < multipartFiles.length; i++){
//////            files[i] = new File(session.getServletContext().getRealPath(multipartFiles[i].getOriginalFilename()));
//////            files[i] = new File("src/main/resources/temp/" + multipartFiles[i].getOriginalFilename());
//////            files[i] = new File("C:\\Users\\mateu\\Desktop\\Nowy folder\\pdf to excel\\t\\" + multipartFiles[i].getOriginalFilename());
//////            files[i] = new File(multipartFiles[i].getOriginalFilename());
////            try {
////                multipartFiles[i].transferTo(files[i]);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            System.out.println(files[i].getAbsolutePath());
////        }
//        File[] files = filesMapWithSessionId.get(session.getId()).toArray(new File[0]);
//        String path = files[0].getAbsolutePath();
//        System.out.println("path = " + path);
//        String pathToFolder = path.replace(files[0].getName(), "");
//        System.out.println("pathToFolder = " + pathToFolder);
//        System.out.println("files[0].getName() = " + files[0].getName());
////        path = files[0].getPath();
////        System.out.println("path = " + path);
//        ArrayList<CustomPair<String, BigDecimal>> accountsAndAmountsList = extractsAccountsAndAmounts.getAccountsAndAmountsList(files);
//        saveAccountsAndAmountsInExcel.saveInExcel(accountsAndAmountsList, pathToFolder);
//        System.out.println("excel created");
////        saveAccountsAndAmountsInExcel.openExcel(pathToFolder);
//        extractsAccountsAndAmounts.printAccountsAndAmounts(accountsAndAmountsList);
//        File excelFile = new File(pathToFolder + saveAccountsAndAmountsInExcel.getExcelName());
//        System.out.println("saveAccountsAndAmountsInExcel.getExcelName() = " + saveAccountsAndAmountsInExcel.getExcelName());
////        String mimeType = URLConnection.guessContentTypeFromName(excelFile.getName());
////        if (mimeType == null){
////            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
////        }
////        response.setContentType(mimeType);
////        response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", excelFile.getName()));
////        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", excelFile.getName()));
////        response.setContentLength((int) excelFile.length());
////        InputStream inputStream = new BufferedInputStream(new FileInputStream(excelFile));
////        FileCopyUtils.copy(inputStream, response.getOutputStream());
////        System.out.println("lolololol");
////        return "redirect:/home";
//
//        HttpHeaders header = new HttpHeaders();
//        header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", excelFile.getName()));
//        ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(Paths.get(excelFile.getAbsolutePath())));
//        return ResponseEntity.ok()
//                .headers(header)
//                .contentLength(excelFile.length())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(byteArrayResource);

        return homeService.downloadExcelService(session);
    }

//    @GetMapping(path = "/deleteSessionFolder")
//    public String deleteSessionFolder(HttpSession session){
//        homeService.deleteSessionFolderService(session);
//        return "redirect:/home";
//    }

//    @GetMapping(path = "/deleteFile/{filename}")
//    @GetMapping(path = "/deleteFile")
//    @PostMapping(path = "/deleteFile")
//    public String deleteFile(
//            @RequestParam(name = "filename") String filename,
////            @PathVariable String filename,
//            HttpSession session){
////        homeService.test(session);
////        String fileAbsolutePath = "test";
//        System.out.println("session = " + session.getId());
//        homeService.deleteFileService(filename, session);
//        return "redirect:/home";
//    }

//    public List<File> arrayToList(File[] multipartFiles){
////    public List<MultipartFile> arrayToList(MultipartFile[] multipartFiles){
////        List<MultipartFile> list = new ArrayList<>();
//        List<File> list = new ArrayList<>();
//        Collections.addAll(list, multipartFiles);
//        return list;
//    }
//
//    /**
//     * thinks about what methods use to delete duplicate
//     */
//    public List<File> addsTwoLists(List<File> first, List<File> second){
////    public List<MultipartFile> addsTwoLists(List<MultipartFile> first, List<MultipartFile> second){
////        first.addAll(second);
//        first.addAll(removeDuplicatedFiles(first, second));
//        return first;
////        return first.stream().distinct().collect(Collectors.toList());
//    }
//
//    public List<File> removeDuplicatedFiles(List<File> first, List<File> second){
//        for (int i = 0; i < first.size(); i++){
//            for (int j = 0; j < second.size(); j++){
//                System.out.println(first.get(i) + " " + second.get(j));
//                if (first.get(i).equals(second.get(j))){
//                    second.remove(j);
//                }
//            }
//        }
//        return second;
//    }
//
//    public boolean arrayIsEmptyOrNull(MultipartFile[] array){
//        if (array == null){
//            System.out.println("arr 1");
//            return true;
//        }
//        if (array.length == 0){
//            System.out.println("arr 2");
//            return true;
//        }
//        for(MultipartFile file : array){
//            System.out.println("arr 3");
//            if (file == null || file.isEmpty()){
//                System.out.println(file.getOriginalFilename());
//                System.out.println("arr 4");
//                return true;
//            }
//        }
//        System.out.println("arr 5");
//        return false;
//    }
//
//    public <T> T[] combineTwoArrays(T[] first, T[] second){
//        T[] result = Arrays.copyOf(first, first.length + second.length);
//        System.arraycopy(second, 0, result, first.length, second.length);
//        return result;
//    }
}
