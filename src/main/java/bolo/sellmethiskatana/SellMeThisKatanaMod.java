package bolo.sellmethiskatana;

import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class SellMeThisKatanaMod {

    public static Config config;

    public void preInit() {
        System.out.println("Sell Me This Katana! Mod...");
        config = new Config("settings.cfg");
        System.out.println(
                "Sell Me This Katana! Mod initialization completed! Selling chance is {" + config.getChance() + "} Selling price is {" + config.getPrice() + "}");
    }
}
