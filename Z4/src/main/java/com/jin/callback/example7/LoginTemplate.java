package com.jin.callback.example7;
/**
 *	��¼���Ƶ�ģ��
 */
public class LoginTemplate {
	/**
	 * �жϵ�¼�����Ƿ���ȷ��Ҳ�����Ƿ��ܵ�¼�ɹ�
	 * @param lm ��װ��¼���ݵ�Model
	 * @param callback LoginCallback����
	 * @return true��ʾ��¼�ɹ���false��ʾ��¼ʧ��
	 */
	public final boolean login(LoginModel lm,LoginCallback callback){
		//1�����ݵ�¼��Ա�ı��ȥ��ȡ��Ӧ������
		LoginModel dbLm = callback.findLoginUser(lm.getLoginId());
		if(dbLm!=null){
			//2����������м���
			String encryptPwd = callback.encryptPwd(lm.getPwd(),this);
			//�Ѽ��ܺ���������ûص���¼����ģ������
			lm.setPwd(encryptPwd);
			//3���ж��Ƿ�ƥ��
			return callback.match(lm, dbLm,this);
		}
		return false;
	}
	/**
	 * ���������ݽ��м���
	 * @param pwd ��������
	 * @return ���ܺ����������
	 */
	public String encryptPwd(String pwd){
		return pwd;
	}
	/**
	 * �ж��û���д�ĵ�¼���ݺʹ洢�ж�Ӧ�������Ƿ�ƥ�����
	 * @param lm �û���д�ĵ�¼����
	 * @param dbLm �ڴ洢�ж�Ӧ������
	 * @return true��ʾƥ��ɹ���false��ʾƥ��ʧ��
	 */
	public boolean match(LoginModel lm,LoginModel dbLm){
		if(lm.getLoginId().equals(dbLm.getLoginId()) 
				&& lm.getPwd().equals(dbLm.getPwd())){
			return true;
		}
		return false;
	}
}
