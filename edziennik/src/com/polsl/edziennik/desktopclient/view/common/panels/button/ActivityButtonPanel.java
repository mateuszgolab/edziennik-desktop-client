package com.polsl.edziennik.desktopclient.view.common.panels.button;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;

public class ActivityButtonPanel extends ButtonPanel{

	private JButton students;
	private JButton attendances;
	private JButton gradesSheet;
	
	
	@Override
	public void create() {
		add = Button.getButton("addIcon", "addActivityHint");
		remove = Button.getButton("removeIcon", "removeActivityHint");
		save = Button.getButton("saveIcon", "saveActivityHint");
	
//		IButton textButton = factory.createTextButton();
//		
//		students = Button.getButton("StudentsBigIcon", "studentsButton");
//		attendances = Button.getButton("AttendancesBigIcon", "attendancesButton");
//		gradesSheet = Button.getButton("GradesBigIcon", "gradesSheetButton");
////		
	}

	@Override
	public void addOthers() {
		
//		add(students);
//		add(attendances);
//		add(gradesSheet);
//	
	}

}
