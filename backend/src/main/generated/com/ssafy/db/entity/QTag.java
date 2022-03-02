package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -1219619688L;

    public static final QTag tag = new QTag("tag");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<RoomTag, QRoomTag> roomTags = this.<RoomTag, QRoomTag>createList("roomTags", RoomTag.class, QRoomTag.class, PathInits.DIRECT2);

    public final StringPath tagName = createString("tagName");

    public final ListPath<UserTag, QUserTag> userTags = this.<UserTag, QUserTag>createList("userTags", UserTag.class, QUserTag.class, PathInits.DIRECT2);

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

