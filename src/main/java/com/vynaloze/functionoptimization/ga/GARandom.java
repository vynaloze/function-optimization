package com.vynaloze.functionoptimization.ga;

import java.util.concurrent.ThreadLocalRandom;

public class GARandom {
    public static double get(){
        return ThreadLocalRandom.current().nextDouble(Params.DOMAIN_MIN, Params.DOMAIN_MAX);
    }
}
