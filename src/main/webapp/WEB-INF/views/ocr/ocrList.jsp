<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.OcrDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.MovieDTO" %>
<%
    // OcrController 함수에서 model 객체에 저장된 값 불러오기
    List<OcrDTO> rList = (List<OcrDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>OCR 리스트</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript">

    </script>

</head>
<body>
<h2>이미지 리스트</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">번호</div>
            <div class="divTableHead">파일명</div>
            <div class="divTableHead">내용</div>
            <div class="divTableHead">작성자</div>
            <div class="divTableHead">작성일</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (OcrDTO rDTO : rList) {
        %>
        <div class="divTableRow">
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getSeq())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getFileName())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getTextFromImage())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getRegId())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getRegDT())%>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>