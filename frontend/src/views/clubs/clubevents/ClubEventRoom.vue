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
      remoteVideo: null,
      peerIdx: 1,
      messageData: null,
      localVideo: null,
      localStream: null,
      pc: [1, 2, 3, 4, 5, 6, 7, 8, 9],
      remoteStream: null,
      turnReady: null,
      pcConfig: {
        'iceServers': [{
          'urls': 'stun:stun.l.google.com:19302'
        }]}
    }
  },
  methods: {
    created(room) {
      console.log('Created room ' + room);
      this.isInitiator = true;
    },
    full(room) {
      console.log('Room ' + room + ' is full');
    },
    join(room){
      console.log('Another peer made a request to join room ' + room);
      console.log('This peer is the initiator of room ' + room + '!');
      this.isChannelReady = true;
    },
    joined(numClients) {
      console.log('joined: ' + numClients);
      this.remoteVideo = document.querySelector(`#remoteVideo-${numClients}`);
      this.peerIdx = numClients
      console.log(this.remoteVideo)
      this.isChannelReady = true;
    },

    onbeforeunload() {
      this.sendMessage('bye');
    },

    message(message) {
      console.log('Client received message:', message);
      if (message === 'got user media') {
        this.maybeStart();
      } else if (message.type === 'offer') {
        if (!this.isInitiator && !this.isStarted) {
         this.maybeStart();
        }
        this.pc[this.peerIdx].setRemoteDescription(new RTCSessionDescription(message));
        this.doAnswer();
      } else if (message.type === 'answer' && this.isStarted) {
        this.pc[this.peerIdx].setRemoteDescription(new RTCSessionDescription(message));
      } else if (message.type === 'candidate' && this.isStarted) {
        var candidate = new RTCIceCandidate({
          sdpMLineIndex: message.label,
          candidate: message.candidate
        });
        this.pc[this.peerIdx].addIceCandidate(candidate);
      } else if (message === 'bye' && this.isStarted) {
        this.handleRemoteHangup();
      }
    },


    gotStream(stream) {
      console.log('Adding local stream.');
      this.localStream = stream;
      this.localVideo = document.querySelector('#localVideo')
      this.localVideo.srcObject = stream;
      this.sendMessage('got user media');
      if (this.isInitiator) {
        this.maybeStart();
      }
    },
    sendMessage(message) {
      console.log('Client sending message: ', message)
      let messageData = {
        room: `clubEventRoom-${this.roomId}`,
        message: message
      }
      console.log(messageData)
      this.$socket.emit('message', messageData);
    },
    maybeStart() {
      console.log('>>>>>>> maybeStart() ', this.isStarted, this.localStream, this.isChannelReady);
      if (typeof this.localStream !== 'undefined' && this.isChannelReady) {
        console.log('>>>>>> creating peer connection');
        this.createPeerConnection();
        this.pc[this.peerIdx].addStream(this.localStream);
        this.isStarted = true;
        console.log('isInitiator', this.isInitiator);
        if (this.isInitiator) {
          this.doCall();
        }
      }
    },
    createPeerConnection() {
      try {
        this.pc[this.peerIdx] = new RTCPeerConnection(null);
        this.pc[this.peerIdx].onicecandidate = this.handleIceCandidate;
        this.pc[this.peerIdx].onaddstream = this.handleRemoteStreamAdded;
        this.pc[this.peerIdx].onremovestream = this.handleRemoteStreamRemoved;
        console.log('Created RTCPeerConnnection');
      } catch (e) {
        console.log('Failed to create PeerConnection, exception: ' + e.message);
        alert('Cannot create RTCPeerConnection object.');
        return;
      }
    },
    handleIceCandidate(event) {
      console.log('icecandidate event: ', event);
      if (event.candidate) {
        this.sendMessage({
          type: 'candidate',
          label: event.candidate.sdpMLineIndex,
          id: event.candidate.sdpMid,
          candidate: event.candidate.candidate
        });
      } else {
        console.log('End of candidates.');
      }
    },

    handleCreateOfferError(event) {
      console.log('createOffer() error: ', event);
    },

    doCall() {
      console.log('Sending offer to peer');
      this.pc[this.peerIdx].createOffer({iceRestart: true})
        .then(this.setLocalAndSendMessage)
        .catch(this.handleCreateOfferError);
    },

    doAnswer() {
      console.log('Sending answer to peer.');
      this.pc[this.peerIdx].createAnswer().then(
        this.setLocalAndSendMessage,
        this.onCreateSessionDescriptionError
      );
    },

    setLocalAndSendMessage(sessionDescription) {
      this.pc[this.peerIdx].setLocalDescription(sessionDescription);
      console.log('setLocalAndSendMessage sending message', sessionDescription);
      this.sendMessage(sessionDescription);
    },

    onCreateSessionDescriptionError(error) {
      console.log('Failed to create session description: ' + error.toString());
    },

    requestTurn(turnURL) {
      var turnExists = false;
      for (var i in this.pcConfig.iceServers) {
        if (this.pcConfig.iceServers[i].urls.substr(0, 5) === 'turn:') {
          turnExists = true;
          this.turnReady = true;
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
            this.pcConfig.iceServers.push({
              'urls': 'turn:' + turnServer.username + '@' + turnServer.turn,
              'credential': turnServer.password
            });
            this.turnReady = true;
          }
        };
        xhr.open('GET', turnURL, true);
        xhr.send();
      }
    },
    handleRemoteStreamAdded(event) {
      console.log('Remote stream added.');
      this.remoteStream = event.stream;
      console.log(this.remoteVideo)
      this.remoteVideo.srcObject = this.remoteStream;
    },

    handleRemoteStreamRemoved(event) {
      console.log('Remote stream removed. Event: ', event);
    },

    hangup() {
      console.log('Hanging up.');
      stop();
      this.sendMessage('bye');
    },

    handleRemoteHangup() {
      console.log('Session terminated.');
      stop();
      this.isInitiator = false;
    },

    stop() {
      this.isStarted = false;
      this.pc[this.peerIdx].close();
      this.pc[this.peerIdx] = null;
    }
  },
  beforeRouteLeave(to, from, next) {
    this.hangup()
    var track = this.localStream.getTracks()[0]
    track.stop()
    next()
    
  },
  mounted() {
    'use strict';

    // var pcConfig = {
    //   'iceServers': [{
    //       'urls': 'stun:stun.l.google.com:19302'
    //     },
    //     // {
    //     //   urls: 'turn:relay.backups.cz',
    //     //   credential: 'webrtc',
    //     //   username: 'webrtc'
    //     // },
    //     // {
    //     //   urls: 'turn:relay.backups.cz?transport=tcp',
    //     //   credential: 'webrtc',
    //     //   username: 'webrtc'
    //     // },
    //   ]};

    // Set up audio and video regardless of what devices are present.
    var sdpConstraints = {
      offerToReceiveAudio: true,
      offerToReceiveVideo: true
    };
    console.log(sdpConstraints)


    var room = `clubEventRoom-${this.roomId}`;

    var socket = this.$socket
    console.log(socket.emit())

    if (room !== '') {
      socket.emit('create or join', room);
      console.log('Attempted to create or join room', room);
    }

    socket.on('created', this.created);

    socket.on('full', this.full);

    socket.on('join', this.join);

    socket.on('joined', this.joined);

    socket.on('log', function(array) {
      console.log.apply(console, array);
    });

    socket.on('message', this.message);

    navigator.mediaDevices.getUserMedia({
      audio: false,
      video: true
    })
    .then(this.gotStream)
    .catch(function(e) {
      alert('getUserMedia() error: ' + e.name);
    });

    var constraints = {
      video: true
    };

    console.log('Getting user media with constraints', constraints);

    if (location.hostname !== 'localhost') {
      this.requestTurn(
        'https://computeengineondemand.appspot.com/turn?username=41784574&key=4080218913'
      );
    }
  }
}
</script>

<style>

</style>