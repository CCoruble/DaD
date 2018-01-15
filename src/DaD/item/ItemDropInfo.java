package DaD.item;

import DaD.commons.MultiValueSet;

/**
 * Class used when creating monster to set their
 * possible item drop.
 * <p>
 *     This will be used when a monster dies. We will browse each
 *     ItemDropInfo to check if the killer earned an item.
 *     Here each itemDropInfo refers to one item template.
 *     The item can be dropped {@link #_maxDrop} times maximum.
 *     Each item have a drop rate of {@link #_dropRate} percent.
 * </p>
 */
public class ItemDropInfo {
    /**
     * Template of the possibly dropped item.
     */
    private final ItemTemplate _itemTemplate;
    /**
     * Max time that the item can be dropped.
     */
    private final int _maxDrop;
    /**
     *
     */
    private final double _dropRate;

    /**
     * Public constructor of class.
     * @param template The template of the item
     * @param maxDrop The max drop for the item
     * @param dropRate The drop rate for the item
     */
    public ItemDropInfo(ItemTemplate template, int maxDrop, double dropRate){
        _itemTemplate = template;
        _maxDrop = maxDrop;
        _dropRate = dropRate;
    }

    /**
     * Public constructor of class.
     * @param templateId The id of the template
     * @param maxDrop The max drop for the item
     * @param dropRate The drop rate for the item
     */
    public ItemDropInfo(int templateId, int maxDrop, double dropRate){
        _itemTemplate = ItemHolder.getInstance().getItem(templateId);
        _maxDrop = maxDrop;
        _dropRate = dropRate;
    }

    /**
     * Public constructor of class.
     * @param allInfo MultiValueSet containing all information.
     */
    public ItemDropInfo(MultiValueSet allInfo){
        _itemTemplate = allInfo.getItemTemplate("itemId");
        _maxDrop = allInfo.getInteger("maxDrop");
        _dropRate = allInfo.getDouble("dropRate");
    }

    /**
     * Return the template.
     * @return ItemTemplate
     */
    public ItemTemplate getTemplate() {
        return _itemTemplate;
    }

    /**
     * Return the maxDrop.
     * @return int
     */
    public int getMaxDrop() {
        return _maxDrop;
    }

    /**
     * Return the dropRate.
     * @return double
     */
    public double getDropRate() {
        return _dropRate;
    }

    /**
     * Display information of instance.
     */
    public void displayItemDropInfo(){
        System.out.println("itemTemplate: " + _itemTemplate.getName());
        System.out.println("maxDrop: " + _maxDrop);
        System.out.println(": " + _dropRate);
    }
}
