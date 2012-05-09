package com.polsl.edziennik.desktopclient.controller.teacher.exams;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherExams;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu�u przegl�dania
 * egzamin�w na poziomie prowadz�cego
 * 
 * @author Mateusz Go��b
 * 
 */
public class ExamBrowseAction extends TeacherAction {

	private static String title = menu.getString("view");

	public ExamBrowseAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TeacherExams teacherExams = new TeacherExams();
		parent.addTab(menu.getString("Teachers") + "  ", teacherExams,
				new ImageIcon(bundle.getString("examBrowseIcon")));
		parent.invalidate();
		parent.validate();

	}
}