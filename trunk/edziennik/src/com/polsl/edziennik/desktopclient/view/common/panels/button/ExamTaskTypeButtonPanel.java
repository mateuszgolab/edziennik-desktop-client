package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class ExamTaskTypeButtonPanel extends AddRemoveButtonPanel {

	@Override
	public void create() {

		add = Button.getButton("addIcon", "addExamTaskTypesHint");
		remove = Button.getButton("removeIcon", "removeExamTaskTypesHint");

	}

}
