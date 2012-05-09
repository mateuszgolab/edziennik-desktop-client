package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.filters.ReportFilter;
import com.polsl.edziennik.desktopclient.model.tables.ReportTableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;

public class ReportTablePanel extends TablePanel {

	private DateFilterPanel filter;

	public ReportTablePanel(ReportTableModel tableModel, DateFilterPanel filter, ButtonPanel buttonPanel, JPanel preview) {
		super(bundle.getString("ReportTitle"), tableModel, filter, buttonPanel, preview);
		setColumnWidths();

	}

	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(165);
		table.getColumnModel().getColumn(3).setPreferredWidth(64);
		table.getColumnModel().getColumn(4).setPreferredWidth(65);

	}

	@Override
	public int getComboSelectedIndex() {
		return filter.getComboSelectedIndex();
	}

	@Override
	public void runFilter(Long min, Long max) {

	}

	@Override
	public void runFilter(Long min, Long max, String s, String s2) {
		ReportFilter rf = new ReportFilter(min, max, s, s2);
		sorter.setRowFilter(rf);

	}

	@Override
	public void runFilter(Long min, Long max, String s) {
		// TODO Auto-generated method stub

	}

}
