package kopo.poly.service;

import kopo.poly.dto.MailDTO;

import java.util.List;

public interface IMailService {

    // 메일 발신
    // 이번 실습에서는 예외처리를 넣어주지 않음
    int doSendMail(MailDTO pDTO);

    /**
     * 메일발신 리스트
     *
     * @return 조회 경과
     */
    List<MailDTO> getMailList() throws Exception;

    /**
     * 메일발신 등록
     *
     * @param pDTO 화면에서 입력된 공지사항 입력된 값들
     */
    void insertMailInfo(MailDTO pDTO) throws Exception;
}
