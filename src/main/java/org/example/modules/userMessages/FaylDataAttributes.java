package org.example.modules.userMessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties
public class FaylDataAttributes {
    long size;
    String createdAt;
    String updatedAt;
    String url;
    String name;
    String mime;
}
