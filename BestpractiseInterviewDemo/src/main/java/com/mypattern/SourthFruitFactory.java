package com.mypattern;

public class SourthFruitFactory implements FactoryFruit {

	public Fruit getApple() {
		// TODO Auto-generated method stub
		return new SouthApple();
	}

	public Fruit getBanana() {
		// TODO Auto-generated method stub
		return new SourthBanana();
	}

}
