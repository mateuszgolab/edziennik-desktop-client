package com.polsl.edziennik.desktopclient.controller.admin.teachers;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.TeacherManagement;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu�u zarz�dzania
 * nauczycielami na poziomie administratora
 * 
 * @author Mateusz Go��b
 * 
 */
public class TeacherManagementAction extends AdminAction {

	private static String title = menu.getString("manageTeachers");

	public TeacherManagementAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj�ca akcj�
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		TeacherManagement teacher = new TeacherManagement();
		parent.addTab(menu.getString("Teachers") + "  ", teacher, new ImageIcon(bundle.getString("TeachersIcon")));
		parent.invalidate();
		parent.validate();

	}

}
