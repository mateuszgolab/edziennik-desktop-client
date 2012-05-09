package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class GradeTypesButtonPanel extends AddRemoveButtonPanel {

	@Override
	public void create() {

		add = Button.getButton("addIcon", "addGradeTypesHint");
		remove = Button.getButton("removeIcon", "removeGradeTypesHint");

	}

}
