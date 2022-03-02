package com.ssafy.api.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.net.URL;
import java.util.HashSet;

@Service
public class FileUploadServiceImpl implements FileUploadService{
    // 파일 저장 경로 반환
    public String saveFile(MultipartFile multipartFile, Long roomId){
        // 썸네일 이미지를 업로드 하지 않은 경우 기본이미지로 설정
        if (multipartFile == null)
            return "https://pparttypeople.kro.kr/upload/dummy.png";

        // 환경에 따라 경우 아래 두 라인의 주석 변경
        String path = "/opt/upload/" + Long.toString(roomId); // linux 환경
        // String path = "C:\\upload\\" + Long.toString(roomId); // windows 환경

        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        String fileName = multipartFile.getName();
        if (fileName.isBlank())
            throw new NullPointerException("File Name is Blank");

        // png, jpg, jpeg 아니면 거르기
        HashSet<String> candidate = new HashSet<>();
        candidate.add("png");
        candidate.add("jpg");
        candidate.add("jpeg");

        if (this.isContain(extension, candidate))
            throw new RuntimeException("Not Supported File Type");

        try {
            File dir = new File(path);
            if(!dir.exists()) dir.mkdirs();

            // 환경에 따라 경우 아래 두 라인의 주석 변경
            // File file = new File(path + "\\img");  // windows 환경
            File file = new File(path + "/thumbnail." + extension);  // linux 환경

            multipartFile.transferTo(file);
        } catch (NullPointerException e){
            return null;
        } catch (RuntimeException e){
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return "https://pparttypeople.kro.kr/upload/" + Long.toString(roomId) + "/thumbnail." + extension;
    }

    // 해당 확장자가 지원되는 확장자인지 확인
    public boolean isContain(String extension, HashSet<String> candidates){
        return !candidates.contains(extension.toLowerCase());
    }
}
