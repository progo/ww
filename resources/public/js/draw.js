function draw_cell(cell) {
    var ctx = document.getElementById('C').getContext('2d');
    ctx.fillStyle = cell.bg || "#fff";
    ctx.fillRect(cell.x * BLOCKSIZE, cell.y * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
    return;
}
