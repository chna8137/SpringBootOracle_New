package kopo.poly.service;

import kopo.poly.dto.FoodDTO;

import java.util.List;

public interface IFoodService {

    // 한국폴리텍대학 강서캠퍼스 식단 정보 가져오기
    List<FoodDTO> toDayFood() throws Exception;
}
