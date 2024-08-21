package bolo.sellmethiskatana.patches;

import bolo.sellmethiskatana.SellMeThisKatanaMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.TravelingMerchantMob;
import necesse.level.maps.levelData.villageShops.ShopItem;
import necesse.level.maps.levelData.villageShops.VillageShopsData;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.function.Consumer;

import static necesse.entity.mobs.friendly.human.humanShop.HumanShop.conditionSection;

@ModMethodPatch(target = TravelingMerchantMob.class, name = "getShopItems", arguments = {VillageShopsData.class, ServerClient.class})
public class TravellingMerchantPatch {

    @Advice.OnMethodExit()
    static void onExit(@Advice.This TravelingMerchantMob merchant, @Advice.Argument(1) ServerClient client, @Advice.Return(readOnly = false) ArrayList<ShopItem> list) {

//        int chance = SellMeThisKatanaMod.config.getChance();
        int chance = 4;
        float price = SellMeThisKatanaMod.config.getPrice();
        int priceMin = (int) (price * 0.9f);
        int priceMax = (int) (price * 1.1f);

        GameRandom random = new GameRandom(merchant.getShopSeed() + 5L);
//        conditionSection(random, random.getEveryXthChance(chance), (r) -> list.add(ShopItem.item("katana", r.getIntBetween(priceMin, priceMax))));
        boolean condition = random.getEveryXthChance(chance);
        random.nextSeeded();
        if(condition) {
            list.add(ShopItem.item("katana", 5));
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!! LUCKY DAY !!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        else {
            System.out.println("$$$$$$$$$$$$  UNLUCKY, NO KATANA $$$$$$$$$$$$$$$$$$$$$$$$");
        }
//        list.add(ShopItem.item("katana", 5));
    }
}