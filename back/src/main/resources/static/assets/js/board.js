const apiRoot = "/api/board";


function writeArticle() {
	let registerInfo = {
			subject: document.querySelector("#subject").value,
			content: document.querySelector("#content").value,
	};
	let config = {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(registerInfo),
	};

	console.log(`${apiRoot}/write`);
	fetch(`${apiRoot}/write`, config)
	.then((response) => {
		console.log(response);
		if (response.status === 200) {
			alert('게시글이 작성되었습니다.');
			location.href = `/assets/board/list.html`;
		} else {
			console.log("error");
			alert("서버가 응답하지 않습니다.");
		}
	});
}

function listArticle(){
	// 게시글목록
	fetch(`${apiRoot}/list`)
	.then((response) => {
		if(response.status === 200){
			// 내용이 있으니까 response를 json화 시킨다.
			return response.json();
		} else if(response.status === 204){
			// 내용이 없으니까 makeList 중에 검색된 결과가 없습니다로 변경.
			makeList()
		} 
		else{
			alert('서버가 응답하지 않습니다.');
		}
	})
	.then((data) => makeList(data));

	// 게시글 등록
	document.querySelector("#btn-mv-register").addEventListener("click", function () {
		location.href = "/assets/board/write.html";
	});

	function makeList(articles) {
		
		let tbody = ``;
		if(!articles){
			tbody += `<td colspan="5">게시글이 없습니다.</td>` 
		}
		else{
			articles.forEach(function (article) {
				console.log(article);
				tbody += `
					<tr id="view_${article.articleNo}" class="view text-center" data-id="${article.articleNo}" ondblclick="articleView(this);">
					<td>${article.articleNo}</td>
					<td>${article.subject}</td>
					<td>${article.userName}</td>
					<td>${article.hit}</td>
					<td>${article.registerTime}
					<td>
					`;
			});
			console.log(tbody);
			document.querySelector("#articlelist");
		}
	}

}