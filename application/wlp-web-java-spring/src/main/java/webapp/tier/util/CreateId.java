package webapp.tier.util;

import java.util.Random;

public class CreateId {

	private CreateId() {
	}

    /**
     * 5桁の正の整数を返す
     */
    public static int createid() {
	Random rand = new Random();
	int id = 0;
	while (id < 10000) {
	    id = rand.nextInt(99999);
	}
	return id;
    }

    /**
     * 5桁の正の整数を返す
     *
     * @return 5桁の正の整数
     */
    public static int createwideid() {
	int length = 4;
	int id = 0;
	for (int i = 0; i <= length; i++) {
	    id = id * 10 + (int) (Math.random() * 9) + 1;
	}
	return id;
    }
}
