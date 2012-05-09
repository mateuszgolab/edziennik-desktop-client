package com.polsl.edziennik.desktopclient.controller.admin.subject;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.admin.AdminAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.admin.SubjectRulesManagement;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u zasad przedmiotu
 * na poziomie administratora
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class SubjectRulesManagementAction extends AdminAction {

	private static String title = menu.getString("manageSubjectRules");

	public SubjectRulesManagementAction(AdminMainView parent) {
		super(title);
		this.parent = parent;
	}

	/**
	 * metoda uruchamiaj¹ca akcjê
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		SubjectRulesManagement subject = new SubjectRulesManagement();
		parent.addTab(menu.getString("subjectRules") + "  ", subject, new ImageIcon(bundle.getString("SubjectIcon")));
		parent.invalidate();
		parent.validate();

	}

}
