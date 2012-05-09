package com.polsl.edziennik.desktopclient.view.common.dialogs;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.jidesoft.dialog.JideOptionPane;
import com.polsl.edziennik.desktopclient.view.common.panels.ProfilePanel;

public class ProfileDialog extends JideOptionPane {

	private ProfilePanel panel;
	private ImageIcon icon;
	private String[] options = { "Zapisz", "Anuluj" };

	public ProfileDialog() {

		panel = new ProfilePanel(this);

		icon = new ImageIcon("icons/profile.png");

		showDialog();
	}

	public void showDialog() {
		if (this.showOptionDialog(this, panel, "Zmiana has³a", JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION,
				icon, options, panel) == 0) panel.new ChangePasswordWorker().execute();

	}
}
