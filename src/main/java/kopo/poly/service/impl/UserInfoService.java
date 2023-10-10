package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mapper.IUserInfoMapper;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService {

    private final IUserInfoMapper userInfoMapper; // 회원관련 SQL 사용하기 위한 Mapper 가져오기

    private final IMailService mailService; // 메일 발송을 위한 MailService 자바 객체 가져오기

    @Override
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists Start !");

        UserInfoDTO rDTO = userInfoMapper.getUserIdExists(pDTO);

        log.info(this.getClass().getName() + ".getUserIdExists End !");

        return rDTO;
    }

    @Override
    public UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".emailAuth Start !");

        // DB 이메일이 존재하는지 SQL 쿼리 실행
        // SQL 쿼리에 COUNT()를 사용하기 때문에 반드시 조회 결과는 존재함
        UserInfoDTO rDTO = userInfoMapper.getEmailExists(pDTO);

        String existsYN = CmmUtil.nvl(rDTO.getExistsYn());

        log.info("existsYN : " + existsYN);

        if (existsYN.equals("N")) {

            // 6자리 랜덤 숫자 생성하기
            int authNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);

            log.info("authNumber : " + authNumber);

            // 인증번호 발송 로직
            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 중복 학인 인증번호 발송 메일");
            dto.setContents("인증번호는 " + authNumber + " 입니다.");
            dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mailService.doSendMail(dto); // 이메일 발송

            rDTO.setAuthNumber(authNumber); // 인증번호를 결과값에 넣어주기
        }

        log.info(this.getClass().getName() + ".emailAuth Start !");

        return rDTO;
    }

    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "insertUserInfo Start !");

        // 회원가입 성공 : 1, 아이디 중복으로 인한 가입 취소 : 2, 기타 에러 발생 : 0
        int res = 0;

        // 회원가입
        int success = userInfoMapper.insertUserInfo(pDTO);

        // db에 데이터가 등록 되었다면 ( 회원가입 성공했다면.....
        if (success > 0) {
            res = 1;

            /*
            * ########################################
            *                               메일 발송 로직 추가 시작 !
            * ########################################
            */
            MailDTO mDTO = new MailDTO();

            // 회원정보화면에서 입력받은 이메일 변수 ( 아직 암호화 되어 넘어오기 때문에 복호화 수행함 )
            mDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mDTO.setTitle("회원가입을 축하드립니다."); // 제목

            // 메일 내용에 가입자 이름 넣어서 내용 발송
            mDTO.setContents(CmmUtil.nvl(pDTO.getUserName()) + "님의 회원가입을 진심으로 축하드립니다.");

            // 회원 가입이 성공했기 때문에 메일을 발송함
            mailService.doSendMail(mDTO);

            /*
             * ########################################
             *                               메일 발송 로직 추가 끝 !
             * ########################################
             */

            log.info(this.getClass().getName() + "insertUserInfo Start !");
        } else {
            res = 0;

        }
        log.info(this.getClass().getName() + ".insertUserInfo End !");

        return res;
    }

    @Override
    public List<UserInfoDTO> getUserList() throws Exception {

        log.info(this.getClass().getName() + ".getUserInfoList Start !");

        return userInfoMapper.getUserList();
    }

    @Transactional
    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getUserInfo Stert !");

        return userInfoMapper.getUserInfo(pDTO);
    }

    /**
     * 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 ( 2023.09.21 )
     *
     * @param pDTO 로그인을 위한 회원아이디, 비밀번호
     * @return 로그인된 회원아이디 정보
     */
    @Override
    public UserInfoDTO getLogin(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getLogin Start !");

        // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기
        // userInfoMapper.getUserLoginCheck(pDTO) 함수 실행 결과가 Null 발생하면, UserInfoDTO 메모리에 올리기
        UserInfoDTO rDTO = Optional.ofNullable(userInfoMapper.getLogin(pDTO)).orElseGet(UserInfoDTO::new);

        /*
        * userInfoMapper로 부터 SELECT 쿼리의 결과로 회원아이디를 받아왔다면, 로그인 성공 ! !
        *
        * DTO의 변수에 값이 있는지 확인하기 처리속도 측면에서 가장 좋은 방법은 별수의 길이를 가져오는 것이다.
        * 따라서 .length() 함수를 통해 회원아이디의 글자수를 가져와 0 보다 큰지 비교한다.
        * 0보다 크다면, 글자가 존재하는 것이기 때문에 값이 존재한다.
        */
        if (CmmUtil.nvl(rDTO.getUserId()).length() > 0) {

            MailDTO mDTO = new MailDTO();

            // 아이디, 패스워드 일치하는지 체크하는 쿼리에서 이메일 값 받아오기 ( 아직 암호화되어 넘어오기 때문에 복호화 수행함 )
            mDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(rDTO.getEmail())));

            mDTO.setTitle("로그인 알림 !"); // 제목

            // 메일 내용에 가입자 이름 넣어서 내용 발송
            mDTO.setContents(DateUtil.getDateTime("yyyy.MM.dd hh:mm:ss") + " 에"
                            + CmmUtil.nvl(rDTO.getUserName()) + "님이 로그인하였습니다.");

            // 회원 가입이 성공했기 때문에 메일을 발송함
            mailService.doSendMail(mDTO);
        }

        log.info(this.getClass().getName() + ".getLogin End !");

        return rDTO;
    }

    /*
    * Controller 에서 전달받는 userId 변수 값의 존재여부
    * - 값이 존재하면, 비밀번호 찾기*/
    @Override
    public UserInfoDTO searchUserIdOrPasswordProc(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".searchUserIdOrPasswordProc Start !");

        UserInfoDTO rDTO = userInfoMapper.getUserId(pDTO);

        log.info(this.getClass().getName() + ".searchUserIdOrPasswordProc End !");

        return rDTO;
    }

    @Override
    public int newPasswordProc(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".newPasswordProc Start !");

        // 비밀번호 재설정
        int success = userInfoMapper.updatePassword(pDTO);

        log.info(this.getClass().getName() + ".newPasswordProc End !");

        return success;
    }

    /*
    * 비밀번호 찾기 페이지 인증번호 발송
    */
    @Override
    public UserInfoDTO getEmailExistsPs(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".emailCheck Start !");

        // DB 이메일이 존재하는지 SQL 쿼리 실행
        // SQL 쿼리에 COUNT()를 사용하기 때문에 반드시 조회 결과는 존재함
        UserInfoDTO rDTO = userInfoMapper.getEmailExists(pDTO);

        String existsYN = CmmUtil.nvl(rDTO.getExistsYn());

        log.info("existsYN : " + existsYN);

        if (existsYN.equals("Y")) {

            // 6자리 랜덤 숫자 생성하기
            int authNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);

            log.info("authNumber : " + authNumber);

            // 인증번호 발송 로직
            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 중복 학인 인증번호 발송 메일");
            dto.setContents("인증번호는 " + authNumber + " 입니다.");
            dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mailService.doSendMail(dto); // 이메일 발송

            rDTO.setAuthNumber(authNumber); // 인증번호를 결과값에 넣어주기
        }

        log.info(this.getClass().getName() + ".emailCheck Start !");

        return rDTO;
    }
}
