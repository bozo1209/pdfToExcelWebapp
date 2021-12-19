package app.services;

import app.excel.SaveAccountsAndAmountsInExcel;
import app.pdf.ExtractsAccountsAndAmounts;
import app.utilities.interfaces.CustomPair;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class HomeService {

    private Map<String, List<File>> filesMapWithSessionId;
    private final String ATTRIBUTE_FILES_NAME = "files";
    private ServletContext servletContext;

    @Autowired
    public HomeService(ServletContext servletContext){
        this.servletContext = servletContext;
    }

    public void getHomeService(HttpSession session, Model model){
        createFilesMap(session);
        createNewSessionIdKey(session);
        addFilesListOfSessionIdToModel(session, model, null);
    }

    public List<File> getHomeRESTService(HttpSession session){
        createFilesMap(session);
        createNewSessionIdKey(session);
        return Collections.unmodifiableList(filesMapWithSessionId.get(session.getId()));
    }

    public void submitRESTService(MultipartFile[] multipartFiles, HttpSession session){
        createNewSessionIdKey(session);
        addFilesToMapOfLists(multipartFiles, session);
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

    private String getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(MultipartFile[] multipartFiles, HttpSession session){
        if (multipartFiles[0].getOriginalFilename().equals("")){
            return null;
        }
        String pathToFile = session.getServletContext().getRealPath(multipartFiles[0].getOriginalFilename());
        return pathToFile.replaceAll(multipartFiles[0].getOriginalFilename(), "") + session.getId();
    }

    private void createSessionIdFolder(String path){
        new File(path).mkdir();
    }

    private File[] createFileArray(MultipartFile[] multipartFiles, HttpSession session){
        String path = getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(multipartFiles, session);
        if (path == null){
            return null;
        }
        createSessionIdFolder(path);
        File[] files = new File[multipartFiles.length];
        for (int i = 0; i < multipartFiles.length; i++){
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
            return;
        }
        if (filesMapWithSessionId.get(session.getId()) != null){
            filesMapWithSessionId.replace(
                    session.getId(),
                    addsTwoLists(
                            filesMapWithSessionId.get(session.getId()),
                            arrayToList(fileArray)
                    )
            );
        }
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
        List<CustomPair<String, BigDecimal>> accountsAndAmountsList = extractsAccountsAndAmounts.getAccountsAndAmountsList(files);
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

        for (File file : filesMapWithSessionId.get(session.getId())){
            if (file.getName().equals(fileName)){
                i = filesMapWithSessionId.get(session.getId()).indexOf(file);
                file.delete();
                break;
            }
        }
        filesMapWithSessionId.get(session.getId()).remove(i);
    }

    private void deleteSessionFolder(HttpSession session){
        String realPath = session.getServletContext().getRealPath(session.getId());
        File folder = new File(realPath);
        if(folder.isDirectory()){
            try {
                FileUtils.deleteDirectory(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            filesMapWithSessionId.remove(session.getId());
        }
    }

    @Scheduled(cron = "0 0 20 * * *")
    private void deleteAllSessionFolders(){
        if (filesMapWithSessionId != null){
            String realPath = servletContext.getRealPath("");
            try{
                List<Path> pathsList = Files.list(Paths.get(realPath)).toList();
                if (pathsList.stream().findAny().isPresent()){
                    for (Path path : pathsList){
                        filesMapWithSessionId.remove(path.getFileName().toString());
                        FileUtils.deleteDirectory(path.toFile());
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    private List<File> arrayToList(File[] multipartFiles){
        List<File> list = new ArrayList<>();
        Collections.addAll(list, multipartFiles);
        return list;
    }

    private List<File> addsTwoLists(List<File> first, List<File> second){
        first.addAll(removeDuplicatedFiles(first, second));
        return first;
    }

    private List<File> removeDuplicatedFiles(List<File> first, List<File> second){
        for (int i = 0; i < first.size(); i++){
            for (int j = 0; j < second.size(); j++){
                if (first.get(i).equals(second.get(j))){
                    second.remove(j);
                }
            }
        }
        return second;
    }

}
