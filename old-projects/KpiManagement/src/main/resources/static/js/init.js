function init() {
    $('tbody').change(function (event) {
        if ($(event.target).is('select')) {
            if (check(event.target))
                markAsValid(event.target)
        }

        $(event.target).parent().parent()[0].dataset.isChanged = true;
    });
    $('tbody').keyup(function (event) {
        if (event.target.checkValidity())
            markAsValid(event.target)


    })

    $('tbody').focusout((event) => check(event.target))
}

function markAsValid(target) {
    $(target).next('.text-field__message').text('')
    $(target).removeClass('invalid')
    $(target).addClass('valid')

}

function markAsInvalid(target) {
    $(target).removeClass('valid')
    $(target).addClass('invalid')
}

function createFormDataAndCheck(inputElements) {
    let isValid = true
    for (let i = 0; i < inputElements.length; i++) {
        if (!check(inputElements[i]))
            isValid = false;
    }

    if (isValid !== true)
        return null
    else
        return createFormData(inputElements)

}

function createFormData(inputElements) {
    let data = new FormData();

    for (let i = 0; i < inputElements.length; i++) {
        let input = inputElements[i];
        let key = input.name;
        let value = input.value;
        data.append(key, value)

    }
    return data;

}

function check(target) {

    if ($(target).find('option:selected:disabled').length !== 0) {
        $(target).next('.text-field__message').text('Необходимо выбрать элемент из списка.');
        markAsInvalid(target)
        return false;
    }


    let isValid = target.checkValidity()
    if (!isValid)
        setTimeout(function () {

            let validity = target.validity
            if (validity.valueMissing === true)
                $(target).next('.text-field__message').text('Это поле должно быть заполнено.');
            if (validity.stepMismatch === true)
                $(target).next('.text-field__message').text('Шаг должен быть 0.01');
            if (validity.tooShort === true)
                $(target).next('.text-field__message').text('Длина должна быть не менее ' + $(target).attr('minlength') + ' символов.');
            if (validity.tooLong === true)
                $(target).next('.text-field__message').text('Длина должна быть не более ' + $(target).attr('maxlength') + 'символов.');
            if (validity.rangeUnderflow === true)
                $(target).next('.text-field__message').text('Значение должно быть не менее ' + $(target).attr('min') + '.');
            if (validity.rangeOverflow === true)
                $(target).next('.text-field__message').text('Значение должно быть не более ' + $(target).attr('max') + '.');
            markAsInvalid(target)
            // $(event.target).focus();
        }, 50);
    return isValid

}