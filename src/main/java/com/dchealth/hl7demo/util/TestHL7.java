package com.dchealth.hl7demo.util;

import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.datatype.CE;
import ca.uhn.hl7v2.model.v24.message.QBP_Q11;
import ca.uhn.hl7v2.model.v24.segment.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author SamJoke
 */
public class TestHL7 {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public void setHeader(MSH msh) throws DataTypeException {
        String nowDate = sdf.format(new Date());
        msh.getFieldSeparator().setValue("|");// 分隔符
        msh.getEncodingCharacters().setValue("^~\\&");// MSH-2
        msh.getMsh3_SendingApplication().getHd1_NamespaceID().setValue("01");
        msh.getMsh5_ReceivingApplication().getHd1_NamespaceID().setValue("PMI");
        msh.getDateTimeOfMessage().getTimeOfAnEvent().setValue(nowDate);// MSH-7
        msh.getMsh10_MessageControlID().setValue(nowDate + new Random().nextInt(100));// MSH-10
        msh.getMsh11_ProcessingID().getProcessingID().setValue("P");
        msh.getMsh12_VersionID().getVersionID().setValue("2.4");
        msh.getMsh15_AcceptAcknowledgmentType().setValue("AL");
        msh.getMsh16_ApplicationAcknowledgmentType().setValue("AL");
        msh.getMsh17_CountryCode().setValue("CHN");
        msh.getMsh18_CharacterSet(0).setValue("UNICODE");
    }

    public String getHL7Text() {

        QBP_Q11 qbp_q11 = new QBP_Q11();
        MSH msh = qbp_q11.getMSH();

        String msg = null;
        try {
            //MSH设置
            HL7Helper.setMsgValue(qbp_q11, MSH.class,"QBP", 9,0,0);
            HL7Helper.setMsgValue(qbp_q11, MSH.class,"ZP7", 9,0,1);
            setHeader(msh);

            //QBP设置
            QPD qbp = qbp_q11.getQPD();

            HL7Helper.setMsgValue(qbp_q11, QPD.class, "ZP7", 1,0,0);
            HL7Helper.setMsgValue(qbp_q11, QPD.class, "Find Candidates", 1,0,1);
            HL7Helper.setMsgValue(qbp_q11, QPD.class, "HL7v2.4", 1,0,2);

            //设置用户自定义Type
            qbp.getQpd3_UserParametersInsuccessivefields().setData(new UserDefineComposite(qbp_q11, 3));

            //新增Type类型为CE的字段
            HL7Helper.segmentAddFeild(qbp, CE.class, 250, qbp_q11);

            HL7Helper.setMsgValue(qbp_q11, QPD.class, "0", 3,0,0);
            HL7Helper.setMsgValue(qbp_q11, QPD.class, "2558856", 3,0,1);
            HL7Helper.setMsgValue(qbp_q11, QPD.class, "0", 3,0,2);

            HL7Helper.segmentAddFeild(qbp, CE.class, 100, qbp_q11);
            HL7Helper.setMsgValue(qbp_q11, QPD.class, "SamJoke", 5,0,0);

            HL7Helper.segmentAddFeild(qbp, CE.class, 250, qbp_q11);
            SimpleDateFormat sdf01 = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat sdf02 = new SimpleDateFormat("yyyy-MM-dd");
            HL7Helper.setMsgValue(qbp_q11, QPD.class, sdf01.format(sdf02.parse("1994-8-30")), 6,0,0);

            HL7Helper.segmentAddFeild(qbp, CE.class, 1, qbp_q11);
            HL7Helper.setMsgValue(qbp_q11, QPD.class, "M", 7,0,0);

            //RCP设置
            HL7Helper.setMsgValue(qbp_q11, RCP.class, "I", 1,0,0);
            HL7Helper.setMsgValue(qbp_q11, RCP.class, "20", 2,0,0);
            HL7Helper.setMsgValue(qbp_q11, RCP.class, "RD", 2,0,1);
            HL7Helper.setMsgValue(qbp_q11, RCP.class, "R", 3,0,0);

            msg = qbp_q11.encode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

    public void hl7Text2Obj(String responseStr) {

        try {
            UserDefineMessage.ASType[] asTypes = new UserDefineMessage.ASType[7];
            asTypes[0] = new UserDefineMessage.ASType(MSH.class,true,true);
            asTypes[1] = new UserDefineMessage.ASType(MSA.class,true,true);
            asTypes[2] = new UserDefineMessage.ASType(QAK.class,true,true);
            asTypes[3] = new UserDefineMessage.ASType(QPD.class,true,true);
            asTypes[4] = new UserDefineMessage.ASType(PID.class,true,true);
            asTypes[5] = new UserDefineMessage.ASType(IN1.class,false,true);
            asTypes[6] = new UserDefineMessage.ASType(QRI.class,false,true);

            UserDefineMessage udm = new UserDefineMessage(asTypes);

            UserDefineMessage udm1 = new UserDefineMessage(asTypes);

            UserDefineMessage udm2 = new UserDefineMessage(asTypes);

            QPD qpd = udm2.getAS(QPD.class);

            HL7Helper.segmentAddFeild(qpd, CE.class, 250, udm2);

            // 添加一个长度为1的CE数组
            HL7Helper.segmentAddFeild(qpd, CE.class, 250, udm2);

            // 添加一个长度为2的CE数组
            HL7Helper.segmentAddFeild(qpd, CE.class, 2, 250, udm2);

            // 添加一个长度为1的CE数组
            HL7Helper.segmentAddFeild(qpd, CE.class, 250, udm2);

            HL7Helper.acceptResponse(udm2, responseStr);

            MSH msh = (MSH) udm2.get("MSH");
            System.out.println(msh.encode());
            System.out.println(msh.getDateTimeOfMessage().getTs1_TimeOfAnEvent().toString());

            QPD qpdt = (QPD) udm2.get("QPD");
            System.out.println(qpdt.encode());
            System.out.println(qpdt.getQpd1_MessageQueryName().getCe1_Identifier().getValue());

            System.out.println(HL7Helper.getMsgValue(udm2, "QRI", 1,0,0));

            System.out.println(udm2.encode());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestHL7 co = new TestHL7();

        System.out.println(co.getHL7Text());

        String resStr = "MSH|^~\\&|PMI||01||20170719143120||RSP^ZP7|YY00000001|P|2.4|\rMSA|AA|YY00000001|[MsgInfo] Method Type: ZP7 -Success Flag: AA -MSG: success QueryPerson return message success.\rQAK||||0|0|0\rQPD|\rIN1|6|0|8\rQRI|15.014454851245674|3|";

        co.hl7Text2Obj(resStr);
    }

}
