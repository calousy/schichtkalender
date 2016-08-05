package com.kmeisl.schichtkalender.view;

import com.kmeisl.schichtkalender.view.dashboard.DashboardView;
import com.kmeisl.schichtkalender.view.reports.ReportsView;
import com.kmeisl.schichtkalender.view.schedule.ScheduleView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum SchichtkalenderViewType {
	DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, true), REPORTS("reports", ReportsView.class, FontAwesome.FILE_TEXT_O, true), SCHEDULE("schedule", ScheduleView.class, FontAwesome.CALENDAR_O, false);

	private final String viewName;
	private final Class<? extends View> viewClass;
	private final Resource icon;
	private final boolean stateful;

	private SchichtkalenderViewType(final String viewName, final Class<? extends View> viewClass, final Resource icon, final boolean stateful) {
		this.viewName = viewName;
		this.viewClass = viewClass;
		this.icon = icon;
		this.stateful = stateful;
	}

	public boolean isStateful() {
		return stateful;
	}

	public String getViewName() {
		return viewName;
	}

	public Class<? extends View> getViewClass() {
		return viewClass;
	}

	public Resource getIcon() {
		return icon;
	}

	public static SchichtkalenderViewType getByViewName(final String viewName) {
		SchichtkalenderViewType result = null;
		for (SchichtkalenderViewType viewType : values()) {
			if (viewType.getViewName().equals(viewName)) {
				result = viewType;
				break;
			}
		}
		return result;
	}

}
