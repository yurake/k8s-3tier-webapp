package webapp.tier.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import webapp.tier.bean.MsgBean;

@Service
public class RedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Value("${common.message}")
	private String message;

	@Value("${spring.redis.channel.name}")
	private String channel;

	@Value("${spring.redis.set.expire}")
	private int setexpire;

	@Value("${spring.redis.split.key}")
	private String splitkey;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public MsgBean set() {
		MsgBean msgbean = new MsgBean(message);
		redisTemplate.opsForValue().set(msgbean.idtoString(), msgbean.getMessage());
		redisTemplate.expire(msgbean.idtoString(), setexpire, TimeUnit.SECONDS);
		logger.info(msgbean.logMessageOut("set"));
		return msgbean;
	}

	public Iterable<MsgBean> get() {
		List<MsgBean> allmsg = new ArrayList<>();
		Set<String> keys = redisTemplate.keys("*");
		for (String key : keys) {
			String msg = redisTemplate.opsForValue().get(key);
			MsgBean msgbean = new MsgBean(key, msg);
			logger.info(msgbean.logMessageOut("get"));
			allmsg.add(msgbean);
		}
		return allmsg;
	}

	public MsgBean publish() {
		MsgBean msgbean = new MsgBean(message);
		redisTemplate.convertAndSend(channel, msgbean.createBody(splitkey));
		redisTemplate.expire(msgbean.idtoString(), setexpire, TimeUnit.SECONDS);
		logger.info(msgbean.logMessageOut("publish"));
		return msgbean;
	}
}
