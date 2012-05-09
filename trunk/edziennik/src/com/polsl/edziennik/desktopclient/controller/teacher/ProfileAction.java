package com.polsl.edziennik.desktopclient.controller.teacher;

import java.awt.event.ActionEvent;

import com.polsl.edziennik.desktopclient.view.common.dialogs.ProfileDialog;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

public class ProfileAction extends TeacherAction {

	private static String title = menu.getString("changePassword");

	public ProfileAction(TeacherMainView parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new ProfileDialog();
	}
}
