<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인하기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            // 회원가입
            $("#btnUserReg").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함 ( onclick 이벤트와 동일함 )
                location.href = "/user/userRegForm";
            })

            // 아이디 찾기
            $("#btnSearchUserId").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함 ( onclick 이벤트와 동일함 )
                location.href = "/user/searchUserId";
            })

            // 비밀번호 찾기
            $("#btnSearchPassword").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함 ( onclick 이벤트와 동일함 )
                location.href = "/user/searchPassword";
            })

            // 로그인
            $("#btnLogin").on("click", function () {

                let f = document.getElementById("f"); // form 태그

                if (f.userId.value === "") {
                    alert("아이디를 입력하세요.");
                    f.userId.focus();
                    return;
                }

                if (f.password.value === "") {
                    alert("비밀번호를 입력하세요");
                    f.password.focus();
                    return;
                }

                // Ajax 호출해서 회원가입하기
                $.ajax({
                        url: "/user/loginProc",
                        type: "post", // 전송방식은 Post
                        dataType: "JSON", // 전송 결과는 JSON으로 받기
                        data: $("#f").serialize(), // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
                        success: function (json) { // /notice/noticeUpdate 호출이 성공했다면..

                            if (json.result === 1) { // 로그인 성공
                                alert(json.msg); // 메시지 띄우기
                                location.href = "/user/loginResult"; // 로그인 성공 페이지 이동

                            } else { // 회원가입 실패
                                alert(json.msg); // 메시지 띄우기
                                $("#UserId").focus(); // 아이디 입력 항목에 마우스 커서 이동
                            }
                        }
                    }
                )

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
                <div class="divTableCell">* 아이디
                </div>
                <div class="divTableCell">
                    <input type="text" name="userId" style="width:80%" placeholder="아이디"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">* 비밀번호
                </div>
                <div class="divTableCell">
                    <input type="password" name="password" style="width:95%" placeholder="비밀번호"/>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnLogin" type="button"><a href="loginResult"> 로그인</a></button>
        <button id="btnSend" type="button"><a href="userList"> 회원가입</a></button>
        <button id="btnSearchUserId" type="button"><a href="searchUserId"> 아이디 찾기</a></button>
        <button id="btnSearchPassword" type="button"><a href="searchPassword"> 비밀번호 찾기</a></button>
    </div>
</form>
</body>
</html>