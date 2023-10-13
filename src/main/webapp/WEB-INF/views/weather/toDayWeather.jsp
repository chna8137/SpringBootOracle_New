<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.WeatherDTO" %>
<%
    // Controller로부터 전달받은 데이터
    List<WeatherDTO> rList = (List<WeatherDTO>) request.getAttribute("rList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>네이버 현재 날씨</title>
    <link rel="stylesheet" href="/css/table.css"/>
</head>
<body>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">장소</div>
            <div class="divTableHead">온도</div>
            <div class="divTableHead">날씨</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (WeatherDTO rDTO : rList) {
        %>
        <div class="divTableRow">
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getLocation())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getTemperature())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getSummary())%>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
