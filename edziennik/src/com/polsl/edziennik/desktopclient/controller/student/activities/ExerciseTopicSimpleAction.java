package com.polsl.edziennik.desktopclient.controller.student.activities;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;
import com.polsl.edziennik.desktopclient.view.teacher.ExerciseTopicOverview;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u teamtów
 * laboratoriów na poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class ExerciseTopicSimpleAction extends StudentAction {

	private static String title = menu.getString("exerciseTopics");

	public ExerciseTopicSimpleAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ExerciseTopicOverview topics = new ExerciseTopicOverview();
		parent.addTab(title + "  ", topics, new ImageIcon(bundle.getString("labTopicIcon")));
		parent.invalidate();
		parent.validate();

	}
}