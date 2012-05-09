package com.polsl.edziennik.desktopclient.controller;

import java.awt.event.ActionEvent;

import javax.security.auth.login.LoginException;
import javax.swing.AbstractAction;

import com.polsl.edziennik.delegates.login.LoginManager;
import com.polsl.edziennik.desktopclient.Start;
import com.polsl.edziennik.desktopclient.view.EdziennikDesktop;

public class LogoutAction extends AbstractAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		EdziennikDesktop.getInstance().hide();

		try {
			LoginManager.logout();
		} catch (LoginException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new Start();
	}
}
