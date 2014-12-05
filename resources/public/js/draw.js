function draw_cell(cell, index) {
    var bs = globals.blocksize;
    var ctx = document.getElementById('C').getContext('2d');

    var x = index       % globals.width;
    var y = Math.floor(index / globals.width);

    ctx.fillStyle = cell || "#fff";
    ctx.fillRect(x * bs, y * bs, bs, bs);

    // ctx.fillStyle = "#000";
    // ctx.font = "7pt Arial";
    // ctx.fillText("x:" + x,      x * bs + 1,     y * bs + (bs/2));
    // ctx.fillText("y:" + y,      x * bs + 1,     y * bs);
    // ctx.fillText("" + index,    x * bs + 5,     y * bs + (bs/2));
    // ctx.fillText("" + x + ", " + y,x * bs + 1,     (y+.7) * bs);

}
