<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<h1>수정</h1>
	<form action="update.do" method="post" enctype="multipart/form-data">
		<div>
			<input type="hidden" id="seq" name="seq" value="${bean.seq }">
		</div>
		<div>
			<label for="title">제목</label>
			<input type="text" id="title" name="title" value="${bean.title }">
		</div>
		<div>
			<label for="name">이름</label>
			<input type="text" id="name" name="name" value="${bean.name }">
		</div>
		<div>
			<input type="hidden" id="pass" name="pass" value=${bean.pass }>
		</div>
		<div>
			<label for="pass1">비밀번호</label>
			<input type="password" id="pass1" name="pass1">
		</div>
		<div>
			내용<textarea id="content" name="content">${bean.content }</textarea>
		</div>
		<div><span id="oriupload">첨부파일 ${bean.oriupload }</span><button type="button" id="filedelbtn">삭제</button></div>
		<div><input type="hidden" name="existfile" value="${bean.upload}"></div>
		<div>
			<label for="upload">파일첨부</label>
			<input type="file" id="upload" name="upload">
		</div>
		<button type="submit" id="editbtn">수정</button>
		<button type="button" id="cenbtn" onclick="history.back()">취소</button>
		<button type="button" id="listbtn" onclick="location.href='list.do'">목록</button>
	</form>	
<script type="text/javascript">
	$(document).ready(function(){
		$('#editbtn').click(function(){
			var title=$('#title').val();
			var name=$('#name').val();
			var content=$('#content').val();
			var pass=$('#pass').val();
			var pass1=$('#pass1').val();
			
			if(pass !== pass1){
				alert('비밀번호를 확인해주세요');//비밀번호 체크
				return false;
			}
		}); //editbtn end
		$('#filedelbtn').click(function(){
			$('#oriupload').html("첨부파일");
			
	});//btn end	
});	
</script>		
		
</body>
</html>