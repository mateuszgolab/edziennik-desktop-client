package com.polsl.edziennik.desktopclient.controller.student;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.delegates.login.LoginManager;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;
import com.polsl.edziennik.desktopclient.view.student.StudentSummary;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u podsumowania
 * studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class SummaryAction extends StudentAction {

	private static String title = menu.getString("summary");

	public SummaryAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO : currentStudent
		StudentSummary s = new StudentSummary(LoginManager.getUser().getId());
		// StudentSummary s = new StudentSummary(2);
		parent.addTab(title + "  ", s, new ImageIcon(bundle.getString("StudentsIcon")));
		parent.invalidate();
		parent.validate();

	}
}
