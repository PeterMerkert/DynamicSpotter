/**
 * Copyright 2014 SAP AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spotter.eclipse.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.spotter.eclipse.ui.Activator;
import org.spotter.eclipse.ui.dialogs.ServiceClientSettingsDialog;

/**
 * A handler for the service client settings command which opens the service
 * client settings dialog.
 * 
 * @author Denis Knoepfle
 * 
 */
public class ServiceClientSettingsHandler extends AbstractHandler {

	/**
	 * The id of the corresponding service client settings command.
	 */
	public static final String CLIENT_SETTINGS_COMMAND_ID = "org.spotter.eclipse.ui.commands.serviceClientSettings";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Activator activator = Activator.getDefault();
		if (activator.getSelectedProjects().size() != 1) {
			return null;
		}
		String projectName = activator.getSelectedProjects().iterator().next().getName();

		Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		ServiceClientSettingsDialog dialog = new ServiceClientSettingsDialog(shell, projectName);

		dialog.open();

		return null;
	}

	@Override
	public boolean isEnabled() {
		return Activator.getDefault().getSelectedProjects().size() == 1;
	}

}
