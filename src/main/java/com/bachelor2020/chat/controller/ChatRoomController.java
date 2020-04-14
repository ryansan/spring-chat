package com.bachelor2020.chat.controller;

import com.bachelor2020.chat.model.ChatMessage;
import com.bachelor2020.chat.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import com.bachelor2020.chat.model.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


import static java.lang.String.format;

@RestController
@CrossOrigin
public class ChatRoomController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    ChatMessageService chatMessageService;

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload Message chatMessage){
        System.out.println(roomId + "Chat message recieved: " + chatMessage.getContent() + "");
        messagingTemplate.convertAndSend(format("/topic/%s", roomId), chatMessage);
        chatMessageService.createNewChatMessage(chatMessage, Long.parseLong(roomId));
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable String roomId, @Payload Message incomingMessage, SimpMessageHeaderAccessor headerAccessor){
        System.out.println("Adding user to room " + roomId);
        System.out.println("Msg: " + incomingMessage);

        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);

        if(currentRoomId != null){
            Message leaveMessage = new Message();
            leaveMessage.setMessageType(Message.MessageType.LEAVE);
            leaveMessage.setSender(incomingMessage.getSender());
            leaveMessage.setStudentID(incomingMessage.getStudentID());
            messagingTemplate.convertAndSend(format("/topic/chat-room/%s", currentRoomId), leaveMessage);
        }

        headerAccessor.getSessionAttributes().put("name", incomingMessage.getSender());

        //istedenfor å sende msg in, convert og send msg ut
        messagingTemplate.convertAndSend(format("/topic/%s", roomId), incomingMessage);
    }
}
