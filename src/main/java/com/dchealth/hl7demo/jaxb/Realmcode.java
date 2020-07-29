package com.dchealth.hl7demo.jaxb;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(value = XmlAccessType.FIELD)
@Data
class Realmcode {
    @XmlAttribute(name = "code")
    private String code;
    @XmlValue
    private String value;
}
