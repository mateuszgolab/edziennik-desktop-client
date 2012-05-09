package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectActivityTableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;

public class ProjectActivityTablePanel extends TablePanel {

	private DateFilterPanel filter;

	public ProjectActivityTablePanel(ProjectActivityTableModel tableModel, ComboModel<?> comboBoxModel,
			DateFilterPanel filter, ButtonPanel buttonPanel, JPanel preview) {
		super(bundle.getString("ProjectActivityFrameTitle"), tableModel, comboBoxModel, bundle
				.getString("activityNamesHint"), filter, buttonPanel, preview);
		setColumnWidths();

	}

	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		for (int i = 1; i < 4; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(145);

		}

	}

	@Override
	public int getComboSelectedIndex() {
		return filter.getComboSelectedIndex();
	}

	@Override
	public void runFilter(Long min, Long max) {
		// TODO Auto-generated method stub

	}

	@Override
	public void runFilter(Long min, Long max, String s, String s2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void runFilter(Long min, Long max, String s) {
		// TODO Auto-generated method stub

	}
}
