package com.polsl.edziennik.desktopclient.controller.student.subject;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.common.SubjectRules;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u przegl¹dania
 * zasad przedmiotu na poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class SubjectRulesAction extends StudentAction {

	private static String title = menu.getString("subjectRules");

	public SubjectRulesAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SubjectRules subject = new SubjectRules();
		parent.addTab(title + "  ", subject, new ImageIcon(bundle.getString("TeachersIcon")));
		parent.invalidate();
		parent.validate();

	}

}
