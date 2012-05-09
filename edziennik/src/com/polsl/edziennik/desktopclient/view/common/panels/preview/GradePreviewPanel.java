package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.GradeTableModel;
import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;
import com.polsl.edziennik.modelDTO.grade.RegularGradeDTO;

public class GradePreviewPanel extends PreviewPanel {

	private JLabel grade;
	private JLabel gradeType;
	private JLabel description;
	private JTextField gradeText;
	private JComboBox gradeTypeBox;
	private JTextArea descriptionText;
	private ComboModel<GradeTypeDTO> comboModel;
	private RegularGradeDTO current;
	private Integer activityId;
	private Integer studentId;
	private GradeTableModel model;

	public GradePreviewPanel(GradeTableModel model, Integer activityId, Integer studentId) {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		this.model = model;
		this.activityId = activityId;
		this.studentId = studentId;

		FormLayout layout = new FormLayout(
				"pref,4dlu,100dlu,min",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref");

		setLayout(layout);

		ILabel label = factory.createLabel();
		factory.createTextButton();

		grade = label.getLabel("grade");
		gradeType = label.getLabel("gradeType");
		description = label.getLabel("description");

		gradeText = new JTextField(TEXT_SIZE);
		comboModel = new ComboModel<GradeTypeDTO>();
		gradeTypeBox = new JComboBox(comboModel);

		descriptionText = new JTextArea(5, 5);
		descriptionText.setLineWrap(true);
		descriptionText.setWrapStyleWord(true);

		setComponents();
		setEnabled(false);
	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(grade, cc.xy(1, 1));
		add(gradeText, cc.xy(3, 1));
		add(gradeType, cc.xy(1, 3));
		add(gradeTypeBox, cc.xy(3, 3));
		add(description, cc.xy(1, 5));
		add(descriptionText, cc.xy(3, 5));
	}

	@Override
	public void clear() {
		gradeText.setText("");
		descriptionText.setText("");

	}

	@Override
	public void setEnabled(boolean b) {
		gradeText.setEnabled(b);
		descriptionText.setEnabled(b);
		gradeTypeBox.setEnabled(b);

	}

	@Override
	public void save() {
		try {
			if (current == null) {
				current = new RegularGradeDTO();
				getData();
				current = DelegateFactory.getTeacherDelegate().addRegularGrade(current);
				model.add(current);
			} else {
				getData();
				DelegateFactory.getTeacherDelegate().updateRegularGrade(current);
				model.update(current, selected);
			}

		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {

		try {
			DelegateFactory.getTeacherDelegate().deleteRegularGrade(current.getId());
			model.remove(current);
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getData() {
		if (current == null) current = new RegularGradeDTO();
		current.setDescription(descriptionText.getText());
		current.setGrade(new Float(gradeText.getText()));
		current.setType(gradeTypeBox.getSelectedItem().toString());
		current.setStudentId(studentId);
		current.setActivityId(activityId);
	}

	public void setData(RegularGradeDTO g) {
		clear();
		if (g != null) {
			gradeText.setText(g.toString());
			comboModel.setSelectedItem(g.getType());
			gradeTypeBox.updateUI();
			descriptionText.setText(g.getDescription());
		} else
			// comboModel.removeAll();

			current = g;
	}

	public void setComboModel(List<GradeTypeDTO> list) {
		comboModel.setModel(list);
		gradeTypeBox.updateUI();
	}

}
