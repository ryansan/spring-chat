package com.bachelor2020.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@ToString
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chat_Message_id;
    private String content;
    private Date sentDate;
    private Time sentTime;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "chat_Message_id=" + chat_Message_id +
                ", content='" + content + '\'' +
                ", sentDate=" + sentDate +
                ", sentTime=" + sentTime +
                ", studentID and name=" + student.getStudent_id() + " " + student.getFirst_name() +
                ", conversationID=" + conversation.getConversation_id() +
                '}';
    }
}
