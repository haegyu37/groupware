package com.groupware.wimir.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTemplate is a Querydsl query type for Template
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemplate extends EntityPathBase<Template> {

    private static final long serialVersionUID = 964034364L;

    public static final QTemplate template = new QTemplate("template");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QTemplate(String variable) {
        super(Template.class, forVariable(variable));
    }

    public QTemplate(Path<? extends Template> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTemplate(PathMetadata metadata) {
        super(Template.class, metadata);
    }

}

