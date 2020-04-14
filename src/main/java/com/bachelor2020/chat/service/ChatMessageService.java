package com.bachelor2020.chat.service;

import com.bachelor2020.chat.model.ChatMessage;
import com.bachelor2020.chat.model.Conversation;
import com.bachelor2020.chat.model.Message;
import com.bachelor2020.chat.model.Student;
import com.bachelor2020.chat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ChatMessageService {
    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    ConversationService conversationService;

    public void createNewChatMessage(Message incomingMessage, long conversationID){
        ChatMessage message = new ChatMessage();

        //Set content of message
        message.setContent(incomingMessage.getContent());

        //Set sent time and date
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        message.setSentDate(java.sql.Date.valueOf(today));
        message.setSentTime(java.sql.Time.valueOf(now));

        System.out.println("Trying to find student with ID " + incomingMessage.getStudentID());
        //Set student sending the message
        Student student = studentService.getStudentByStudentID(incomingMessage.getStudentID());
        if(student != null) {
            message.setStudent(student);
        }else{
            System.out.println("Couldnt find student");
        }

        Conversation conversation = conversationService.getConversationByID(conversationID);
        //conversation.getChatMessages().add(message);
        message.setConversation(conversation);

        //Conversation saveConversation = conversationService.saveConversation(conversation);
        //message.setConversation(saveConversation);
        chatMessageRepository.save(message);
    }
}
