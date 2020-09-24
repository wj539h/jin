package com.jin.callback.example7;
/**
 * ��¼���Ƶ�ģ�巽����Ҫ�Ļص��ӿڣ�
 * ��Ҫ�����ܵİ�������Ҫ�Ľӿڷ������������,
 * ����˵�����п��Ա���չ�ķ�������Ҫ���������
 */
public interface LoginCallback {
	/**
	 * ���ݵ�¼��������Һͻ�ȡ�洢����Ӧ������
	 * @param loginId ��¼���
	 * @return ��¼����ڴ洢�����Ӧ������
	 */
	public LoginModel findLoginUser(String loginId);
	/**
	 * ���������ݽ��м���
	 * @param pwd ��������
	 * @param template LoginTemplate����ͨ������������
	 * 				LoginTemplate�ж���Ĺ���������ȱʡʵ��
	 * @return ���ܺ����������
	 */
	public String encryptPwd(String pwd,LoginTemplate template);
	/**
	 * �ж��û���д�ĵ�¼���ݺʹ洢�ж�Ӧ�������Ƿ�ƥ�����
	 * @param lm �û���д�ĵ�¼����
	 * @param dbLm �ڴ洢�ж�Ӧ������
	 * @param template LoginTemplate����ͨ������������
	 * 				LoginTemplate�ж���Ĺ���������ȱʡʵ��
	 * @return true��ʾƥ��ɹ���false��ʾƥ��ʧ��
	 */
	public boolean match(LoginModel lm,LoginModel dbLm,LoginTemplate template);
}
