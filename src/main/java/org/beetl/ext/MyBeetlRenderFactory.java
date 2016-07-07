package org.beetl.ext;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.fn.ShiroExt;
import org.beetl.ext.jfinal.BeetlRenderFactory;

public class MyBeetlRenderFactory extends BeetlRenderFactory {

	public MyBeetlRenderFactory() {
		super();
		init();
	}
	
	public void init() {
		GroupTemplate gt = BeetlRenderFactory.groupTemplate;
		gt.registerFunctionPackage("shiro", new ShiroExt());
		registerModelVirtualAttr(gt);
	}
	
	private void registerModelVirtualAttr(GroupTemplate gt) {
		//TODO:虚拟属性
	}
}
