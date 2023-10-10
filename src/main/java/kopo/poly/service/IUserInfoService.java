package kopo.poly.service;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.UserInfoDTO;
import org.apache.catalina.User;

import java.util.List;

public interface IUserInfoService {

    // 아이디 중복 체크
    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    // 이메일 주소 중복 체크 및 인증 값
    UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception;

    // 히원 가입하기 ( 회원정보 등록하기 )
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    // 회원정보 리스트
    List<UserInfoDTO> getUserList() throws Exception;

     // 회원 상세보기
    UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception;

    // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 ( 2023.09.20 )
    UserInfoDTO getLogin(UserInfoDTO pDTO) throws Exception;

    // 아이디, 비밀번호 찾기에 활용
    UserInfoDTO searchUserIdOrPasswordProc(UserInfoDTO pDTO) throws Exception;

    // 비밀번호 재설정
    int newPasswordProc(UserInfoDTO pDTO) throws Exception;

    // 비밀번호 찾기 페이지 이메일 인증번호
    UserInfoDTO getEmailExistsPs(UserInfoDTO pDTO) throws Exception;
}
