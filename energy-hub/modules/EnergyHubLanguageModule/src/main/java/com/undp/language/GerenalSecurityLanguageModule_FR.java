package com.undp.language;



import com.liferay.portal.kernel.language.UTF8Control;

import java.util.Enumeration;
import java.util.ResourceBundle;



import org.osgi.service.component.annotations.Component;

/**
 * @author lifray
 */
@Component(
	immediate = true,
	property = { "language.id=fr_FR" }, 
	service = ResourceBundle.class
)
public class GerenalSecurityLanguageModule_FR extends ResourceBundle {

	ResourceBundle bundle = ResourceBundle.getBundle("content.Language_fr_FR", UTF8Control.INSTANCE);

	
	@Override
	protected Object handleGetObject(String key) {
		// TODO Auto-generated method stub
		return bundle.getObject(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		// TODO Auto-generated method stub
		return bundle.getKeys();
	}
}