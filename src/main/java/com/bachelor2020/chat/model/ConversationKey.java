package com.bachelor2020.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@NoArgsConstructor
public class ConversationKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long conversationKeyId;
    private byte[] signingKey;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
}
