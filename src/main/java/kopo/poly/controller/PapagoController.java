package kopo.poly.controller;

import kopo.poly.dto.PapagoDTO;
import kopo.poly.service.IPapagoService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "papago")
@RequiredArgsConstructor
@RestController
public class PapagoController {

    private final IPapagoService papagoService;

    @PostMapping(value = "detectLangs")
    public PapagoDTO detectLangs(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".detectLangs Start!!!!!");

        // 분석할 문장
        String text = CmmUtil.nvl(request.getParameter("text"));

        log.info("text : " + text);

        PapagoDTO pDTO = new PapagoDTO();
        pDTO.setText(text);

        // Null 에러 처리는 Java 8 부터 지원되는 Optional 사용
        // 입력된 문장의 언어 감지를 위해 서비스 호출하여 결과 받기
        PapagoDTO rDTO = Optional.ofNullable(papagoService.detectLangs(pDTO)).orElseGet(PapagoDTO::new);

        log.info(this.getClass().getName() + ".detectLangs End!");

        return rDTO;

    }
}
