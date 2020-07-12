<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<h1>답글</h1>
	<form action="replywri.do" method="post" enctype="multipart/form-data">
		<div>
			<input type="hidden" name="seq" value="${bean.seq }">
		</div>
		<div>
			<input type="hidden" name="ref" value="${bean.ref }">
		</div>
		<div>
			<input type="hidden" name="indent" value="${bean.indent }">
		</div>
		<div>
			<input type="hidden" name="step" value="${bean.step }">
		</div>
		<div>
			<label for="title">제목</label>
			<input type="text" id="title" name="title">
		</div>
		<div>
			<label for="name">이름</label>
			<input type="text" id="name" name="name">
		</div>
		<div>
			<label for="pass">비밀번호</label>
			<input type="password" id="pass" name="pass">
		</div>
		<div>
			내용<textarea id="content" name="content"></textarea>
		</div>
		 <div>
			<label for="upload">파일첨부</label>		
			<input type="file" id="upload" name="upload">
		</div>
		<input type="submit" id="repbtn" value="전송">
		<button type="reset" onclick="location.href='list.do'">취소</button>
	</form>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#repbtn').click(function(){
				var title=$('#title').val();
				var name=$('#name').val();
				var pass=$('#pass').val();
				var content=$('#content').val();
				var space = /\s/;
				
				if(title=='' || title==null || space.exec(title) || name =='' || name==null || space.exec(name) || pass=='' || pass==null || space.exec(pass) || space.exec(con) || content=='' || content==null){
					alert('내용을 입력해주세요');
					return false;
				}else if(title.length>30 || name.length>20 || pass.length>5 || content.length>100) {
					alert('글자수가 너무 많습니다.');
					return false;
				}
			});
		});
	
	</script>
</body>
</html>