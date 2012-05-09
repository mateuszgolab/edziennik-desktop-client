package com.polsl.edziennik.desktopclient.view.student.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.polsl.edziennik.desktopclient.model.tables.ExamTableModel;
import com.polsl.edziennik.desktopclient.view.common.dialogs.ExamTaskGradesDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.ExamPreviewPanel;
import com.polsl.edziennik.modelDTO.grade.ExamGradeDTO;
import com.polsl.edziennik.modelDTO.grade.ExamTaskGradeDTO;

public class StudentExamPreviewPanel extends ExamPreviewPanel {

	private JLabel grade;
	private JTextField gradeText;
	private JButton gradesButton;

	public StudentExamPreviewPanel(String title, ExamTableModel model) {
		super(title);
		setEditable(false);
		disable(true);
	}

	@Override
	public void create() {
		super.create();
		grade = label.getLabel("grade");
		gradeText = new JTextField(TEXT_SIZE);
		gradesButton = button.getButton("partialGradesButton", "examPartialGradesHint");
		gradesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ExamTaskGradesDialog(currentExam.getId());
			}
		});
	}

	@Override
	public void setComponents() {
		super.setComponents();
		add(grade, cc.xy(1, 11));
		add(gradeText, cc.xy(3, 11));
		add(gradesButton, cc.xyw(5, 11, 5));
	}

	@Override
	public void setEnabled(boolean b) {
		datePicker.setEnabled(b);
		roomText.setEnabled(b);
		approachText.setEnabled(b);
		scopeText.setEnabled(b);
		gradeText.setEnabled(b);
		gradesButton.setEnabled(b);
	}

	@Override
	public void clear() {
		super.clear();
		gradeText.setText("");
	}

	public void setExamGrade(ExamGradeDTO examGradeDTO) {

		if (examGradeDTO != null) gradeText.setText(examGradeDTO.getGrade().toString());
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	public void setExamGrades(List<ExamTaskGradeDTO> list) {
		// TODO Auto-generated method stub

	}

}
