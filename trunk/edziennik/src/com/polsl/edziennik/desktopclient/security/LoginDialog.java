package com.polsl.edziennik.desktopclient.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jidesoft.dialog.JideOptionPane;
import com.jidesoft.swing.JideLabel;
import com.polsl.edziennik.delegates.login.IUserPassGetter;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;

public class LoginDialog extends JideOptionPane implements IUserPassGetter {
	private JPasswordField passwordField;
	private Object[] items;
	private JideLabel login;
	private JideLabel password;
	private JTextField loginField;
	private JPanel loginPanel;
	private JPanel passwordPanel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private String[] options = { "Zaloguj", "Aktywacja", bundle.getString("exitTextButton") };
	private ImageIcon icon;

	public LoginDialog() {
		login = new JideLabel("email");

		password = new JideLabel(bundle.getString("password"));
		loginField = new JTextField(15);
		loginField.setFocusable(true);
		passwordField = new JPasswordField(15);

		icon = new ImageIcon("icons/login.png");

		loginPanel = new JPanel();
		loginPanel.add(login);
		loginPanel.add(loginField);
		passwordPanel = new JPanel();
		passwordPanel.add(password);
		passwordPanel.add(passwordField);

		items = new Object[2];
		items[0] = loginPanel;
		items[1] = passwordPanel;

		loginField.setVisible(true);

	}

	public int showDialog() {
		int result;
		result = this.showOptionDialog(this, items, bundle.getString("loginDialog"), JOptionPane.DEFAULT_OPTION,
				JOptionPane.YES_NO_OPTION, icon, options, items[0]);
		while (result == 0) {

			final String emailReg = loginField.getText().toLowerCase().trim();
			if (!emailReg.matches(".+@.+\\.[a-z]+")) {
				JOptionPane.showMessageDialog(null, bundle.getString("badLogin"), bundle.getString("loginError"),
						JOptionPane.ERROR_MESSAGE);
			} else
				return 0;

			result = this.showOptionDialog(this, items, bundle.getString("loginDialog"), JOptionPane.DEFAULT_OPTION,
					JOptionPane.YES_NO_OPTION, icon, options, items[0]);
		}

		if (result == 2) System.exit(0);
		new AccountActivationDialog(this);

		return 0;
	}

	@Override
	public Map<String, String> getUserPass() throws IOException {
		Map<String, String> map = new HashMap<String, String>();

		switch (showDialog()) {
		case 0:
			map.put(LOGIN, loginField.getText());
			map.put(HASLO, new String(passwordField.getPassword()));
			break;
		case 1:
			break;
		default:
			break;

		}

		return map;
	}

}
