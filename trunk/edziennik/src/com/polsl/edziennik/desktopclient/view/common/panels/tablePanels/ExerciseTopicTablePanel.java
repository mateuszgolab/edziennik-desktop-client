package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;

public class ExerciseTopicTablePanel extends SimpleTablePanel {

	public ExerciseTopicTablePanel(TableModel tableModel, final ButtonPanel buttonPanel, final JPanel preview) {
		super(bundle.getString("ExerciseTopicFrameTitle"), tableModel, bundle.getString("exerciseTopicsHint"), "exerciseSubject", buttonPanel, preview);
		setColumnWidths();
		filterPosition = 1;
	}

	public void setColumnWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(350);

	}

}
