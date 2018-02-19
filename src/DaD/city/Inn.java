package DaD.city;

import DaD.Commons.Utils.InputFunction;
import DaD.creature.Hero;

/**
 * Created by Clovis on 09/02/2017.
 * Singleton representing the inn zone.
 * This is where hero can heal wounds and skip a day.
 */
public class Inn
{
	/**
	 * Private instance of class.
	 */
	private static final Inn _instance = new Inn();

	/**
	 * Private constructor.
	 */
	private Inn(){}

	/**
	 * Accessor for private instance.
	 * @return Inn
	 */
	public static Inn getInstance(){
		return _instance;
	}

	/**
	 * Main menu of this class, this is
	 * where the hero can choose
	 * which room he wants.
	 * @param hero Hero entering Inn
	 */
	public void innMenu(Hero hero) {
		System.out.println("Bienvenue à l'auberge jeune aventurier !");
		System.out.println("Tu as actuellement " + (int)hero.getPercentHp() + "% de tes points de vie et " + hero.getAmountOfGold() + " golds.");


		System.out.println("Dans quelle chambre veux-tu dormir ?");
		System.out.println("1 : Chambre Emaüs (30% de ta vie) pour " + calcInnPrice(hero.getLevel(),1) + " gold");
		System.out.println("2 : Chambre Ikea (50% de ta vie) pour " + calcInnPrice(hero.getLevel(),2) + " gold");
		System.out.println("3 : Chambre de luxe (100% de ta vie) pour " + calcInnPrice(hero.getLevel(),3) + " gold");
		System.out.println("Autre : quitter");
		int choice = InputFunction.getIntInput();

		if(choice < 1 || choice > 3) {
			// Player wants to leave
			return;
		}
		// Player did a valid choice
		int price = calcInnPrice(hero.getLevel(),choice);
		if(hero.canAfford(price)){
			// Hero has enough money to pay
			hero.decreaseGold(price);
			System.out.println("Bonne nuit !");
			int hpGained = rest(hero,choice);
			System.out.println("+" + hpGained + "Hp.");
		} else {
			// Hero cannot pay
			System.out.println("Désolé tu n'a pas assez d'argent, une prochaine fois !");
		}
	}

	/**
	 * Calculate the price of a night depending
	 * on hero level and quality of room.
	 * @param level hero level
	 * @param quality quality of room
	 * @return int
	 */
	private int calcInnPrice(int level, int quality){
		return (int)(20*((9+level)/10)*(quality/1.5));
	}

	/**
	 * Give HP to hero depending on
	 * the room quality he choosed.
	 * Return amount of hp gain.
	 * @param hero Hero resting
	 * @param quality Quality of selected room
	 * @return int
	 */
	private int rest(Hero hero, int quality){
		double hpBeforeRest = hero.getHp().getValue();
		switch(quality){
			case 1 : // Low quality +30% Hp
				hero.addHp(hero.getHpMax().getValue()*0.3); // Get +30% hp (with a max of 100%)
				break;
			case 2 : // Medium quality +50% Hp
				hero.addHp(hero.getHpMax().getValue()*0.5); // Get +50% Hp (with a max of 100%)
				break;
			case 3 : // High quality +100% Hp
				hero.addHp(hero.getHpMax().getValue()); // hp = HpMax
				break;
		}
		return (int)(hero.getHp().getValue() - hpBeforeRest);
	}
}
