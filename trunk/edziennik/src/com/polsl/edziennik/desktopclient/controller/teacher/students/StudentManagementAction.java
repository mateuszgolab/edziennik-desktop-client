package com.polsl.edziennik.desktopclient.controller.teacher.students;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.teacher.StudentsManagementForTeacher;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u studentów na
 * poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class StudentManagementAction extends TeacherAction {

	private static String title = menu.getString("view");

	public StudentManagementAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StudentsManagementForTeacher studenciPanel = new StudentsManagementForTeacher();
		parent.addTab(menu.getString("Students") + "  ", studenciPanel, new ImageIcon(bundle.getString("StudentsIcon")));
		parent.invalidate();
		parent.validate();

	}
}
