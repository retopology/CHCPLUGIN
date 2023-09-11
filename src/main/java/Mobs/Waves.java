package Mobs;

import Mobs.ReferenseMobs.SkeletonRef;
import Mobs.ReferenseMobs.ZombieRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Waves {
    public static ArrayList<WavesObj> Wave_Only_Zombies(){
        ArrayList<WavesObj> zombies = new ArrayList<>();

        WavesObj zomn = new WavesObj();
        zomn.count = 10;
        zomn.mob = new ZombieRef();

        zombies.add(zomn);

        return zombies;
    }

    public static ArrayList<WavesObj> Wave_Only_Skeletons(){
        ArrayList<WavesObj>  skeletons = new ArrayList<>();
        WavesObj skel = new WavesObj();
        skel.mob = new SkeletonRef();
        skel.count = 6;

        skeletons.add(skel);
        return skeletons;
    }

    public static ArrayList<WavesObj> Wave_Zombie_And_Skeleton(){
        ArrayList<WavesObj>  mix = new ArrayList<>();
        WavesObj zomn = new WavesObj();
        zomn.count = 6;
        zomn.mob = new ZombieRef();
        mix.add(zomn);

        WavesObj skel = new WavesObj();
        skel.count = 3;
        skel.mob = new SkeletonRef();

        mix.add(skel);
        return mix;
    }
}
