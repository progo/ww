function draw_cell(cell, index) {
    var bs = globals.blocksize;
    var ctx = document.getElementById('C').getContext('2d');

    var x = index % globals.width;
    var y = Math.floor(index / globals.width);

    ctx.fillStyle = cell || "#fff";
    ctx.fillRect(x * bs, y * bs, bs, bs);

    // ctx.fillStyle = "#000";
    // ctx.font = "7pt Arial";
}
