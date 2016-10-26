package arr.pirate.ship.util;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Used to obtain beans where we cannot rely on injection (POJOs) or do not want
 * to inject something we might not need.
 * 
 * @author Alexander_Koledzhikov
 *
 */
@Component
public class ApplicationContextUtil {

	private static ApplicationContextUtil INSTANCE;

	@Autowired
	private ApplicationContext context;

	public ApplicationContextUtil() {
	}

	@PostConstruct
	private void postConstruct() {
		INSTANCE = this;
	}

	public static ApplicationContextUtil getInstance() {
		if (INSTANCE == null) {
			throw new IllegalStateException("Application context not yet initialized!");
		}
		
		return INSTANCE;
	}

	public <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public <T> T getBean(String beanName, Class<T> beanClass) {
		return context.getBean(beanName, beanClass);
	}

	public <T> Map<String, T> getBeansForClass(Class<T> clazz) {
		return context.getBeansOfType(clazz);
	}
}
