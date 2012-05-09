package com.polsl.edziennik.desktopclient.controller.teacher;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Abstrakcyjna klasa akcji uruchomienia modu�u aplikacji na poziomie
 * prowadz�cego
 * 
 * @author Mateusz Go��b
 * 
 */
public abstract class TeacherAction extends AbstractAction {

	protected TeacherMainView parent;
	protected static ResourceBundle menu = LangManager.getResource(Properties.Menu);
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public TeacherAction(String title) {
		super(title);
	}

	/**
	 * metoda uruchamiaj�ca akcj�
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);
}
