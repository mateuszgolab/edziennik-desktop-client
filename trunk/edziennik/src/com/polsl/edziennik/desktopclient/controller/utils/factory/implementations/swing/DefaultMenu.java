package com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JMenu;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IMenu;
import com.polsl.edziennik.desktopclient.properties.Properties;

/**
 * 
 * Klasa implementuj¹ca interfejs IMenu ,dostarcza JMenu
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class DefaultMenu implements IMenu {

	private static ResourceBundle bundle = LangManager.getResource(Properties.Menu);
	private static ResourceBundle component = LangManager.getResource(Properties.Component);

	@Override
	public JMenu getMenu(String name, String icon) {
		JMenu menu = new JMenu(bundle.getString(name));
		menu.setIcon(new ImageIcon(component.getString(icon)));

		return menu;
	}

}
