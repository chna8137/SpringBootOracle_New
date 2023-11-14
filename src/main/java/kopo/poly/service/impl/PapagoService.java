package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.PapagoDTO;
import kopo.poly.service.IPapagoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PapagoService implements IPapagoService {

    @Value("${naver.papago.clientId}")
    private String clientId;

    @Value("${naver.papago.clentSecret}")
    private String clientSecret;

    private Map<String, String> setNaverInfo() {
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("X-Naver-Client-Id", clientId); // papago 사용자 ID
        requestHeader.put("X-Naver-Client-Secret", clientSecret); // Papago 사용자 비밀번호

        log.info("clientId : " + clientId);
        log.info("clientSecret : " + clientSecret);

        return requestHeader;
    }

    @Override
    public PapagoDTO detectLangs(PapagoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".detectLangs Start!!");

        String text = CmmUtil.nvl(pDTO.getText()); // 영잘할 문장

        // 호출할 Papago 번역 API 정보 설정
        String param = "query=" + URLEncoder.encode(text, "UTF-8"); // 언어 감지할 문장

        // PapagoAPI 호출하기
        String json = NetworkUtil.post(IPapagoService.detectLangsApiURL, this.setNaverInfo(), param);

        // json 변수 형태 예 : {"langCode":"ko"}
        log.info("json : " + json);

        // JSON 구조를 Map 데이터 구조로 변경하기
        // 키와 값 구조의 JSON 구조로부터 데이터를 쉽게 가져오기 위해 Map 데이터구조로 변경함
        PapagoDTO rDTO = new ObjectMapper().readValue(json, PapagoDTO.class);

        // 언어 감지를 위한 원문 저장하기
        rDTO.setText(text);

        log.info(this.getClass().getName() + ".detectLangs End!!");

        return rDTO;
    }

    /**
     * 네이버 API 사용을 위한 접속 정보 설정하기
     */



}
