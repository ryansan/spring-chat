package com.bachelor2020.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long conversation_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "study_group_id")
    @JsonIgnore
    private StudyGroup studyGroup;

    //@JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "conversation")
    @JsonIgnore
    private ConversationKey conversationKey;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages;
}
