package app.services;

import app.excel.SaveAccountsAndAmountsInExcel;
import app.pdf.ExtractsAccountsAndAmounts;
import app.utilities.interfaces.CustomPair;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class HomeService {

    private Map<String, List<File>> filesMapWithSessionId;
    private final String ATTRIBUTE_FILES_NAME = "files";
    private final String REDIRECT_HOME = "redirect:/home";

    public void getHomeService(HttpSession session, Model model){
        createFilesMap(session);
        createNewSessionIdKey(session);
        addFilesListOfSessionIdToModel(session, model, null);
    }

    public List<File> getHomeRESTService(HttpSession session){
        createFilesMap(session);
        createNewSessionIdKey(session);
//        return getFilesFromListBySessionIdFromMap(session);
        return filesMapWithSessionId.get(session.getId());
    }

    public void submitService(MultipartFile[] multipartFiles, HttpSession session, RedirectAttributes redirectModel){
        addFilesToMapOfLists(multipartFiles, session);
        addFilesListOfSessionIdToModel(session, null, redirectModel);
    }

    public void submitRESTService(MultipartFile[] multipartFiles, HttpSession session){
//        System.out.println(filesMapWithSessionId.get(session.getId()).size());
        createNewSessionIdKey(session);
        addFilesToMapOfLists(multipartFiles, session);
//        System.out.println("****** po ***");
//        System.out.println(filesMapWithSessionId.get(session.getId()).size());
//        System.out.println(filesMapWithSessionId.get(session.getId()).get(0));
    }

    public ResponseEntity<Resource> downloadExcelService(HttpSession session) throws IOException{
        return createResponse(session);
    }

    public void deleteSessionFolderService(HttpSession session){
        deleteSessionFolder(session);
    }

    public void deleteFileService(String fileName, HttpSession session){
        deleteFile(fileName, session);
    }

    public void test(HttpSession session){
        List<File> files = filesMapWithSessionId.get(session.getId());
        for (File file : files){
//            String absolutePath = file.getAbsolutePath();
            String absolutePath = file.getName();
            System.out.println(absolutePath);
            deleteFile(absolutePath, session);
        }
    }

    private void createFilesMap(HttpSession session){
        if (filesMapWithSessionId == null){
            filesMapWithSessionId = new HashMap<>();
        }
    }

    private void createNewSessionIdKey(HttpSession session){
        if (filesMapWithSessionId.get(session.getId()) == null){
            filesMapWithSessionId.put(session.getId(), new ArrayList<>());
        }
    }

    private void addFilesListOfSessionIdToModel(HttpSession session, Model model, RedirectAttributes redirectModel){
        if (model != null){
            if (filesMapWithSessionId.get(session.getId()) != null){
                model.addAttribute(ATTRIBUTE_FILES_NAME, filesMapWithSessionId.get(session.getId()));
            }
        }else if (redirectModel != null){
            redirectModel.addFlashAttribute(ATTRIBUTE_FILES_NAME, filesMapWithSessionId.get(session.getId()));
        }
    }

    public void test(MultipartFile[] multipartFiles, HttpSession session){
        System.out.println("********test************");
        getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(multipartFiles, session);
    }

//    private String redirectHome2(){
//        return REDIRECT_HOME;
//    }
//
//    private void redirectHome(){
//        redirectHome2();
//    }

//    private void redirectHome3(){
//        return;
//    }

    private String getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(MultipartFile[] multipartFiles, HttpSession session){
        if (multipartFiles[0].getOriginalFilename().equals("")){
//            return REDIRECT_HOME;
            return null;
//            redirectHome3();
        }
        String pathToFile = session.getServletContext().getRealPath(multipartFiles[0].getOriginalFilename());
        return pathToFile.replaceAll(multipartFiles[0].getOriginalFilename(), "") + session.getId();
    }

    private void createSessionIdFolder(String path){
//        System.out.println("create folder");
        new File(path).mkdir();
    }

    private File[] createFileArray(MultipartFile[] multipartFiles, HttpSession session){
        String path = getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(multipartFiles, session);
//        if (path.equals(REDIRECT_HOME)){
//            return null;
//        }
        if (path == null){
            return null;
        }
        createSessionIdFolder(path);
        File[] files = new File[multipartFiles.length];
        for (int i = 0; i < multipartFiles.length; i++){
//            System.out.println("multipartFiles[i].getOriginalFilename() = " + multipartFiles[i].getOriginalFilename());
//            System.out.println("Paths.get(path, multipartFiles[i].getOriginalFilename()).toString() = " + Paths.get(path, multipartFiles[i].getOriginalFilename()).toString());
            files[i] = Paths.get(path, multipartFiles[i].getOriginalFilename()).toFile();
            try {
                multipartFiles[i].transferTo(files[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    private void addFilesToMapOfLists(MultipartFile[] multipartFiles, HttpSession session){
        File[] fileArray = createFileArray(multipartFiles, session);
        if (fileArray == null){
//            System.out.println("****null*******");
            return;
        }
//        System.out.println("fileArray[0] " + fileArray[0]);
        if (filesMapWithSessionId.get(session.getId()) != null){
//            System.out.println("filesMapWithSessionId.get(session.getId()) != null");
            filesMapWithSessionId.replace(
                    session.getId(),
                    addsTwoLists(
                            filesMapWithSessionId.get(session.getId()),
                            arrayToList(fileArray)
                    )
            );
        }
//        System.out.println("fileArray[0] " + fileArray[0]);
    }

    private File[] getFilesFromListBySessionIdFromMap(HttpSession session){
        return filesMapWithSessionId.get(session.getId()).toArray(new File[0]);
    }

    private String getPathToSessionIdFolder(File[] files){
        return files[0].getAbsolutePath().replace(files[0].getName(), "");
    }

    private File createExcelFile(HttpSession session){
        ExtractsAccountsAndAmounts extractsAccountsAndAmounts = new ExtractsAccountsAndAmounts();
        SaveAccountsAndAmountsInExcel saveAccountsAndAmountsInExcel = new SaveAccountsAndAmountsInExcel();
        File[] files = getFilesFromListBySessionIdFromMap(session);
        String pathToSessionIdFolder = getPathToSessionIdFolder(files);
        ArrayList<CustomPair<String, BigDecimal>> accountsAndAmountsList = extractsAccountsAndAmounts.getAccountsAndAmountsList(files);
        saveAccountsAndAmountsInExcel.saveInExcel(accountsAndAmountsList, pathToSessionIdFolder);
        return new File(pathToSessionIdFolder + saveAccountsAndAmountsInExcel.getExcelName());
    }

    private ResponseEntity<Resource> createResponse(HttpSession session) throws IOException{
        File excelFile = createExcelFile(session);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", excelFile.getName()));
        ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(Paths.get(excelFile.getAbsolutePath())));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(excelFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

    private void deleteFile(String fileName, HttpSession session){
        int i = -1;

//        List<File> files = filesMapWithSessionId.get(session.getId());

        for (File file : filesMapWithSessionId.get(session.getId())){
            if (file.getName().equals(fileName)){
                i = filesMapWithSessionId.get(session.getId()).indexOf(file);
                file.delete();
                break;
            }
        }

//        System.out.println("index to delete: " + i);
        filesMapWithSessionId.get(session.getId()).remove(i);

    }

    private void deleteSessionFolder(HttpSession session){
        String realPath = session.getServletContext().getRealPath(session.getId());
//        System.out.println("realPath = " + realPath);
        File folder = new File(realPath);
        if(folder.isDirectory()){
//            System.out.println("realPath = " + realPath);
//            boolean delete = folder.delete();
//            System.out.println("delete = " + delete);
            try {
                FileUtils.deleteDirectory(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            filesMapWithSessionId.remove(session.getId());
        }else {
            System.out.println("nope");
        }
    }

    private List<File> arrayToList(File[] multipartFiles){
//    public List<MultipartFile> arrayToList(MultipartFile[] multipartFiles){
//        List<MultipartFile> list = new ArrayList<>();
        List<File> list = new ArrayList<>();
        Collections.addAll(list, multipartFiles);
        return list;
    }

    /**
     * thinks about what methods use to delete duplicate
     */
    private List<File> addsTwoLists(List<File> first, List<File> second){
//    public List<MultipartFile> addsTwoLists(List<MultipartFile> first, List<MultipartFile> second){
//        first.addAll(second);
        first.addAll(removeDuplicatedFiles(first, second));
        return first;
//        return first.stream().distinct().collect(Collectors.toList());
    }

    private List<File> removeDuplicatedFiles(List<File> first, List<File> second){
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

}
