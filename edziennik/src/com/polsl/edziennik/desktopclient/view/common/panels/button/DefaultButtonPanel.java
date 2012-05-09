package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class DefaultButtonPanel extends ButtonPanel {

	public DefaultButtonPanel(String saveName, String addName, String removeName) {

		super(saveName, addName, removeName);

	}

	@Override
	public void create() {
		add = Button.getButton("addIcon", addName);
		remove = Button.getButton("removeIcon", removeName);
		save = Button.getButton("saveIcon", saveName);
	}

	@Override
	public void addOthers() {
		// TODO Auto-generated method stub

	}

}
