package com.jak.pattern.template.example;
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
		System.out.println("method3()在子类TemplatePatternImpl2中实现了！！");

	}

	/* (non-Javadoc)
	 * @see com.jak.pattern.template.example.TemplatePattern#method2()
	 */
	@Override
	public void method2() {
		System.out.println("子类TemplatePatternImpl2覆盖了父类的method2()方法！！");
	}
	
}


