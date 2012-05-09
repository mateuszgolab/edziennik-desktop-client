package com.polsl.edziennik.desktopclient.view.common.panels.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;

import com.polsl.edziennik.delegates.CommonDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.view.common.dialogs.StudentsDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.DownloadFilePanel;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class ReportButtonPanel extends DownloadFilePanel {

	private JButton studentsButton;
	private StudentsDialog students;
	private Integer reportId;

	public ReportButtonPanel() {
		super("reportDownloadHint");

		students = new StudentsDialog();

		studentsButton = button.getButton("studentsButton", "reportStudentsHint");
		add(studentsButton);

		studentsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (reportId != null) {
					StudentsProvider students = new StudentsProvider("get");
					students.execute();
					students.startProgress();
				}
			}
		});

		setEnabled(false);
	}

	public void setReport(Integer reportId) {
		this.reportId = reportId;
		studentsButton.setEnabled(true);

	}

	public void setFile(Integer fileId) {
		this.fileId = fileId;
		if (fileId != null)
			download.setEnabled(true);
		else
			download.setEnabled(false);

		studentsButton.setEnabled(true);
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		studentsButton.setEnabled(b);
	}

	private class StudentsProvider extends Worker<List<StudentDTO>> {

		public StudentsProvider(String operationType) {
			super(operationType);
		}

		@Override
		protected List<StudentDTO> doInBackground() throws Exception {
			CommonDelegate delegate = DelegateFactory.getCommonDelegate();
			return delegate.getStudentsFromReport(reportId);
		}

		@Override
		public void done() {

			stopProgress();
			try {

				students.setModel(get());
				students.setVisible(true);

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