package com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.jide;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import com.jidesoft.swing.JideMenu;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IMenu;
import com.polsl.edziennik.desktopclient.properties.Properties;

/**
 * 
 * Klasa implementuj¹ca interfejs IButton ,dostarcza JideMenu
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class JIDEMenu implements IMenu {

	private static ResourceBundle bundle = LangManager.getResource(Properties.Menu);
	private static ResourceBundle component = LangManager.getResource(Properties.Component);

	@Override
	public JideMenu getMenu(String name, String icon) {
		JideMenu menu = new JideMenu(bundle.getString(name));
		menu.setIcon(new ImageIcon(component.getString(icon)));
		return menu;
	}

}