package kopo.poly.service.impl;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class WeatherService implements IWeatherService {

    @Override
    public List<WeatherDTO> getWeatherInfo() throws Exception {
        log.info(this.getClass().getName() + ".getWeatherInfo Start!");

        int res = 0; // 크롤링 결과 (0보다 크면 크롤링 성공)

        // 날씨 정보 가져올 사이트 주소
        String url = "https://weather.naver.com/";

        // JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML 소스 저장할 변수
        Document doc = null;

        // 사이트 접속(http프로토콜만 가능, https 프로토콜은 보안상 안됨)
        doc = Jsoup.connect(url).get();

//        WeatherDTO pDTO = new WeatherDTO();

        Elements element = doc.select("div.today_weather");

        List<WeatherDTO> weatherList = new ArrayList<>();

        // location 정보 확인 후 설정
        Element locationElement = element.select("strong.location_name").first();
        // temperature 정보 확인 후 설정
        Element temperatureElement = element.select("strong.current").first();
        // summary 정보 확인 후 설정
        Element summaryElement = element.select("span.weather").first();

        if (locationElement != null && temperatureElement != null && summaryElement != null) {
            WeatherDTO pDTO = new WeatherDTO();
            pDTO.setLocation(locationElement.text());
            pDTO.setTemperature(temperatureElement.text().substring(5));
            pDTO.setSummary(summaryElement.text());

            weatherList.add(pDTO);
        }

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getWeatherInfo End!");

        return weatherList;
    }

}
