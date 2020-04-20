package com.bachelor2020.chat.model;

import java.sql.Date;
import java.sql.Time;

public class Message {
    public enum MessageType {CHAT, JOIN, LEAVE}

    private MessageType messageType;
    private String content;
    private String sender;
    private long studentID;
    private Student student;
    private Date sentDate;
    private Time sentTime;

    //messageType
    //content
    //Student: id, name...

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Time getSentTime() {
        return sentTime;
    }

    public void setSentTime(Time sentTime) {
        this.sentTime = sentTime;
    }


    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", studentID=" + studentID +
                ", student ID and name=" + student.getStudent_id() + " " + student.getFirst_name() + " " + student.getLast_name() +
                ", sentDate=" + sentDate +
                ", sentTime=" + sentTime +
                '}';
    }
}