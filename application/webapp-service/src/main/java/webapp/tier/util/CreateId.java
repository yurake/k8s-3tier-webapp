package webapp.tier.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class CreateId {
	
	private CreateId() {
	}

	/**
	 * 5桁の正の整数を返す.
	 * @return 5桁の整数
	 * @throws NoSuchAlgorithmException This exception is thrown when a particular cryptographic algorithm is requested but is not available in the environment.
	 */
	public static int createid() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstanceStrong();
		int id = 0;
		while (id < 10000) {
			id = sr.nextInt(99999);
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
