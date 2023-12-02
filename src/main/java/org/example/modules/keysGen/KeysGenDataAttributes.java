package org.example.modules.keysGen;

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
public class KeysGenDataAttributes {
    String createdAt;
    String updatedAt;
    String publishedAt;
    String privkey;
    String pubkey;
    String nomi;
}
