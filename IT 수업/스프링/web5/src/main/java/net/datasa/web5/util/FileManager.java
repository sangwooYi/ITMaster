package net.datasa.web5.util;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FileManager {

    /**
     * 파일 저장후 파일네임 반환
     * @param path
     * @param file
     * @return
     * @throws IOException
     */
    public String saveFile(String path, MultipartFile file) throws IOException {

        File directoryPath = new File(path);

        if (!directoryPath.isDirectory()) {
            // 디렉토리 생성하는 메서드
            directoryPath.mkdir();
        }

        // 원래 이름
        String originalFileName = file.getOriginalFilename();
        // 원래 이름의 확장자  . 으로 구분해서 마지막 인덱스 꺼내오기
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 오늘 날짜 문자열 변환
        String dateString = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // UUID 생성 => 그냥 UUID 타입으로 생성되므로 toString() 과정 필요
        String uuidString = UUID.randomUUID().toString();
        String fileName = dateString + "_" + uuidString + fileExtension;

        // 파일 복사하여 저장 (c:/upload/20251219_4567465788.jpg)
        File filePath = new File(directoryPath + "/" + fileName);
        file.transferTo(filePath);

        log.info("파일 정보 : 원래 이름: {}, 저장된 이름: {}, 크기: {} bytes", file.getOriginalFilename(), fileName, file.getSize());
        return fileName;
    }

    public boolean deleteFile(String path, String fileName) throws Exception{
        Path filePath = Paths.get(path, fileName);

        // 존재하면 삭제
        return Files.deleteIfExists(filePath);
    }
}
