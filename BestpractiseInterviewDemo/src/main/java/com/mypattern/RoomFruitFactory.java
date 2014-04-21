package com.mypattern;

public class RoomFruitFactory implements FactoryFruit {

	public Fruit getApple() {
		// TODO Auto-generated method stub
		return new RoomApple();
	}

	public Fruit getBanana() {
		// TODO Auto-generated method stub
		return new RoomBanana();
	}

}
