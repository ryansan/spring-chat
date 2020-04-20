package com.bachelor2020.chat.service;

import com.bachelor2020.chat.model.*;
import com.bachelor2020.chat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private StudyGroupService studyGroupService;

    public ChatMessage createNewChatMessage(Message incomingMessage, long studyGroupID){
        ChatMessage message = new ChatMessage();

        //Set content of message
        message.setContent(incomingMessage.getContent());

        //Set sent time and date
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        System.out.println(today);
        System.out.println(now);

        if(incomingMessage.getSentDate() == null && incomingMessage.getSentDate() == null){
            message.setSentDate(java.sql.Date.valueOf(today));
            message.setSentTime(java.sql.Time.valueOf(now));
        }else{
            message.setSentDate(incomingMessage.getSentDate());
            message.setSentTime(incomingMessage.getSentTime());
            System.out.println(message.getSentDate());
            System.out.println(message.getSentTime());
        }


        System.out.println("Trying to find student with ID " + incomingMessage.getStudentID());
        //Set student sending the message
        Student student = studentService.getStudentByStudentID(incomingMessage.getStudentID());
        if(student != null) {
            message.setStudent(student);
        }else{
            System.out.println("Couldnt find student");
        }

        //get convo by studygroudid
        StudyGroup studyGroup = studyGroupService.getStudyGroupByStudyGroupID(studyGroupID);
        Conversation conversation = null;

        if(studyGroup != null){
            conversation = studyGroup.getConversation();
        }else{
            System.out.println("Couldnt find studygroup");
        }

        if(conversation != null){
            message.setConversation(conversation);
        }else{
            System.out.println("Couldn't find conversation");
        }

        return chatMessageRepository.save(message);
    }
}
