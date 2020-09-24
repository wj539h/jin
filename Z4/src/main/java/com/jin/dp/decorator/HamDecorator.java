package com.jin.dp.decorator;

public abstract class HamDecorator extends Ham {
    protected Ham ham;
    public HamDecorator(Ham ham) {
	this.ham = ham;
    }
}

class MeatFlossHamDecorator extends HamDecorator {
    public MeatFlossHamDecorator(Ham ham) {
	super(ham);
    }
    @Override
    public int cost() {
	return ham.cost()+3;
    }
}

class FillitHamDecorator extends HamDecorator {
    public FillitHamDecorator(Ham ham) {
	super(ham);
    }
    @Override
    public int cost() {
	return super.ham.cost() + 5;
    }
}

class BeefHamDecorator extends HamDecorator {
    public BeefHamDecorator(Ham ham) {
	super(ham);
    }
    @Override
    public int cost() {
	return ham.cost()+12;
    }
}

class EggHamDecorator extends HamDecorator {
    public EggHamDecorator(Ham ham) {
	super(ham);
    }
    public int cost() {
	return super.ham.cost() + 2;
    }
}

