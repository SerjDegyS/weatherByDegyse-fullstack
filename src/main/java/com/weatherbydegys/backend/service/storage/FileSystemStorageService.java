package com.weatherbydegys.backend.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${rootLocation.path}")
    private String rootLocationPath;

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(rootLocationPath));
        } catch (IOException e) {
            throw new StorageException("Could not initialise storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        /* to avoid collision */
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + filename;
        try{
            /* Download file if exist*/
            if (file.isEmpty()) {
                throw new StorageException("Filed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                /* This is security check */
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                        + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(rootLocationPath).resolve(resultFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + resultFilename, e);
        }
        return resultFilename;
    }

    @Override
    public Path load(String filename) {
        return Paths.get(rootLocationPath).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()){
            return resource;
        }else {
            throw new StorageFileNotFoundException(
                    "Could not read file " + filename);
        }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file " + filename, e);
        }
    }
}

//@Bean
//@ConfigurationProperties("storage")
//class StorageProperties{
//    @Value("${rootLocation.path}")
//    private String rootLocationPath;
//
//    private String  rootLocation = rootLocationPath;
//
//    public Path getRootLocation() {
//        return Paths.get(this.rootLocation);
//    }
//}


