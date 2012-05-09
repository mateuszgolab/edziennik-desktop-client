package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.login.LoginManager;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.StudentExamTaskGradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.student.panels.table.StudentExamTaskGradesTablePanel;
import com.polsl.edziennik.modelDTO.grade.ExamTaskGradeDTO;

public class ExamTaskGradesDialog extends JDialog {

	private FrameToolkit frameToolkit = new FrameToolkit();
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private Integer examId;
	private StudentExamTaskGradeTableModel model;
	private StudentExamTaskGradesTablePanel panel;

	public ExamTaskGradesDialog(Integer examId) {
		this.examId = examId;
		setTitle(bundle.getString("grades"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(315, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());

		init();
		new GradesProvider().execute();

	}

	public void init() {
		model = new StudentExamTaskGradeTableModel();
		panel = new StudentExamTaskGradesTablePanel(model, bundle.getString("examGrades"));
		add(panel);
	}

	private class GradesProvider extends Worker<List<ExamTaskGradeDTO>> {

		@Override
		protected List<ExamTaskGradeDTO> doInBackground() throws Exception {
			startProgress();
			// TODO : current student
			// return
			// DelegateFactory.getCommonDelegate().getExamStudentGrades(examId,
			// 2);
			return DelegateFactory.getCommonDelegate().getExamStudentGrades(examId, LoginManager.getUser().getId());

		}

		@Override
		public void done() {
			try {
				model.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setVisible(true);
			stopProgress();
		}
	}

}
