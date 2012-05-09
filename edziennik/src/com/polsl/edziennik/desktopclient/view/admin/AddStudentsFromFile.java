package com.polsl.edziennik.desktopclient.view.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.HtmlEditor;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.SendFilePanel;
import com.polsl.edziennik.exceptions.InvalidFileException;
import com.polsl.edziennik.modelDTO.file.FileDTO;

public class AddStudentsFromFile extends JPanel {

	private SendFilePanel filePanel;
	public final ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory;
	private IFilter filter;
	private HtmlEditor htmlEditor;

	public AddStudentsFromFile() {

		setLayout(new BorderLayout());

		factory = GuiComponentFactory.getInstance();

		htmlEditor = new HtmlEditor(bundle.getString("addStudentsFromFileText"));

		// text = new JTextArea();
		// text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		// text.setLineWrap(true);
		// text.setWrapStyleWord(true);
		//
		// text.setText(bundle.getString("addStudentsFromFileText"));
		//
		// icon = new JLabel(new
		// ImageIcon(bundle.getString("addStudentsFromFileBigIcon")));
		//
		// textPanel = new JPanel(new BorderLayout());
		//
		// textPanel.add(text, BorderLayout.CENTER);
		// textPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
		// BorderFactory.createEmptyBorder(6, 0, 6, 0)));
		// textPanel.add(icon, BorderLayout.EAST);

		filter = factory.createFilter();

		filePanel = new SendFilePanel(filter.getXlsFilter());
		filePanel.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileDTO file = filePanel.getFile();

				if (file != null && file.getContent() != null) {
					DataSendWorker worker = new DataSendWorker(file.getContent());
					worker.execute();
				} else {
					JOptionPane.showMessageDialog(null, bundle.getString("fileTransferError"));
				}
			}
		});

		filePanel.getChoseButton().addActionListener(filePanel.new XlsFileListener());

		// add(textPanel, BorderLayout.NORTH);
		add(htmlEditor, BorderLayout.NORTH);
		add(filePanel, BorderLayout.CENTER);

	}

	private class DataSendWorker extends Worker<Void> {

		private byte[] content;

		public DataSendWorker(byte[] content) {
			super("set");

			this.content = content;

		}

		@Override
		protected Void doInBackground() {

			startProgress();
			// progress.setVisible(true);

			TeacherDelegate teacher;
			try {
				teacher = DelegateFactory.getTeacherDelegate();
				teacher.addStudentsFromXls(content);
			} catch (DelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {
			stopProgress();
			// progress.setVisible(false);
			JOptionPane.showMessageDialog(null, bundle.getString("fileTransferSucceeded"));
		}
	}

	// private class DataSendWorker extends SwingWorker<Void, Integer> {
	//
	// private byte[] content;
	// private ProgressDialog progress;
	//
	// public DataSendWorker(byte[] content) {
	// this.content = content;
	// progress = new ProgressDialog("set");
	// }
	//
	// @Override
	// protected Void doInBackground() throws Exception {
	//
	// progress.setVisible(true);
	//
	// TeacherDelegate teacher = DelegateFactory.getTeacherDelegate();
	// teacher.addStudentsFromXls(content);
	//
	// return null;
	// }
	//
	// @Override
	// protected void done() {
	// progress.setVisible(false);
	// JOptionPane.showMessageDialog(null,
	// bundle.getString("fileTransferSucceeded"));
	// }
	//
	// }
}
