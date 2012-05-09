package com.polsl.edziennik.desktopclient.controller.utils.filters;

import javax.swing.RowFilter;

import com.polsl.edziennik.desktopclient.model.tables.ReportTableModel;
import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.resource.ReportDTO;

public class ReportFilter extends DateFilter {

	private String exerciseTopicName;
	private String passed;

	public ReportFilter(Long min, Long max, String exerciseTopicName, String passed) {
		super(min, max);
		this.exerciseTopicName = exerciseTopicName;
		this.passed = passed;

	}

	@Override
	public boolean include(RowFilter.Entry<? extends TableModel<?>, ? extends Integer> entry) {
		ReportTableModel model = (ReportTableModel) entry.getModel();
		ReportDTO report = model.get(entry.getIdentifier());

		if (report == null) return false;
		if (report.getSentDate() < min || report.getSentDate() > max) return false;
		if (exerciseTopicName != null && exerciseTopicName.compareTo("Wszystkie") != 0) {
			LabActivityDTO lab = report.getLabActivity();
			if (lab != null) {
				ExerciseTopicDTO et = lab.getExerciseTopic();
				if (et != null) {
					if (et.getSubject().compareTo(exerciseTopicName) != 0) return false;
				} else
					return false;
			} else
				return false;
		}

		if (passed != null && passed.compareTo("Wszystkie") != 0) {
			if (report.getPassed() == null && !model.boolResult(passed))
				return true;
			else
				return false;
			// else if (report.getPassed() != null && report.getPassed() !=
			// model.boolResult(passed)) return false;
		}

		return true;
	}
}
