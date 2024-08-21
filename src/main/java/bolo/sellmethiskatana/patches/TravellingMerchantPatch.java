package bolo.sellmethiskatana.patches;

import bolo.sellmethiskatana.SellMeThisKatanaMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.TravelingMerchantMob;
import necesse.level.maps.levelData.settlementData.SettlementLevelData;
import necesse.level.maps.levelData.settlementData.settlementQuestTiers.SettlementQuestTier;
import necesse.level.maps.levelData.villageShops.ShopItem;
import necesse.level.maps.levelData.villageShops.VillageShopsData;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;

@ModMethodPatch(target = TravelingMerchantMob.class, name = "getShopItems", arguments = {VillageShopsData.class, ServerClient.class})
public class TravellingMerchantPatch {

    @Advice.OnMethodExit()
    static void onExit(@Advice.This TravelingMerchantMob merchant, @Advice.Argument(1) ServerClient client, @Advice.Return(readOnly = false) ArrayList<ShopItem> list) {
        int chance = SellMeThisKatanaMod.config.getChance();
        float price = SellMeThisKatanaMod.config.getPrice();
        int priceMin = (int) (price * 0.9f);
        int priceMax = (int) (price * 1.1f);

        GameRandom random = new GameRandom(merchant.getShopSeed() + 5L);

        if(random.nextSeeded().getEveryXthChance(chance)) {
            list.add(ShopItem.item("katana", random.getIntBetween(priceMin, priceMax)));
        }
    }
}