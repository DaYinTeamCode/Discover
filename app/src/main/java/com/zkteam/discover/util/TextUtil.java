package com.zkteam.discover.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本工具类
 */
public class TextUtil {
	
	public static final String TEXT_EMPTY = "";
	public static final String TEXT_NULL = "null";
	public static final String TEXT_ZERO = "0";

	/**
	 * 将字符序列转成字符串对象
	 * @param charSequence
	 * @return
	 */
	public static String toString(CharSequence charSequence){

		return charSequence == null ? TextUtil.TEXT_EMPTY : charSequence.toString();
	}

	/**
	 * 过滤空串
	 * @param str
	 * @return
	 */
	public static String filterNull(String str){
		
		return str == null ? TEXT_EMPTY : str;
	}

	/**
	 * 将空串过滤为0
	 * @param str
	 * @return
	 */
	public static String filterEmptyZero(String str){

		return filterEmpty(str, TEXT_ZERO);
	}

	/**
	 * 如果str为空或空串，则返回默认值
	 * @param str
	 * @param defStr
	 * @return
	 */
	public static String filterEmpty(String str, String defStr){

		return isEmpty(str) ? defStr : str;
	}

	/**
	 * 过滤trim后的字符串
	 * @param str
	 * @return
	 */
	public static String filterTrim(String str){

		return str == null ? TEXT_EMPTY : str.trim();
	}

	/**
	 * 过滤trim后的字符串，如果串为空，则返回默认值
	 * @param str
	 * @param defStr
	 * @return
	 */
	public static String filterTrim(String str, String defStr){

		return isEmptyTrim(str) ? defStr : str;
	}

	/**
	 * 比较两个字符串
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsText(String str1, String str2){

		if(str1 == null && str2 == null){

			return true;
		}else if(str1 != null && str2 != null){

			return str1.equals(str2);
		}else{

			return false;
		}
	}

	/**
	 * 判断文本是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str) {

		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 返回字符序列长度
	 * @param chars
	 * @return
	 */
	public static int size(CharSequence chars){

		return chars == null ? 0 : chars.length();
	}

	/**
	 * 返回字符串trim后的size
	 * @param str
	 * @return
	 */
	public static int trimSize(String str){

		return str == null ? 0 : str.trim().length();
	}

	/**
	 * 判断是否为0
	 * @param str
	 * @return
	 */
	public static boolean isZero(String str){

		return TEXT_ZERO.equals(str);
	}

	/**
	 * 判断trim后的文本是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmptyTrim(String str) {
		
		if (str == null || str.trim().length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断str是否是数字组成的
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {

		if(TextUtil.isEmpty(str))
			return false;

		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断text是否是小数
	 * @param text
	 * @return
	 */
	public static boolean isDecimal(String text){

		if(TextUtil.isEmpty(text))
			return false;

		Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");
		return pattern.matcher(text).matches();
	}

	/**
	 * 从文本中截取小数,如果有多个匹配，则取第一个，无匹配返回空串
	 * @param text
	 * @return
	 */
	public static String parseDecimalText(String text){

		if(TextUtil.isEmpty(text))
			return TextUtil.TEXT_EMPTY;

		Pattern pattern = Pattern.compile("[0-9]+(\\.[0-9]+)?");
		Matcher matcher = pattern.matcher(text);
		while(matcher.find()){

			return matcher.group();
		}

		return TextUtil.TEXT_EMPTY;
	}
	
	/**
	 * 验证手机号
	 * 判断规则有过时的危险
	 * @param str
	 * @return
	 */
	public static boolean isMobile(CharSequence str) {
		
		if(isEmpty(str))
			return false;
		
		Pattern p = Pattern.compile("^[1][3-9][0-9]{9}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static String filterTklTitle(String searchKey) {

		String word = searchKey;
		if (TextUtil.isTaobaoTkl(searchKey))
			word = getTaobaoTklTitle(searchKey);

		return word;
	}

	public static String getTaobaoTklTitle(String searchKey) {

		Pattern pattern = Pattern.compile("(?<=【).*?(?=】)");
		Matcher matcher = pattern.matcher(searchKey);

		if (matcher.find())
			return matcher.group();
		else
			return searchKey;
	}

	/**
	 * 是否为淘口令
	 *
	 * @param str
	 * @return
	 */
	public static boolean isTaobaoTkl(CharSequence str) {

		if(isEmpty(str))
			return false;

		Pattern p = Pattern.compile("[《,￥][0-9,a-z,A-Z]{5,20}[《,￥]");
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 检查邮箱格式是否正确
	 * @param email
	 * @return
	 */
	public static boolean checkMail(String email) {

		if(TextUtil.isEmpty(email))
			return false;

		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.find();
	}

	/**
	 * 按照新浪微博的规则统计字符数
	 * 好像不是特别准确
	 * @param c
	 * @return
	 */
	public static int calculateWeiboLength(CharSequence c) {

		double len = 0;
		for (int i = 0; i < c.length(); i++) {

			int temp = (int) c.charAt(i);
			if (temp > 0 && temp < 127) {

				len += 0.5;
			} else {

				len++;
			}
		}
		return (int) Math.round(len);
	}

	/**
	 * 判断文本是否包含中文
	 * @param text
	 * @return
	 */
	public static boolean isContainsChinese(CharSequence text){

		if(isEmpty(text))
			return false;

		for (int i=0; i<text.length(); i++) {

			if(isChinese(text.charAt(i)))
				return true;
		}

		return false;
	}

	public static int containsChineseCount(CharSequence text){

		int count = 0;

		if (isEmpty(text))
			return 0;

		for (int i = 0; i < text.length(); i++) {

			if (isChinese(text.charAt(i)))
				count ++;
		}

		return count;
	}

	/**
	 * 判断指定字符是否是中文字符
	 * 根据Unicode编码的判断中文汉字和符号
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
	}

	/**
	 * 根据指定的字符，将数组中的元素进行拼接
	 * @param intArray
	 * @param split
	 * @return
	 */
	public static String splitJoint(int[] intArray, char split){

		if(intArray == null || intArray.length == 0)
			return TextUtil.TEXT_EMPTY;

		StringBuilder sb = new StringBuilder();
		for(int i=0; i<intArray.length; i++){

			if(i > 0)
				sb.append(split);

			sb.append(intArray[i]);
		}

		return sb.toString();
	}

	/**
	 * 过滤字符中的emoji表情，不能保证全部过滤
	 * @param text
	 * @return
	 */
	public static String filterEmoji(String text){

		if(isEmpty(text))
			return text;

		try{

			return text.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]", TEXT_EMPTY);

		}catch(Throwable t){

		}

		return text;
	}

	/**
	 * 手机号码加*
	 * @param phoneNum
	 * @return
	 */
	public static String getSecretPhoneNum(String phoneNum){

		phoneNum = TextUtil.filterNull(phoneNum);
		return phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

	/**
	 * 自动转换字符空串
	 * 且保证 start end 在字符串指定范围内
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String subStringFilter(String str, int start, int end){

		str = filterNull(str);

		if(start < 0)
			start = 0;

		if(start > str.length() - 1)
			start = str.length() - 1;

		if(end < 0)
			end = 0;

		if(end > str.length())
			end = str.length();

		return str.substring(start, end);

	}
}
