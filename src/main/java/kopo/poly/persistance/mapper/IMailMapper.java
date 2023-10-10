package kopo.poly.persistance.mapper;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMailMapper {

    // 발신이력 리스트
    List<MailDTO> getMailList() throws Exception;

    // 발신이력 등록
    void insertMailInfo(MailDTO pDTO) throws Exception;
}
