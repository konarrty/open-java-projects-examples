$(document).ready(() => {
    init()
    restoreFilterState();
    formChange()
})

function handleFilterChange() {
    setCookie("employee", $('#employee').val(), 30);
    setCookie("period", $('#period').val(), 30);
}

function restoreFilterState() {
    let filter1Value = getCookie("employee");
    let filter2Value = getCookie("period");

    console.log(filter1Value)
    if (filter1Value !== '' && filter1Value !== null)
        document.getElementById("employee").value = filter1Value;
    if (filter2Value !== '' && filter2Value !== null)
        document.getElementById("period").value = filter2Value;

}

function change(event) {
    $(event.target).parent().parent()[0].dataset.isChanged = "true";

}

function doDelete(id, operation, params) {
    return fetch("/targets/" + operation + "/" + id + params, {
            method: 'DELETE',
        }
    )

}

function formChange() {

    let params = {
        employee: $("#employee").val(),
        period: $("#period").val(),
    };

    if (params.period === null || params.employee === null)
        return

    $.ajax({
        type: "GET",
        url: "list",
        data: $.param(params),
        success: function (result) {
            $('tbody').html(result)
            $.get('/indicators/list', data => {
                $("[name=indicator]").autocomplete({
                    source: data
                });

                let period = $('#period').val()
                $.get('/periods/isAfter?period=' + period, data => {
                    if (data === true)
                        $('#addButton').removeAttr('disabled')
                    else {
                        $('#addButton').attr('disabled', 'disabled')
                    }
                })
            })
            handleFilterChange()

        },
        error: function (e) {
            window.location.reload()
        }
    })
}

function doPost(data) {
    return $.ajax({
        type: "POST",
        url: "create",
        contentType: false,
        processData: false,
        data: data,
        error: function (e) {
            let note = $("#notification")
            note.find('span').text(e.responseText)
            note.removeAttr("hidden")
            setTimeout(() => {
                note.find('span').text("")
                note.attr("hidden", "hidden")
            }, 4000)
        },
    });
}

function doPatch(data, id) {
    return $.ajax({
        type: "PATCH",
        url: "edit/" + id,
        contentType: false,
        processData: false,
        data: data,
        error: function (e) {
            let note = $("#notification")
            note.find('span').text(e.responseText)
            note.removeAttr("hidden")
            setTimeout(() => {
                note.find('span').text("")
                note.attr("hidden", "hidden")
            }, 4000)
        },
    });


}

function saveChanges() {
    let employeeId = $('#employee option:selected').val()
    let periodId = $('#period option:selected').val()

    $.ajax({
        type: "POST",
        url: "save",
        data: 'employeeId=' + employeeId + "&periodId=" + periodId,
        error: function (e) {
            if (e.status === 404) {
                let note = $("#notification")
                note.find('span').text(e.responseText)
                note.removeAttr("hidden")
                setTimeout(() => {
                    note.find('span').text("")
                    note.attr("hidden", "hidden")
                }, 2500)
            }
        },
        success: function (data) {
            $.get('list?employee=' + employeeId + '&period=' + periodId).success(data => {

                $('tbody').html(data)
            })

        }
    });
}


function addTarget() {
    $('#kpi').remove()

    $.get('/targets/add?employee=' + $('#employee option:selected').val() + '&period=' + $('#period option:selected').val(),
        (data) => {
            let parser = new DOMParser();
            let newNode = parser.parseFromString(data, "text/html");

            let indicatorOptions = $($("tr")[1]).find("option").clone()
            let select = $(newNode).find('select');
            if (indicatorOptions.length !== 0)
                select.html(indicatorOptions)
            else
                $.get('/indicators/list', data => {
                    $("[name =indicator]").autocomplete({
                        source: data
                    });
                })

            $('tbody').append($(newNode).find("#tr"))

            $.get('/targets/Kpi?employee=' + $('#employee option:selected').val() + '&period=' + $('#period option:selected').val(),
                (data) => {
                    $('tbody').append(data)

                })

        })
}

function deleteTarget(event) {
    let trNode = $(event).parent().parent()

    let id = event.dataset.id
    let cacheId = $(trNode).find('#button').attr('data-is-cache')
    if (id !== undefined)
        doDelete(id, "delete", '').then(() => {
            trNode.remove()
            $('table').attr('data-delete-row', true)

        })
    else if (cacheId !== undefined)
        doDelete(cacheId, "deleteFromCache", '?employeeId=' + $('#employee option:selected').val() + '&periodId=' + $('#period option:selected').val())
            .then(() => {
                trNode.remove()
            })
    else
        trNode.remove()

}

function onLoadMarksWindow(event) {
    if ($(event).attr('data-target-id') !== '')
        $("#markModal").attr('data-target-id', $(event).attr('data-target-id'))
    else
        $("#markModal").removeAttr('data-target-id')

    if ($(event).parent().parent().find("#mark").val() !== '') {
        $('#deleteMarkButton').removeAttr('disabled', 'disabled')
        $("#markModal").attr('data-mark-id', $(event).parent().parent().find("#mark").val())
    } else {
        $('#deleteMarkButton').attr('disabled', 'disabled')
        $("#markModal").removeAttr('data-mark-id')

    }
    $("#markModal #actual").val($(event).parent().parent().find("#actual")[0].innerText)
    $("#markModal #note").val($(event).parent().parent().find("#note")[0].innerText)

}

function deleteMark() {

    let markId = $("#markModal").attr('data-mark-id');
    let targetId = $("#markModal").attr('data-target-id');

    fetch("/marks/" + markId, {
            method: 'DELETE',
        }
    ).then(r => {
        $.get('/targets/' + targetId,
            (data) => {
                $("tbody").children("[data-id=" + targetId + "]").html(data)
                $('#kpi').remove()
                $.get('/targets/Kpi?employee=' + $('#employee option:selected').val() + '&period=' + $('#period option:selected').val(),
                    (data) => {
                        $('tbody').append(data)
                    })
            })

    })
}

function doPutMark() {
    let markId = $('#markModal').attr('data-mark-id')
    let targetId = $('#markModal').attr('data-target-id'),

        data = {
            actual: $('#markModal #actual').val(),
            note: $('#markModal #note').val(),
            target: targetId,
            mark: markId
        }

    $.ajax({
        type: "PUT",
        url: "/marks/edit/" + markId,
        data: $.param(data),
        success: (result) => {
            $.get('/targets/' + targetId,
                (data) => {
                    $("tbody").children("[data-id=" + targetId + "]").html(data)
                    $('#kpi').remove()
                    $.get('/targets/Kpi?employee=' + $('#employee option:selected').val() + '&period=' + $('#period option:selected').val(),
                        (data) => {
                            $('tbody').append(data)
                        })
                })
        },
        error: function (e) {
            if (e.status === 404) {
                let note = $("#notification")
                note.find('span').text(e.responseText)
                note.removeAttr("hidden")
                setTimeout(() => {
                    note.find('span').text("")
                    note.attr("hidden", "hidden")
                }, 2500)
            }
        },

    })
}

function doPostMark() {
    let targetId = $('#markModal').attr('data-target-id')
    let data = {
        actual: $('#markModal #actual').val(),
        note: $('#markModal #note').val(),
        target: targetId
    }

    $.ajax({
        type: "POST",
        url: "/marks/create",
        data: $.param(data),
        success: (result) => {
            $.get('/targets/' + targetId,
                (data) => {
                    $("tbody").children("[data-id=" + targetId + "]").html(data)
                    $('#kpi').remove()
                    $.get('/targets/Kpi?employee=' + $('#employee option:selected').val() + '&period=' + $('#period option:selected').val(),
                        (data) => {
                            $('tbody').append(data)
                        })

                })
        },
        error: function (e) {
            if (e.status === 404) {
                let note = $("#notification")
                note.find('span').text(e.responseText)
                note.removeAttr("hidden")
                setTimeout(() => {
                    note.find('span').text("")
                    note.attr("hidden", "hidden")
                }, 2500)
            }
        },

    })
}

function addMark() {
    console.log($("#markModal").attr('data-mark-id'))
    if ($("#markModal").attr('data-mark-id') !== undefined)
        doPutMark()
    else
        doPostMark()

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
        let data = !isChecked ? createFormDataAndCheck(inputElements) : createFormData(inputElements)
        if (data === null)
            continue

        if ($(div).find('#button').attr('data-is-cache') === undefined)
            doPost(data).success((result) => {
                $(div).html(result)
                incrementCounter(size);
                $.get('/indicators/list', data => {
                    $("[name=indicator]").autocomplete({
                        source: data
                    });
                })
                div.dataset.isChanged = "false";

            })
        else
            doPatch(data, $(div).find('#button').attr('data-is-cache')).success((result) => {
                $(div).html(result)
                incrementCounter(size);
                $.get('/indicators/list', data => {
                    $("[name=indicator]").autocomplete({
                        source: data
                    });
                })
                div.dataset.isChanged = "false";

            })


    }
}