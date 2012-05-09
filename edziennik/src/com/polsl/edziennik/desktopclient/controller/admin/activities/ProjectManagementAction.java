package com.polsl.edziennik.desktopclient.controller.admin.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.ProjectManagement;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u projektów na
 * poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ProjectManagementAction extends AdminAction {

	private static String title = menu.getString("projects");

	public ProjectManagementAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ProjectManagement projects = new ProjectManagement(null);
		parent.addTab(title + "  ", projects, new ImageIcon(bundle.getString("projectIcon")));
		parent.invalidate();
		parent.validate();

	}

}