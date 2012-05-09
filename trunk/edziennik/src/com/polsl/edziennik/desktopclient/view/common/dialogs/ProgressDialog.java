package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;

public class ProgressDialog extends JDialog {

	public JProgressBar progressBar;
	private FrameToolkit frameToolkit = new FrameToolkit();
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public ProgressDialog(String operation) {

		setTitle(bundle.getString(operation));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(500, 80);
		setSize(preferredSize);
		// setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);
		add(progressBar);

	}

	public void start() {

		setVisible(true);

	}

	public void stop() {
		setVisible(false);
	}

}
