package webapp.tier.util;

import java.util.Random;

public final class CreateId {

	private static Random rand = new Random();

	private CreateId() {
	}

	/**
	 * 5桁の正の整数を返す.
	 * @return 5桁の整数
	 */
	public static int createid() {
		int id = 0;
		while (id < 10000) {
			id = rand.nextInt(99999);
		}
		return id;
	}

	/**
	 * 正負含む10桁の整数を返す.
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
