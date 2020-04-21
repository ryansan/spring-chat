package com.bachelor2020.chat.service;

import com.bachelor2020.chat.model.Conversation;
import com.bachelor2020.chat.model.ConversationKey;
import com.bachelor2020.chat.repository.ConversationKeyRepository;
import com.bachelor2020.chat.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationKeyService {

    @Autowired
    ConversationKeyRepository conversationKeyRepository;

    public ConversationKey getConversationKeyByConversation(Conversation conversation){
        return conversationKeyRepository.findConversationKeyByConversation(conversation);
    }
}
