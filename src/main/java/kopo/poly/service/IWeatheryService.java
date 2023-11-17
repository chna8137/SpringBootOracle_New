package kopo.poly.service;

import kopo.poly.dto.WeatheryDTO;

public interface IWeatheryService {

    String apiURL = "https://api.openweathermap.org/data/3.0/onecall";

    // 날씨 API를 호출하여 날씨 결과 받아오기

    WeatheryDTO getWeather(WeatheryDTO pDTO) throws Exception;
}