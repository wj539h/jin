package com.jin.callback.example;
/**   
 * @project: SpringTemplateCallbackTemplate
 * @description: 
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@99bill.com
 */
public class TemplatePatternImpl2 extends TemplatePattern {

	@Override
	protected void method3() {
		System.out.println("method3()������TemplatePatternImpl2��ʵ���ˣ���");

	}

	/* (non-Javadoc)
	 * @see com.jin.callback.example.TemplatePattern#method2()
	 */
	@Override
	public void method2() {
		System.out.println("����TemplatePatternImpl2�����˸����method2()��������");
	}
	
}


