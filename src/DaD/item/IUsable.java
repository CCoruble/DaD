package DaD.item;

import DaD.creature.Creature;

public interface IUsable {
    public boolean isUsable();
    public void use(Creature user);
}
