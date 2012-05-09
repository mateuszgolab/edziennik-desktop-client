package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.polsl.edziennik.desktopclient.controller.utils.workers.RemoveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.SaveWorker;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.GradeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.DefaultButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.GradePreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.GradeTablePanel;
import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class GradeDetailsDialog extends JDialog {

	private GradeTableModel model;
	private GradeTablePanel panel;
	private GradePreviewPanel preview;
	private JSplitPane splitPane;
	private DefaultButtonPanel buttonPanel;
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private FrameToolkit frameToolkit = new FrameToolkit();
	private JScrollPane scrollPane;

	public GradeDetailsDialog(StudentDTO s, Integer activityId) {

		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(800, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		if (s != null) {
			model = new GradeTableModel(s.getGrades());
			preview = new GradePreviewPanel(model, activityId, s.getId());
			setTitle(s.toString());
		} else {
			model = new GradeTableModel();
			preview = new GradePreviewPanel(model, activityId, null);
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

		buttonPanel = new DefaultButtonPanel("saveGradeHint", "addGradeHint", "removeGradeHint");
		add(buttonPanel, BorderLayout.SOUTH);

		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SaveWorker worker = new SaveWorker(preview, panel, buttonPanel);
				worker.execute();
				worker.startProgress();

			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveWorker w = new RemoveWorker(preview, panel, buttonPanel);
				w.execute();
				w.startProgress();
			}
		});

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GradeTypesProvider g = new GradeTypesProvider(null);
				g.execute();
				g.startProgress();
			}
		});

		buttonPanel.activate(false);

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
				buttonPanel.activateSave(true);
			} else
				buttonPanel.activate(true);
			preview.setEnabled(true);
			preview.setData(grade);
		}

	}
}
