<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    String ssUserName = CmmUtil.nvl((String) session.getAttribute("SS_USER_NAME")); // 로그인된 회원 이름
    String ssUserId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 로그인된 회원 아이디
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인 성공</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            // 버튼 클릭했을때, 발생되는 이벤트 생성함 ( onclick 이벤트와 동일함 )
            $("#btnSend").on("click", function () {
                location.href = "/html/index";
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함 ( onclick 이벤트와 동일함 )
            // 유저의 세션정보값을 담은 상세보기 페이지로 이동
            $("#btnInfo").on("click", function () {
                location.href = "/user/userInfoSession";
            })
        })
    </script>
</head>
<body>
<h2>로그인하기</h2>
<hr/>
<br/>
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">* 로그인된 사용자이름
                </div>
                <div class="divTableCell"><%=ssUserName%> 님이 로그인하였습니다. </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">* 로그인된 사용자아이디
                </div>
                <div class="divTableCell"><%=ssUserId%> 입니다. </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnSend" type="button">메인 화면 이동</button>
        <button id="btnInfo" type="button">상세보기</button>
    </div>
</form>
</body>
</html>