package com.polsl.edziennik.desktopclient.view.common;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.GroupTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.admin.panels.GroupTablePanel;
import com.polsl.edziennik.desktopclient.view.admin.panels.preview.GroupPreviewPanel;
import com.polsl.edziennik.modelDTO.group.GroupDTO;

public class GroupsOverview extends JPanel {
	private JSplitPane splitPane;
	private GroupPreviewPanel preview;
	private GroupTablePanel tablePanel;
	private GroupTableModel tableModel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public GroupsOverview() {
		setLayout(new BorderLayout());
		// setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("viewGroups")),
		// BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		preview = new GroupPreviewPanel(bundle.getString("PreviewTitle"), tableModel);
		tableModel = new GroupTableModel();

		DataProvider provider = new DataProvider("get");
		provider.execute();
		provider.startProgress();

		tablePanel = new GroupTablePanel(tableModel, null, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new TableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(preview);
		add(splitPane);

		setVisible(true);
		preview.setEnabled(false);
		preview.setEditable(false);

	}

	private class TableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				if (tableModel.get(selected) != null) {

					preview.setSelected(selected);
					preview.setData(tableModel.get(selected));
					preview.setEnabled(true);
				}

			}
		}

	}

	private class DataProvider extends Worker<List<GroupDTO>> {

		public DataProvider(String operationType) {
			super(operationType);
		}

		@Override
		protected List<GroupDTO> doInBackground() throws Exception {

			TeacherDelegate teacher = DelegateFactory.getTeacherDelegate();
			return teacher.getAllGroups();
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