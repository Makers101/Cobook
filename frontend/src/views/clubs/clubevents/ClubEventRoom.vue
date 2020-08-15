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
      pc: null,
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
        this.pc.setRemoteDescription(new RTCSessionDescription(message));
        this.doAnswer();
      } else if (message.type === 'answer' && this.isStarted) {
        this.pc.setRemoteDescription(new RTCSessionDescription(message));
      } else if (message.type === 'candidate' && this.isStarted) {
        var candidate = new RTCIceCandidate({
          sdpMLineIndex: message.label,
          candidate: message.candidate
        });
        this.pc.addIceCandidate(candidate);
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
      if (!this.isStarted && typeof this.localStream !== 'undefined' && this.isChannelReady) {
        console.log('>>>>>> creating peer connection');
        this.createPeerConnection();
        this.pc.addStream(this.localStream);
        this.isStarted = true;
        console.log('isInitiator', this.isInitiator);
        if (this.isInitiator) {
          this.doCall();
        }
      }
    },
    createPeerConnection() {
      try {
        this.pc = new RTCPeerConnection(null);
        this.pc.onicecandidate = this.handleIceCandidate;
        this.pc.onaddstream = this.handleRemoteStreamAdded;
        this.pc.onremovestream = this.handleRemoteStreamRemoved;
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
      this.pc.createOffer(this.setLocalAndSendMessage, this.handleCreateOfferError);
    },

    doAnswer() {
      console.log('Sending answer to peer.');
      this.pc.createAnswer().then(
        this.setLocalAndSendMessage,
        this.onCreateSessionDescriptionError
      );
    },

    setLocalAndSendMessage(sessionDescription) {
      this.pc.setLocalDescription(sessionDescription);
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
      this.pc.close();
      this.pc = null;
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

    // var isChannelReady = this.isChannelReady;
    // var isInitiator = this.isInitiator;
    // var isStarted = this.isStarted;
    // var localStream;
    // var pc;
    // var remoteStream;
    // var turnReady;
    // console.log(turnReady)

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


    /////////////////////////////////////////////

    var room = `clubEventRoom-${this.roomId}`;
    // let remoteVideo
    // Could prompt for room name:
    // room = prompt('Enter room name:');

    // var socket = io.connect();
    var socket = this.$socket
    console.log(socket.emit())

    if (room !== '') {
      socket.emit('create or join', room);
      console.log('Attempted to create or join room', room);
    }

    // socket.on('created', function(room) {
    //   console.log('Created room ' + room);
    //   isInitiator = true;
    // });

    socket.on('created', this.created);

    // socket.on('full', function(room) {
    //   console.log('Room ' + room + ' is full');
    // });

    socket.on('full', this.full);

    // socket.on('join', function(numClients) {
    //   console.log('Another peer made a request to join room ' + numClients);
    //   console.log('This peer is the initiator of room ' + numClients + '!');
    //   remoteVideo = document.querySelector(`#remoteVideo-${numClients}`);
    //   console.log(remoteVideo)
    //   isChannelReady = true;
    // });

    socket.on('join', this.join);

    // socket.on('joined', function(room) {
    //   console.log('joined: ' + room);
    //   isChannelReady = true;
    // });

    socket.on('joined', this.joined);

    socket.on('log', function(array) {
      console.log.apply(console, array);
    });

    ////////////////////////////////////////////////

    // function sendMessage(message) {
    //   console.log('Client sending message: ', message)
    //   let messageData = {
    //     room: room,
    //     message: message
    //   }
    //   // console.log(messageData)
    //   socket.emit('message', messageData);
    // }

    // This client receives a message
    // socket.on('message', function(message) {
    //   console.log('Client received message:', message);
    //   if (message === 'got user media') {
    //     maybeStart();
    //   } else if (message.type === 'offer') {
    //     if (!isInitiator && !isStarted) {
    //       maybeStart();
    //     }
    //     pc.setRemoteDescription(new RTCSessionDescription(message));
    //     doAnswer();
    //   } else if (message.type === 'answer' && isStarted) {
    //     pc.setRemoteDescription(new RTCSessionDescription(message));
    //   } else if (message.type === 'candidate' && isStarted) {
    //     var candidate = new RTCIceCandidate({
    //       sdpMLineIndex: message.label,
    //       candidate: message.candidate
    //     });
    //     pc.addIceCandidate(candidate);
    //   } else if (message === 'bye' && isStarted) {
    //     handleRemoteHangup();
    //   }
    // });

    socket.on('message', this.message);

    ////////////////////////////////////////////////////

    // this.localVideo = document.querySelector('#localVideo');
    // var remoteVideo = document.querySelector(`#remoteVideo-${this.peerIdx}`);

    navigator.mediaDevices.getUserMedia({
      audio: false,
      video: true
    })
    .then(this.gotStream)
    .catch(function(e) {
      alert('getUserMedia() error: ' + e.name);
    });

    // function gotStream(stream) {
    //   console.log('Adding local stream.');
    //   localStream = stream;
    //   localVideo.srcObject = stream;
    //   sendMessage('got user media');
    //   if (isInitiator) {
    //     maybeStart();
    //   }
    // }

    var constraints = {
      video: true
    };

    console.log('Getting user media with constraints', constraints);

    if (location.hostname !== 'localhost') {
      this.requestTurn(
        'https://computeengineondemand.appspot.com/turn?username=41784574&key=4080218913'
      );
    }

    // function maybeStart() {
    //   console.log('>>>>>>> maybeStart() ', isStarted, localStream, isChannelReady);
    //   if (!isStarted && typeof localStream !== 'undefined' && isChannelReady) {
    //     console.log('>>>>>> creating peer connection');
    //     createPeerConnection();
    //     pc.addStream(localStream);
    //     isStarted = true;
    //     console.log('isInitiator', isInitiator);
    //     if (isInitiator) {
    //       doCall();
    //     }
    //   }
    // }

    // window.onbeforeunload = onbeforeunload() {
    //   sendMessage('bye');
    // };

    // window.onbeforeunload = this.onbeforeunload()

    /////////////////////////////////////////////////////////

    // function createPeerConnection() {
    //   try {
    //     pc = new RTCPeerConnection(null);
    //     pc.onicecandidate = handleIceCandidate;
    //     pc.onaddstream = handleRemoteStreamAdded;
    //     pc.onremovestream = handleRemoteStreamRemoved;
    //     console.log('Created RTCPeerConnnection');
    //   } catch (e) {
    //     console.log('Failed to create PeerConnection, exception: ' + e.message);
    //     alert('Cannot create RTCPeerConnection object.');
    //     return;
    //   }
    // }

    // function handleIceCandidate(event) {
    //   console.log('icecandidate event: ', event);
    //   if (event.candidate) {
    //     sendMessage({
    //       type: 'candidate',
    //       label: event.candidate.sdpMLineIndex,
    //       id: event.candidate.sdpMid,
    //       candidate: event.candidate.candidate
    //     });
    //   } else {
    //     console.log('End of candidates.');
    //   }
    // }

    // function handleCreateOfferError(event) {
    //   console.log('createOffer() error: ', event);
    // }

    // function doCall() {
    //   console.log('Sending offer to peer');
    //   pc.createOffer(setLocalAndSendMessage, handleCreateOfferError);
    // }

    // function doAnswer() {
    //   console.log('Sending answer to peer.');
    //   pc.createAnswer().then(
    //     setLocalAndSendMessage,
    //     onCreateSessionDescriptionError
    //   );
    // }

    // function setLocalAndSendMessage(sessionDescription) {
    //   pc.setLocalDescription(sessionDescription);
    //   console.log('setLocalAndSendMessage sending message', sessionDescription);
    //   sendMessage(sessionDescription);
    // }

    // function onCreateSessionDescriptionError(error) {
    //   console.log('Failed to create session description: ' + error.toString());
    // }

    // function requestTurn(turnURL) {
    //   var turnExists = false;
    //   for (var i in pcConfig.iceServers) {
    //     if (pcConfig.iceServers[i].urls.substr(0, 5) === 'turn:') {
    //       turnExists = true;
    //       turnReady = true;
    //       break;
    //     }
    //   }
    //   if (!turnExists) {
    //     console.log('Getting TURN server from ', turnURL);
    //     // No TURN server. Get one from computeengineondemand.appspot.com:
    //     var xhr = new XMLHttpRequest();
    //     xhr.onreadystatechange = function() {
    //       if (xhr.readyState === 4 && xhr.status === 200) {
    //         var turnServer = JSON.parse(xhr.responseText);
    //         console.log('Got TURN server: ', turnServer);
    //         pcConfig.iceServers.push({
    //           'urls': 'turn:' + turnServer.username + '@' + turnServer.turn,
    //           'credential': turnServer.password
    //         });
    //         turnReady = true;
    //       }
    //     };
    //     xhr.open('GET', turnURL, true);
    //     xhr.send();
    //   }
    // }
    // console.log(remoteVideo)
    // function handleRemoteStreamAdded(event) {
    //   console.log('Remote stream added.');
    //   remoteStream = event.stream;
    //   console.log(remoteVideo)
    //   remoteVideo.srcObject = remoteStream;
    // }

    // function handleRemoteStreamRemoved(event) {
    //   console.log('Remote stream removed. Event: ', event);
    // }

    // function hangup() {
    //   console.log('Hanging up.');
    //   stop();
    //   sendMessage('bye');
    // }
    // console.log(hangup)

    // function handleRemoteHangup() {
    //   console.log('Session terminated.');
    //   stop();
    //   isInitiator = false;
    // }

    // function stop() {
    //   isStarted = false;
    //   pc.close();
    //   pc = null;
    // }
  }
}
</script>

<style>

</style>