package com.boot.selftop_web.member.customer.biz;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoService {
    @Autowired
    private CustomerBiz customerBiz;

    public String getAccessToken(String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로 변경을 해주세요

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            // BufferedWriter 간단하게 파일을 끊어서 보내기로 토큰값을 받아오기위해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=66f3a9b8a7fc33ae96bc2f1fbc513320");  //발급받은 key
            sb.append("&redirect_uri=http://localhost:8080/kakaoLogin");     // 로컬
            //sb.append("&redirect_uri=http://15.168.89.127:8999/kakaoLogin");     // 서버
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            // 여기서 안되는경우가 많이 있어서 필수 확인 !! **
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode + "확인");

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result + "결과");

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return access_Token;
    }


    public CustomerDto getuserinfo(String access_Token) {
        Map<String, Object> userInfo = new HashMap<>();
        String requestURL = "https://kapi.kakao.com/v2/user/me";
        CustomerDto kakaoUser = new CustomerDto();

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to fetch user info: " + responseCode);
            }

            BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                result.append(line);
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String id = element.getAsJsonObject().get("id").getAsString();

            String name = properties.getAsJsonObject().get("nickname").getAsString();
            System.out.println("mnickname : " + name);
            System.out.println("kakao_account : " + kakao_account);
            System.out.println(element);

            //userInfo에 사용자 정보 저장
            userInfo.put("id", id);
            userInfo.put("name", name);
            userInfo.put("access_Token", access_Token);

        } catch (Exception e) {
            e.printStackTrace();
        }

        CustomerDto member = customerBiz.findkakao(userInfo);
        // 저장되어있는지 확인
        System.out.println("S :" + member);
        System.out.println("userInfo : " + userInfo);
        System.out.println("id :" + userInfo.get("id"));
        System.out.println("name :" + userInfo.get("name"));
        System.out.println("access_Token :" + userInfo.get("access_Token"));

        System.out.println("member : " + member);
        if (member == null) {
            //member null 이면 DB에 저장하기 위해 dto에 kakao 인자값 지정
            CustomerDto dto = new CustomerDto();
            dto.setId(userInfo.get("id").toString());
            dto.setPw("kakao_default");
            dto.setName(userInfo.get("name").toString());
            dto.setEmail("kakao@kakao.com");
            dto.setPhone("N/A");

            // kakaoinsert 실행으로 customer 테이블에 정보 저장
            int res = customerBiz.kakaoinsert(dto);

            if (res > 0) {
                System.out.println("null o : 등록 성공");
            } else {
                System.out.println("null o : 등록 실패");
            }
        } else {
            System.out.println("null x : 로그인 성공");
        }
        kakaoUser = customerBiz.findkakao(userInfo);
        return kakaoUser;
    }
}
