package org.doogwood.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public void doGreeting(final String m) {
		logger.info(m);
	}
}
