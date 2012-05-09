package com.polsl.edziennik.desktopclient.controller.admin;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;

/**
 * 
 * Klasa abstrakcyjna odpowiedzialna za wykoananie akcji na poziomie
 * administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public abstract class AdminAction extends AbstractAction {

	protected AdminMainView parent;
	protected static ResourceBundle menu = LangManager.getResource(Properties.Menu);
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public AdminAction(String title) {
		super(title);
	}

	/**
	 * metoda uruchamiajaca akcjê
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);
}
