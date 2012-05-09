package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class TeacherButtonPanel extends ButtonPanel {

	@Override
	public void create() {
		add = Button.getButton("addUserIcon", "addTeacherHint");
		remove = Button.getButton("removeUserIcon", "removeTeacherHint");
		save = Button.getButton("saveUserIcon", "saveTeacherHint");

	}

	@Override
	public void addOthers() {
		

	}

}
