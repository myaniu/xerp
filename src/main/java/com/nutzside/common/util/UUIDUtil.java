/**
 * 
 */
package com.nutzside.common.util;


import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * UUID 工具类 <br>
 * 
 * @author Dawn email: csg0328#gmail.com
 * @date 2011-11-22   上午11:33:13   
 * @version 1.0
 * @since 1.0
 */
public class UUIDUtil {

   
	public static String get() {
		return UUID.randomUUID().toString().replace("_", "");
	}

	
	/** The FieldPosition. */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

    /** This Format for format the data to special format. */
    private final static Format dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");

    /** This Format for format the number to special format. */
    private final static NumberFormat numberFormat = new DecimalFormat("0000");

    /** This int is the sequence number ,the default value is 0. */
    private static int seq = 0;

    private static final int MAX = 9999;

    /**
     * 时间格式生成序列
     * @return String
     */
    public static synchronized String generateSequenceNo() {

        Calendar rightNow = Calendar.getInstance();

        StringBuffer sb = new StringBuffer();

        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

        numberFormat.format(seq, sb, HELPER_POSITION);

        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }

        return sb.toString();
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s = generateSequenceNo();
		System.out.println(s);
	}
	
}
