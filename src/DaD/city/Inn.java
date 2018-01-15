package DaD.city;

import DaD.creature.Hero;

import java.util.Scanner;

/**
 * Created by Clovis on 09/02/2017.
 */
public class Inn
{
	private static final Inn _instance = new Inn();
	private Inn(){}
	public static Inn getInstance(){
		return _instance;
	}
	public void innMenu()
	{
		Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player
		System.out.println("Bienvenue à l'auberge jeune aventurier !");
		System.out.println("Tu as actuellement " + Hero.getInstance().getPercentHp() + "% de tes points de vie.");
		int innChoice = 0;
		while (innChoice < 1 || innChoice > 4)
		{
			System.out.println("Dans quelle chambre veux-tu dormir ?");
			System.out.println("1 : Chambre Emaüs (30% de ta vie) pour " + calcInnPrice(Hero.getInstance().getLevel(),1) + " gold");
			System.out.println("2 : Chambre Ikea (50% de ta vie) pour " + calcInnPrice(Hero.getInstance().getLevel(),2) + " gold");
			System.out.println("3 : Chambre de luxe (100% de ta vie) pour " + calcInnPrice(Hero.getInstance().getLevel(),3) + " gold");
			System.out.println("4 : Annuler");
			innChoice = scanner.nextInt();
		}
		switch(innChoice)
		{
			case 1 : // Low quality +30% Hp
				Hero.getInstance().decreaseGold(calcInnPrice(Hero.getInstance().getLevel(),1)); // Pay the price
				Hero.getInstance().setHpValue((int)(Hero.getInstance().getHp().getValue() + Hero.getInstance().getHpMax().getValue()*0.3)); // Get +30% hp (with a max of 100%)
				break;
			case 2 : // Medium quality +50% Hp
				Hero.getInstance().decreaseGold(calcInnPrice(Hero.getInstance().getLevel(),1)); // Pay the price
				Hero.getInstance().setHpValue((int)(Hero.getInstance().getHp().getValue() + Hero.getInstance().getHpMax().getValue()*0.5)); // Get +50% Hp (with a max of 100%)
				break;
			case 3 : // High quality +100% Hp
				Hero.getInstance().decreaseGold(calcInnPrice(Hero.getInstance().getLevel(),1)); //  Pay the price
				Hero.getInstance().setHpValue(Hero.getInstance().getHpMax().getValue()); // hp = HpMax
				break;
		}
	}

	private int calcInnPrice(int level, int quality){
		return (int)(20*((9+level)/10)*(quality/1.5));
	}
}
