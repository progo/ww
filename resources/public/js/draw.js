function draw_cell(cell) {
    var bs = globals.blocksize;
    var ctx = document.getElementById('C').getContext('2d');
    ctx.fillStyle = cell.bg || "#fff";
    ctx.fillRect(cell.x * bs, cell.y * bs, bs, bs);
    return;
}
