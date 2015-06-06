package org.doogwood.base;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Hello {
	@Autowired
	private GreetingService greetingService;
	
	public void execute(final String[] args) throws Exception {
		greetingService.doGreeting("hello world");
	}
	
	public static void main(final String[] args) {
		final Class<Hello> classMeta = Hello.class;
		final String packageName = classMeta.getPackage().getName();
		final AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(packageName);
		try {
			ctx.getBean(classMeta).execute(args);
		} catch (final BeansException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			ctx.close();
		}
	}
}
