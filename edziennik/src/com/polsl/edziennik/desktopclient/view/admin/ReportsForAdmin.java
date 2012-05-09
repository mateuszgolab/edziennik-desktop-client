package com.polsl.edziennik.desktopclient.view.admin;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.view.common.Reports;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.resource.ReportDTO;

public class ReportsForAdmin extends Reports {

	public ReportsForAdmin() {
		AdminDataProvider provider = new AdminDataProvider();
		provider.execute();
		provider.startProgress();
	}

	private class AdminDataProvider extends Worker<List<ReportDTO>> {

		private List<ExerciseTopicDTO> topics;

		@Override
		protected List<ReportDTO> doInBackground() throws Exception {

			topics = DelegateFactory.getCommonDelegate().getExerciseTopics();
			return DelegateFactory.getAdminDelegate().getAllReports();
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

}
