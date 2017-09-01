package com.gsww.uids.gateway.util;

public class Contants {
	/**
	 * Crm鎺ュ彛淇℃伅缁撴灉杩斿洖鐮�
	 */
	public static final String SUB_CUST_SUCCESS="00000";//鎴愬姛
	public static final String DATE_BASE_ERROR = "00200";//鏁版嵁搴撳紓甯�
	public static final String CUST_BIZ_IS_EXIST = "00400";//棰嗚埅瀹㈡埛璁¤垂鏍囪瘑宸插瓨鍦�
	public static final String CUST_IS_EXIST = "00401";//棰嗚埅瀹㈡埛甯愬彿宸插瓨鍦�
	public static final String IMPORTANT_NULL = "00402";//鍏抽敭鏁版嵁椤逛负绌�
	public static final String DATA_ERROR = "00403";//鏁版嵁鏍煎紡涓嶆纭�
	public static final String NETERROR = "00500";//缃戠粶寮傚父
	public static final String SYSERROR = "00600";//绯荤粺寮傚父
	public static final String XMLERROR = "00601";//鎶ユ枃寮傚父
	/**
	 * 瀹㈡埛璧勬枡鐘舵�佺爜
	 */
	public static final String CUST_NORMAL = "1"; //瀹㈡埛,鐢ㄦ埛姝ｅ父鐘舵��
	public static final String CUST_INVALID = "0";//瀹㈡埛,鐢ㄦ埛娉ㄩ攢鐘舵��
	public static final String CUST_PAUSE = "2";  //瀹㈡埛,鐢ㄦ埛鏆傚仠鐘舵��
	/**
	 * 鐢ㄦ埛瑙掕壊
	 */
	public static final String CUST_USER_COMMON = "1"; //鐢ㄦ埛瑙掕壊涓烘櫘閫氱敤鎴�
	public static final String CUST_USER_ADMIN = "2"; //鐢ㄦ埛瑙掕壊涓虹鐞嗗憳
	public static final String CUST_USER_PSW = "123456"; //鐢ㄦ埛鍒濆瀵嗙爜
	/**
	 * 鐢ㄦ埛鏉ユ簮
	 */
	public static final String CUST_USER_ORIGIN_ISMP = "1";//鐪侀鑸钩鍙�
	public static final String CUST_USER_ORIGIN_CRM = "2";//CRM鍙楃悊锛堟墜鏈虹敤鎴凤級
	public static final String CUST_USER_ORIGIN_GROUP="3";//闆嗗洟棰嗚埅骞冲彴
	public static final String AUTH_TYPE_PC_USER="1";
	public static final String AUTH_TYPE_CLIENT_USER="2";
	
	/**涓氬姟鎿嶄綔绫诲瀷锛氳璐�  */
	public static final String ACTION_TYPE_SUB="1";
	/**涓氬姟鎿嶄綔绫诲瀷锛氬彉鏇� */
	public static final String ACTION_TYPE_CHANGE="2";
	/**涓氬姟鎿嶄綔绫诲瀷锛氶��璁� */
	public static final String ACTION_TYPE_UNSUB="3";
	/**涓氬姟鎿嶄綔绫诲瀷锛氬仠鏈�  */
	public static final String ACTION_TYPE_SUSPEND="4";
	/**涓氬姟鎿嶄綔绫诲瀷锛氬鏈� */
	public static final String ACTION_TYPE_RECOVER="5";
		
	/**涓氬姟宸ュ崟绫诲瀷锛氫紒涓氳璐�*/
	public static final String ORDER_TYPE_ENTERPRISE="1";
	/**涓氬姟宸ュ崟绫诲瀷锛氬弻璁㈣喘涓嬩紒涓氱敤鎴疯璐� */
	public static final String ORDER_TYPE_USER="2";
	/**涓氬姟宸ュ崟绫诲瀷锛氫釜浜哄鎴疯璐�*/
	public static final String ORDER_TYPE_INDIVIDUAL="3";
	/**
	 * 鍙楃悊宸ュ崟閿欒绫诲瀷锛欯棰嗚埅骞冲彴 
	 */
	public static final String ERROR_TYPE_ISMP ="1";  
	/**
	 * 鍙楃悊宸ュ崟閿欒绫诲瀷锛欯浜у搧骞冲彴
	 */
	public static final String ERROR_TYPE_GATEWAY="2";
	/**
	 * 鍙楃悊宸ュ崟閿欒绫诲瀷锛欯闆嗗洟棰嗚埅
	 */
	public static final String ERROR_TYPE_GROUP="3";
	
	/**
	 * 鍙楃悊宸ュ崟绔ｅ伐鐘舵�侊細鏈鐞�
	 */
	public static final String ORDER_DEAL_NOT="0";
	/**
	 * 鍙楃悊宸ュ崟绔ｅ伐鐘舵�侊細鏈繑鍗�
	 */
	public static final String ORDER_RETURN_NOT="1";
	/**
	 * 鍙楃悊宸ュ崟绔ｅ伐鐘舵�侊細宸茬宸�
	 */
	public static final String ORDER_DEAL_END="2";
	/**
	 * 鍙楃悊宸ュ崟绔ｅ伐鐘舵�侊細澶勭悊涓�
	 */
	public static final String ORDER_DEAL_ING="3";
	/**
	 * 鍙楃悊宸ュ崟澶勭悊缁撴灉锛氭垚鍔�
	 */
	public static final String ORDER_DEAL_SUCCESS="1";
	/**
	 * 鍙楃悊宸ュ崟澶勭悊缁撴灉锛氬け璐�
	 */
	public static final String ORDER_DEAL_ERROR="0";
	/**UDB鎺ュ彛璋冪敤鎿嶄綔绫诲瀷鍚嶇О瀹氫箟  0.瀵嗙爜淇敼锛�1.鐢ㄦ埛寮�鎴凤紝2.鐢ㄦ埛娉ㄩ攢锛�3.鍒悕璁剧疆锛�4.涓浗鐢典俊閫氳璇佺粦瀹氾紝
	 * 5.涓浗鐢典俊閫氳璇佽В缁戯紝6.寮傜綉鍙风粦瀹氾紝7.寮傜綉鍙疯В缁戯紝8.閭鍦板潃缁戝畾锛�9.閭鍦板潃瑙ｇ粦*/
	public static final String UDB_PASSWORD_UPD="0";  //瀵嗙爜淇敼
	public static final String UDB_USER_OPEN="1";     //棰嗚埅鐢ㄦ埛寮�鎴�
	public static final String UDB_USER_CANCLE="2";   //棰嗚埅鐢ㄦ埛娉ㄩ攢
	public static final String UDB_ALIAS_SET="3";     //鍒悕璁剧疆
	public static final String UDB_COMMONACCOUNT_BINDING="4";//涓浗鐢典俊閫氳璇佺粦瀹�
	public static final String UDB_COMMONACCOUNT_CANCLE="5"; //涓浗鐢典俊閫氳璇佽В缁�
	public static final String UDB_NETACCOUNR_BINDING="6";	 //寮傜綉鍙风粦瀹�
	public static final String UDB_NETACCOUNR_CANCLE="7";	 //寮傜綉鍙疯В缁�
	public static final String UDB_EMAILACCOUNR_BINDING="8"; //閭鍦板潃缁戝畾
	public static final String UDB_EMAILACCOUNR_CANCLE="9";	 //閭鍦板潃瑙ｇ粦
	/**UDB鎺ュ彛绫诲瀷鍚嶇О瀹氫箟*/
	public static final String UDB_BNETUSERINFO_SYNC="BNETUserInfoSync";   			 	//棰嗚埅瀹㈡埛寮�鎴�/娉ㄩ攢鎺ュ彛
	public static final String UDB_BNETACCOUNT_LOGIN="BNETAccountLogin"; 			 	//鍚庡彴鐧诲綍璁よ瘉鎺ュ彛
	public static final String UDB_BNETACCOUNT_PWD_MODIFY="BNETAccountPWDModify";    	//瀵嗙爜璁剧疆鎺ュ彛
	public static final String UDB_BNETACCOUNT_ALIAS_CONFIG="BNETAccountAliasConfig";	//鍒悕璁剧疆鎺ュ彛
	public static final String UDB_BNETACCOUNT_INFO_QUERY="BNETAccountInfoQuery";	    //淇℃伅鏌ヨ鎺ュ彛
	public static final String UDB_BNETACCOUNT_ACCOUNR_BINDING="BNETAccountBinding";	//缁戝畾/瑙ｇ粦璁剧疆鎺ュ彛
	public static final String UDB_BNETUSER_IDENTIFY_CODE_SMS="BNETUserIDentifyCodeSMS";//寮傜綉鎵嬫満楠岃瘉鐮佷笅鍙戞帴鍙�
	public static final String UDB_BNETUSER_LOGIN_STATUS="BNETUserLoginStatus";			//鍦ㄧ嚎鐘舵�侀�氱煡鎺ュ彛
	public static final String UDB_BNETUSER_INFO_CHECK="BNETUserInfoCheck";				//涓存椂韬唤淇℃伅鏌ヨ鎺ュ彛
	public static final String UDB_BNETPASSPORT_LOGIN="BNETPassportLogin";   		 	//閲嶅畾鍚戠櫥褰曡璇佹帴鍙�
	public static final String UDB_BNETPASSPORT_LOGOUT="BNETPassportLogout";         	//閲嶅畾鍚戜护鐗屾敞閿�鎺ュ彛
	
	/**宸ュ崟鎻愰啋鐭俊妯℃澘涓殑涓氬姟绫诲瀷锛氬紑閫氬伐鍗�  */
	public static final String PRODUCT_WORK_ORDER_SMS_REMIND="2";
	/**宸ュ崟鎻愰啋鐭俊妯℃澘涓殑涓氬姟绫诲瀷锛氬彈鐞嗗伐鍗�  */
	public static final String SUB_WORK_ORDER_SMS_REMIND="1";
	/**
	 * 鐭俊閫氱煡涓氬姟鎿嶄綔绫诲瀷 1:瀹㈡埛璁㈣喘;2:瀹㈡埛閫�璁�;3:瀹㈡埛灞炴�у彉鏇�;4:鐢ㄦ埛璁㈣喘;5:鐢ㄦ埛閫�璁�;6:鐢ㄦ埛鍙樻洿
	 */
	public static final String SMS_CUST_PURCHASE="1";
	public static final String SMS_CUST_CANCEL="2";
	public static final String SMS_CUST_MODIFY="3";
	public static final String SMS_USER_PURCHASE="4";
	public static final String SMS_USER_CANCEL="5";
	public static final String SMS_USER_MODIFY="6";
}
