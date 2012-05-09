package com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing;

import java.util.ResourceBundle;

import javax.swing.JLabel;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.properties.Properties;

/**
 * 
 * Klasa implementuj¹ca interfejs ILabel ,dostarcza JLabel
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class DefaultLabel implements ILabel {

	private static ResourceBundle entity = LangManager.getResource(Properties.Entity);

	@Override
	public JLabel getLabel(String name) {
		return new JLabel(entity.getString(name) + " : ");
	}

}
