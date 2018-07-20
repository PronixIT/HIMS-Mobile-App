package com.pronix.hms.common;


import com.pronix.hms.models.UserDetails;

/**
 * Created by ravi on 1/2/2018.
 */

public class Constants {

    public static String ROOTDIRECTORYPATH = "";
    public static String DATABASE_DIRECTORY = "SSAAT_DB" ;
    public static String DATABASE_NAME = "SSAATDB";

    public static int DATABASE_VERSION = 1;

    public static UserDetails userDetails;


//        public static String URLBase = "http://env-0687861.cloud.cms500.com/apssaat-audit/";
    public static String URLBase = "http://192.168.0.102:1234/hms/doctor/";
//public static String URLBase = "http://192.168.0.129:1234/hms/doctor/";


    //RequestMethods
    public static String REQUEST_REGISTER = "register";
    public static String REQUEST_LOGIN = "login";
    public static String REQUEST_PROFILE = "viewProfile";
    public static String REQUEST_UPDATEPROFILE = "updateProfile";
    public static String REQUEST_PATIENTLIST = "todayAppointments";
    public static String REQUEST_PRESCRIPTION = "updatePrescription";
    public static String REQUEST_NEXTINTERVENTION = "nextIntervention";
    public static String REQUEST_PATIENTHISTORY = "patientHealthHistory";
    public static String REQUEST_PREVIOUSINTERVENTION = "previousInterventions";
    public static String REQUEST_IMPPATIENTLIST = "impCasesPatients";


    public static int PANCHAYATID = 0;
    public static int MANDALID = 0;
    public static int DISTRICTID = 0;
    public static String PANCHAYATNAME = "";


    //Service
    public static String SUCCESS = "SUCCESS";
    public static String FAILED = "FAILED";
    public static String EXCEPTION = "EXCEPTION";
    public static String SENT = "SENT";
}
