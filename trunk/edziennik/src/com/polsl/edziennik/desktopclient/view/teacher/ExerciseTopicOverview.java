package com.polsl.edziennik.desktopclient.view.teacher;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.delegates.CommonDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.ExerciseTopicTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.ExerciseTopicSimplePreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ExerciseTopicTablePanel;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;

public class ExerciseTopicOverview extends JPanel {

	private ExerciseTopicSimplePreviewPanel preview;
	private ExerciseTopicTablePanel tablePanel;
	private ExerciseTopicTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private JSplitPane splitPane;

	public ExerciseTopicOverview() {
		setLayout(new BorderLayout());

		tableModel = new ExerciseTopicTableModel();
		DataProvider p = new DataProvider();
		p.execute();
		p.startProgress();

		preview = new ExerciseTopicSimplePreviewPanel(bundle.getString("PreviewTitle"));
		tablePanel = new ExerciseTopicTablePanel(tableModel, null, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new ExerciseTopicTableSelectionListener());

		preview.setEditable(false);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(new JScrollPane(preview));
		add(splitPane);
		setVisible(true);

	}

	private class ExerciseTopicTableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				preview.setData(tableModel.get(selected));
				preview.setEnabled(true);

			}
		}

	}

	private class DataProvider extends Worker<List<ExerciseTopicDTO>> {

		@Override
		protected List<ExerciseTopicDTO> doInBackground() throws Exception {

			CommonDelegate factory = DelegateFactory.getCommonDelegate();
			return factory.getExerciseTopics();
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

		}

	}

}
