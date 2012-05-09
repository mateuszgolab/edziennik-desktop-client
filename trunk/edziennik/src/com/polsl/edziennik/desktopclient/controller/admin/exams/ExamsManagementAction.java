package com.polsl.edziennik.desktopclient.controller.admin.exams;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.ExamsManagement;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u egzaminów na
 * poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ExamsManagementAction extends AdminAction {

	private static String title = menu.getString("view");

	public ExamsManagementAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ExamsManagement exams = new ExamsManagement();
		parent.addTab(menu.getString("Exams") + "  ", exams, new ImageIcon(bundle.getString("examBrowseIcon")));
		parent.invalidate();
		parent.validate();

	}
}
