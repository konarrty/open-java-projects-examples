function formChange() {

    let params = {
        period: $("#period").val(),
    };

    $.ajax({
        type: "GET",
        url: "myResult",
        data: $.param(params),
        success: function (result) {
            $('tbody').html(result)
            handleFilterChange()

        },
        error: function (e) {
            window.location.reload()
        }
    });
}

function handleFilterChange() {
    setCookie("periodPersonalArea", $('#period').val(), 30);
}

function restoreFilterState() {
    let filter1Value = getCookie("periodPersonalArea");

    if (filter1Value !== '' && filter1Value !== null)
        $('#period').val(filter1Value)
}

window.onload = function () {
    restoreFilterState();
    formChange()
};

function onLoadModal(event) {
    console.log($(event).parent().parent().find('#note'))
    let note = $(event).parent().parent().find('#note').text()
    if (note !== '')
        $('#noteModal textarea').val(note)
    else
        $('#noteModal textarea').val('Примечание отсутствует')

}

