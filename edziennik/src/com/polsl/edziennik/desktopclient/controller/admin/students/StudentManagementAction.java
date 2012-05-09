package com.polsl.edziennik.desktopclient.controller.admin.students;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.StudentsManagement;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u zarz¹dzania
 * studentami na poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class StudentManagementAction extends AdminAction {

	private static String title = menu.getString("manage");

	public StudentManagementAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		StudentsManagement studenciPanel = new StudentsManagement();
		parent.addTab(menu.getString("Students") + "  ", studenciPanel, new ImageIcon(bundle.getString("StudentsIcon")));
		parent.invalidate();
		parent.validate();

	}

}