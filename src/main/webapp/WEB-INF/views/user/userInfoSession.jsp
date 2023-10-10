<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.EncryptUtil" %>
<%
    // NoticeController 함수에서 model 객체에 저장된 값 불러오기
    UserInfoDTO rDTO = (UserInfoDTO) request.getAttribute("rDTO");
    String ssUserName = CmmUtil.nvl((String) session.getAttribute("SS_USER_NAME")); // 로그인된 회원 이름
    String ssUserId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 로그인된 회원 아이디
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시판 글보기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script>
        // Controller에서 받은 세션에 저장된 값
        <%--const session_user_id = "<%=CmmUtil.nvl((String)session.getAttribute("SESSION_USER_ID"))%>";--%>
        <%--const session_user_name = "<%=CmmUtil.nvl((String) session.getAttribute("SS_USER_NAME"))%>";--%>

        // 공지사항 게시글 작성자 아이디
        const user_id = "<%=CmmUtil.nvl(rDTO.getUserId())%>";

        // 현재 글번호, 자바에서 받을 변수들은 자바스크립트 변수로 저장하면 편함
        <%--const nSeq = "<%=CmmUtil.nvl(rDTO.getUserSeq())%>";--%>

        // HTML로딩이 완료되고, 실행됨
        // $(document).ready(function () {
        //
        //     // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
        //     $("#btnList").on("click", function () {
        //         location.href = "/user/user"; // 공지사항 리스트 이동
        //     })
        // })
    </script>
</head>
<body>
<h2>유저정보 상세보기</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell">회원아이디
            </div>
            <div class="divTableCell"><%=ssUserId%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">이름
            </div>
            <div class="divTableCell"><%=ssUserName%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">이메일
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(EncryptUtil.decAES128CBC(rDTO.getEmail()))%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">주소
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getAddr1())%> <%=CmmUtil.nvl(rDTO.getAddr2())%>
            </div>
        </div>
    </div>
</div>
<div>
    <button id="btnList" type="button">목록</button>
</div>
</body>
</html>
