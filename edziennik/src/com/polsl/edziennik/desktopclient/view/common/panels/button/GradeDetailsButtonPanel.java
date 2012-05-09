package com.polsl.edziennik.desktopclient.view.common.panels.button;

import javax.swing.JButton;

public class GradeDetailsButtonPanel extends SaveExitButtonPanel {

	private JButton details;

	public GradeDetailsButtonPanel(String title, String hint) {

		super(hint);
		details = Button.getButton(title, hint);

		add(details);
		add(exit);
	}

	public JButton getDetailsButton() {
		return details;
	}

	@Override
	public void activate(boolean b) {
		save.setEnabled(b);
		details.setEnabled(b);

	}

}
