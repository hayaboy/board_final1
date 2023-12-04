package org.zerock.b01.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;  //멀티파트 요청으로 수신된 업로드된 파일의 표현입니다.
// 파일 내용은 메모리에 저장되거나 임시로 디스크에 저장됩니다.
// 두 경우 모두 사용자는 원하는 경우 파일 콘텐츠를 세션 수준 또는 영구 저장소에 복사할 책임이 있습니다. 임시 저장소는 요청 처리가 끝나면 지워집니다.

import java.util.List;


@Data
public class UploadFileDTO {

    private List<MultipartFile> files;
}
