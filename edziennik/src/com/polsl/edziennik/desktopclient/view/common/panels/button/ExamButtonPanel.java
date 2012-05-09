package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class ExamButtonPanel extends ButtonPanel {

	@Override
	public void create() {
		add = Button.getButton("addIcon", "addExamHint");
		remove = Button.getButton("removeIcon", "removeExamHint");
		save = Button.getButton("saveIcon", "saveExamHint");

	}

	@Override
	public void addOthers() {
		// TODO Auto-generated method stub

	}
}