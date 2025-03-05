package com.example.embitipstest.controller;

import com.example.embitipstest.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TestController {

    private static final String API_URL = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-DASH-001";
    private static final String API_KEY = "nv-6a4c07a6a4984efb81d5a82ab120756d5wea";

    private final TestService service;
    @GetMapping("/test/{name}")
    public String test(@PathVariable String name){
        System.out.println("receive name :" + name);
        String result = service.saveEntity(name).getName();
        return result;
    }
    @GetMapping("/test1")
    public String test1(){

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ API_KEY);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString()); // 🔹 요청 ID 랜덤 생성
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "text/event-stream");

        // 요청 바디 설정 (JSON 데이터)
        String requestBody = "{\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"system\",\n" +
                "      \"content\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"topP\": 0.8,\n" +
                "  \"topK\": 0,\n" +
                "  \"maxTokens\": 256,\n" +
                "  \"temperature\": 0.5,\n" +
                "  \"repeatPenalty\": 5.0,\n" +
                "  \"stopBefore\": [],\n" +
                "  \"includeAiFilters\": true,\n" +
                "  \"seed\": 0\n" +
                "}";

        // HTTP 요청 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        System.out.println("응답 코드: " + response.getStatusCode());
        System.out.println("응답 바디: " + response.getBody());
        System.out.println("응답완료");

        return "testtest";
    }

    @GetMapping("/test2")
    public String test2(){
        String url = "https://clovastudio.stream.ntruss.com";
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY); // API 키 설정
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString()); // 요청 ID 설정
        headers.setContentType(MediaType.MULTIPART_FORM_DATA); // 요청 타입: multipart/form-data
        headers.set("Accept", "text/event-stream");

        // 로컬 파일 경로
        File datasetFile = new File("C:/Users/Yuhojae/Desktop/Conversation.csv"); // 로컬 데이터셋 파일 경로

        // 요청 바디 (multipart/form-data 형태로 데이터를 설정)
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "testtt"); // 학습 이름 (선택 사항)
        body.add("model", "HCX-003"); // 모델 이름
        body.add("tuningType", "PEFT"); // 기본 튜닝 기법
        body.add("taskType", "GENERATION"); // 기본 학습 유형
        body.add("trainEpochs", "8"); // 에폭 수 (기본값: 8)
        body.add("learningRate", "1.0E-4"); // 학습률 (기본값: 1.0E-4)

        // 로컬 파일을 MultipartFile로 추가
        body.add("trainingDatasetFilePath", new FileSystemResource(datasetFile)); // 데이터셋 파일 경로
        body.add("trainingDatasetBucket", ""); // 버킷 이름
        body.add("trainingDatasetAccessKey", ""); // 데이터셋 접근을 위한 액세스 키
        body.add("trainingDatasetSecretKey", ""); // 데이터셋 접근을 위한 시크릿 키

        // HttpEntity 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // POST 요청 보내기
        try {
            ResponseEntity<String> response = restTemplate.exchange(url + "/tuning/v2/tasks", HttpMethod.POST, requestEntity, String.class);

            // 응답 코드 및 바디 출력
            System.out.println("응답 코드: " + response.getStatusCode());
            System.out.println("응답 바디: " + response.getBody());

            return response.getBody();
        } catch (Exception e) {
            System.out.println("=======");
            e.printStackTrace();
            System.out.println("=======");
            System.out.println(e.getMessage());
            System.out.println("=======");
            System.err.println("API 요청 실패: " + e.getMessage());
            return "API 요청 실패: " + e.getMessage();
        }
    }
    @GetMapping("/test3")
    public String test3(){
        // 지역변수 설정
        String apiUrl = "https://clovastudio.stream.ntruss.com/tuning/v2/tasks";

        int page = 0;  // 페이지 번호 (0~N)
        int size = 20; // 페이지 출력 수 (1~100)

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);  // API 키
        headers.set("Content-Type", "application/json");   // JSON 형식
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString()); // 요청 ID 설정

        // 요청 쿼리 파라미터 설정
        String urlWithParams = apiUrl + "?page=" + page + "&size=" + size;

        // RestTemplate 사용하여 GET 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(urlWithParams, HttpMethod.GET,
                new org.springframework.http.HttpEntity<>(headers), String.class);

        // 응답 출력
        System.out.println("Response: " + response.getBody());
        return "test";
    }
}
