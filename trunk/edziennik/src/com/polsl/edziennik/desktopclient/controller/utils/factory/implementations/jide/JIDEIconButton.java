package com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.jide;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import com.jidesoft.swing.JideButton;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.properties.Properties;

/**
 * 
 * Klasa implementuj¹ca interfejs IButton ,dostarcza przycisk JideButton z ikon¹
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class JIDEIconButton implements IButton {

	private static ResourceBundle component = LangManager.getResource(Properties.Component);

	@Override
	public JideButton getButton(String name, String hint) {
		JideButton b = new JideButton(new ImageIcon(component.getString(name)));
		b.setEnabled(true);
		b.setToolTipText(component.getString(hint));
		return b;
	}

}
