package com.polsl.edziennik.desktopclient.view.teacher.menu;

import com.polsl.edziennik.desktopclient.controller.student.activities.ExerciseTopicSimpleAction;
import com.polsl.edziennik.desktopclient.controller.student.subject.SubjectRulesAction;
import com.polsl.edziennik.desktopclient.controller.student.teachers.TeachersOverviewAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.HappyHoursAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.LabActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ProjectAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ReportsAction;
import com.polsl.edziennik.desktopclient.controller.teacher.exams.ExamBrowseAction;
import com.polsl.edziennik.desktopclient.controller.teacher.groups.GroupsOverviewAction;
import com.polsl.edziennik.desktopclient.controller.teacher.students.StudentManagementAction;
import com.polsl.edziennik.desktopclient.controller.teacher.students.SummaryAction;
import com.polsl.edziennik.desktopclient.view.student.menu.StudentMenu;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

public class TeacherMenu extends StudentMenu {

	public TeacherMenu() {
		super();
	}

	public TeacherMenu(TeacherMainView parent) {
		create();
		init();
		addActions(parent);

	}

	@Override
	public void create() {
		super.create();
		students = menu.getMenu("Students", "StudentsIcon");
	}

	@Override
	public void init() {
		super.init();
		add(students);
	}

	public void addActions(TeacherMainView parent) {
		students.add(new StudentManagementAction(parent));
		students.add(new SummaryAction(parent));
		teachers.add(new TeachersOverviewAction(parent));
		groups.add(new GroupsOverviewAction(parent));
		activities.add(new ActivitiesAction(parent));
		activities.add(new LabActivitiesAction(parent));
		activities.add(new ExerciseTopicSimpleAction(parent));
		subject.add(new SubjectRulesAction(parent));
		activities.add(new HappyHoursAction(parent));
		activities.add(new ReportsAction(parent));
		activities.add(new ExerciseTopicSimpleAction(parent));
		// profile.add(new ProfileAction(parent));
		activities.add(new ProjectAction(parent));
		exams.add(new ExamBrowseAction(parent));

	}

}
