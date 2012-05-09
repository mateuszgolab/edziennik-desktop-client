package com.polsl.edziennik.desktopclient.view.admin.panels.preview;

import java.util.Calendar;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.model.tables.ExamTableModel;
import com.polsl.edziennik.desktopclient.view.teacher.panels.TeacherExamPreviewPanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;

public class AdminExamPreviewPanel extends TeacherExamPreviewPanel {

	private ExamTableModel model;

	public AdminExamPreviewPanel(String title, ExamTableModel model) {
		super(title);
		this.model = model;
		setEditable(true);
	}

	protected void setExamData() {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, new Integer(lengthHours.getSelectedItem().toString()));
		c.set(Calendar.MINUTE, new Integer(lengthMinutes.getSelectedItem().toString()));

		currentExam.setDate(dateConverter.getDate(datePicker.getDate().getTime(), (String) hours.getSelectedItem(),
				(String) minutes.getSelectedItem()));

		currentExam.setPlace(roomText.getText());
		currentExam.setApproach(approachText.getText());
		currentExam.setScope(scopeText.getText());
		currentExam.setDuration(dateConverter.getDate(0L, (String) lengthHours.getSelectedItem(),
				(String) lengthMinutes.getSelectedItem()));
	}

	@Override
	public void save() {
		try {
			// zapis nowego egzaminu
			if (currentExam == null) {
				currentExam = new ExamDTO();
				setExamData();
				currentExam = DelegateFactory.getAdminDelegate().addExam(currentExam);
				model.add(currentExam);

			} else {
				setExamData();
				DelegateFactory.getAdminDelegate().updateExam(currentExam);
				model.update(currentExam, selected);

			}
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		if (model.get(selected) != null) try {
			DelegateFactory.getAdminDelegate().removeExam((model.get(selected)).getId());
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.remove(selected);
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		hours.setEnabled(b);
		minutes.setEnabled(b);
		lengthHours.setEnabled(b);
		lengthMinutes.setEnabled(b);
	}
}
