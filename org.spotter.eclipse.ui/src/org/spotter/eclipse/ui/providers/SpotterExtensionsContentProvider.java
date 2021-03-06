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
package org.spotter.eclipse.ui.providers;

import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.spotter.eclipse.ui.listeners.IItemChangedListener;
import org.spotter.eclipse.ui.model.ExtensionItem;

/**
 * Content provider for extension items that can be used e.g. in a
 * <code>TableViewer</code> or <code>TreeViewer</code>. This content provider
 * expects input of type {@link ExtensionItem}.
 * 
 * @author Denis Knoepfle
 * 
 */
public class SpotterExtensionsContentProvider implements ITreeContentProvider, IItemChangedListener {

	private ExtensionItem extensionsInput;
	private ColumnViewer viewer;

	@Override
	public void dispose() {
		if (extensionsInput != null) {
			extensionsInput.removeItemChangedListener(this);
		}
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// remember viewer
		this.viewer = (ColumnViewer) viewer;
		// deregister listener from old input
		if (extensionsInput != null) {
			extensionsInput.removeItemChangedListener(this);
			extensionsInput = null;
		}
		// register listener at new input
		if (newInput instanceof ExtensionItem) {
			extensionsInput = (ExtensionItem) newInput;
			extensionsInput.addItemChangedListener(this);
		}
		this.viewer.refresh();
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ExtensionItem) {
			return ((ExtensionItem) parentElement).getItems();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof ExtensionItem) {
			return ((ExtensionItem) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ExtensionItem) {
			return ((ExtensionItem) element).hasItems();
		}
		return false;
	}

	@Override
	public void childAdded(ExtensionItem parent, ExtensionItem item) {
		if (viewer == null) {
			return;
		}
		if (viewer instanceof AbstractTableViewer) {
			((AbstractTableViewer) viewer).add(item);
		} else if (viewer instanceof AbstractTreeViewer) {
			((AbstractTreeViewer) viewer).add(parent, item);
		} else {
			viewer.refresh();
		}
	}

	@Override
	public void childRemoved(ExtensionItem parent, ExtensionItem item) {
		if (viewer == null) {
			return;
		}
		if (viewer instanceof AbstractTableViewer) {
			((AbstractTableViewer) viewer).remove(item);
		} else if (viewer instanceof AbstractTreeViewer) {
			((AbstractTreeViewer) viewer).remove(parent, new Object[] { item });
		} else {
			viewer.refresh();
		}
	}

	@Override
	public void appearanceChanged(ExtensionItem item) {
		if (viewer != null) {
			viewer.update(item, null);
		}
	}

}
