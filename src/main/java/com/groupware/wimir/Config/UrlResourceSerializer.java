package com.groupware.wimir.Config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.core.io.UrlResource;

import java.io.IOException;

public class UrlResourceSerializer extends StdSerializer<UrlResource> {

    public UrlResourceSerializer() {
        this(null);
    }

    public UrlResourceSerializer(Class<UrlResource> t) {
        super(t);
    }

    @Override
    public void serialize(UrlResource urlResource, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (urlResource != null) {
            jsonGenerator.writeString(urlResource.getURL().toString());
        } else {
            jsonGenerator.writeString(""); // 또는 null 등으로 처리
        }
    }
}
