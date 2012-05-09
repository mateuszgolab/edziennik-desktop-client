package com.polsl.edziennik.desktopclient.security;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jidesoft.dialog.JideOptionPane;

public class AccountActivationDialog extends JideOptionPane {

	private ActivationPanel panel;
	private ImageIcon icon;
	private String[] options = { "Aktywuj konto", "Anuluj" };
	private LoginDialog parent;

	public AccountActivationDialog(LoginDialog parent) {

		this.parent = parent;
		panel = new ActivationPanel(parent);

		icon = new ImageIcon("icons/login.png");

		showDialog();
	}

	public void showDialog() {
		if (this.showOptionDialog(this, panel, "Aktywacja", JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION,
				icon, options, panel) == 0) {
			panel.new ActivateWorker().execute();

		} else
			parent.showDialog();
	}

}
