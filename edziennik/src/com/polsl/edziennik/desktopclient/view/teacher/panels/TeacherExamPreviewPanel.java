package com.polsl.edziennik.desktopclient.view.teacher.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.polsl.edziennik.desktopclient.controller.admin.exams.AdminExamTasksAction;
import com.polsl.edziennik.desktopclient.controller.teacher.exams.ExamTasksAction;
import com.polsl.edziennik.desktopclient.view.EdziennikDesktop;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.ExamPreviewPanel;

public class TeacherExamPreviewPanel extends ExamPreviewPanel {

	private JButton tasks;

	public TeacherExamPreviewPanel(String title) {
		super(title);
		setEditable(false);
		disable(true);
	}

	@Override
	public void create() {
		super.create();
		tasks = button.getButton("tasksButton", "examTasksHint");
		tasks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				EdziennikDesktop edziennik = EdziennikDesktop.getInstance();
				if (edziennik.getAdminParent() != null)
					(new AdminExamTasksAction(edziennik.getAdminParent(), currentExam)).actionPerformed(null);
				else if (edziennik.getTeacherParent() != null)
					(new ExamTasksAction(edziennik.getTeacherParent(), currentExam)).actionPerformed(null);
			}

		});
	}

	@Override
	public void setComponents() {
		super.setComponents();
		add(tasks, cc.xyw(3, 11, 5));
	}

	@Override
	public void setEnabled(boolean b) {

		datePicker.setEnabled(b);
		roomText.setEnabled(b);
		approachText.setEnabled(b);
		scopeText.setEnabled(b);
		tasks.setEnabled(b);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}
