package org.example.modules.AliesKey;

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
public class AliesKey {
    int id;
    String cn;
    String name;
    String surname;
    String l;
    String st;
    String c;
    String o;
    String uid;
    String jshshir;
    String serialnumber;
    String validfrom;
    String validto;
}
