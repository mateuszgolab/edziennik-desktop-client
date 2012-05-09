package com.polsl.edziennik.desktopclient.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.security.auth.login.LoginException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.login.IUserPassGetter;
import com.polsl.edziennik.delegates.login.LoginManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.PreviewPanel;

public class ActivationPanel extends PreviewPanel {

	private JLabel name;
	private JLabel lastName;
	private JLabel index;
	private JLabel email;
	private JLabel password;

	private JTextField nameText;
	private JTextField lastNameText;
	private JTextField indexText;
	private JTextField emailText;
	private JPasswordField passwordText;
	private IButton button;
	private LoginDialog parent;

	private ILabel label;

	public ActivationPanel(LoginDialog parent) {
		this.parent = parent;
		FormLayout layout = new FormLayout(
				"pref,4dlu, 100dlu,, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		label = factory.createLabel();
		button = factory.createTextButton();

		init();
		setComponents();

	}

	public void init() {
		name = label.getLabel("name");
		lastName = label.getLabel("lastName");
		index = label.getLabel("indexNumber");
		email = label.getLabel("sotsLogin");
		password = label.getLabel("password");

		nameText = new JTextField(TEXT_SIZE);
		lastNameText = new JTextField(TEXT_SIZE);
		indexText = new JTextField(TEXT_SIZE);
		emailText = new JTextField(TEXT_SIZE);
		passwordText = new JPasswordField(TEXT_SIZE);

		// activate = button.getButton("activateButton", "activateButton");
		// activate.get

	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(name, cc.xy(1, 1));
		add(lastName, cc.xy(1, 3));
		add(name, cc.xy(1, 5));
		add(index, cc.xy(1, 7));
		add(email, cc.xy(1, 9));
		add(password, cc.xy(1, 11));

		add(nameText, cc.xy(3, 1));
		add(lastNameText, cc.xy(3, 3));
		add(indexText, cc.xy(3, 5));
		add(nameText, cc.xy(3, 7));
		add(emailText, cc.xy(3, 9));
		add(passwordText, cc.xy(3, 11));

		// add(activate, cc.xy(3, 13));

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	private class InstantLogin implements IUserPassGetter {

		@Override
		public Map<String, String> getUserPass() throws IOException {

			Map<String, String> map = new HashMap<String, String>();
			map.put(LOGIN, emailText.getText());
			map.put(HASLO, new String(passwordText.getPassword()));

			return map;
		}

	}

	public class ActivateWorker extends Worker<Boolean> {
		public ActivateWorker() {
			super("set");
		}

		@Override
		protected Boolean doInBackground() throws Exception {
			startProgress();
			return DelegateFactory.getCommonDelegate().activateAccount(nameText.getText(), lastNameText.getText(),
					indexText.getText(), emailText.getText() + "@student.polsl.pl");
		}

		@Override
		public void done() {
			try {
				Boolean result = get();
				stopProgress();
				if (result != null) {
					if (result) {

						LoginManager.login(new InstantLogin());

					} else {
						JOptionPane.showMessageDialog(null, "B³êdne dane", "B³¹d aktywacji konta",
								JOptionPane.ERROR_MESSAGE);
						new AccountActivationDialog(parent);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Konto jest ju¿ aktywne", "B³¹d aktywacji konta",
							JOptionPane.ERROR_MESSAGE);
					new AccountActivationDialog(parent);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LoginException e) {
				JOptionPane.showMessageDialog(null, "B³êdne has³o lub brak konta w SOTS", "B³¹d aktywacji konta",
						JOptionPane.ERROR_MESSAGE);
				new RevertActivationWorker().execute();
			}
		}

	}

	private class RevertActivationWorker extends Worker<Void> {

		@Override
		protected Void doInBackground() throws Exception {
			startProgress();
			DelegateFactory.getCommonDelegate().revertActivation(emailText.getText());
			return null;
		}

		@Override
		public void done() {
			stopProgress();

		}

	}

}
