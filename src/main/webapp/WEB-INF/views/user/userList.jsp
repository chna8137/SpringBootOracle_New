<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.MailDTO" %>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.util.EncryptUtil" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%
    // MailController 함수에서 model 객체에 저장된 값 불러오기
    List<UserInfoDTO> rList = (List<UserInfoDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>유저 목록</title>
    <link rel="stylesheet" href="/css/table.css"/>
        <script type="text/javascript">

            //상세보기 이동
            function doDetail(userId) {
                location.href = "/user/userInfo?userId=" + userId;
            }

        </script>
</head>
<body>
<h2>유저 목록</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">순번</div>
            <div class="divTableHead">회원아이디</div>
            <div class="divTableHead">이름</div>
            <div class="divTableHead">이메일</div>
            <div class="divTableHead">주소</div>
        </div>
    </div>

    <div class="divTableBody">
        <%
            for (UserInfoDTO dto : rList) {
        %>
        <div class="divTableRow">
<%--            <%--%>
<%--                if (CmmUtil.nvl(dto.getUserId()).equals("Y")) { //공지글이라면, [공지]표시--%>
<%--                    out.print("<div class=\"divTableCell\">공지</div>");--%>

<%--                } else { //공지글이 아니라면, 글번호 보여주기--%>
<%--                    out.print("<div class=\"divTableCell\">" + CmmUtil.nvl(dto.getUserId()) + "</div>");--%>

<%--                }--%>
<%--            %>--%>
            <div class="divTableCell"><%=dto.getUserSeq()%></div>
            <div class="divTableCell" onclick="doDetail('<%=CmmUtil.nvl(dto.getUserId())%>')"> <%=CmmUtil.nvl(dto.getUserId())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getUserName())%></div>
             <div class="divTableCell"><%=EncryptUtil.decAES128CBC(CmmUtil.nvl(dto.getEmail()))%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getAddr1())%> <%=CmmUtil.nvl(dto.getAddr2())%></div>
        </div>
        <%
            }
        %>
    </div>


    <%--    <div class="divTableBody">--%>
    <%--        <%--%>
    <%--            for (MailDTO dto : rList) {--%>
    <%--        %>--%>
    <%--        <div class="divTableRow">--%>
    <%--            <%--%>
    <%--                if (CmmUtil.nvl(dto.getMailSeq()).equals("Y")) { //공지글이라면, [공지]표시--%>
    <%--                    out.print("<div class=\"divTableCell\">공지</div>");--%>

    <%--                } else { //공지글이 아니라면, 글번호 보여주기--%>
    <%--                    out.print("<div class=\"divTableCell\">" + CmmUtil.nvl(dto.getMailSeq()) + "</div>");--%>

    <%--                }--%>
    <%--            %>--%>
    <%--            <div class="divTableCell"--%>
    <%--                 onclick="doDetail('<%=CmmUtil.nvl(dto.getMailSeq())%>')"><%=CmmUtil.nvl(dto.getTitle())%>--%>
    <%--            </div>--%>
    <%--            <div class="divTableCell"><%=CmmUtil.nvl(dto.getReadCnt())%>--%>
    <%--            </div>--%>
    <%--            <div class="divTableCell"><%=CmmUtil.nvl(dto.getToMail())%>--%>
    <%--            </div>--%>
    <%--            <div class="divTableCell"><%=CmmUtil.nvl(dto.getSendTime())%>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--        <%--%>
    <%--            }--%>
    <%--        %>--%>
</div>
<%--<a href="mailForm">메일 쓰기</a>--%>
</body>
</html>
