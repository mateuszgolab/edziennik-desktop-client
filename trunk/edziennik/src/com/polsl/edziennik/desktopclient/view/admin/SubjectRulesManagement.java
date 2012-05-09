package com.polsl.edziennik.desktopclient.view.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.view.admin.dialogs.ExamTaskTypeDialog;
import com.polsl.edziennik.desktopclient.view.admin.dialogs.GradeTypeDialog;
import com.polsl.edziennik.desktopclient.view.common.SubjectRules;
import com.polsl.edziennik.desktopclient.view.common.panels.SendFilePanel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.SaveButtonPanel;
import com.polsl.edziennik.modelDTO.file.FileDTO;
import com.polsl.edziennik.modelDTO.subjectrules.SubjectRulesDTO;

public class SubjectRulesManagement extends SubjectRules {

	private SendFilePanel sendPanel;

	private final SaveButtonPanel buttonPanel;
	private JButton gradeTypes;
	private JButton examTaskTypes;
	private JLabel configure;
	private GradeTypeDialog gradeTypesPanel;
	private ExamTaskTypeDialog examTaskTypesPanel;
	private IFilter filter;

	public SubjectRulesManagement() {
		setEditable(true);

		buttonPanel = new SaveButtonPanel();
		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateWorker worker = new UpdateWorker();
				worker.execute();
				worker.startProgress();

			}
		});
		add(buttonPanel, BorderLayout.SOUTH);

	}

	@Override
	public void create() {
		super.create();

		filter = factory.createFilter();
		sendPanel = new SendFilePanel(filter.getDocumentFilter());
		sendPanel.getChoseButton().addActionListener(sendPanel.new DefaultListener());
		sendPanel.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDTO file = sendPanel.getFile();

				if (file == null || file.getContent() == null) {

					JOptionPane.showMessageDialog(null, bundle.getString("fileTransferError"));
				} else {
					SaveFileWorker w = new SaveFileWorker(file);
					w.execute();
					w.startProgress();

				}
			}
		});

		configure = label.getLabel("configure");

		gradeTypes = button.getButton("gradeTypes", "gradeTypesHint");
		gradeTypes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				gradeTypesPanel = new GradeTypeDialog();
			}
		});
		examTaskTypes = button.getButton("examTaskTypes", "examTaskTypesHint");
		examTaskTypes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				examTaskTypesPanel = new ExamTaskTypeDialog();

			}
		});
	}

	public void setSubjectRulesData() {
		if (currentSubject == null) currentSubject = new SubjectRulesDTO();

		currentSubject.setAcademicYear(academicYearText.getText());
		currentSubject.setSemester(semesterText.getText());
		currentSubject.setDescription(descriptionText.getText());
		currentSubject.setName(nameText.getText());
	}

	@Override
	public void setComponents() {

		super.setComponents();

		panel.add(sendPanel, cc.xyw(3, 17, 5));
		panel.add(configure, cc.xy(1, 21));
		panel.add(gradeTypes, cc.xy(3, 21));
		panel.add(examTaskTypes, cc.xyw(4, 21, 3));
	}

	@Override
	public void setEditable(boolean b) {
		nameText.setEditable(b);
		academicYearText.setEditable(b);
		semesterText.setEditable(b);
		descriptionText.setEditable(b);
	}

	private class UpdateWorker extends Worker<Void> {

		public UpdateWorker() {
			super("save");
		}

		@Override
		protected Void doInBackground() {

			try {
				setSubjectRulesData();
				DelegateFactory.getAdminDelegate().updateSubjectRules(currentSubject);
			} catch (DelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void done() {
			stopProgress();

		}
	}

	private class SaveFileWorker extends Worker<FileDTO> {
		private FileDTO f;

		public SaveFileWorker(FileDTO f) {
			super("save");
			this.f = f;
		}

		@Override
		protected FileDTO doInBackground() throws Exception {
			if (f != null) return DelegateFactory.getCommonDelegate().addFile(f);
			return null;

		}

		@Override
		public void done() {
			stopProgress();

			FileDTO tmp;
			try {
				tmp = get();
				if (tmp != null) {
					f.setId(tmp.getId());

					if (currentSubject == null) currentSubject = new SubjectRulesDTO();
					currentSubject.setFileId(tmp.getId());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private class FileProvider extends Worker<FileDTO> {

		@Override
		protected FileDTO doInBackground() throws Exception {
			return DelegateFactory.getCommonDelegate().getFile(currentSubject.getFileId());
		}

		@Override
		public void done() {

			stopProgress();
			try {
				FileDTO result = get();

				if (result != null)
					downloadFileChooser.setSelectedFile(new File(result.getName() + result.getExtension()));
				if (downloadFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					File f = downloadFileChooser.getSelectedFile();
					fileManager.saveFile(result, f.getAbsolutePath());

				}

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
