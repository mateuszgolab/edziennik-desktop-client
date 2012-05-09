package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class ExamTaskButtonPanel extends ButtonPanel {

	@Override
	public void create() {
		add = Button.getButton("addIcon", "addExamTaskHint");
		remove = Button.getButton("removeIcon", "removeExamTaskHint");
		save = Button.getButton("saveIcon", "saveExamTaskHint");
	}

	@Override
	public void addOthers() {
		// TODO Auto-generated method stub

	}

}
