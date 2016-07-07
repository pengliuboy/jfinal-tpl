package com.lp.test;

import com.jfinal.aop.Duang;
import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

//@ControllerBind(controllerKey="/feature")
public class NewFeature extends Controller{

	//Redis例子，需要有Redis服务
	public void hello() {
		Cache cache = Redis.use();
		String name = cache.get("name");
		if (null == name) {
			cache.set("name", "liupeng");
		}
		renderJson("hello", name);
	}
	
	//业务层拦截器实例，不使用注解直接注入Sevice
	public void save() {
		Service service = enhance(Service.class);
		service.save();
	}
	
	//业务层增强，注入拦截器，可以使用在非Web项目中
	public void ddd() {
		Service service = Enhancer.enhance(Service.class, ServiceInterceptor.class);
		service.delete();
		//Or use 
		Service service1 = Duang.duang(Service.class, ServiceInterceptor.class);
		service1.save();
	}
}
