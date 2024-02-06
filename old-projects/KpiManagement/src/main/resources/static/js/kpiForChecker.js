$(document).ready(() => {

    restoreFilterState()
})

function formChange() {

    let params = {
        period: $("#period").val(),
        employee: $("#employee").val(),
    };

    $.ajax({
        type: "GET",
        url: "checksList",
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
    setCookie("periodForChecker", $('#period').val(), 30);
    setCookie("employeeForChecker", $('#employee').val(), 30);
}

function restoreFilterState() {
    let filterPeriodValue = getCookie("periodForChecker");
    let filterEmployeeValue = getCookie("employeeForChecker");

    if (filterPeriodValue !== '' && filterPeriodValue !== null)
        $('#period').val(filterPeriodValue)
    if (filterEmployeeValue !== '' && filterEmployeeValue !== null)
        $('#employee').val(filterEmployeeValue)

    window.onload = function () {
        restoreFilterState();
        formChange()
    };
}

function onLoadModal(event) {
    console.log($(event).parent().parent().find('#note'))
    let note = $(event).parent().parent().find('#note').text()
    if (note !== '')
        $('#noteModal textarea').val(note)
    else
        $('#noteModal textarea').val('Примечание отсутствует')

}

