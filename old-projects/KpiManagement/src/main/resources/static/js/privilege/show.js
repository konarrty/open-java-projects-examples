$(document).ready(() => init())

function formChange() {

    let type = $("#type").val()
    let params = {
        employee: $("#employee").val(),
        type: type
    };
    if (params.type === null || params.employee == null)
        return

    $.ajax({
        type: "GET",
        url: "list",
        data: $.param(params),
        success: function (result) {
            $('tbody').html(result)
            if (type === 'com.example.kpimanagment.model.PrivilegeRead') {
                $('[name=typeHidden]').attr('hidden', 'hidden')
                $('#typeHidden').removeAttr('hidden')

            } else {
                $('[name=typeHidden]').removeAttr('hidden')
                $('#typeHidden').attr('hidden', 'hidden')
            }

            handleFilterChange()

        }

    });
}

function handleFilterChange() {
    setCookie("employeePrivilege", $('#employee').val(), 30);
    setCookie("privilegeType", $("#type").val(), 30);

}

function restoreFilterState() {
    let filterValuePrivilege = getCookie("employeePrivilege");
    let filterValueType = getCookie("privilegeType");

    if (filterValuePrivilege !== '' && filterValuePrivilege !== null) {
        $('#employee').val(filterValuePrivilege)
        if ($('#employee').val() === null)
            $('#employee').val($('#employee option:disabled').val())
    }
    if (filterValueType !== '' && filterValueType !== null)
        $("#type").val(filterValueType)
}

window.onload = function () {
    restoreFilterState();
    formChange()
};

function change(event) {
    $(event.target).parent().parent()[0].dataset.isChanged = "true";

}

function doDelete(id, operation, params) {
    return fetch("/privileges/" + operation + "/" + id + params, {
            method: 'DELETE',
        }
    )

}

function printErrorAlert(jqXHR) {
    let note = $("#notification")
    note.find('span').text(jqXHR.responseText)
    note.removeAttr("hidden")
    setTimeout(() => {
        note.find('span').text("")
        note.attr("hidden", "hidden")
    }, 5000)

}

function doPost(data, url) {
    return $.ajax({
        type: "POST",
        url: url,
        contentType: false,
        processData: false,
        data: data,
        error: function (jqXHR, exception) {
            if (jqXHR.status === 409) {
                let params = {
                    employee: $("#employee").val(),
                    type: $("#type").val()
                };
                $.ajax({
                    type: "GET",
                    url: "list",
                    data: $.param(params),
                    success: function (result) {
                        $('tbody').html(result)
                        printErrorAlert(jqXHR)
                    }

                });
            } else
                printErrorAlert(jqXHR)
        }
    });
}

function doPatch(data, url, id) {
    return $.ajax({
        type: "PATCH",
        url: url + id,
        contentType: false,
        processData: false,
        data: data,
        error: function (jqXHR, exception) {
            if (jqXHR.status === 409) {
                let params = {
                    employee: $("#employee").val(),
                    type: $("#type").val()
                };
                $.ajax({
                    type: "GET",
                    url: "list",
                    data: $.param(params),
                    success: function (result) {
                        $('tbody').html(result)
                        printErrorAlert(jqXHR)
                    }

                });
            } else
                printErrorAlert(jqXHR)
        }
    });


}

function saveChanges() {
    let type = $("#type").val()
    let employeeId = $('#employee option:selected').val()
    $.ajax({
        type: "POST",
        url: "save",
        data: 'employeeId=' + employeeId,
        error: function (jqXHR, exception) {
            if (jqXHR.status === 404) {
                let note = $("#notification")
                note.find('span').text(jqXHR.responseText)
                note.removeAttr("hidden")
                setTimeout(() => {
                    note.find('span').text("")
                    note.attr("hidden", "hidden")
                }, 2500)
            }
        },
        success: function (data) {
            $.get('list?employee=' + employeeId + '&type=' + type).success(data => {

                $('tbody').html(data)
            })

        },
    });
}


function addPrivilege() {
    $.get('/privileges/add?employee=' + $('#employee option:selected').val() + '&type=' + $('#type').val(),
        (data) => {
            $('tbody').append(data)

        })
}

function deletePrivilege(event) {
    let trNode = $(event).parent().parent()
    let type = $('#type').val()
    let id = event.dataset.id
    let cacheId = $(trNode).find('#button').attr('data-is-cache')
    if (id !== undefined) {
        if (type === 'com.example.kpimanagment.model.PrivilegeWrite')
            doDelete(id, "delete", '').then(() => {
                trNode.remove()
                $('table').attr('data-delete-row', true)
            })
        else
            doDelete(id, "deletePermanently", '').then(() => {
                trNode.remove()
            })
    } else if (cacheId !== undefined)
        doDelete(cacheId, "deleteFromCache", '?employeeId=' + $('#employee option:selected').val())
            .then(() => {
                trNode.remove()
            })
    else
        trNode.remove()

}

function sendChanges(elements, i) {
    if (elements.length === i) {
        saveChanges()
        return
    }

    let inputElements = $(elements[i]).find('input,select')
    let type = $('#type').val()
    let isChecked = $(elements[i]).find(`[name=id]`).val() !== ''
    let data = !isChecked ? createFormDataAndCheck(inputElements) : createFormData(inputElements)

    if (type === 'com.example.kpimanagment.model.PrivilegeWrite') {

        if ($(elements[i]).find('#button').attr('data-is-cache') === undefined)
            doPost(data, 'create').success((result) => {
                $(elements[i]).html(result)
                sendChanges(elements, ++i)
            })
        else {
            doPatch(data, 'edit/', $(elements[i]).find('button').attr('data-is-cache')).success((result) => {
                $(elements[i]).html(result)
                sendChanges(elements, ++i)


            })

        }
    } else {
        if ($(div).find(`[name=id]`).val() === '')
            doPost(data, 'createWithoutCache').success((result) => {
                $(elements[i]).html(result)
            })
        else {
            doPatch(data, 'editWithoutCache/', isChecked).success((result) => {
                $(elements[i]).html(result)
            })

        }

    }
    elements[i].dataset.isChanged = "false";

}

let responseCounter = 0;

function incrementCounter(size) {
    if (++responseCounter === size) {
        responseCounter = 0
        saveChanges()
    }
}

function acceptChanges() {
    if ($('.invalid').length !== 0)
        return;
    responseCounter = 0;

    let divElement = document.getElementsByName('tr');
    let size = 0;

    for (const div of divElement) {
        if ($(div).attr("data-is-changed") !== "true")
            continue;
        size++;
    }
    console.log('size ' + size)
    if (size === 0) {
        if ($('table').attr('data-delete-row') === 'true')
            saveChanges()
        return;

    }
    for (const div of divElement) {
        if ($(div).attr("data-is-changed") !== "true")
            continue;

        let inputElements = $(div).find('input,select')
        let isChecked = $(div).find(`[name=id]`).val() !== ''
        let type = $('#type').val()
        let data = createFormDataAndCheck(inputElements)
        if (data === null)
            continue

        if (type === 'com.example.kpimanagment.model.PrivilegeWrite') {

            if ($(div).find('#button').attr('data-is-cache') === undefined)
                doPost(data, 'create').success((result) => {
                    $(div).html(result)
                    incrementCounter(size);
                    div.dataset.isChanged = "false";

                })
            else {
                doPatch(data, 'edit/', $(div).find('button').attr('data-is-cache')).success((result) => {
                    $(div).html(result)
                    incrementCounter(size);
                    div.dataset.isChanged = "false";


                })
            }
        } else {
            if ($(div).find(`[name=id]`).val() === '')
                doPost(data, 'createWithoutCache').success((result) => {
                    $(div).html(result)
                    div.dataset.isChanged = "false";

                })
            else {
                doPatch(data, 'editWithoutCache/', $(div).find('button').attr('data-id')).success((result) => {
                    $(div).html(result)
                    div.dataset.isChanged = "false";

                })

            }

        }

    }
}