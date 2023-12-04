package org.zerock.b01.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "Replies POST", notes = "POST 방식으로 댓글 등록")  //특정 경로에 대한 작업 또는 일반적으로 HTTP 메서드를 설명합니다.
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)  // consumes는 해당 메서드를 받아서 소비(consumes)하는 데이터가 어떤 종류인지 명시, JSON 타입의 데이터를 처리하는 메서드임을 명시
    //@RequestBody는 JSON 문자열을 ReplyDTO로 변환하기 위해서 표시
    public  Map<String,Long> register( @Valid @RequestBody ReplyDTO replyDTO,  BindingResult bindingResult) throws BindException {

        log.info("댓글 DTO : " + replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

//        Map<String, Long> resultMap =Map.of("rno", 3L);


        /*
        * private Map<Integer, String> map = new HashMap<>() {
  {
    put(1, "sangwoo kang");
    put(2, "james kang");
    put(3, "stef you");
  }
};
하지만 Java 9 이상부터는 Map.of()를 통해 조금 더 간단하게 작성할 수 있었습니다.

private Map<Integer, String> map = Map.of(
    1, "sangwoo kang",
    2, "james kang",
    3, "stef you"
);
        *
        * */


        Map<String, Long> resultMap = new HashMap<>();

        Long rno = replyService.register(replyDTO);


        resultMap.put("rno",rno);




    return resultMap;
    }


    //list/{bno}	GET	특정한 번호의 댓글 조회	댓글 객체를 JSON으로 반환한 문자열



    //글 하나에 대한 댓글 목록 조회
    @ApiOperation(value = "Replies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno,
                                             PageRequestDTO pageRequestDTO){

        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);
        return responseDTO;

    }

    //댓글 하나 조회
    @ApiOperation(value = "Read Reply", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{rno}")
    public ReplyDTO getReplyDTO( @PathVariable("rno") Long rno ){

        ReplyDTO replyDTO = replyService.read(rno);

        return replyDTO;
    }



    //특정 댓글 삭제
    @ApiOperation(value = "Delete Reply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String,Long> remove( @PathVariable("rno") Long rno ){

        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }



    //특정 댓글 수정
    @ApiOperation(value = "Modify Reply", notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE )
    public Map<String,Long> modify( @PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO ){
        replyDTO.setRno(rno); //번호를 일치시킴

        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }



}
