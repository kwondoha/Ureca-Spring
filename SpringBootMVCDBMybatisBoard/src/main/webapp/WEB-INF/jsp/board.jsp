<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycom.myapp.user.dto.UserDto" %>
<%
	UserDto userDto = (UserDto) session.getAttribute("userDto");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

<title>게시판</title>
</head>
<body>
	<!-- JSP Include 기능으로 별도의 navbar.jsp 로 분리 후 개별 jsp 에서 활용 -->
	<nav class="navbar navbar-expand-lg bg-light">
	  <div class="container">
	    <a class="navbar-brand" href="#">
			<img src="/assets/img/user/<%= userDto.getUserProfileImage()%>" style="width: 32px; height: 32px; border-radius: 50%;">
		</a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	        <li class="nav-item">
	          <a class="nav-link active" aria-current="page" href="#">Home</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="#">Link</a>
	        </li>
	      </ul>
		  <ul class="navbar-nav">
  	        <li class="nav-item">
			  <!-- a 태그를 클릭 -> 페이지 요청 -> 페이지 요청을 처리하는 Controller 에서 Session Invalidate & Login Page 이동 -->
			  <!-- 단순 페이지 이동이 아니라, 마지막 로그인시각 기록 등 별도의 백엔드 처리가 필요하다. 
				   페이지 요청 처리 Controller 보다 Login Controller 에 데이터 요청 응답 받아서 처리 (Javascript ) 더 효율적이다. -->
  	          <a class="nav-link" href="/pages/logout">Logout</a>
  	        </li>
  	      </ul>		  
	    </div>
	  </div>
	</nav>
	
	<div class="container mt-4">

		<h4 class="text-center">게시판</h4>
		
		<div class="input-group">
		  <input type="text" class="form-control" placeholder="검색어를 입력하세요." id="inputSearchWord">
		  <button class="btn btn-success" type="button" id="btnSearchWord">검색</button>
		</div>
		
		<table class="table table-hover">
		  <thead>
		    <tr>
		      <th>#</th>
		      <th>제목</th>
		      <th>작성자</th>
		      <th>작성일시</th>
		      <th>조회수</th>
		    </tr>
		  </thead>
		  <tbody id="boardTbody">

		  </tbody>
		</table>
		
		<div id="paginationWrapper"></div>
		
		<!-- bootstrap 을 통한 모달창 띄우기는 static content 에 의미 -->
		<!-- 
		<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#insertBoardModal">글 쓰기</button>
		-->
		<button type="button" class="btn btn-primary" id="btnInsertPage">글 쓰기</button>
	</div>	
	
	<script src="/assets/js/util.js"></script>
	<script>
		let LIST_ROW_COUNT = 10; 	// 화면에 게시글을 몇개 행으로 보여줄 것인가? == limit, Pagination Factor #1
		let OFFSET = 0;				// 몇개를 건너뛰고 보여줄 것인가?
		let SEARCH_WORD = '';		// 검색어

		let PAGE_LINK_COUNT = 10;	// 화면에 보여 줄 페이지 링크 1-2-3-4-5.. 몇 개, Pagination Factor #2
		let CURRENT_PAGE_INDEX = 1;	// 현재 화면에 보여줄 페이지 링크 인덱스 1-2-3-4-5.. 중 현재 몇 번째?, Pagination Factor #3
		let TOTAL_LIST_COUNT = 0;	// 현재 게시글 전체 수, Pagination Factor #4 <= 백엔드 제공

		window.onload = function(){
			// 글 목록
			listBoard();

			// 검색어 목록
			document.querySelector("#btnSearchWord").onclick = function(){
				// 검색 버튼을 눌렀을 때, 검색어가 있으면 검색 목록, 없으면 전체 목록 한꺼번에 처리
				SEARCH_WORD = document.querySelector("#inputSearchWord").value;
				listBoard();
			}
		}		
	
		
		// 목록
		async function listBoard(){
			
			let url = "/boards/list";
			let urlParams = "?limit=" + LIST_ROW_COUNT + "&offset=" + OFFSET + "&searchWord=" + SEARCH_WORD;
			let response = await fetch(url + urlParams);
			let data = await response.json();
			
			console.log(data);		
			
			if( data.result == "success" ){			
				// data -> html
				makeListHtml(data.list);
				TOTAL_LIST_COUNT = data.count;
				addPagination();
			}		
		}
		
		function makeListHtml(list){
			let listHtml = ``;
			
			list.forEach( el => {
				let boardId = el.boardId;
				let userName = el.userName;
				let title = el.title;
				
				let regDt = new Date(el.regDt); // "2025-11-11T09:30:05" -> javascript Date 객체			
				let regDtStr = makeDateStr(regDt.getFullYear(), regDt.getMonth() + 1, regDt.getDate(), '.');
				
				let readCount = el.readCount;
				
	// 			console.log(regDtStr);
				listHtml += `
					<tr style="cursor:pointer" data-boardId="\${boardId}">
						<td>\${boardId}</td>
						<td>\${title}</td>
						<td>\${userName}</td>
						<td>\${regDtStr}</td>
						<td>\${readCount}</td>
					</tr>
				`;
			} );
			
			document.querySelector("#boardTbody").innerHTML = listHtml;
			
			// 동적으로 추가한 <tr> 항목에 대한 click 이벤트 핸들러 작성
			document.querySelectorAll("#boardTbody tr").forEach( el => {
				el.onclick = function(){
					let boardId = this.getAttribute("data-boardId");
					detailBoard(boardId);
				}
			} ); // el 하나가 <tr> 하나
		}
		
		function addPagination(){
			console.log(LIST_ROW_COUNT, PAGE_LINK_COUNT, CURRENT_PAGE_INDEX, TOTAL_LIST_COUNT);
			makePaginationHtml(LIST_ROW_COUNT, PAGE_LINK_COUNT, CURRENT_PAGE_INDEX, TOTAL_LIST_COUNT, "paginationWrapper" );
		}

		// 9 를 누르면 movePage(9) 가 호출
		// 현재 페이지가 9 로 변경
		// 9 이전 1~8 페이지까지의 데이터를 skip
		function movePage(pageIndex){
			CURRENT_PAGE_INDEX = pageIndex;
			OFFSET = (pageIndex -1) * LIST_ROW_COUNT;
			listBoard();
		}		
		
		
		
		async function detailBoard(boardId){
			alert(boardId);
		}	
	</script>	
</body>
</html>