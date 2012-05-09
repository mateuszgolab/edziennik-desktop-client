package com.polsl.edziennik.desktopclient.view.admin.menu;

import com.polsl.edziennik.desktopclient.controller.admin.activities.ExerciseTopicAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.HappyHoursManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.LabActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.ProjectManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.ReportsAction;
import com.polsl.edziennik.desktopclient.controller.admin.exams.ExamsManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.groups.GroupsManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.students.AddStudentsFromFileAction;
import com.polsl.edziennik.desktopclient.controller.admin.students.StudentManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.students.SummaryAction;
import com.polsl.edziennik.desktopclient.controller.admin.subject.SubjectRulesManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.teachers.TeacherManagementAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ActivitiesAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.teacher.menu.TeacherMenu;

public class AdminMenu extends TeacherMenu {

	public AdminMenu(AdminMainView parent) {
		create();
		init();
		addActions(parent);
	}

	public void addActions(AdminMainView parent) {
		students.add(new StudentManagementAction(parent));
		students.add(new SummaryAction(parent));
		teachers.add(new TeacherManagementAction(parent));
		groups.add(new GroupsManagementAction(parent));
		activities.add(new ExerciseTopicAction(parent));
		activities.add(new ProjectManagementAction(parent));
		subject.add(new SubjectRulesManagementAction(parent));
		students.add(new AddStudentsFromFileAction(parent));
		activities.add(new HappyHoursManagementAction(parent));
		activities.add(new ReportsAction(parent));
		activities.add(new ActivitiesAction(parent));
		activities.add(new LabActivitiesAction(parent));
		exams.add(new ExamsManagementAction(parent));
	}

}
