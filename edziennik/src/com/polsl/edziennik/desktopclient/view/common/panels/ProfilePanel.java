package com.polsl.edziennik.desktopclient.view.common.panels;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.login.LoginManager;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.view.common.dialogs.ProfileDialog;
import com.polsl.edziennik.modelDTO.person.PersonDTO;

public class ProfilePanel extends JPanel {

	private JLabel password;
	private JLabel retype;

	private JPasswordField passwordText;
	private JPasswordField retypeText;
	private FrameToolkit frameToolkit = new FrameToolkit();

	private JButton save;

	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();

	private ILabel label;
	private IButton button;
	private CellConstraints cc;
	private ProfileDialog parent;

	public ProfilePanel(ProfileDialog parent) {

		this.parent = parent;

		FormLayout layout = new FormLayout(
				"pref,4dlu, 100dlu,, min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		label = factory.createLabel();
		button = factory.createTextButton();

		init();
		setComponents();
		setVisible(true);

	}

	public void init() {

		password = label.getLabel("newPassword");
		retype = label.getLabel("retype");

		passwordText = new JPasswordField(30);
		retypeText = new JPasswordField(30);

		save = button.getButton("save", "saveNewPass");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

	}

	public void setComponents() {
		cc = new CellConstraints();

		add(password, cc.xy(1, 1));
		add(retype, cc.xy(1, 3));

		add(passwordText, cc.xy(3, 1));
		add(retypeText, cc.xy(3, 3));

	}

	public class ChangePasswordWorker extends Worker<Boolean> {

		public ChangePasswordWorker() {
			super("set");
		}

		@Override
		protected Boolean doInBackground() throws Exception {
			startProgress();

			if (new String(passwordText.getPassword()).compareTo(new String(retypeText.getPassword())) == 0) {
				PersonDTO person = LoginManager.getUser();
				DelegateFactory.getTeacherDelegate().changeTeachersPassword(person.getId(),
						new String(passwordText.getPassword()));
				return true;
			} else
				return false;

		}

		@Override
		public void done() {
			stopProgress();
			try {
				if (!get()) {
					JOptionPane.showMessageDialog(null, "Podane has³a ró¿ni¹ siê", "B³¹d zmiany has³a",
							JOptionPane.ERROR_MESSAGE);

					parent.showDialog();
				}
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}