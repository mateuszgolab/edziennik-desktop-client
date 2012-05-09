package com.polsl.edziennik.desktopclient.controller.teacher.students;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.teacher.Summary;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u podsumowañ
 * studentów na poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class SummaryAction extends TeacherAction {

	private static String title = menu.getString("summary");

	public SummaryAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Summary summary = new Summary();
		parent.addTab(title + "  ", summary, new ImageIcon(bundle.getString("StudentsIcon")));
		parent.invalidate();
		parent.validate();

	}
}