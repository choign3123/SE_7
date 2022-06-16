package se7.closet.src.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se7.closet.config.BaseResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@CrossOrigin(origins = {"http://localhost:9000"})
@RestController
@RequestMapping("/test")
public class TestController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TestController() {}

    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */


    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
//        logger.warn("Warn Level 테스트");
////        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
////        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
//        logger.error("ERROR Level 테스트");

        return "Success Test but no";
    }

    //이미지 출력
    // [GET] /test/display/{img}
    @ResponseBody
    @GetMapping("display/{img}")
    public ResponseEntity<Resource> showImg(@PathVariable("img") String imgName){
        System.out.println("이미지 출력 테스트");

        //저장된 이미지의 경로
        String path = "C:\\Users\\Choi ga na\\Desktop\\SE_7\\server\\";
        String folder = "img\\";
        //이미지 리소스
        Resource resource = new FileSystemResource(path + folder + imgName);

        //이미지가 존재하지 않으면
        if(!resource.exists()){
            //HttpStatus.NOT_FOUND = 404 에러
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        Path imgPath; //이미지 경로

        try{
            //이미지 경로 설정
            imgPath = Paths.get(path + imgName);
            //헤더에 이미지 확장자 넣기
            headers.add("Content-Type", Files.probeContentType(imgPath));
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    //이미지 저장(업로드)
    // [POST] /test/upload
    @ResponseBody
    @PostMapping("upload")
    public ResponseEntity<String> createImg(@RequestBody MultipartFile file){
        // 시간과 originalFilename으로 매핑 시켜서 src 주소를 만들어 낸다.
        Date date = new Date();
        StringBuilder sb = new StringBuilder();

        // file image 가 없을 경우
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //이미지 이름 설정
        sb.append(date.getTime());
        sb.append(file.getOriginalFilename());
        //이미지가 저장될 경로 설정
        File dest = new File("C:\\Users\\Choi ga na\\Desktop\\SE_7\\server\\img\\" + sb.toString());

        try {
            file.transferTo(dest); //해당 경로로 이미지 옮기기
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>("성공", HttpStatus.OK);
    }
}
