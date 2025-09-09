package com.ecommerce.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
	
	@Override
	public String uploadImage(String path, MultipartFile image) throws IOException {
		//get file name of original file
		String orginalFilename = image.getOriginalFilename();//mat.jpg
		
		//generate unique file name
		String uniqueId = UUID.randomUUID().toString();//134
		String fileName = uniqueId.concat(orginalFilename.substring(orginalFilename.lastIndexOf('.')));//134.jpg
		String filePath = path + File.separator + fileName;
		
		//check if path exists and create
		File folder = new File(path);
		if(!folder.exists()) {
			folder.mkdir();
		}
		//upload to server
		
		Files.copy(image.getInputStream(), Paths.get(filePath));
		
		//return file name
		return fileName;
	}

}
