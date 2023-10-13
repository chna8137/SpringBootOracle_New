package kopo.poly.controller;

import kopo.poly.dto.FoodDTO;
import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
@Controller
public class WeatherController {

    private final IWeatherService weatherService; // Weather 서비스 객체 주입하기

    @GetMapping(value = "toDayWeather")
    public String toDayWeather(ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".toDayWeatherd Start!");

        // weatherServie.toDayWeather() 결과를 Null 값 체크하여 rList 객체에 저장하기
        List<WeatherDTO> rList = Optional.ofNullable(weatherService.getWeatherInfo()).orElseGet(ArrayList::new);

        // 크롤링 결과를 넣어주기
        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".toDayWeather End!");

        return "/weather/toDayWeather";
    }

}
