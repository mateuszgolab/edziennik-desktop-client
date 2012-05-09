package com.polsl.edziennik.desktopclient.view.common.panels.button;


public class StudentNoRemoveButtonPanel extends StudentButtonPanel {

	public StudentNoRemoveButtonPanel() {
		remove(remove);

	}

	public void activate(boolean value) {
		save.setEnabled(value);
	}

}
