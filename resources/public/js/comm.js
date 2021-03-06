var socket = new WebSocket("ws://" + location.host + "/f");

socket.onmessage = function(e) {
    var message;
    try {
        message = JSON.parse(e.data);
    } catch (e) {
        console.error(e);
        return;
    }

    if (message.draw) {
        var draws;
        if (! $.isArray(message.draw)) {
            draws = [message.draw];
        }
        else {
            draws = message.draw;
        }
        draws.map(draw_cell);
    }
    else if (message.msg) {
        $("#output").text(message.msg);
    }
    else {
        console.debug(message);
    }
};

function send_message(obj) {
    socket.send(JSON.stringify(obj));
}
