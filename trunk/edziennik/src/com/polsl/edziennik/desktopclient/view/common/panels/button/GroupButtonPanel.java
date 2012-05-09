package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class GroupButtonPanel extends ButtonPanel {

	@Override
	public void create() {
		add = Button.getButton("addIcon", "addGroupHint");
		remove = Button.getButton("removeIcon", "removeGroupHint");
		save = Button.getButton("saveIcon", "saveGroupHint");

	}

	@Override
	public void addOthers() {

	}

}
