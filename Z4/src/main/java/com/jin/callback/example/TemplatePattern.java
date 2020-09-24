package com.jin.callback.example;
/**   
 * @project: SpringTemplateCallbackTemplate
 * @description: 
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@99bill.com
 */
public abstract class TemplatePattern {

	//ģ�巽��
	public final void templateMethod(){
		
		method1();
		method2();//���ӷ���
		method3();//���󷽷�
	}
	private void method1(){
		System.out.println("����ʵ��ҵ���߼�");
	}
	public void method2(){
		System.out.println("����Ĭ��ʵ�֣�����ɸ���");
	}
	protected abstract void method3();//���ฺ��ʵ��ҵ���߼�
}


