package kopo.poly.controller;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/mail")
@RequiredArgsConstructor
@Controller
public class MailController {

    private final IMailService mailService; //메일 발송을 위한 서비스 객체를 사용하기

    /**
     * 메일 리스트 보여주기
     * <p>
     * GetMapping(value = "Mail/MailList") =>  GET방식을 통해 접속되는 URL이 Mail/MailList 경우 아래 함수를 실행함
     */
    @GetMapping(value = "mailList")
    public String mailList(HttpSession session, ModelMap model)
            throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".mailList Start!");

        // 로그인된 사용자 아이디는 Session에 저장함
        // 교육용으로 아직 로그인을 구현하지 않았기 때문에 Session에 데이터를 저장하지 않았음
        // 추후 로그인을 구현할 것으로 가정하고, 공지사항 리스트 출력하는 함수에서 로그인 한 것처럼 Session 값을 생성함
//        session.setAttribute("SESSION_USER_ID", "USER01");

        // 공지사항 리스트 조회하기
        // Java 8부터 제공되는 Optional 활용하여 NPE(Null Pointer Exception) 처리
        List<MailDTO> rList = Optional.ofNullable(mailService.getMailList())
                .orElseGet(ArrayList::new);

//        List<MailDTO> rList = mailService.getMailList();
//
//        if (rList == null) {
//            rList = new ArrayList<>();
//        }

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".mailList End!");

        // 함수 처리가 끝나고 보여줄 JSP 파일명
        // webapp/WEB-INF/views/Mail/MailList.jsp
        return "mail/mailList";

    }

    /**
     * 발신이력 등록
     * <p>
     * 발신이력 등록은 Ajax로 호출되기 때문에 결과는 JSON 구조로 전달해야만 함
     * JSON 구조로 결과 메시지를 전송하기 위해 @ResponseBody 어노테이션 추가함
     */
    @ResponseBody
    @PostMapping(value = "sendMail")
    public void insertMailInfo(HttpServletRequest request, HttpSession session)  throws Exception{

        log.info(this.getClass().getName() + ".mailInsert Start!");

        String msg = ""; // 메시지 내용

        MsgDTO dto = null; // 결과 메시지 구조

        // 로그인된 사용자 아이디를 가져오기
        // 로그인을 아직 구현하지 않았기에 공지사항 리스트에서 로그인 한 것처럼 Session 값을 저장함
        // 이 기능은 메일 보낸 이력을 불러오는 것임. ( DB에서 )
        String toMail = CmmUtil.nvl(request.getParameter("toMail")); // 받는사람
        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
        String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

        /*
         * ####################################################################################
         * 반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함 반드시 작성할 것
         * ####################################################################################
         */

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents : " + contents);

        // 데이터 저장하기 위해 DTO에 저장하기
        MailDTO pDTO = new MailDTO();
        pDTO.setToMail(toMail);
        pDTO.setTitle(title);
        pDTO.setContents(contents);

        /*
         * 게시글 등록하기위한 비즈니스 로직을 호출
         */
        mailService.insertMailInfo(pDTO);

        // 메일 발송하기
        int res = mailService.doSendMail(pDTO);

        if (res == 1 ) { // 메일 발송 성공
            msg = "메일 발송하였습니다.";
        } else { // 메일 발송 실패
            msg = "메일 발송 실패하였습니다.";
        }

        log.info(msg);

        // 결과 메시지 전달하기
//        MsgDTO dto = new MsgDTO();
        dto.setMsg(msg);

        // 로그 찍기 ( 추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다. )
        log.info(this.getClass().getName() + ".sendMail End !");

    }

    /**
     * 메일 발송하기폼
     */
    @GetMapping(value = "mailForm")
    public String mailForm() throws Exception {

        // 로그 찍기 ( 추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다. )
        log.info(this.getClass().getName() + "mailForm Start !");

        return "mail/mailForm";
    }

    /**
     * @메일발송하기 ( 매퍼 생성 후 연결하기전에 구현해둔 메일만 보내는 코드 )
     */
//    @ResponseBody
//    @PostMapping(value = "sendMail")
//    public MsgDTO sendMail(HttpServletRequest request, ModelMap model) throws Exception {
//
//        // 로그 찍기 ( 추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다. )
//        log.info(this.getClass().getName() + ".sendMail Start !");
//
//        String msg = "";
//
//        // 웹 URL로부터 전달받는 값들
//        String toMail = CmmUtil.nvl(request.getParameter("toMail")); // 받는 사람
//        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
//        String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용
//
//        /*
//         * ##########################################################
//         * 반드시 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함 반드시 작성할 것
//         * ##########################################################
//         */
//        log.info("toMail : " + toMail);
//        log.info("title : " + title);
//        log.info("contents : " + contents);
//
//        // 메일 발송할 정보 넣기 위한 DTO객체 생성하기
//        MailDTO pDTO = new MailDTO();
//
//        // 웹에서 받은 값을 DTO에 넣기
//        pDTO.setToMail(toMail); // 받는 사람을 DTO 저장
//        pDTO.setTitle(title); // 제목을 DTO 저장
//        pDTO.setContents(contents); // 내용을 DTO 저장
//
//        // 메일 발송하기
//        int res = mailService.doSendMail(pDTO);
//
//        if (res == 1 ) { // 메일 발송 성공
//            msg = "메일 발송하였습니다.";
//        } else { // 메일 발송 실패
//            msg = "메일 발송 실패하였습니다.";
//        }
//
//        log.info(msg);
//
//        // 결과 메시지 전달하기
//        MsgDTO dto = new MsgDTO();
//        dto.setMsg(msg);
//
//        // 로그 찍기 ( 추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다. )
//        log.info(this.getClass().getName() + ".sendMail End !");
//
//        return dto;
//    }

}
