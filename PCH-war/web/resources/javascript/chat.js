/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function init() {
    console.log('init');
    jsf.push.open('webSocketChatChannel');
    console.log('init done');
}

function receivePush(message, channel, event) {
    if (message.type == "MESSAGE") {
        addText(message);
        scrollToBottom();
    } else if (message.type == "USER_JOIN") {
        addUser(message.name);
    } else if (message.type == "USER_LEFT") {
        removeUser(message.name);
    }
}

function onopen() {
    console.log('onopen');
}

function onclose() {
    console.log('onclose');
}

function removeUser(name) {
    var ul = document.getElementById('user-list');

    for (i = 0; i < ul.children.length; i++) {
        if (ul.children[i].innerHTML == name) {
            ul.removeChild(ul.children[i]);
            break;
        }
    }
}

function addUser(name) {
    var ul = document.getElementById('user-list');
    var li = document.createElement("li");
    li.appendChild(document.createTextNode(name));
    ul.appendChild(li);
}

function addText(message) {
    var container = document.getElementById('text-container');
    container.innerHTML += '<div class="message">' +
        '<div class="text">' +
            '<div class="name">' + message.name + '</div>' +
            message.value +
        '</div>' +
        '<div class="time">' +
            '<div>' + message.time + '</div>' +
        '</div>' +
    '</div>';
}

function doTheFocus(data) {
    document.getElementById('sendForm:txt-message').focus();
}

function scrollToBottom(){
    var container = document.getElementById('text-container');
    container.scrollTop = container.scrollHeight;
}

