package com.example.hackathon;

public class todo2 {
    String topic;
    String desc;
    String status;
    String uid;

    public todo2() {
    }

    public todo2(String topic, String desc, String status) {
        this.topic = topic;
        this.desc = desc;
        this.status = status;
    }

    public todo2(String topic, String desc, String status, String uid) {
        this.topic = topic;
        this.desc = desc;
        this.status = status;
        this.uid = uid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

