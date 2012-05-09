package com.polsl.edziennik.desktopclient.controller.student.exams;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.student.StudentExams;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u egzaminów na
 * poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class StudentExamsAction extends StudentAction {

	private static String title = menu.getString("view");

	public StudentExamsAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StudentExams exams = new StudentExams();
		parent.addTab(menu.getString("Exams") + "  ", exams, new ImageIcon(bundle.getString("examBrowseIcon")));
		parent.invalidate();
		parent.validate();

	}
}