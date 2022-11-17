// 로그인
function login() {
    // 서버로 보낼 정보 작성
    let loginInfo = {
        id: document.querySelector("#userid").value,
        password: document.querySelector("#userpw").value,
    };

    let config = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginInfo),
    };

    fetch(`/api/user/login`, config)
        .then((response) => {
            if(response.status === 200) {
                return response.json();
            } else if(response.status === 401){
                alert("아이디나 비밀번호가 일치하지 않습니다!");

            } else{
                alert("서버가 응답하지 않습니다. 관리자에게 문의하세요.");
                location.href = '/';
            }
        })
        .then(data => {
            if(data) {
                sessionStorage.setItem("loginUser", JSON.stringify(data));
                location.href = '/';
            }
        });
}

// 로그아웃
function logout() {
    fetch(`/api/user/logout`)
        .then(response => {
            if (response.status === 200) {
                sessionStorage.removeItem("loginUser");
                location.href = `/`;
            }
        });
}

// 회원가입
function regist(){
    const inputDiv = document.querySelector(".inputBox");
    let userInfo = {
        id: inputDiv.querySelector("#id").value,
        password: inputDiv.querySelector("#password").value,
        name: inputDiv.querySelector("#name").value,
        address: inputDiv.querySelector("#address").value,
        phone: inputDiv.querySelector("#phone").value
    };

    let config = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userInfo),
    };

    fetch('/api/user/regist', config)
        .then((res) => {
            if(res.status === 201){
                alert('회원가입 되었습니다.');
                location.href = '/';
            }
            else{
                alert("서버가 응답하지 않습니다. 관리자에게 문의하세요.");
                location.href = '/';
            }
        });
}

function detail(){
    const el = document.querySelector(".infoContainer");

    fetch('/api/user/detail')
        .then((res) => {
            if(res.status === 200){
                return res.json();
            } else {
                alert("데이터를 불러오지 못하였습니다.");
                location.href = "/";
            }
        })
        .then((data) => {
            console.log(data);
            el.querySelector("#id").innerHTML = data.id;
            el.querySelector("#password").innerHTML = data.password;
            el.querySelector("#name").innerHTML = data.name;
            el.querySelector("#address").innerHTML = data.address;
            el.querySelector("#phone").innerHTML = data.phone;
        });
}

function deleteUser(){
    let config = {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
    };

    fetch('/api/user/delete', config)
        .then((res) => {
            if(res.status === 200){
                sessionStorage.removeItem("loginUser");

                alert("회원탈퇴되었습니다. 이용해주셔서 감사합니다.");
                location.href = "/";
            }
            else{
                alert("서버가 응답하지 않습니다.");
                location.href = "/";
            }
        })
        .then();
}

function deleteUserAdmin(id){
    let config = {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
    };

    fetch(`/api/user/delete/${id}`, config)
        .then((res) => {
            if(res.status === 200){
                alert("회원탈퇴 처리되었습니다.");
                location.href = "/assets/user/manage.html";
            }
            else{
                alert("서버가 응답하지 않습니다.");
                location.href = "/";
            }
        })
        .then();
}

function setForm(){
    const el = document.querySelector(".inputBox");

    fetch('/api/user/detail')
        .then((res) => {
            if(res.status === 200){
                return res.json();
            } else {
                alert("데이터를 불러오지 못하였습니다.");
                location.href = "/";
            }
        })
        .then((data) => {
            console.log(data);
            el.querySelector("#id").value = data.id;
            el.querySelector("#password").value = data.password;
            el.querySelector("#name").value = data.name;
            el.querySelector("#address").value = data.address;
            el.querySelector("#phone").value = data.phone;
        });
}

function setFormAdmin(userId){
    const el = document.querySelector(".inputBox");

    fetch(`/api/user/detail/${userId}`)
        .then((res) => {
            if(res.status === 200){
                return res.json();
            } else {
                alert("데이터를 불러오지 못하였습니다.");
                location.href = "/";
            }
        })
        .then((data) => {
            console.log(data);
            el.querySelector("#id").value = data.id;
            el.querySelector("#password").value = data.password;
            el.querySelector("#name").value = data.name;
            el.querySelector("#address").value = data.address;
            el.querySelector("#phone").value = data.phone;
        });
}

function modify(){
    const inputDiv = document.querySelector(".inputBox");
    let userInfo = {
        id: inputDiv.querySelector("#id").value,
        password: inputDiv.querySelector("#password").value,
        name: inputDiv.querySelector("#name").value,
        address: inputDiv.querySelector("#address").value,
        phone: inputDiv.querySelector("#phone").value
    };

    let config = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userInfo),
    };

    fetch('/api/user/modify', config)
        .then((res) => {
            if(res.status === 200){
                alert('회원정보가 수정되었습니다.');
                logout();
                location.href = "/";
            }
            else{
                alert("서버가 응답하지 않습니다. 관리자에게 문의하세요.");
                location.href = '/';
            }
        });
}



function getUserList(){
    const tbodyEl = document.querySelector("#tbl>tbody");

    fetch('/api/user/list')
        .then((res) => {
            if(res.status === 200){

                return res.json();
            } else if(res.status === 204){
                tbodyEl.innerHTML = `<tr><td class="tdColor" colspan="5">표시할 내용이 없습니다.</td></tr>`;
            }
            else{
                alert('서버가 응답하지 않습니다. 관리자에게 문의하세요.');
                tbodyEl.innerHTML = `<tr><td class="tdColor" colspan="5">표시할 내용이 없습니다.</td></tr>`;
            }
        })
        .then((datas) => {
            console.log(datas);
            let tbody = ``;
            datas.forEach((data) => {
                tbody += `
                <tr>
                    <td class="tdColor">${data.name}</td>
                    <td class="tdColor"><a href="/assets/user/userInfoAdmin.html?id=${data.id}">${data.id}</td>
                    <td class="tdColor">${data.password}</td>
                    <td class="tdColor">${data.address}</td>
                    <td class="tdColor">${data.phone}</td>
                </tr>
                `;
            });
            tbodyEl.innerHTML = tbody;
        })
}

function detailUser(id){
    const el = document.querySelector(".infoContainer");

    fetch(`/api/user/detail/${id}`)
        .then((res) => {
            if(res.status === 200){
                return res.json();
            } else {
                alert("데이터를 불러오지 못하였습니다.");
                location.href = "/";
            }
        })
        .then((data) => {
            console.log(data);
            el.querySelector("#id").innerHTML = data.id;
            el.querySelector("#password").innerHTML = data.password;
            el.querySelector("#name").innerHTML = data.name;
            el.querySelector("#address").innerHTML = data.address;
            el.querySelector("#phone").innerHTML = data.phone;
        });
}