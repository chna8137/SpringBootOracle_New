package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDTO {

    private String name; // 이름
    private String msg; // 채팅 메시지
    private String date; // 발송 날짜
    private String koMsg; // 한국어 채팅 메시지
    private String enMsg; // 영어 채팅 메시지
}
