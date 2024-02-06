function login() {

    let data = new FormData()
    data.append('username', $('#username').val())
    data.append('password', $('#password').val())

    $.ajax({
        type: "POST",
        url: "/login",
        contentType: false,
        processData: false,
        data: data,
        error: (e) => errors(e),
        success: (resp) => window.location = '/home'

    })
}