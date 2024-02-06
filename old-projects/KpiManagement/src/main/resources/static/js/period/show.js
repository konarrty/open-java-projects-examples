$(document).ready(() => init())

function doDelete(id) {
    return fetch("/periods/delete/" + id, {
            method: 'DELETE',
        }
    )

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
        type: "PUT",
        url: "edit/" + id,
        contentType: false,
        processData: false,
        data: data,
        error: (e) => errors(e)

    });

}


function addPeriod() {

    $.get('/periods/add', function (data) {
        $('tbody').append(data)
    })

}

function deletePeriod(event) {
    let trNode = $(event).parent().parent()

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
        let isChecked = $(div).find(`[name=period]`).val() !== ''
        let data = !isChecked ? createFormDataAndCheck(inputElements) : createFormData(inputElements)
        if (data === null)
            continue

        let id = div.dataset.id
        if (id !== undefined)
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