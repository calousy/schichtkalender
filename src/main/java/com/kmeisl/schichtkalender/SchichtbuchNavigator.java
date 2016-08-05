package com.kmeisl.schichtkalender;

import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.BrowserResizeEvent;
import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.CloseOpenWindowsEvent;
import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.PostViewChangeEvent;
import com.kmeisl.schichtkalender.event.SchichtkalenderEventBus;
import com.kmeisl.schichtkalender.view.SchichtkalenderViewType;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class SchichtbuchNavigator extends Navigator {

	private static final SchichtkalenderViewType ERROR_VIEW = SchichtkalenderViewType.DASHBOARD;
	private ViewProvider errorViewProvider;

	public SchichtbuchNavigator(final ComponentContainer container) {
		super(UI.getCurrent(), container);

		initViewChangeListener();
		initViewProviders();

	}

	private void initViewChangeListener() {
		addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(final ViewChangeEvent event) {
				// Since there's no conditions in switching between the views
				// we can always return true.
				return true;
			}

			@Override
			public void afterViewChange(final ViewChangeEvent event) {
				SchichtkalenderViewType view = SchichtkalenderViewType.getByViewName(event.getViewName());
				// Appropriate events get fired after the view is changed.
				SchichtkalenderEventBus.post(new PostViewChangeEvent(view));
				SchichtkalenderEventBus.post(new BrowserResizeEvent());
				SchichtkalenderEventBus.post(new CloseOpenWindowsEvent());
			}
		});
	}

	private void initViewProviders() {
		// A dedicated view provider is added for each separate view type
		for (final SchichtkalenderViewType viewType : SchichtkalenderViewType.values()) {
			ViewProvider viewProvider = new ClassBasedViewProvider(viewType.getViewName(), viewType.getViewClass()) {

				// This field caches an already initialized view instance if the
				// view should be cached (stateful views).
				private View cachedInstance;

				@Override
				public View getView(final String viewName) {
					View result = null;
					if (viewType.getViewName().equals(viewName)) {
						if (viewType.isStateful()) {
							// Stateful views get lazily instantiated
							if (cachedInstance == null) {
								cachedInstance = super.getView(viewType.getViewName());
							}
							result = cachedInstance;
						} else {
							// Non-stateful views get instantiated every time
							// they're navigated to
							result = super.getView(viewType.getViewName());
						}
					}
					return result;
				}
			};

			if (viewType == ERROR_VIEW) {
				errorViewProvider = viewProvider;
			}

			addProvider(viewProvider);
		}

		setErrorProvider(new ViewProvider() {
			@Override
			public String getViewName(final String viewAndParameters) {
				return ERROR_VIEW.getViewName();
			}

			@Override
			public View getView(final String viewName) {
				return errorViewProvider.getView(ERROR_VIEW.getViewName());
			}
		});
	}
}