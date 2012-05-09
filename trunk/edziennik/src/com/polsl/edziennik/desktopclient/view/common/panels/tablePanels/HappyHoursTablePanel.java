package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.filters.HappyHoursDateFilter;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DateFilterPanel;
import com.polsl.edziennik.modelDTO.happyhours.HappyHoursDTO;

public class HappyHoursTablePanel extends TablePanel {
	public HappyHoursTablePanel(TableModel<HappyHoursDTO> tableModel, final JPanel preview) {
		super(bundle.getString("happyHoursHint"), tableModel, bundle.getString("happyHoursHint"), new DateFilterPanel(
				tableModel), null, preview);
		setColumnWidths();
		// filterPosition = ?
	}

	public void setColumnWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int i = 1; i < 3; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(215);

		}

	}

	@Override
	public int getComboSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void runFilter(Long min, Long max) {
		HappyHoursDateFilter rf = new HappyHoursDateFilter(min, max);
		sorter.setRowFilter(rf);

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
