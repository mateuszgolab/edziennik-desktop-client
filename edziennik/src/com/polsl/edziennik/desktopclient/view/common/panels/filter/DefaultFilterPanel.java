package com.polsl.edziennik.desktopclient.view.common.panels.filter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.TablePanel;

public class DefaultFilterPanel extends FilterPanel {

	private JTextField text;
	
	public DefaultFilterPanel(TableModel tableModel, ComboModel<?> comboModel, String hint, String label,
			String comboHint, String comboLabelName) {
		super(tableModel, comboModel, hint,label, comboHint, comboLabelName);
	}

	@Override
	public void setFilter(int size) {
		if(filter == null) filter = new JPanel();
		label = Label.getLabel(labelName);
		text = new JTextField(size);
		text.setToolTipText(hint);
		icon = new JButton(new ImageIcon(bundle.getString("SearchIcon")));
		icon.setToolTipText(bundle.getString("filterHint"));
		add(icon);

		filter.add(label);
		filter.add(text);
		filter.add(icon);

		

	}

	@Override
	public String getText() {
		if(text != null) return text.getText();
		return null;
	}

}
