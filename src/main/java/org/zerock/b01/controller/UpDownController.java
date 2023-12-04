package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.upload.UploadFileDTO;
import org.zerock.b01.dto.upload.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UpDownController {

    @Value("${org.zerock.upload.path}")// import 시에 springframework으로 시작하는 Value
    private String uploadPath;


    // 이미지 파일 업로드
    @ApiOperation(value = "Upload POST", notes = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  List<UploadResultDTO>  upload(UploadFileDTO uploadFileDTO){
//        log.info("uploadFileDTO : " + uploadFileDTO);
//        log.info("uploadFileDTO_getFiles : " + uploadFileDTO.getFiles());

        if(uploadFileDTO.getFiles() !=null){
            final List<UploadResultDTO> list = new ArrayList<>();
            uploadFileDTO.getFiles().forEach((multipartFile)->{
                log.info(multipartFile.getOriginalFilename());
                String originalName=multipartFile.getOriginalFilename();
                String uuid=UUID.randomUUID().toString();
                Path savePath= Paths.get(uploadPath, uuid+"_"+originalName);
                boolean image = false;
                log.info("savePath : " + savePath);
                try {
                    multipartFile.transferTo(savePath);
                    //썸네일 이미지 처리
                    log.info("savePath 컨텐트 타입 검증 : " + Files.probeContentType(savePath));
                    //이미지 파일의 종류라면
                    if(Files.probeContentType(savePath).startsWith("image")){
                        image=true;

                        File thumbFile=new File(uploadPath, "s_" + uuid + "_" + originalName);
                        log.info(thumbFile);
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200,200);
                    }
                } catch (IOException e) {
                    log.info("파일 업로드 중 에러");
                }

                list.add(UploadResultDTO.builder()
                        .uuid(uuid)
                        .fileName(originalName)
                        .img(image).build()
                );

            });

        return list;
        }
        return null;
    }


    //첨부 파일 조회
    @ApiOperation(value = "view 파일", notes = "GET방식으로 첨부파일 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){

        log.info("File.separator : " + File.separator);
        Resource resource = new FileSystemResource(uploadPath+File.separator + fileName);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath() ));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }



    //첨부 파일 삭제
    @ApiOperation(value = "remove 파일", notes = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/remove/{fileName}")
    public Map<String,Boolean> removeFile(@PathVariable String fileName)  {

        Resource resource = new FileSystemResource(uploadPath+File.separator + fileName);
        String resourceName = resource.getFilename();

        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;


        try {
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete();

            //첨부 파일 삭제 시 섬네일이 존재한다면 같이 삭제
            if(contentType.startsWith("image")){
                File thumbnailFile = new File(uploadPath+File.separator +"s_" + fileName);
                thumbnailFile.delete();
            }

        } catch (IOException e) {
            log.info("첨부 파일 삭제시 에러");
        }
        resultMap.put("result", removed);
        return  resultMap;
    }


}
