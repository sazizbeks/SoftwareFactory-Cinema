let dayInput = $('#dayInput');
let monthInput = $('#monthInput');
let yearInput = $('#yearInput');
let months = ['January', 'February',
    'March', 'April', 'May',
    'June', 'July', 'August',
    'September', 'October', 'November',
    'December'];
let editDay = $('#editDay') != null ? Number.parseInt($('#editDay').html()) : null;
let editMonth = $('#editMonth') != null ? Number.parseInt($('#editMonth').html()) : null;
let editYear = $('#editYear') != null ? Number.parseInt($('#editYear').html()) : null;

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

monthInput.change(function () {
    fillDayInput();
});

yearInput.change(function () {
    fillDayInput();
});

//init year input
for (let i = new Date().getFullYear(); i >= 1888; i--) {
    yearInput.append('<option value="' + i + '"' + (editYear != null && editYear === i ? ' selected' : '') + '>' + i + '</option>');
}

//init month input
for (let i = 0; i < months.length; i++) {
    monthInput.append('<option value="' + (i + 1) + '"' + ((editMonth != null && editMonth === i) ? ' selected' : '') + '>' + months[i] + '</option>');
}

//init day input
for (let i = 1; i <= getDaysInMonth(monthInput.val(),yearInput.val()); i++) {
   dayInput.append('<option value="' + i + '"' + (editDay != null && editDay === i ? ' selected' : '') + '">' + i + '</option>');
}