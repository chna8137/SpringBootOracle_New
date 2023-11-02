package kopo.poly.service;

import kopo.poly.dto.OcrDTO;

import java.util.List;

public interface IOcrService {

    // 이미지 리스트
    List<OcrDTO> getOcrList() throws Exception;

    // 이미지 파일 등록
    void insertOcrInfo(OcrDTO pDTO) throws Exception;

    // 이미지 파일로부터 문자 읽어 오기
    OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception;
}
