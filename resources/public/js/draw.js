
function get_cell_xy(x, y, table_id) {
    table_id = table_id || "#T";
    var selector = table_id
        + '> tbody '
        + '> tr:nth-of-type(' + y + ')'
        + '> td:nth-of-type(' + x + ')';
    return document.querySelector(selector);
}

function draw_cell(cell) {
    var cellh = get_cell_xy(cell.x, cell.y);
    if (cellh) {
        if (cell.text !== undefined)
            cellh.innerHTML = cell.text;
        if (cell.fg !== undefined)
            cellh.style.color = cell.fg;
        if (cell.bg !== undefined)
            cellh.style.backgroundColor = cell.bg;
    }
}

function build_table(num_cols, num_rows, table_id) {
    table_id = table_id || "T";
    var T = document.getElementById(table_id);
    var TB = document.createElement('tbody');

    while(T.firstChild) {
        T.removeChild(T.firstChild);
    }
    T.appendChild(TB);

    for (var r = 0 ; r < num_rows ; ++r) {
        var row = document.createElement('tr');
        for (var c = 0 ; c < num_cols ; ++c) {
            var cell = document.createElement('td');
            cell.innerHTML = '&nbsp;';
            row.appendChild(cell);
        }
        TB.appendChild(row);
    }
}
