package com.polsl.edziennik.desktopclient.controller.admin.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.teacher.LabActivities;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u laboratoriów na
 * poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class LabActivitiesAction extends AdminAction {

	private static String title = menu.getString("labActivities");

	public LabActivitiesAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO currentTeacher
		LabActivities labActivities = new LabActivities(null);
		parent.addTab(title + "  ", labActivities, new ImageIcon(bundle.getString("labActivitiesIcon")));
		parent.invalidate();
		parent.validate();

	}
}