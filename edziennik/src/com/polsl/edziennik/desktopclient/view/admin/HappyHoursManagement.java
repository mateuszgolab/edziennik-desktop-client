package com.polsl.edziennik.desktopclient.view.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.polsl.edziennik.delegates.AdminDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.view.common.HappyHours;
import com.polsl.edziennik.desktopclient.view.common.panels.button.DefaultButtonPanel;

public class HappyHoursManagement extends HappyHours {

	public HappyHoursManagement() {

		buttonPanel = new DefaultButtonPanel("saveHappyHoursHint", "addHappyHoursHint", "removeHappyHoursHint");
		add(buttonPanel, BorderLayout.SOUTH);

		buttonPanel.activate(false);

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				preview.setEnabled(true);
				preview.setData(null);
				buttonPanel.activateSave(true);

			}
		});

		buttonPanel.getSaveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (preview.checkDates()) {
					SaveWorker worker = new SaveWorker();
					worker.execute();
				} else {
					JOptionPane.showMessageDialog(null, bundle.getString("dateErrorText"),
							bundle.getString("dateError"), JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				RemoveWorker worker = new RemoveWorker();
				worker.startProgress();
				worker.execute();

			}
		});

	}

	private class RemoveWorker extends Worker<Void> {

		public RemoveWorker() {
			super("delete");
		}

		@Override
		protected Void doInBackground() throws Exception {

			startProgress();

			if (tableModel.get(tablePanel.getSelected()) != null) try {
				AdminDelegate delegate = DelegateFactory.getAdminDelegate();
				if (delegate != null) {
					delegate.removeHappyHours((tableModel.get(tablePanel.getSelected())).getId());
				}
			} catch (DelegateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			tableModel.remove(tablePanel.getSelected());

			return null;
		}

		@Override
		public void done() {

			stopProgress();
			preview.clear();
			buttonPanel.activate(false);
			preview.setEnabled(false);
		}
	}

	private class SaveWorker extends Worker<Void> {

		public SaveWorker() {
			super("set");
		}

		@Override
		protected Void doInBackground() {
			try {
				startProgress();
				preview.saveHappyHours();

			} catch (DelegateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {
			stopProgress();
			buttonPanel.activate(false);
			preview.setEnabled(false);
		}
	}

}
