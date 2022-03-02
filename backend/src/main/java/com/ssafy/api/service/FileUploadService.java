package com.ssafy.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;

/**
 *	파일 업로드를 위한 서비스 인터페이스 정의.
 */
public interface FileUploadService {
    public String saveFile(MultipartFile multipartFile, Long roomId);
    public boolean isContain(String extension, HashSet<String> candidates);
}
