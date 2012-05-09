package com.polsl.edziennik.desktopclient.view.common.panels.filter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class DefaultSimpleFilterPanel extends SimpleFilterPanel {

	private JTextField text;

	public DefaultSimpleFilterPanel(TableModel tableModel, String hint, String labelName) {
		super(tableModel, hint, labelName);
	}

	@Override
	public void setFilter(int size) {

		filter = new JPanel();

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

	public String getText() {
		if (text == null) return null;
		return text.getText();
	}

}
