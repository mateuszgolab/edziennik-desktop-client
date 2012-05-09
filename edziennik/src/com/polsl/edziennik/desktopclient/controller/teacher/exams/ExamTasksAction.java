package com.polsl.edziennik.desktopclient.controller.teacher.exams;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherExamTasks;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u przegl¹dania
 * zadañ egzaminacyjnych na poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ExamTasksAction extends TeacherAction {

	private static String title = menu.getString("examTasks");
	private ExamDTO exam;

	public ExamTasksAction(TeacherMainView parent, ExamDTO e) {
		super(title);
		this.parent = parent;
		this.exam = e;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TeacherExamTasks exams = new TeacherExamTasks(exam);
		parent.addTab(title + "  ", exams, new ImageIcon(bundle.getString("examTaskIcon")));
		parent.invalidate();
		parent.validate();

	}
}
