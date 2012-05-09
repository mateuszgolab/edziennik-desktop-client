package com.polsl.edziennik.desktopclient.controller.admin.groups;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.GroupsManagement;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u grup na poziomie
 * administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class GroupsManagementAction extends AdminAction {

	private static String title = menu.getString("manage");

	public GroupsManagementAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		GroupsManagement groups = new GroupsManagement();
		parent.addTab(menu.getString("Groups") + "  ", groups, new ImageIcon(bundle.getString("GroupsIcon")));
		parent.invalidate();
		parent.validate();

	}
}