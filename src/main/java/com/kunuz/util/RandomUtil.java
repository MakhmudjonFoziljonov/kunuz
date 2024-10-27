package com.kunuz.util;


import java.util.Random;

public class RandomUtil {
    static Random random = new Random();

    public static int randomInt() {
        return random.nextInt(10_000, 99_999);
    }
}
