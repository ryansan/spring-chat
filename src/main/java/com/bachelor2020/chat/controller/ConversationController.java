package com.bachelor2020.chat.controller;

import com.bachelor2020.chat.model.Conversation;
import com.bachelor2020.chat.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatapi/conversation")
@CrossOrigin
public class ConversationController {

    @Autowired
    ConversationService conversationService;

    @GetMapping("/conversation/{studyGroupID}")
    public Conversation getConversationByStudyGroupID(@PathVariable long studyGroupID){
        return conversationService.getConversationByStudyGroupID(studyGroupID);
    }

}
