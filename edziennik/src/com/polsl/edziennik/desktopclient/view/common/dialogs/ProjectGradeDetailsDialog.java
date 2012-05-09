package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jidesoft.swing.JideScrollPane;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.GradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.GradePreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.GradeTablePanel;
import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;
import com.polsl.edziennik.modelDTO.summary.ProjectSummary;

public class ProjectGradeDetailsDialog extends JDialog {

	private GradeTableModel model;
	private GradeTablePanel panel;
	private GradePreviewPanel preview;
	private JSplitPane splitPane;
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private FrameToolkit frameToolkit = new FrameToolkit();
	private JScrollPane scrollPane;

	public ProjectGradeDetailsDialog(StudentDTO s, Integer projectId, ProjectSummary<RegularGradeDTO> summary) {

		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(800, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		if (s != null) {
			model = new GradeTableModel();
			if (summary != null) model.setModel(summary.getGrades());
			preview = new GradePreviewPanel(model, projectId, s.getId());
			setTitle(s.toString());
		} else {
			model = new GradeTableModel();
			model.setModel(summary.getGrades());
			preview = new GradePreviewPanel(model, projectId, null);
		}
		panel = new GradeTablePanel(model);

		panel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = panel.getSelected();
				if (selected > -1) {
					preview.setSelected(selected);
					GradeTypesProvider gtp = new GradeTypesProvider(model.get(selected));
					gtp.execute();
					gtp.startProgress();

				}
			}
		});

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.1);
		splitPane.setDividerSize(1);
		splitPane.add(panel);
		splitPane.add(preview);
		scrollPane = new JideScrollPane(splitPane);

		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	private class GradeTypesProvider extends Worker<List<GradeTypeDTO>> {

		private RegularGradeDTO grade;

		public GradeTypesProvider(RegularGradeDTO g) {
			super();
			grade = g;
		}

		@Override
		protected List<GradeTypeDTO> doInBackground() throws Exception {
			return DelegateFactory.getCommonDelegate().getGradeTypes();
		}

		@Override
		public void done() {
			stopProgress();
			try {
				preview.setComboModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (grade == null) {
				panel.clearSelection();

			} else

				preview.setEnabled(true);
			preview.setData(grade);
		}

	}
}
