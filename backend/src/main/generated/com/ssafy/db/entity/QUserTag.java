package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserTag is a Querydsl query type for UserTag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserTag extends EntityPathBase<UserTag> {

    private static final long serialVersionUID = -700945971L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserTag userTag = new QUserTag("userTag");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QTag tag;

    public final QUser user;

    public QUserTag(String variable) {
        this(UserTag.class, forVariable(variable), INITS);
    }

    public QUserTag(Path<? extends UserTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserTag(PathMetadata metadata, PathInits inits) {
        this(UserTag.class, metadata, inits);
    }

    public QUserTag(Class<? extends UserTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

