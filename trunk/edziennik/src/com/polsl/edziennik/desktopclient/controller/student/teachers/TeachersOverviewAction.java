package com.polsl.edziennik.desktopclient.controller.student.teachers;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.common.Teachers;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u przegl¹ania
 * prowadz¹cych na poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class TeachersOverviewAction extends StudentAction {

	private static String title = menu.getString("viewTeachers");

	public TeachersOverviewAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Teachers teacherOverview = new Teachers();
		parent.addTab(menu.getString("Teachers") + "  ", teacherOverview,
				new ImageIcon(bundle.getString("examBrowseIcon")));
		parent.invalidate();
		parent.validate();

	}

}
