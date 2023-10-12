package kopo.poly.service.impl;

import kopo.poly.dto.FoodDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class FoodService implements IFoodService {


    @Scheduled(cron = "* * 3 * * *")
    @Override
    public List<FoodDTO> toDayFood() throws Exception {

        log.info(this.getClass().getName() + ".toDayFood Start!");

        int res = 0; // 크롤링 결과 (0보다 크면 크롤링 성공)

        // 서울강서캠퍼스 식단 정보가져올 사이트 주소
        String url = "http://www.kopo.ac.kr/kangseo/content.do?menu=262";

        // JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML 소스 저장할 변수
        Document doc = null;

        // 사이트 접속(http프로토콜만 가능, https 프로토콜은 보안상 안됨)
        doc = Jsoup.connect(url).get();

        Elements element = doc.select("table.tbl_table tbody");

        // Iterator을 사용하여 식단 정보를 가져오기
        Iterator<Element> foodIt = element.select("tr").iterator();

        FoodDTO pDTO = null;

        List<FoodDTO> pList = new ArrayList<>();
        int idx = 0; // 반복횟수를 월요일부터 금요일까지만 되도록함(5일동안만)

        // 수집된 데이터 DB에 저장하기
        while (foodIt.hasNext()) {

            // 반복횟수 카운팅하기, 5번째가 금요일이라 6번째인 토요일은 실행안되게 하기 위함
            // 반복문 5번만 돌기(월요일부터 금요일까지만 넣기)
            if (idx++ > 4) {
                break;
            }
            pDTO = new FoodDTO(); // 수집된 식단정보를 DTO에 저장하기 위해 메모리에 올리기

            // 요일별 식단 정보 들어옴
            String food = CmmUtil.nvl(foodIt.next().text()).trim();

            log.info("food : " + food);

            // 앞의 3글자가 요일이기 때문에 요일 저장
            pDTO.setDay(food.substring(0,3));

            // 식단 정보
            pDTO.setFood_nm(food.substring(4));

            pList.add(pDTO);

        }
        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".toDayFood End!");

        return pList;
    }
}
