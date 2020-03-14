package com.qf.beat.team.smsservice.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
  * @ClassName: ApiDemo
  * @Description: TODO
  *
  */
public class ApiDemo4Java {

	/**
	 * �����ṩ�̿����˺�ʱ�ṩһ�²���:
	 *
	 * �˺š����롢ͨ��key��IP���˿�
	 */
	static String account = "JSM42639";
	static String password = "l43d7myq";
	static String veryCode = "irnsloy2kjhd";
	static String http_url  = "http://112.74.76.186:8030";
	/**
	 * Ĭ���ַ����뼯
	 */
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * ��ѯ�˺����
	 * @return �˺�������10Ϊ��������
	 * String xml�ַ�������ʽ��ο��ĵ�˵��
	 */
	public static String getBalance(){
		String balanceUrl = http_url + "/service/httpService/httpInterface.do?method=getAmount";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username",account);
		params.put("password",password);
		params.put("veryCode",veryCode);
		String result = sendHttpPost(balanceUrl, params);
		return result;
	}
	/**
	 * ������ͨ����  ��ͨ���ŷ�����Ҫ�˹����
	 * �����ַ��
	 *   UTF-8���룺/service/httpService/httpInterface.do?method=sendUtf8Msg
	 *   GBK���룺/service/httpService/httpInterface.do?method=sendGbkMsg
	 * @param mobile �ֻ�����, ���������Ӣ�Ķ��Ÿ���,���֧��100������
	 * @param content ��������
	 * @return
	 * String xml�ַ�������ʽ��ο��ĵ�˵��
	 */
	public static String sendSms(String mobile,String content){
		String sendSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendMsg";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		params.put("mobile", mobile);
		params.put("content", content);
		params.put("msgtype", "1");
		params.put("code", "utf-8");
		String result = sendHttpPost(sendSmsUrl, params);
		return result;
	}

	/**
	 * ģ�����,�����˹���ˣ�ֱ�ӷ���
	 *   (����ģ��Ĵ����ο��ͻ��˲����ֲ�)
	 *   ģ�棺@1@��Ա����̬��֤��@2@(���������Ч)����ע�Ᵽ�ܣ�����֤���֪���ˡ�
	 *   ����ֵ:@1@=ĳĳ,@2@=4293
	 *   �ֻ��������ݣ�������ǩ����ĳĳ��Ա����̬��֤��4293(���������Ч)����ע�Ᵽ�ܣ�����֤���֪���ˡ�
	 *
	 *   �����ַ��
	 *   UTF-8���룺/service/httpService/httpInterface.do?method=sendUtf8Msg
	 *   GBK���룺/service/httpService/httpInterface.do?method=sendGbkMsg
	 *   ע�⣺
	 * 	1.����ģ����ű���ֵ���ܰ���Ӣ�Ķ��ź͵Ⱥţ�, =��
	 *  2.�����ַ�����ת��:
	 *  	+   %2b
	 *  	�ո�    %20
	 * 		&   %26
	 * 		%	%25
	 * @param mobile �ֻ�����
	 * @param tplId ģ���ţ���Ӧ�ͻ���ģ���� JSM42639-0001
	 * @param content ģ�����ֵ����Ӣ�Ķ��Ÿ������磺@1@=ĳĳ,@2@=4293
	 * @return xml�ַ�������ʽ��ο��ĵ�˵��
	 */
	public static String sendTplSms(String mobile,String tplId,String content){
		String sendTplSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendMsg";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		params.put("mobile", mobile);
		params.put("content", content);	//����ֵ����Ӣ�Ķ��Ÿ���
		params.put("msgtype", "2");		//2-ģ�����
		params.put("tempid", tplId);	//ģ����
		params.put("code", "utf-8");
		String result = sendHttpPost(sendTplSmsUrl, params);
		return result;
	}


	/***
	 * ��ѯ�·����ŵ�״̬����
	 * @return
	 * String  xml�ַ�������ʽ��ο��ĵ�˵��
	 */
	public static String queryReport(){
		String reportUrl = http_url + "/service/httpService/httpInterface.do?method=queryReport";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		String result = sendHttpPost(reportUrl, params);



		return result;
	}
	/**
	 * ��ѯ���лظ�����
	 * @return
	 * String  xml�ַ�������ʽ��ο��ĵ�˵��
	 */
	public static String queryMo(){
		String moUrl = http_url + "/service/httpService/httpInterface.do?method=queryMo";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		String result = sendHttpPost(moUrl, params);
		return result;
	}


	/***
	 *
	 * @param apiUrl �ӿ������ַ
	 * @param paramsMap �����������
	 * @return xml�ַ�������ʽ��ο��ĵ�˵��
	 * String
	 */
	private static String sendHttpPost(String apiUrl, Map<String, String> paramsMap) {
		String responseText = "";
		StringBuilder params = new StringBuilder();
		Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}

		try {
			URL url = new URL(apiUrl);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), CHARSET_UTF8);
			out.write(params.toString()); //post�Ĺؼ����ڣ�
			out.flush();
			out.close();
			//��ȡ��Ӧ����ֵ
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,CHARSET_UTF8));
			String temp = "";
			while (( temp = br.readLine()) != null) {
				responseText += temp;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseText;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//��ѯ�˺����
		//System.out.println(getBalance());

		//������ͨ���ţ��޸Ľ��ն��ŵ��ֻ����뼰��������,���������Ӣ�Ķ��Ÿ��������֧��100������
		//System.out.println(sendSms("159*******1,159*******2", "������֤����8888,��ע�Ᵽ�ܣ�����֤���֪���ˡ�"));

		/**
		 * ����ģ����ţ��޸Ľ��ն��ŵ��ֻ����뼰���õ�ģ����
		 * ע�⣺
		 * 	1.����ģ����ű���ֵ���ܰ���Ӣ�Ķ��ź͵Ⱥţ�, =��
		 *  2.�����ַ�����ת��:
		 *  	+   %2b
		 *  	�ո�    %20
		 * 		&   %26
		 * 		%	%25
		 */
//		System.out.println(sendTplSms("159********","ģ����","@1@=����ֵ1,@2@=����ֵ2"));

		//��ѯ�·����ŵ�״̬����
//		System.out.println(queryReport());

		//��ѯ���лظ�����
		//System.out.println(queryMo());
	}

}
