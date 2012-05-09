package com.polsl.edziennik.desktopclient.controller.admin.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.ReportsForAdmin;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u raportów na
 * poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */

public class ReportsAction extends AdminAction {

	private static String title = menu.getString("reports");

	public ReportsAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		ReportsForAdmin reports = new ReportsForAdmin();
		parent.addTab(title + "  ", reports, new ImageIcon(bundle.getString("reportIcon")));
		parent.invalidate();
		parent.validate();

	}
}