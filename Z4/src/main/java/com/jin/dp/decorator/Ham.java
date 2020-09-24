package com.jin.dp.decorator;

public abstract class Ham {
    public abstract int cost(); 
}

class GeneralHam extends Ham {
    @Override
    public int cost() {
	return 10;
    }
}