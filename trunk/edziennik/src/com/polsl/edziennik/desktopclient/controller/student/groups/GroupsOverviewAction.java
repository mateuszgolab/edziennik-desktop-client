package com.polsl.edziennik.desktopclient.controller.student.groups;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.student.StudentAction;
import com.polsl.edziennik.desktopclient.view.common.GroupsOverview;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

/**
 * 
 * Klasa odpowiedzialna za wykoananie akcji uruchomienia modu³u przegl¹dania
 * grup na poziomie studenta
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class GroupsOverviewAction extends StudentAction {

	private static String title = menu.getString("view");

	public GroupsOverviewAction(StudentMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GroupsOverview GroupOverview = new GroupsOverview();
		parent.addTab(menu.getString("Groups") + "  ", GroupOverview, new ImageIcon(bundle.getString("GroupsIcon")));
		parent.invalidate();
		parent.validate();

	}
}