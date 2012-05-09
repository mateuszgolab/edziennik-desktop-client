package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.filters.LabActivityFilter;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;

public class LabActivityTablePanel extends TablePanel {

	private DateFilterPanel filter;

	public LabActivityTablePanel(TableModel<?> tableModel, ComboModel<?> comboBoxModel, DateFilterPanel filter,
			ButtonPanel buttonPanel, JPanel preview) {
		super(bundle.getString("LabActivityFrameTitle"), tableModel, comboBoxModel, bundle
				.getString("activityNamesHint"), filter, buttonPanel, preview);
		setColumnWidths();

	}

	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		for (int i = 1; i <= 2; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(140);

		}
		table.getColumnModel().getColumn(3).setPreferredWidth(110);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
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
		LabActivityFilter rf = new LabActivityFilter(min, max, s);
		sorter.setRowFilter(rf);

	}

}
