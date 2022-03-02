package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * 파티룸 태그 모델 정의.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RoomTag extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @JsonManagedReference
    private Tag tag;

    public RoomTag(Room room, Tag tag) {
        this.room = room;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "RoomTag{" +
                "room=" + room.getTitle() +
                "tag=" + tag +
                '}';
    }
}
