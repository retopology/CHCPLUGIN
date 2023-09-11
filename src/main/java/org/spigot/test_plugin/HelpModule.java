package org.spigot.test_plugin;

import CustomGame.GamePlayer;
import CustomGame.InventoriesGame.MagazineInventory;
import CustomGame.InventoriesGame.RoleInventory;
import Mobs.MobRef;
import Objects.Inventories.Casino.CasinoMain;
import Objects.Inventories.Casino.CasinoResult;
import Objects.Inventories.Casino.CasinoRoulette;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HelpModule {

    // Можно ли забирать вещи из кастомного инвентаря
    public static boolean isClickInventory(String name){
        if(name.equals(CasinoMain.GetInventoryName()) ||
            name.equals(CasinoRoulette.GetInventoryName()) ||
            name.equals(CasinoResult.GetInventoryName()) ||
        name.equals(RoleInventory.GetInventoryName()) ||
        name.equals(MagazineInventory.GetInventoryName())) return true;
        else return false;
    }

    // Возвращает GamePlayer
    // Если возвращает null, значит у игрока на волне ещё есть крипы
    // Если возвращает информацию об игроке, значит он закончил чистить волну
    public static GamePlayer CalculateKills(Player player, LivingEntity ent){
        ArrayList<MobRef> mobs = Test_plugin.gameChc.GetWaveList();
        GamePlayer playerWhoDef = null;

        MobRef remover = null;
        for (MobRef wav: mobs) {
            if(ent.getName().equals(wav.getUnicode())){
                remover = wav;
                playerWhoDef = wav.getPlayer();
                break;
            }
        }
        if(remover !=null){

            ArrayList<GamePlayer> players = Test_plugin.gameChc.GetPlayersArray();
            for (GamePlayer play: players) {
                if(play.getPlayer() == player){
                    play.setMoney(play.getMoney() + (int)remover.getHealth() + (int)remover.GetUpgradePropetry() + Test_plugin.gameChc.GetCurrentWave());
                    play.RegisterScoreBar();
                    break;
                }
            }

            mobs.remove(remover);
            Test_plugin.gameChc.SetWaveList(mobs);

            playerWhoDef.RegisterScoreBar();

            int count = Test_plugin.gameChc.GetCountMobsInWavePlayer(playerWhoDef);
            if(count == 0){

                return playerWhoDef;
            }
            else return null;


        }
        else return null;
    }

}
