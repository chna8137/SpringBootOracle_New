package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDTO {

    private String collectTime; // 수집시간
    private String seq; // 수집된 데이터 순번
    private String location;
    private String Temperature;
    private String summary;





}
