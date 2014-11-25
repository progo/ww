function __cell_id(x, y) {
    return 'cell-' + x + 'x' + y;
}

function draw_cell(cell) {
    var cellh = document.getElementById(__cell_id(cell.x, cell.y));
    if (cellh) {
        if (cell.text !== undefined)
            cellh.innerHTML = cell.text;
        if (cell.fg !== undefined)
            cellh.style.color = cell.fg;
        // if (cell.bg !== undefined)
        cellh.style.backgroundColor = cell.bg || "#fff";
    }
}

function build_table(num_cols, num_rows, table_id) {
    var $T = $(table_id || "#T");
    $T.empty();
    for (var y = 0 ; y < num_rows ; ++y) {
        for (var x = 0 ; x < num_cols ; ++x) {
            var $cell = $("<div/>", {
                'class': 'cell',
                id: __cell_id(x, y),
                html: '&nbsp'
            });
            $cell.css('left', 17*x);
            $cell.css('top', 17*y);
            $T.append($cell);
        }
    }
}
