package com.polsl.edziennik.desktopclient.controller.student;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Abstrakcyjna klasa odpowiedzialna za wykonanie akcji na poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public abstract class StudentAction extends AbstractAction {

	protected StudentMainView parent;
	protected static ResourceBundle menu = LangManager.getResource(Properties.Menu);
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public StudentAction(String title) {
		super(title);
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);
}
