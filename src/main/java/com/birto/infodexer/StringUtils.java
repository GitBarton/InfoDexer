package com.birto.infodexer;

public class StringUtils {
	public static String removeStart(String str, String remove) {
	      if (isEmpty(str) || isEmpty(remove)) {
	          return str;
	      }
	      if (str.startsWith(remove)){
	          return str.substring(remove.length());
	      }
	      return str;
	  }
	  public static boolean isEmpty(CharSequence cs) {
	      return cs == null || cs.length() == 0;
	  }
}
