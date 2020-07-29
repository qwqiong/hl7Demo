package com.dchealth.hl7demo.jaxb;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * 病历实体
 */
@XmlRootElement(name = "ClinicalDocument")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ClinicalDocument {

    @XmlElement
    private Realmcode realmcode;
}