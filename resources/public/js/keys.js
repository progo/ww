function on_key(ev) {
    console.log(ev);
    $("#output").text('Key: ' + ev.data.keys);
    return false;
}

// NB: keymaps on this thing, case insensitive!
// $(document).bind('keydown', 'ctrl+a', on_key);
// $(document).bind('keydown', 'ctrl+space', on_key);
// $(document).bind('keydown', 'alt+a', on_key);
// $(document).bind('keydown', 'F2', on_key);
// $(document).bind('keydown', 'Return', on_key);
// $(document).bind('keydown', 'Shift+F', on_key);
// $(document).bind('keydown', 'g', on_key);
// $(document).bind('keydown', 'f', on_key);

function send_move(m) {
    send_message({
        action: 'move',
        move: m
    });
}

$(document).bind('keydown', 'left',     function() { send_move('left'); });
$(document).bind('keydown', 'h',        function() { send_move('left'); });
$(document).bind('keydown', 'right',    function() { send_move('right'); });
$(document).bind('keydown', 'l',        function() { send_move('right'); });
$(document).bind('keydown', 'up',       function() { send_move('up'); });
$(document).bind('keydown', 'k',        function() { send_move('up'); });
$(document).bind('keydown', 'down',     function() { send_move('down'); });
$(document).bind('keydown', 'j',        function() { send_move('down'); });
