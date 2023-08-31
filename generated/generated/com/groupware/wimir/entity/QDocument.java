package com.groupware.wimir.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = -1147665443L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocument document = new QDocument("document");

    public final DatePath<java.time.LocalDate> appDate = createDate("appDate", java.time.LocalDate.class);

    public final StringPath content = createString("content");

    public final DatePath<java.time.LocalDate> createDate = createDate("createDate", java.time.LocalDate.class);

    public final NumberPath<Long> dno = createNumber("dno", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath result = createString("result");

    public final NumberPath<Long> sno = createNumber("sno", Long.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final QTemplate template;

    public final NumberPath<Long> tempNo = createNumber("tempNo", Long.class);

    public final StringPath title = createString("title");

    public final DatePath<java.time.LocalDate> updateDate = createDate("updateDate", java.time.LocalDate.class);

    public final QMember writer;

    public QDocument(String variable) {
        this(Document.class, forVariable(variable), INITS);
    }

    public QDocument(Path<? extends Document> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocument(PathMetadata metadata, PathInits inits) {
        this(Document.class, metadata, inits);
    }

    public QDocument(Class<? extends Document> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.template = inits.isInitialized("template") ? new QTemplate(forProperty("template")) : null;
        this.writer = inits.isInitialized("writer") ? new QMember(forProperty("writer"), inits.get("writer")) : null;
    }

}

