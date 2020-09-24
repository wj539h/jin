package com.jin.callback.example7;


public class Client {
	public static void main(String[] args) {
		//׼����¼�˵���Ϣ
		LoginModel lm = new LoginModel();
		lm.setLoginId("admin");
		lm.setPwd("workerpwd");
		//׼�����������жϵĶ���
		LoginTemplate lt = new LoginTemplate();
		
		//���е�¼���ԣ��Ȳ�����ͨ��Ա��¼
		boolean flag = lt.login(lm,new LoginCallback(){
			public String encryptPwd(String pwd, LoginTemplate template) {
				//�Լ�����Ҫ��ֱ��ת��ģ���е�Ĭ��ʵ��
				return template.encryptPwd(pwd);
			}
			public LoginModel findLoginUser(String loginId) {
				// ����ʡ�Ծ���Ĵ�������ʾ�⣬����һ����Ĭ�����ݵĶ���
				LoginModel lm = new LoginModel();
				lm.setLoginId(loginId);
				lm.setPwd("testpwd");
				return lm;
			}
			public boolean match(LoginModel lm, LoginModel dbLm,
					LoginTemplate template) {
				//�Լ�����Ҫ���ǣ�ֱ��ת��ģ���е�Ĭ��ʵ��
				return template.match(lm, dbLm);
			}
			
		});
		System.out.println("���Խ�����ͨ��Ա��¼="+flag);

		//���Թ�����Ա��¼
		boolean flag2 = lt.login(lm,new LoginCallback(){
			public String encryptPwd(String pwd, LoginTemplate template) {
				//���Ǹ���ķ������ṩ�����ļ���ʵ��
				//�����������м��ܣ�����ʹ�ã�MD5��3DES�ȵȣ�ʡ����
				System.out.println("ʹ��MD5�����������");
				return pwd;
			}
			public LoginModel findLoginUser(String loginId) {
				// ����ʡ�Ծ���Ĵ�������ʾ�⣬����һ����Ĭ�����ݵĶ���
				LoginModel lm = new LoginModel();
				lm.setLoginId(loginId);
				lm.setPwd("workerpwd");
				return lm;
			}
			public boolean match(LoginModel lm, LoginModel dbLm,
					LoginTemplate template) {
				//�Լ�����Ҫ���ǣ�ֱ��ת��ģ���е�Ĭ��ʵ��
				return template.match(lm, dbLm);
			}
			
		});		
		System.out.println("���Ե�¼����ƽ̨="+flag2);
	}
}
