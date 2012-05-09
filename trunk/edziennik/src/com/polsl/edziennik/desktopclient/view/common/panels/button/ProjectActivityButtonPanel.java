package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class ProjectActivityButtonPanel extends ButtonPanel{

	@Override
	public void create() {

		add = Button.getButton("addIcon", "addProjectActivityHint");
		remove = Button.getButton("removeIcon", "removeProjectActivityHint");
		save = Button.getButton("saveIcon", "saveProjectActivityHint");
		
	}

	@Override
	public void addOthers() {
		
	}

}
