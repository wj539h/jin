package com.jin.dp.decorator;

public class Buyer {
    public static void main(String[] args) {
	Ham ham = new GeneralHam();
	System.out.println("第1个人 普通的价格: "+ham.cost());
	
	HamDecorator deco2 = new BeefHamDecorator(new GeneralHam());
	System.out.println("第2个人 牛肉的价格 : "+deco2.cost());
	
	HamDecorator deco3 = new EggHamDecorator(new GeneralHam());
	System.out.println("第3个人 鸡蛋的价格: "+deco3.cost());
	
	HamDecorator deco4 = new FillitHamDecorator(new EggHamDecorator(new GeneralHam()));
	System.out.println("第4个人 肉片和鸡蛋的价格: "+deco4.cost());
	
	HamDecorator deco5 = new BeefHamDecorator(new MeatFlossHamDecorator(new FillitHamDecorator(new EggHamDecorator(new GeneralHam()))));
	System.out.println("第5个人 全套（牛肉，肉松，肉片，鸡蛋）的价格: "+deco5.cost());
    }
}
