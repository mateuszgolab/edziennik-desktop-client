package com.polsl.edziennik.desktopclient.view.admin.panels.preview;

import java.util.List;

import javax.swing.JComboBox;

import com.jgoodies.forms.layout.CellConstraints;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.model.comboBoxes.ComboModel;
import com.polsl.edziennik.desktopclient.model.tables.ExamTaskTableModel;
import com.polsl.edziennik.desktopclient.view.teacher.panels.TeacherExamTaskPreviewPanel;
import com.polsl.edziennik.modelDTO.exam.ExamDTO;
import com.polsl.edziennik.modelDTO.exam.ExamTaskDTO;
import com.polsl.edziennik.modelDTO.exam.ExamTaskTypeDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class AdminExamTaskPreviewPanel extends TeacherExamTaskPreviewPanel {

	protected JComboBox typeBox;
	protected JComboBox teacherBox;

	protected ComboModel<ExamTaskTypeDTO> typeModel;
	protected ComboModel<TeacherDTO> teacherModel;

	public AdminExamTaskPreviewPanel(String title, ExamTaskTableModel model) {
		super(title, model);
		setEditable(true);

	}

	@Override
	public void create() {
		super.create();

		typeModel = new ComboModel<ExamTaskTypeDTO>();
		typeBox = new JComboBox(typeModel);
		teacherModel = new ComboModel<TeacherDTO>();
		teacherBox = new JComboBox(teacherModel);

	}

	@Override
	public void setComponents() {
		cc = new CellConstraints();

		add(name, cc.xy(1, 1));
		add(nameText, cc.xy(3, 1));
		add(type, cc.xy(1, 3));
		add(typeBox, cc.xy(3, 3));
		add(teacher, cc.xy(1, 5));
		add(teacherBox, cc.xy(3, 5));
		add(grades, cc.xy(3, 7));

	}

	@Override
	public void clear() {
		nameText.setText("");
		typeBox.setSelectedItem(null);
		teacherBox.setSelectedItem(null);
	}

	protected void setExamTaskData() {

		currentExamTask.setTeacher((TeacherDTO) teacherBox.getSelectedItem());
		currentExamTask.setType(typeBox.getSelectedItem().toString());
		currentExamTask.setName(nameText.getText());
		currentExamTask.setExam(currentExam);
	}

	@Override
	public void save() {
		try {
			// zapis nowego egzaminu
			if (currentExamTask == null) {
				currentExamTask = new ExamTaskDTO();
				setExamTaskData();
				currentExamTask = DelegateFactory.getAdminDelegate().addExamTask(currentExamTask);
				model.add(currentExamTask);

			} else {
				setExamTaskData();
				DelegateFactory.getAdminDelegate().updateExamTask(currentExamTask);
				model.update(currentExamTask, selected);

			}
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete() {
		if (model.get(selected) != null) try {
			DelegateFactory.getAdminDelegate().removeExamTask((model.get(selected)).getId());
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.remove(selected);
	}

	public void setExamTaskTypesModel(List<ExamTaskTypeDTO> list) {
		typeModel.setModel(list);
	}

	public void setTeacherModel(List<TeacherDTO> list) {
		teacherModel.setModel(list);
	}

	@Override
	public void setData(ExamTaskDTO e, ExamDTO exam) {

		currentExam = exam;
		// nowy exam task
		if (e == null) {
			clear();
		} else {

			if (e.getTeacher() != null) teacherBox.setSelectedItem(e.getTeacher());
			typeBox.setSelectedItem(e.getType());
			nameText.setText(e.getName());

		}

		currentExamTask = e;

	}

	@Override
	public void setEnabled(boolean b) {

		nameText.setEnabled(b);
		typeBox.setEnabled(b);
		teacherBox.setEnabled(b);
		grades.setEnabled(b);

	}
}
