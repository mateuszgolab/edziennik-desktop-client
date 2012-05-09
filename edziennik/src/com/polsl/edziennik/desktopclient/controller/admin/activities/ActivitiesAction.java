package com.polsl.edziennik.desktopclient.controller.admin.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.teacher.Activities;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u æwiczeñ
 * tablicowych na poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ActivitiesAction extends AdminAction {

	private static String title = menu.getString("activities");

	public ActivitiesAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		Activities activities = new Activities(null);
		parent.addTab(title + "  ", activities, new ImageIcon(bundle.getString("exerciseIcon")));
		parent.invalidate();
		parent.validate();

	}
}