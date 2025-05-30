package com.glcl.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {
  @Value("${file.upload-dir}")
  private String uploadDir;

  public boolean saveFile(MultipartFile multipartFile) throws IOException {
    if(multipartFile == null){
      throw new NullPointerException("File not found");
    }
    // Take relative path of upload folder to the project
    String currentDirectory = System.getProperty("user.dir");
    String directoryPath = currentDirectory + File.separator + uploadDir;

    // Create upload folder if not exists
    Path dirPathObj = Path.of(directoryPath);
    if(!Files.exists(dirPathObj)){
      Files.createDirectories(dirPathObj);
    }

    File targetFile = new File(directoryPath + File.separator + multipartFile.getOriginalFilename());

    // Sanitize file name
    if(!Objects.equals(targetFile.getParent(), directoryPath)){
      throw new SecurityException("Malicious file name");
    }

    // Copy file to the directory
    // If a file with similar name exists, then it replaces the file
    Files.copy(multipartFile.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    return targetFile.exists();
  }

  public File getFile(String fileName) throws Exception{
    if(fileName == null){
      throw new NullPointerException("File name required");
    }
    File fileToDownload = new File(uploadDir + File.separator + uploadDir + File.separator + fileName);
    if (!Objects.equals(fileToDownload.getParent(), uploadDir)) {
      throw new SecurityException("Unsupported file name!");
    }
    if (!fileToDownload.exists()) {
      throw new RuntimeException("No file named: " + fileName);
    }
    return fileToDownload;
  }

  public boolean deleteFile(String fileName) throws Exception {
    if(fileName == null){
      throw new NullPointerException("File name required");
    }
    File fileToDelete = new File(uploadDir + File.separator + fileName);
    if (!Objects.equals(fileToDelete.getParent(), uploadDir)) {
      throw new SecurityException("Unsupported file name!");
    }
    if (!fileToDelete.exists()) {
      return false; // File does not exist
    }
    return fileToDelete.delete(); // Returns true if deletion was successful
  }
}
