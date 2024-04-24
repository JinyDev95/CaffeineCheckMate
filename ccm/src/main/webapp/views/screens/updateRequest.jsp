<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/resources/css/updateRequest.css"rel="stylesheet" type="text/css">

<!-- 임시 모달 코드 -->

<style>
    /* 모달을 위한 스타일 */
    .modal-container {
        display: none; /* 기본적으로 모달은 숨겨져 있어야 함 */
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5); /* 배경을 어둡게 함 */
        z-index: 9999; /* 다른 요소 위에 모달이 나타나도록 함 */
    }

    .modal-content {
        background-color: #fff;
        width: 500px;
        margin: 100px auto; /* 화면 중앙에 위치하도록 함 */
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5); /* 그림자 효과 추가 */
    }
</style>

<script>
	    // 댓글 모달을 보여주는 함수
	    function showPasswordModal() {
	        var modal = document.getElementById("modal");
	        modal.style.display = "block";
	
	        // AJAX를 통해 다른 JSP 페이지를 불러와 모달에 표시
	        var xhr = new XMLHttpRequest();
	        
	        xhr.open("POST", "<%=request.getContextPath()%>/views/screens/passwordChange.jsp");
	        xhr.send();
	        
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState == 4 && xhr.status == 200) {
	                var modalContent = document.getElementById("modal-content");
	                modalContent.innerHTML = xhr.responseText;
	
	                // 외부 JavaScript 파일의 경로
	                var jsFilePath = "<%=request.getContextPath()%>/resources/js/customReply.js";
	
	                // 외부 JavaScript 파일을 가져와서 모달에 추가
	                var scriptElement = document.createElement("script");
	                scriptElement.src = jsFilePath;
	                modalContent.appendChild(scriptElement);
	            }
	        };
	    }
	
	    // 모달을 닫는 함수
	    function closeModal() {
	        var modal = document.getElementById("modal");
	        modal.style.display = "none";
	    }
	</script>
</head>
<body>
<div class="container">
<h1>회원정보수정</h1>
<% 
	String UpdateID = (String)session.getAttribute("AUTH_USER_ID");
	String UpdateNAME = (String)session.getAttribute("AUTH_USER_NAME");
	String UpdateSSN = (String)session.getAttribute("AUTH_USER_SSN");
	String UpdateEMAIL = (String)session.getAttribute("AUTH_USER_EMAIL");
	String UpdateNICKNAME = (String)session.getAttribute("AUTH_USER_NICKNAME");
	String UpdateTEL = (String)session.getAttribute("AUTH_USER_TEL");
	String UpdateGENDER = (String)session.getAttribute("AUTH_USER_GENDER");
	String UpdateSNS = (String)session.getAttribute("AUTH_USER_SNS");
%>
	<form action="/UpdateMemberHandler.do" name="updateForm" id="updateForm" method="post">

		아이디 :<input type="text" value="<%= UpdateID%>"  style="border:none" readonly/></br>
		비밀번호 :
				<!-- <input type="button" value="변경" onclick="location.href='/views/screens/passwordChange.jsp'"/><br/> -->
				<input type="button" value="변경" onclick="showPasswordModal()"/><br/>
		<!-- 모달을 나타내는 HTML -->
	<div id="modal" class="modal-container">
	    <button onclick="closeModal()">닫기</button>
	    <!-- 모달 내용 영역(비워두세요) -->
	    <div class="modal-content" id="modal-content">
	    </div>
	</div>
		
		이름 :<input type="text" value="<%= UpdateNAME%>"  style="border:none" readonly/></br>
		생년월일 :<input type="text" value="<%= UpdateSSN%>"  style="border:none" readonly/></br>
		E_mail :<input type="text" value="<%= UpdateEMAIL%>"  style="border:none" readonly/></br>
		닉네임 :<input type="text" value="<%= UpdateNICKNAME%>"  style="border:none" readonly/></br>
		TEL :<input type="text" value="<%= UpdateTEL%>" name="updateTel" id="updateTel" style="border:none" size=10 readonly/>
		<input type="button" value="변경" onclick="updateTEL()"/><br/>
				<script>
				function updateTEL(){
						  var url = "/views/screens/updateTEL.jsp"
						  		window.open(url,"_blank_1","width=500,height=300, toolbar=no,menubar=no,resizble=no,scrollbars=yes")
				}
				</script>
		성별 :<input type="text" value="<%= UpdateGENDER%>"  style="border:none" readonly/></br>
		SNS 수신여부 :<input type="text" value=" <%= UpdateSNS%>" size=3 style="border:none" readonly/>
	<% 
	
	if(UpdateSNS.equals("동의")){%>
			<form action="<%=request.getContextPath() %>/UpdateMemberHandler.do" method = "post" name="UpdateSnsForm">
				<input type="radio" name="updateSnsYN" id="updateSnsY" value="Y" checked="checked"/>동의
				<input type="radio" name="updateSnsYN" id="updateSnsN" value="N" required="required"/>거절
				<input type="hidden" value="UpdateSsn" name="Value">
				<input type="submit" value="변경"/><br/>
			</form>
	<%} else {%>		
			<form action="<%=request.getContextPath() %>/UpdateMemberHandler.do" method = "post" name="UpdateSnsForm">
				<input type="radio" name="updateSnsYN" id="updateSnsY" value="Y" required="required"/>동의
				<input type="radio" name="updateSnsYN" id="updateSnsN" value="N" checked="checked"/>거절
				<input type="hidden" value="UpdateSsn" name="Value">
				<input type="submit" value="변경"/><br/>
			</form>	
				<%}%>
			<br/>

			<form action="/views/screens/identify.jsp" method="post" name= "deleteSubmit" id="deleteSubmit">
				<input type="hidden" value="delete" name="Value"/>
				<div class="button-container">	
					<input type= "button" value="홈으로" onclick="location.href='/views/screens/testView.jsp'"/>
					<input type="submit" value="탈퇴"/>
				</div>
			</form>
	</form>
</div>
</html>