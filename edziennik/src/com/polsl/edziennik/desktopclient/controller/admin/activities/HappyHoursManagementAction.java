package com.polsl.edziennik.desktopclient.controller.admin.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.HappyHoursManagement;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu�u godzin
 * dzieka�skich na poziomie administratora
 * 
 * @author Mateusz Go��b
 * 
 */
public class HappyHoursManagementAction extends AdminAction {

	private static String title = menu.getString("happyHours");

	public HappyHoursManagementAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj�ca akcj�
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		HappyHoursManagement hours = new HappyHoursManagement();
		parent.addTab(title + "  ", hours, new ImageIcon(bundle.getString("happyHoursIcon")));
		parent.invalidate();
		parent.validate();

	}
}