package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.persistance.mapper.IMailMapper;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService implements IMailService {

    // 매퍼 연결
    private final IMailMapper mailMapper;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public int doSendMail(MailDTO pDTO) {

        // 로그 찍기 ( 추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다. )
        log.info(this.getClass().getName() + ".doSendMail start !");

        // 메일 발송 성공여부 ( 발송성공 : 1 / 발송실패 : 0 )
        int res = 1;

        // 전달 받은 DTO로부터 데이터 가져오기 ( DTO객체가 메모리에 올라가지 않아 Null이 발생할 수 있기 때문에 에러방지차원으로 if문 사용함
        if (pDTO == null) {
            pDTO = new MailDTO();
        }
        String toMail = CmmUtil.nvl(pDTO.getToMail()); // 받는 사람
        String title = CmmUtil.nvl(pDTO.getTitle()); // 받는 사람
        String contents = CmmUtil.nvl(pDTO.getContents()); // 받는 사람

        log.info("toMail  : " + toMail);
        log.info("title  : " + title);
        log.info("contents  : " + contents);

        // 메일 발송 메시지 구조 ( 파일 첨부 가능 )
        MimeMessage message = mailSender.createMimeMessage();

        // 메일 발송 메시지 구조를 쉽게 생성하게 도와주는 객체
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

        try {

            messageHelper.setTo(toMail); // 받는 사람
            messageHelper.setFrom(fromMail); // 보내는 사람
            messageHelper.setSubject(title); // 메일 제목
            messageHelper.setText(contents); // 메일 내용

            mailSender.send(message);
        } catch (Exception e) { // 모든 에러 다 잡기
            res = 0; // 메일 발송이 실패하기 때문에 0으로 변경
            log.info("[ERROR] " + this.getClass().getName() + ".doSenMail : " + e);

        }

        // 로그 찍기 ( 추후 찍은 로그를 통해 이 함수 호출이 끝났는지 하악하기 용이하다. )
        log.info(this.getClass().getName() + ".doSendMail end !");

        return res;
    }

    // 발신이력 조회 테이블을 만들기위해 추가
    @Override
    public List<MailDTO> getMailList() throws Exception {

        log.info(this.getClass().getName() + ".getMailList start!");

        return mailMapper.getMailList();

    }

    // 발신이력 조회 테이블을 만들기위해 추가
    @Transactional
    @Override
    public void insertMailInfo(MailDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".InsertMailInfo start!");

        mailMapper.insertMailInfo(pDTO);
    }


}
