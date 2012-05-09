package com.polsl.edziennik.desktopclient.view.teacher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.view.common.Reports;
import com.polsl.edziennik.desktopclient.view.common.panels.button.SaveButtonPanel;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.resource.ReportDTO;

public class ReportsForTeacher extends Reports {

	public ReportsForTeacher() {

		preview.setEditable(true);
		TeacherDataProvider provider = new TeacherDataProvider();
		provider.execute();
		provider.startProgress();

		savePanel = new SaveButtonPanel();
		savePanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SaveWorker worker = new SaveWorker("set");
				worker.execute();
				worker.startProgress();

			}
		});

		add(savePanel, BorderLayout.SOUTH);
	}

	private class TeacherDataProvider extends Worker<List<ReportDTO>> {

		private List<ExerciseTopicDTO> topics;

		@Override
		protected List<ReportDTO> doInBackground() throws Exception {

			topics = DelegateFactory.getCommonDelegate().getExerciseTopics();
			// TODO currentTeacher
			return DelegateFactory.getTeacherDelegate().getReportsByTeacher(7);
		}

		@Override
		public void done() {
			stopProgress();
			try {
				tableModel.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ExerciseTopicDTO e = new ExerciseTopicDTO();
			e.setSubject(bundle.getString("all"));
			e.setId(-1);
			topics.add(e);
			exerciseTopicComboModel.setModel(topics);
			exerciseTopicComboModel.setSelectedItem(e);
			tablePanel.refresh();

		}
	}

	private class SaveWorker extends Worker<Void> {

		public SaveWorker(String operationType) {
			super(operationType);
		}

		@Override
		protected Void doInBackground() throws Exception {
			preview.save();
			return null;

		}

		@Override
		public void done() {
			stopProgress();
			savePanel.activate(false);
			preview.setEnabled(false);
		}

	}

}
