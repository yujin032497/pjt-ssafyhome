
function initHeader() {
    const notLoginContainer = document.querySelectorAll(".notLogin");
    const loginContainer = document.querySelectorAll(".logined");
    const adminContainer = document.querySelectorAll(".admin");

    let user = JSON.parse(sessionStorage.getItem("loginUser"));
    if (user) {
        notLoginContainer.forEach((el) => {
            el.style.display = "none";
        })
        loginContainer.forEach((el) => {
            el.style.display = "block";
        })
        if(user.isAdmin === 1){
            adminContainer.forEach((el) => {
                el.style.display = "block";
            });
        }
        else{
            adminContainer.forEach((el) => {
                el.style.display = "none";
            });
        }
    } else {
        notLoginContainer.forEach((el) => {
            el.style.display = "block";
        })
        loginContainer.forEach((el) => {
            el.style.display = "none";
        })
        adminContainer.forEach((el) => {
            el.style.display = "none";
        });
    }
}
