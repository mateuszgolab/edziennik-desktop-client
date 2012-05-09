package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class ProjectButtonPanel extends ButtonPanel{

	@Override
	public void create() {
		add = Button.getButton("addIcon", "addProjectHint");
		remove = Button.getButton("removeIcon", "removeProjectHint");
		save = Button.getButton("saveIcon", "saveProjectHint");
		
	}

	@Override
	public void addOthers() {
		
	}

}
