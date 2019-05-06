package batch.util;

import java.util.Random;

public class CreateId {

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
     * 正負含む5桁の整数を返す
     *
     * @return 正負含む10桁の整数
     */
    public static int createwideid() {
	int length = 5;
	int id = 0;
	for (int i = 0; i <= length; i++) {
	    id = id * 10 + (int) (Math.random() * 10); // 0~9の数値を入れる
	}
	return id;
    }
}
