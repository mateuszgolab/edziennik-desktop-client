package com.polsl.edziennik.desktopclient.view.common.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;

public class GradePanel extends JPanel {
	protected ResourceBundle components = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	private ILabel label;
	private JComboBox gradeTypeBox;
	private JLabel gradeType;
	private JLabel description;
	private JTextField text;
	private ComboModel<GradeTypeDTO> model;

	public GradePanel() {
		super(new FlowLayout(FlowLayout.LEADING, 6, 6));

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		label = factory.createLabel();

		gradeType = label.getLabel("gradeType");
		description = label.getLabel("description");

		model = new ComboModel<GradeTypeDTO>();
		gradeTypeBox = new JComboBox(model);
		gradeTypeBox.setPreferredSize(new Dimension(120, (int) gradeTypeBox.getPreferredSize().getHeight()));
		text = new JTextField(15);

		add(gradeType);
		add(gradeTypeBox);
		add(description);
		add(text);

	}

	public void setModel(List<GradeTypeDTO> g) {
		model.setModel(g);
	}

	public String getType() {
		if (gradeTypeBox.getSelectedItem() == null) return "";
		return gradeTypeBox.getSelectedItem().toString();
	}

	public String getDescription() {
		return text.getText();
	}

	public Float getGrade() {
		return new Float(text.getText());
	}

}