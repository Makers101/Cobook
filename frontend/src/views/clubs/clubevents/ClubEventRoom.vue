<template>
  <div>
    <p>
      클럽 이벤트 방 번호: {{ roomId }}
    </p>
    <video id="localVideo" autoplay playsinline></video>
    <video v-for="idx in [1, 2, 3, 4, 5, 6, 7, 8, 9]" :key="`remote-${idx}`" :id="`remoteVideo-${idx}`" autoplay playsinline></video>
  </div>
</template>

<script>
export default {
  name: "ClubEventRoom",
  data() {
    return {
      roomId: this.$route.params['roomId'],
      mediaStreamConstraints: {
        audio: false,
        video: true,
      },
      isChannelReady: false,
      isInitiator: false,
      isStarted: false,
      peerIdx: 1
    }
  },
  mounted() {
    'use strict';

    var isChannelReady = this.isChannelReady;
    var isInitiator = this.isInitiator;
    var isStarted = this.isStarted;
    var localStream;
    var pc;
    var remoteStream;
    var turnReady;
    console.log(turnReady)

    var pcConfig = {
      'iceServers': [{
          'urls': 'stun:stun.l.google.com:19302'
        },
        {
          urls: 'turn:numb.viagenie.ca',
          credential: 'muazkh',
          username: 'webrtc@live.com'
        }
      ]};

    // Set up audio and video regardless of what devices are present.
    var sdpConstraints = {
      offerToReceiveAudio: true,
      offerToReceiveVideo: true
    };
    console.log(sdpConstraints)


    /////////////////////////////////////////////

    var room = `clubEventRoom-${this.roomId}`;
    // Could prompt for room name:
    // room = prompt('Enter room name:');

    // var socket = io.connect();
    var socket = this.$socket
    console.log(socket)

    if (room !== '') {
      socket.emit('create or join', room);
      console.log('Attempted to create or join room', room);
    }

    socket.on('created', function(room) {
      console.log('Created room ' + room);
      isInitiator = true;
    });

    socket.on('full', function(room) {
      console.log('Room ' + room + ' is full');
    });

    socket.on('join', function (room){
      console.log('Another peer made a request to join room ' + room);
      console.log('This peer is the initiator of room ' + room + '!');
      isChannelReady = true;
    });

    socket.on('joined', function(room) {
      console.log('joined: ' + room);
      isChannelReady = true;
    });

    socket.on('log', function(array) {
      console.log.apply(console, array);
    });

    ////////////////////////////////////////////////

    function sendMessage(message) {
      console.log('Client sending message: ', message);
      socket.emit('message', message);
    }

    // This client receives a message
    socket.on('message', function(message) {
      console.log('Client received message:', message);
      if (message === 'got user media') {
        maybeStart();
      } else if (message.type === 'offer') {
        if (!isInitiator && !isStarted) {
          maybeStart();
        }
        pc.setRemoteDescription(new RTCSessionDescription(message));
        doAnswer();
      } else if (message.type === 'answer' && isStarted) {
        pc.setRemoteDescription(new RTCSessionDescription(message));
      } else if (message.type === 'candidate' && isStarted) {
        var candidate = new RTCIceCandidate({
          sdpMLineIndex: message.label,
          candidate: message.candidate
        });
        pc.addIceCandidate(candidate);
      } else if (message === 'bye' && isStarted) {
        handleRemoteHangup();
      }
    });

    ////////////////////////////////////////////////////

    var localVideo = document.querySelector('#localVideo');
    var remoteVideo = document.querySelector(`#remoteVideo-${this.peerIdx}`);

    navigator.mediaDevices.getUserMedia({
      audio: false,
      video: true
    })
    .then(gotStream)
    .catch(function(e) {
      alert('getUserMedia() error: ' + e.name);
    });

    function gotStream(stream) {
      console.log('Adding local stream.');
      localStream = stream;
      localVideo.srcObject = stream;
      sendMessage('got user media');
      if (isInitiator) {
        maybeStart();
      }
    }

    var constraints = {
      video: true
    };

    console.log('Getting user media with constraints', constraints);

    if (location.hostname !== 'localhost') {
      requestTurn(
        'https://computeengineondemand.appspot.com/turn?username=41784574&key=4080218913'
      );
    }

    function maybeStart() {
      console.log('>>>>>>> maybeStart() ', isStarted, localStream, isChannelReady);
      if (!isStarted && typeof localStream !== 'undefined' && isChannelReady) {
        console.log('>>>>>> creating peer connection');
        createPeerConnection();
        pc.addStream(localStream);
        isStarted = true;
        console.log('isInitiator', isInitiator);
        if (isInitiator) {
          doCall();
        }
      }
    }

    window.onbeforeunload = function() {
      sendMessage('bye');
    };

    /////////////////////////////////////////////////////////

    function createPeerConnection() {
      try {
        pc = new RTCPeerConnection(null);
        pc.onicecandidate = handleIceCandidate;
        pc.onaddstream = handleRemoteStreamAdded;
        pc.onremovestream = handleRemoteStreamRemoved;
        console.log('Created RTCPeerConnnection');
      } catch (e) {
        console.log('Failed to create PeerConnection, exception: ' + e.message);
        alert('Cannot create RTCPeerConnection object.');
        return;
      }
    }

    function handleIceCandidate(event) {
      console.log('icecandidate event: ', event);
      if (event.candidate) {
        sendMessage({
          type: 'candidate',
          label: event.candidate.sdpMLineIndex,
          id: event.candidate.sdpMid,
          candidate: event.candidate.candidate
        });
      } else {
        console.log('End of candidates.');
      }
    }

    function handleCreateOfferError(event) {
      console.log('createOffer() error: ', event);
    }

    function doCall() {
      console.log('Sending offer to peer');
      pc.createOffer(setLocalAndSendMessage, handleCreateOfferError);
    }

    function doAnswer() {
      console.log('Sending answer to peer.');
      pc.createAnswer().then(
        setLocalAndSendMessage,
        onCreateSessionDescriptionError
      );
    }

    function setLocalAndSendMessage(sessionDescription) {
      pc.setLocalDescription(sessionDescription);
      console.log('setLocalAndSendMessage sending message', sessionDescription);
      sendMessage(sessionDescription);
    }

    function onCreateSessionDescriptionError(error) {
      console.log('Failed to create session description: ' + error.toString());
    }

    function requestTurn(turnURL) {
      var turnExists = false;
      for (var i in pcConfig.iceServers) {
        if (pcConfig.iceServers[i].urls.substr(0, 5) === 'turn:') {
          turnExists = true;
          turnReady = true;
          break;
        }
      }
      if (!turnExists) {
        console.log('Getting TURN server from ', turnURL);
        // No TURN server. Get one from computeengineondemand.appspot.com:
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4 && xhr.status === 200) {
            var turnServer = JSON.parse(xhr.responseText);
            console.log('Got TURN server: ', turnServer);
            pcConfig.iceServers.push({
              'urls': 'turn:' + turnServer.username + '@' + turnServer.turn,
              'credential': turnServer.password
            });
            turnReady = true;
          }
        };
        xhr.open('GET', turnURL, true);
        xhr.send();
      }
    }

    function handleRemoteStreamAdded(event) {
      console.log('Remote stream added.');
      remoteStream = event.stream;
      remoteVideo.srcObject = remoteStream;
    }

    function handleRemoteStreamRemoved(event) {
      console.log('Remote stream removed. Event: ', event);
    }

    function hangup() {
      console.log('Hanging up.');
      stop();
      sendMessage('bye');
    }
    console.log(hangup)

    function handleRemoteHangup() {
      console.log('Session terminated.');
      stop();
      isInitiator = false;
    }

    function stop() {
      isStarted = false;
      pc.close();
      pc = null;
    }
  }
}
</script>

<style>

</style>