package com.polsl.edziennik.desktopclient.view.common.panels.button;

public class ExerciseTopicButtonPanel extends ButtonPanel {

	@Override
	public void create() {
		add = Button.getButton("addIcon", "addExerciseTopicHint");
		remove = Button.getButton("removeIcon", "removeExerciseTopicHint");
		save = Button.getButton("saveIcon", "saveExerciseTopicHint");

	}

	@Override
	public void addOthers() {
		// TODO Auto-generated method stub

	}

}
