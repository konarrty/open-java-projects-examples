function errors(e) {
    let note = $("#notification")
    note.find('span').text(e.responseText)
    note.removeAttr("hidden")
    setTimeout(() => {
        note.find('span').text("")
        note.attr("hidden", "hidden")
    }, 4000)
}

