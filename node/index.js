'use strict';

var os = require('os');
const fs = require('fs');
const path = require('path')
var nodeStatic = require('node-static');
// var http = require('http');
var https = require('https');
var socketIO = require('socket.io');

var fileServer = new(nodeStatic.Server)();
// var app = http.createServer(function(req, res) {
//   fileServer.serve(req, res);
// }).listen(8000);

try {
  const option = {
    ca: fs.readFileSync('/etc/letsencrypt/live/i3a111.p.ssafy.io/fullchain.pem'),
    key: fs.readFileSync(path.resolve(process.cwd(), '/etc/letsencrypt/live/i3a111.p.ssafy.io/privkey.pem'), 'utf8').toString(),
    cert: fs.readFileSync(path.resolve(process.cwd(), '/etc/letsencrypt/live/i3a111.p.ssafy.io/cert.pem'), 'utf8').toString(),
  };

  var app = https.createServer(option, function(req, res) {
    fileServer.serve(req, res);
  }).listen(8000, () => {
    console.log('[HTTPS] Soda Server is started on port 8000');
  });

} catch (error) {
  console.log('[HTTPS] HTTPS 오류가 발생하였습니다. HTTPS 서버는 실행되지 않습니다.');
  console.log(error);
}


var io = socketIO.listen(app);
io.sockets.on('connection', function(socket) {

  // convenience function to log server messages on the client
  function log() {
    var array = ['Message from server:'];
    array.push.apply(array, arguments);
    socket.emit('log', array);
  }

  socket.on('message', function(messageData) {
    log('Client said: ', messageData['message']);
    socket.broadcast.in(messageData['room']).emit('message', messageData['message']);
    if (messageData['message'] === 'bye') {
      socket.leave(messageData['room'])
      log('Client leave', messageData['room']);
    }
    // let roomMembers
    io.in(messageData['room']).clients((error, clients) => {
      if (error) throw error;
      log(messageData['room'], clients, socket.id);
    })
  });

  socket.on('create or join', function(room) {
    log('Received request to create or join room ' + room);

    var clientsInRoom = io.sockets.adapter.rooms[room];
    var numClients = clientsInRoom ? Object.keys(clientsInRoom.sockets).length : 0;
    log('Room ' + room + ' now has ' + numClients + ' client(s)');

    if (numClients === 0) {
      socket.join(room);
      log('Client ID ' + socket.id + ' created room ' + room);
      socket.emit('created', room, socket.id);

    } else if (numClients < 10) {
      log('Client ID ' + socket.id + ' joined room ' + room);
      io.sockets.in(room).emit('join', room);
      socket.join(room);
      io.sockets.in(room).emit('joined', numClients, socket.id);
      io.sockets.in(room).emit('ready');
    } else { // max two clients
      socket.emit('full', room);
    }
  });

  socket.on('ipaddr', function() {
    var ifaces = os.networkInterfaces();
    for (var dev in ifaces) {
      ifaces[dev].forEach(function(details) {
        // if (details.family === 'IPv4' && details.address !== '127.0.0.1') {
          socket.emit('ipaddr', details.address);
        // }
      });
    }
  });

  socket.on('bye', function(){
    console.log('received bye');
  });

});
