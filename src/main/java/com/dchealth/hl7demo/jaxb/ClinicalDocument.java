package com.dchealth.hl7demo.jaxb;

import javax.xml.bind.annotation.*;

/**
 * 病历实体
 */
@XmlRootElement(name = "ClinicalDocument")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClinicalDocument {

    @XmlElement
    private Realmcode realmcode;

    public Realmcode getRealmcode() {
        return realmcode;
    }

    public void setRealmcode(Realmcode realmcode) {
        this.realmcode = realmcode;
    }
}

@XmlAccessorType(value = XmlAccessType.FIELD)
class Realmcode {
    @XmlAttribute(name = "code")
    private String code;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlValue
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode() {
        this.code = code;
    }
}