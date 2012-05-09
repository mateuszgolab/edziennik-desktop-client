package com.polsl.edziennik.desktopclient.controller.admin.exams;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.ExamTaskManagement;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u zadañ
 * egzaminacyjnych na poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class AdminExamTasksAction extends AdminAction {

	private static String title = menu.getString("examTasks");
	private ExamDTO exam;

	public AdminExamTasksAction(AdminMainView parent, ExamDTO e) {
		super(title);
		this.parent = parent;
		this.exam = e;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ExamTaskManagement exams = new ExamTaskManagement(exam);
		parent.addTab(title + "  ", exams, new ImageIcon(bundle.getString("examTaskIcon")));
		parent.invalidate();
		parent.validate();

	}
}
