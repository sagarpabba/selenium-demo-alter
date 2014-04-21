package com.mypattern;

public class Main {

	
	public static void main(String[] args) {
		
		
		FactoryFruit ff = new NorthFruitFactory(); 
        Fruit apple = ff.getApple(); 
        apple.get(); 
 
        Fruit banana = ff.getBanana(); 
        banana.get(); 
 
        FactoryFruit ff2= new SourthFruitFactory(); 
        Fruit apple2 = ff2.getApple(); 
        apple2.get(); 
 
        Fruit banana2 = ff2.getBanana(); 
        banana2.get(); 
 
        FactoryFruit ff3 = new RoomFruitFactory(); 
        Fruit apple3 = ff3.getApple(); 
        apple3.get(); 
 
        Fruit banana3 = ff3.getBanana(); 
        banana3.get(); 
	}
}
