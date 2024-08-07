'use strict';
window.onload=function () {
    var usernamePage = document.querySelector('#username-page');
    var chatPage = document.querySelector('#chat-page');
    var usernameForm = document.querySelector('#usernameForm');
    var messageForm = document.querySelector('#messageForm');
    var messageInput = document.querySelector('#message');
    var messageArea = document.querySelector('#messageArea');
    var activeUsers=document.querySelector('#active-users')
    var connectingElement = document.querySelector('.connecting');
    var surveyButton=document.querySelector("#sendSurveyButton")
    var stompClient = null;
    var username = null;
    var survey=document.querySelector("#survey")
    var role;
    let users_array = [];
    var exit_button=document.querySelector("#exitButton")
    var colors = [
        '#2196F3', '#32c787', '#00BCD4', '#ff5652',
        '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
    ];

    function connect(event) {
        username = document.querySelector('#name').value.trim();

        if (username) {
            usernamePage.classList.add('hidden');
            chatPage.classList.remove('hidden');

            var socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);

            stompClient.connect({}, onConnected, onError);
        }
        event.preventDefault();
    }


    function onConnected(options) {
        // Subscribe to the Public Topic
        stompClient.subscribe('/topic/public', onMessageReceived);
        stompClient.subscribe()
        stompClient.subscribe('/topic/changes',function (message)
        {
                showChange(message.body)
        })


        // Tell your username to the server
        stompClient.send("/app/chat.addUser",
            {},
            JSON.stringify({sender: username, type: 'JOIN'})
        )
        connectingElement.classList.add('hidden');
    }


    function onError(error) {
        connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
        connectingElement.style.color = 'red';
    }


    function sendMessage(event) {
        var messageContent = messageInput.value.trim();
        if (messageContent && stompClient) {
            var chatMessage = {
                sender: username,
                content: messageInput.value,
                type: 'CHAT'
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }


    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);
        hideIfUserIsNotAdmin(message.role)
        role=message.role
        var messageElement = document.createElement('li');
        if (message.type === 'JOIN') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' joined!';
        } else if (message.type === 'LEAVE') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' left!';
        }
        else {
            messageElement.classList.add('chat-message');

            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(message.sender[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.sender);

            messageElement.appendChild(avatarElement);

            var usernameElement = document.createElement('span');
            var usernameText = document.createTextNode(message.sender);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);
        }

        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }

    function hideIfUserIsNotAdmin(role)
    {
        if(role==="ROLE_ADMIN")
            surveyButton.style.display='block'
    }
    function getAvatarColor(messageSender) {
        var hash = 0;
        for (var i = 0; i < messageSender.length; i++) {
            hash = 31 * hash + messageSender.charCodeAt(i);
        }
        var index = Math.abs(hash % colors.length);
        return colors[index];
    }

    function fetchActiveUsers() {

        fetch('/api/connected-users')
            .then(response => response.json())
            .then(users => {
                users_array=users
                const userList = document.getElementById('activeUsersList');
                userList.innerHTML = '';
                users.forEach(user => {
                    const li = document.createElement('li');
                    li.textContent = user.username;
                    userList.appendChild(li);
                });
            })
            .catch(error => console.error('Error fetching active users:', error));
    }
    function showChange(message)
    {
        survey.style.display="block"
    }
    setInterval(fetchActiveUsers, 3000); // Fetch every 5 seconds
    usernameForm.addEventListener('submit', connect, true)
    messageForm.addEventListener('submit', sendMessage, true)
    surveyButton.addEventListener('click',function ()
    {
        stompClient.send("/app/change", {}, "Button clicked!");
    })
    exit_button.addEventListener("submit",function (event)
        {
            event.preventDefault()
            var url='/exit'
            role=users_array.find(user=>user.username===username).role
            var form_data=new FormData();
            form_data.append("role",role)
            form_data.append("username",username)
            var options = {
                method: 'POST',
                body: form_data
            };
            fetch(url, options)
                .then(function(response) {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    if(response.ok)
                    {
                        stompClient.disconnect()
                        window.location.reload()
                    }
                    return response.ok // assuming response is JSON
                })
                .then(function(data) {
                    console.log('POST request succeeded with JSON response', data);
                })
                .catch(function(error) {
                    console.error('Error making POST request:', error);
                });

        }

    )
}