package com.polsl.edziennik.desktopclient.controller.admin.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.ExerciseTopicManagement;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u tematów
 * laboratoriów na poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ExerciseTopicAction extends AdminAction {

	private static String title = menu.getString("exerciseTopics");

	public ExerciseTopicAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ExerciseTopicManagement topics = new ExerciseTopicManagement();
		parent.addTab(title + "  ", topics, new ImageIcon(bundle.getString("labTopicIcon")));
		parent.invalidate();
		parent.validate();

	}
}