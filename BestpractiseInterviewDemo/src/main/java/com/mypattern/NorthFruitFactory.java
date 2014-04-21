package com.mypattern;

public class NorthFruitFactory implements FactoryFruit {

	public Fruit getApple() {
		// TODO Auto-generated method stub
		return new NorthApple();
	}

	public Fruit getBanana() {
		// TODO Auto-generated method stub
		return new NorthBanana();
	}

}
