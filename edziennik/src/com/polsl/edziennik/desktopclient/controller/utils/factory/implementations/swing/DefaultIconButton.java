package com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.properties.Properties;

/**
 * 
 * Klasa implementuj¹ca interfejs IButton ,dostarcza standardowy przycisk swing
 * z ikon¹
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class DefaultIconButton implements IButton {

	private static ResourceBundle component = LangManager.getResource(Properties.Component);

	@Override
	public JButton getButton(String name, String hint) {
		JButton b = new JButton(new ImageIcon(component.getString(name)));
		b.setEnabled(true);
		b.setToolTipText(component.getString(hint));
		return b;
	}

}
