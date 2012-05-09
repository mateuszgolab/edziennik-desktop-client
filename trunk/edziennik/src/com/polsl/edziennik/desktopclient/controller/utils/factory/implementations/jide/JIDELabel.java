package com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.jide;

import java.util.ResourceBundle;

import com.jidesoft.swing.JideLabel;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.properties.Properties;

/**
 * 
 * Klasa implementuj¹ca interfejs IButton ,dostarcza JideLabel
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class JIDELabel implements ILabel {

	private static ResourceBundle entity = LangManager.getResource(Properties.Entity);

	@Override
	public JideLabel getLabel(String name) {
		return new JideLabel(entity.getString(name) + " : ");
	}

}
