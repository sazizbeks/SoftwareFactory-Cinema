let dayInput = $('#dayInput');
let monthInput = $('#monthInput');
let yearInput = $('#yearInput');
let months = ['January', 'February',
    'March', 'April', 'May',
    'June', 'July', 'August',
    'September', 'October', 'November',
    'December'];

function getDaysInMonth(month, year) {
    return new Date(year, month, 0).getDate();
}

function getDaysSelectHtml(days) {
    let daysHtml = '';
    for (let i = 1; i <= days; i++) {
        daysHtml += '<option value="' + i + '">' + i + '</option>';
    }
    return daysHtml;
}

function fillDayInput() {
    let monthNumber = monthInput.val();
    let yearInputVal = yearInput.val();
    let year = yearInputVal == null ? new Date().getFullYear() : yearInputVal;
    let days = getDaysInMonth(monthNumber, year);
    dayInput.html(getDaysSelectHtml(days));
}

//init month input
for (let i = 0; i < months.length; i++) {
    monthInput.append('<option value="' + (i + 1) + '">' + months[i] + '</option>');
}

//init day input
fillDayInput();

//init year input
for (let i = new Date().getFullYear(); i >= 1888; i--) {
    yearInput.append('<option value="' + i + '">' + i + '</option>');
}


monthInput.change(function () {
    fillDayInput();
});

yearInput.change(function () {
    fillDayInput();
});