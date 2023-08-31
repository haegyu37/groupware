package com.groupware.wimir.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QApproval is a Querydsl query type for Approval
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApproval extends EntityPathBase<Approval> {

    private static final long serialVersionUID = -824141563L;

    public static final QApproval approval = new QApproval("approval");

    public final DatePath<java.time.LocalDate> appDate = createDate("appDate", java.time.LocalDate.class);

    public final StringPath category = createString("category");

    public final StringPath current = createString("current");

    public final NumberPath<Long> document = createNumber("document", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> lineId = createNumber("lineId", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath name = createString("name");

    public final StringPath reason = createString("reason");

    public final StringPath refer = createString("refer");

    public final StringPath status = createString("status");

    public final NumberPath<Long> writer = createNumber("writer", Long.class);

    public QApproval(String variable) {
        super(Approval.class, forVariable(variable));
    }

    public QApproval(Path<? extends Approval> path) {
        super(path.getType(), path.getMetadata());
    }

    public QApproval(PathMetadata metadata) {
        super(Approval.class, metadata);
    }

}

