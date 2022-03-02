package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 인 메모리 파티룸 모델 정의.
 */
@Entity
@Table(name = "room_in_memory")
@Getter
@Setter
public class SuggestionRoom extends BaseEntity{
    private String title;
    private String description;
    private String thumbnailUrl;
    private String capacity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    private boolean isLocked;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "room")
    private List<Session> sessions;

    @JsonManagedReference
    @OneToMany(mappedBy = "room")
    private List<RoomTag> roomTags;

    public SuggestionRoom(Room room){
        this.title = room.getTitle();
        this.description = room.getDescription();
        this.thumbnailUrl = room.getThumbnailUrl();
        this.capacity = room.getCapacity();
    }

    public SuggestionRoom() {

    }
}
