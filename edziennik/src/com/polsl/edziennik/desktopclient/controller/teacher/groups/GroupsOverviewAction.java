package com.polsl.edziennik.desktopclient.controller.teacher.groups;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.polsl.edziennik.desktopclient.controller.teacher.TeacherAction;
import com.polsl.edziennik.desktopclient.view.common.GroupsOverview;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

/**
 * 
 * Klasa odpowiedzialna za wykonanie akcji uruchomienia modu³u przegl¹dania grup
 * na poziomie prowadz¹cego
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class GroupsOverviewAction extends TeacherAction {

	private static String title = menu.getString("view");

	public GroupsOverviewAction(TeacherMainView parent) {
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