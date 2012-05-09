package com.polsl.edziennik.desktopclient.view.common;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ExerciseTopicComboBoxModel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.PassedComboBoxModel;
import com.polsl.edziennik.desktopclient.model.tables.ReportTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.SaveButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.ReportPreviewPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.ReportTablePanel;

public class Reports extends JPanel {
	private JSplitPane splitPane;
	protected ReportPreviewPanel preview;
	protected ReportTablePanel tablePanel;
	protected ReportTableModel tableModel;
	public ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private DateFilterPanel filter;
	protected ExerciseTopicComboBoxModel exerciseTopicComboModel;
	private PassedComboBoxModel passedComboModel;
	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected SaveButtonPanel savePanel;

	public Reports() {
		setLayout(new BorderLayout());

		tableModel = new ReportTableModel();
		factory.createLabel();
		exerciseTopicComboModel = new ExerciseTopicComboBoxModel();
		passedComboModel = new PassedComboBoxModel();
		filter = new DateFilterPanel(tableModel, exerciseTopicComboModel, bundle.getString("choseExerciseTopicHint"),
				"exerciseSubject", passedComboModel, bundle.getString("chosePassedHint"), "passed");

		preview = new ReportPreviewPanel(tableModel);
		tablePanel = new ReportTablePanel(tableModel, filter, null, preview);
		tablePanel.getSelectionModel().addListSelectionListener(new TableSelectionListener());

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(0.5);
		splitPane.setDividerSize(1);
		splitPane.add(tablePanel);
		splitPane.add(preview);
		add(splitPane);

		setVisible(true);

		preview.setEditable(false);
		preview.setEnabled(false);

	}

	private class TableSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selected = tablePanel.getSelected();
			if (selected > -1) {
				preview.setSelected(selected);
				preview.setEnabled(true);
				preview.setData(tableModel.get(selected));
				if (savePanel != null) savePanel.activateSave(true);

			}
		}

	}

}