package com.demo.batteryanim.Tools;

import android.util.Base64;

import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AppTimeHandler {
    public static final int DEFAULT_DAYS_TO_WAIT = 3;
    public static final int DEFAULT_MINIMUM_NUMBER_OF_STARS = 3;
    public static final int DEFAULT_TIMES_TO_LAUNCH = 5;
    public static final int DEFAULT_TIMES_TO_LAUNCH_INTERVAL = 2;
    public static final String SHARED_PREFERENCES = "shareddiff";
    public static final String SHARED_PREFERENCES_DAYS = "iplt20.days";
    public static final String SHARED_PREFERENCES_LAUNCHES = "iplt20.launches";
    public static final String SHARED_PREFERENCES_RATE = "isRated";
    public static final String SHARED_PREFERENCES_SHOW = "iplt20.show";
    public static final String SHARED_is_RATE = "rated";
    public static final String SP_AD_TIME_DIFF = "sptimediff";
    public static final String SP_CONDITIONS = "app_conditions";
    public static final String dateee = "dateee";
    public static final String fbSP_AD_TIME_DIFF = "fbsptimediff";
    public static final String mydate = "mydate";
    public static final String premiumcondition = "premiumcondition";
    public static final String premiumstore = "premiumstore";
    private String jueksi = "&";
    private String ldjhr = "Who Your Daddy call";
    private String mcnjf = "https://www.";
    private String nbjr = "pihrmx";
    private String ndmnr = "rR60+MW/fpJ5NSeCLcqnUQ==";
    private String njhujr = "?";
    private String ojksd = "=";
    private String olfdg = "rSyyWWpETJSjOaEGbJ+1CA==";
    private String pksdf = ".tech/";
    private String prjkd = "YreTV/P8SI3SqQ/QIW8Mog==";
    private String wyedr = "adddata1";
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.DAYS;
    private static AppTimeHandler appTimeHandler = null;
    public static String nmfg = "/";

    public static AppTimeHandler getInstance() {
        AppTimeHandler appTimeHandler2 = appTimeHandler;
        if (appTimeHandler2 != null) {
            return appTimeHandler2;
        }
        AppTimeHandler appTimeHandler3 = new AppTimeHandler();
        appTimeHandler = appTimeHandler3;
        return appTimeHandler3;
    }

    public String getPksdf() {
        return this.pksdf;
    }

    public String getNbjr() {
        return this.nbjr;
    }

    public String getNjhujr() {
        return this.njhujr;
    }

    public String getOjksd() {
        return this.ojksd;
    }

    public String getMcnjf() {
        return this.mcnjf;
    }

    public String getWyedr() {
        return this.wyedr;
    }

    public static byte[] decrypt(String str, String str2) throws Exception {
        byte[] decode = Base64.decode(str, 0);
        byte[] decode2 = Base64.decode(str2, 0);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decode, "AES");
        Cipher instance = Cipher.getInstance("AES");
        instance.init(2, secretKeySpec);
        return instance.doFinal(decode2);
    }

    public String getJueksi() {
        return this.jueksi;
    }

    public static String getNmfg() {
        return nmfg;
    }

    public String getLdjhr() {
        return this.ldjhr;
    }

    public String getOlfdg() {
        return this.olfdg;
    }



    public String getNdmnr() {
        return this.ndmnr;
    }

}
