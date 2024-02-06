function registration() {

    let data = {
        username: $('#username').val(),
        password: $('#password').val(),
        invitationCode: $('#invitationCode').val()

    }
    // let data = new FormData()
    // data.append('username',
    //     data.append('password
    // data.append('invitationCode',

        $.ajax({
            type: "POST",
            url: "/registration/",
            // contentType: false,
            // processData: false,
            data: data,
            error: (e) => errors(e),
            success: (resp) => window.location = '/login'

        })
}