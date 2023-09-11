package Mobs;

import CustomGame.GamePlayer;

import java.util.ArrayList;
import java.util.Map;

public class WaveRef {
    private ArrayList<WavesObj> mobsWave;
    private GamePlayer player;

    public ArrayList<WavesObj> getMobsWave() {
        return mobsWave;
    }

    public void setMobsWave(ArrayList<WavesObj> mobsWave) {
        this.mobsWave = mobsWave;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public void setPlayer(GamePlayer player) {
        this.player = player;
    }
}
