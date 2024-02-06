$(document).ready(() => init())

function doDelete(id) {
    return fetch("/employees/delete/" + id, {
            method: 'DELETE',
        }
    ).catch((e) => errors(e))

}

function doPost(data) {
    return $.ajax({
        type: "POST",
        url: "create",
        contentType: false,
        processData: false,
        data: data,
        error: (e) => errors(e)
    });
}

function doPatch(data, id) {
    return $.ajax({
        type: "PATCH",
        url: "edit/" + id,
        contentType: false,
        processData: false,
        data: data,
        error: (e) => errors(e)

    });

}


function cache() {

    $.get('/employees/add', function (data) {
        $('tbody').append(data)

    })

}

function deleteEmployee(event) {
    let trNode = $(event).parent().parent().parent()

    let id = event.dataset.id
    if (id === undefined) {
        trNode.html("")
    } else
        doDelete(id).then(() => {
            trNode.html("")
        })
}


function save() {
    if ($('.invalid').length !== 0)
        return;

    let divElement = document.getElementsByName('tr');
    for (const div of divElement) {

        if (div.dataset.isChanged !== "true")
            continue;

        let inputElements = $(div).find('input,select')
        console.log($(div).find('button').attr('data-id'))
        let isChecked = $(div).find('button').attr('data-id') !== ''
        let data = !isChecked ? createFormDataAndCheck(inputElements) : createFormData(inputElements)
        if (data === null)
            continue

        let id = $(div).find('button').attr('data-id')
        if (id !== '')
            doPatch(data, id).success((result) => {
                $(div).html(result)
                div.dataset.isChanged = "false";

            })
        else
            doPost(data).success((result) => {
                $(div).html(result)
                div.dataset.isChanged = "false";

            })


    }


}