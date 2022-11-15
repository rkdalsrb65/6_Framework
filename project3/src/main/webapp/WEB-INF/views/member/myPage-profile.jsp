<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>

    <link rel="stylesheet" href="/resources/css/main-style.css">
    <link rel="stylesheet" href="/resources/css/myPage-style.css">

    <!-- fontsome 사이트 아이콘 이용 -->
    <script src="https://kit.fontawesome.com/f7459b8054.js" crossorigin="anonymous"></script>
</head>
<body>
    <main>
        <%-- header.jsp include --%>
        <jsp:include page="/WEB-INF/views/common/header.jsp" />

        <session class="myPage-content">

            <%-- <section class="left-side">
                사이드메뉴
                <ul class="list-group">
                    <li><a href="/member/myPage/profile">프로필</a></li>
                    <li><a href="/member/myPage/info">내정보</a></li>
                    <li><a href="/member/myPage/changePw">비밀번호 변경</a></li>
                    <li><a href="/member/myPage/delete">회원 탈퇴</a></li>
                </ul>
            </section> --%>

            <%-- sideMenu.jsp include --%>
            <jsp:include page="/WEB-INF/views/member/sideMenu.jsp" />

            <section class="myPage-main">

                <h1 class="myPage-title">프로필</h1>
                <span class="myPage-subject">프로필 이미지를 변경할 수 있습니다.</span>
                
                <%-- 
                    현재 경로 : /member/myPage/profile
                    요청 경로 : /memer/myPage/updateProfile
                --%>

                <%--  
                    form 태그의 enctype 속성
                    - 서버로 제출되는 데이터의 인코딩 방법을 지정

                    1) application/x-www-form-urlencoded (기본값)
                    -> 모든 문자를 서버로 전송하기 전에 인코딩
                    == 제출되는 데이터를 모두 문자열 형식으로 인코딩
                    == 문자형식만 제출 가능한 형식 (파일 X)

                    2) multipart/form-data (무조건 POST 방식만 가능!!!)
                    -> 모든 문자를 인코딩 하지 않음
                    == 제출되는 데이터를 인코딩 하지 않음
                    == 파일은 파일, 문자열은 문자열로 전달
                    (단, 서버에서 파일/문자열에 대한 별도 처리가 필요)

                --%>

                <form action="updateProfile" method="POST" 
                    name="myPage-frm" enctype="multipart/form-data"
                    onsubmit="return profileValidate()">

                    <div class="profile-image-area">

                        <c:if test="${empty loginMember.profileImage}">
                        <img id="profile-image" src="/resources/images/user.png">
                        </c:if>

                        <c:if test="${!empty loginMember.profileImage}">
                        <img id="profile-image" src="${loginMember.profileImage}">
                        </c:if>


                    </div>
                    <span id="delete-image">&times;</span>

                    <div class="profile-btn-area">
                        <label for="image-input">이미지 선택</label>
                        <!-- accept="image/*" 속성 : 업로드 가능한 파일의 타입을 제한하는 속성 -->
                        <input type="file" name="profileImage" id="image-input" accept="image/*">

                        <button>변경하기</button>
                    </div>

                    <div class="myPage-row">
                        <label>이메일</label>
                        <span>${loginMember.memberEmail}</span>
                    </div>

                    <div class="myPage-row">
                        <label>가입일</label>
                        <span>${loginMember.enrollDate}</span>
                    </div>
                </form>

            </section>
        </session>

    </main>
    <%-- footer.jsp include --%>
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <!-- 다음 주소 api 추가 -->


    <script src="/resources/js/member/myPage.js"></script>
</body>
</html>