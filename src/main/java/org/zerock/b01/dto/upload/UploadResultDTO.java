package org.zerock.b01.dto.upload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {


    private String uuid;

    private String fileName;

    //이미지인지 여부
    private boolean img;


    //JSON 처리시 link라는 속성으로 자동 처리
    public String getLink(){

        if(img){
            return  "s_"+ uuid +"_"+fileName; //이미지인 경우 섬네일
        }else{
            return  "uuid" +"_"+fileName;
        }

    }





}
