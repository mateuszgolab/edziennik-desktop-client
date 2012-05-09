package com.polsl.edziennik.desktopclient.controller.utils.workers;

import javax.swing.SwingWorker;

import com.polsl.edziennik.desktopclient.view.common.dialogs.ProgressDialog;

public abstract class Worker<T> extends SwingWorker<T, Void> {

	protected ProgressDialog progress;

	public Worker(String operationType) {
		progress = new ProgressDialog(operationType);
	}

	public Worker(ProgressDialog dialog) {
		progress = dialog;
	}

	public Worker() {
		this("get");
	}

	@Override
	protected abstract T doInBackground() throws Exception;

	@Override
	public abstract void done();

	public void startProgress() {

		progress.start();
	}

	public void stopProgress() {
		progress.stop();
	}

}
