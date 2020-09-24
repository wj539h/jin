package com.jin.callback.example;
/**   
 * @project: SpringTemplateCallbackTemplate
 * @description: 
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@99bill.com
 */
public class Test {

	public static void main(String[] args) {
		
		TemplatePattern t1 = new TemplatePatternImpl();
		TemplatePattern t2 = new TemplatePatternImpl2();
		
		System.out.println("-------TemplatePatternImp-----------");
		t1.templateMethod();
		System.out.println("-------TemplatePatternImp2-----------");
		t2.templateMethod();
	}
}


