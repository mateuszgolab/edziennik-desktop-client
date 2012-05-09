package com.polsl.edziennik.desktopclient.view.teacher.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.model.tables.ExamTaskTableModel;
import com.polsl.edziennik.desktopclient.view.common.dialogs.ExamGradesDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.PreviewPanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;
import com.polsl.edziennik.modelDTO.exam.ExamTaskDTO;

public class TeacherExamTaskPreviewPanel extends PreviewPanel {

	protected JLabel name;
	protected JLabel type;
	protected JLabel teacher;
	protected JTextField nameText;
	protected JTextField typeText;
	protected JTextField teacherText;
	protected ILabel label;
	protected IButton button;
	protected JButton grades;
	protected ExamTaskDTO currentExamTask;
	protected ExamDTO currentExam;
	protected ExamTaskTableModel model;

	public TeacherExamTaskPreviewPanel(String title, ExamTaskTableModel model) {
		this.model = model;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		FormLayout layout = new FormLayout(
				"pref,4dlu, 100dlu,10dlu",
				"pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu, pref,4dlu, pref, 8dlu, pref, 1dlu, pref,1dlu, pref, 10dlu, pref");

		setLayout(layout);

		create();
		setComponents();
		setEnabled(false);
		setEditable(false);
	}

	public void create() {
		label = factory.createLabel();
		button = factory.createTextButton();
		name = label.getLabel("standardName");
		type = label.getLabel("type");
		teacher = label.getLabel("teacher");
		nameText = new JTextField(TEXT_SIZE);
		typeText = new JTextField(TEXT_SIZE);
		teacherText = new JTextField(TEXT_SIZE);
		grades = button.getButton("gradesButton", "examTaskGradesHint");
		grades.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ExamTaskDTO et = model.get(selected);
				if (et != null) new ExamGradesDialog(et.getId(), et.getName());
			}
		});
	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(name, cc.xy(1, 1));
		add(nameText, cc.xy(3, 1));
		add(type, cc.xy(1, 3));
		add(typeText, cc.xy(3, 3));
		add(teacher, cc.xy(1, 5));
		add(teacherText, cc.xy(3, 5));
		add(grades, cc.xy(3, 7));

	}

	@Override
	public void clear() {
		nameText.setText("");
		typeText.setText("");
		teacherText.setText("");
	}

	@Override
	public void setEnabled(boolean b) {
		nameText.setEnabled(b);
		typeText.setEnabled(b);
		teacherText.setEnabled(b);
		grades.setEnabled(b);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	public void setEditable(boolean b) {
		nameText.setEditable(b);
		typeText.setEditable(b);
		teacherText.setEditable(b);

	}

	public void setData(ExamTaskDTO e, ExamDTO exam) {
		// nowy exam task
		if (e == null) {
			clear();
		} else {

			if (e.getTeacher() != null) teacherText.setText(e.getTeacher().toString());
			typeText.setText(e.getType());
			nameText.setText(e.getName());

			currentExam = exam;

		}

		currentExamTask = e;

	}

}
