package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class StudentButtonPanel extends ButtonPanel
{
	@Override
	public void create()
	{
		add = Button.getButton("addUserIcon", "addStudentHint");
		remove = Button.getButton("removeUserIcon", "removeStudentHint");
		save = Button.getButton("saveUserIcon", "saveStudentHint");
	}

	@Override
	public void addOthers() {
		
	}

}
