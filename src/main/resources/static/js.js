var auth = null;
var role = null;
$('#login-button').click(function (ev) {
    ev.preventDefault();
    var userName = $('#username').val();
    var password = $('#password').val();
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/login',
        headers: {
            "Content-Type": "application/json"
        },
        data: JSON.stringify({
            "userName": userName,
            "password": password
        })
    }).done(function (body) {
            auth = body["Authorization"];
            localStorage.setItem("token", auth);
            role = body["Role"];
            console.log(role);
            console.log(auth);
            if (role === "[ROLE_USER]") {
                window.location.href = "/home.html";
                test();
            } else {
                window.location.href = "/admin.html";
            }

    });
});

