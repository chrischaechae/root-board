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
<h1>상세 보기</h1>
	<div>
			<label for="title">제목</label>
			<input type="text" id="title" name="title" value="${bean.title }" readonly="readonly">
		</div>
		<div>
			<label for="name">이름</label>
			<input type="text" id="name" name="name" value="${bean.name }" readonly="readonly">
		</div>
		<div>
			내용<textarea id="content" name="content" readonly="readonly">${bean.content }</textarea>
		</div>
		<div>
		파일첨부 <a href="download.do?oriupload=${bean.oriupload }">${bean.oriupload }</a>
		</div>
		<button type="button" onclick="location.href='reply.do?seq=${bean.seq}&ref=${bean.ref }'">답글</button>
		<button type="button" onclick="location.href='edit.do?seq=${bean.seq}'">수정</button>
		<button type="button" id="delbtn">삭제</button>
		<button type="button" onclick="location.href='list.do'">목록</button>

<script type="text/javascript">		
		$('#delbtn').click(function(){
			var conpw=prompt("비밀번호를 입력하세요");
			var pass=${bean.pass};
			if(conpw == pass){
				var seq="${bean.seq}";
				var upload="${bean.upload}";
				 $.ajax({
					url:'delete.do',
					type:'POST',
					data:{seq:seq,upload:upload},
					success:function(){
						location.href="list.do";
					},
					error:function(){		
					}
				});//ajax end	
			}else{
				alert('비밀번호를 확인해주세요');
				return false;
			}
		});
</script>		
</body>
</html>