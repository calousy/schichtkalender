package com.kmeisl.schichtkalender.view.dashboard;

import java.util.Iterator;

import com.google.common.eventbus.Subscribe;
import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.CloseOpenWindowsEvent;
import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.NotificationsCountUpdatedEvent;
import com.kmeisl.schichtkalender.event.SchichtkalenderEventBus;
import com.kmeisl.schichtkalender.view.dashboard.DashboardEdit.DashboardEditListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class DashboardView extends Panel implements View, DashboardEditListener {

	public static final String EDIT_ID = "dashboard-edit";
	public static final String TITLE_ID = "dashboard-title";

	private Label titleLabel;
	private NotificationsButton notificationsButton;
	private CssLayout dashboardPanels;
	private final VerticalLayout root;
	private Window notificationsWindow;

	public DashboardView() {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		setSizeFull();
		SchichtkalenderEventBus.register(this);

		root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.addStyleName("dashboard-view");
		setContent(root);
		Responsive.makeResponsive(root);

		root.addComponent(buildHeader());

		root.addComponent(buildSparklines());

		Component content = buildContent();
		root.addComponent(content);
		root.setExpandRatio(content, 1);
		
		// All the open sub-windows should be closed whenever the root layout
		// gets clicked.
		root.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(final LayoutClickEvent event) {
				SchichtkalenderEventBus.post(new CloseOpenWindowsEvent());
			}
		});
	}

	private Component buildSparklines() {
		CssLayout sparks = new CssLayout();
		sparks.addStyleName("sparks");
		sparks.setWidth("100%");
		Responsive.makeResponsive(sparks);

		// SparklineChart s = new SparklineChart("Traffic", "K", "",
		// DummyDataGenerator.chartColors[0], 22, 20, 80);
		// sparks.addComponent(s);
		//
		// s = new SparklineChart("Revenue / Day", "M", "$",
		// DummyDataGenerator.chartColors[2], 8, 89, 150);
		// sparks.addComponent(s);
		//
		// s = new SparklineChart("Checkout Time", "s", "",
		// DummyDataGenerator.chartColors[3], 10, 30, 120);
		// sparks.addComponent(s);
		//
		// s = new SparklineChart("Theater Fill Rate", "%", "",
		// DummyDataGenerator.chartColors[5], 50, 34, 100);
		// sparks.addComponent(s);

		return sparks;
	}

	private Component buildHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName("viewheader");
		header.setSpacing(true);

		titleLabel = new Label("Dashboard");
		titleLabel.setId(TITLE_ID);
		titleLabel.setSizeUndefined();
		titleLabel.addStyleName(ValoTheme.LABEL_H1);
		titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(titleLabel);

		notificationsButton = buildNotificationsButton();
		HorizontalLayout tools = new HorizontalLayout(notificationsButton);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		header.addComponent(tools);

		return header;
	}

	private NotificationsButton buildNotificationsButton() {
		NotificationsButton result = new NotificationsButton();
		result.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				openNotificationsPopup(event);
			}
		});
		return result;
	}


	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("dashboard-panels");
		Responsive.makeResponsive(dashboardPanels);

		dashboardPanels.addComponent(buildOwnUpcomingShifts());
		dashboardPanels.addComponent(buildUpcomingShifts());

		// dashboardPanels.addComponent(buildTop10TitlesByRevenue());
		// dashboardPanels.addComponent(buildPopularMovies());

		return dashboardPanels;
	}

	private Component buildUpcomingShifts() {
		Grid notes = new Grid("Offene Schichten");
		notes.setSizeFull();
//		notes.addStyleName(ValoTheme.);
		Component panel = createContentWrapper(notes);
		panel.addStyleName("notes");
		return panel;
	}
	
	private Component buildOwnUpcomingShifts() {
		Grid notes = new Grid("Meine Schichten");
		notes.setSizeFull();
//		notes.addStyleName(ValoTheme.);
		Component panel = createContentWrapper(notes);
		panel.addStyleName("notes");
		return panel;
	}

	// private Component buildTop10TitlesByRevenue() {
	// Component contentWrapper = createContentWrapper(new TopTenMoviesTable());
	// contentWrapper.addStyleName("top10-revenue");
	// return contentWrapper;
	// }
	//
	// private Component buildPopularMovies() {
	// return createContentWrapper(new TopSixTheatersChart());
	// }

	private Component createContentWrapper(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		Label caption = new Label(content.getCaption());
		caption.addStyleName(ValoTheme.LABEL_H4);
		caption.addStyleName(ValoTheme.LABEL_COLORED);
		caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		content.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!slot.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(slot, true);
				} else {
					slot.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(slot, false);
				}
			}
		});
		max.setStyleName("icon-only");
		MenuItem root = tools.addItem("", FontAwesome.COG, null);
//		root.addItem("Configure", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				Notification.show("Not implemented in this demo");
//			}
//		});
//		root.addSeparator();
//		root.addItem("Close", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				Notification.show("Not implemented in this demo");
//			}
//		});

		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}

	private void openNotificationsPopup(final ClickEvent event) {
		VerticalLayout notificationsLayout = new VerticalLayout();
		notificationsLayout.setMargin(true);
		notificationsLayout.setSpacing(true);

		Label title = new Label("Notifications");
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		notificationsLayout.addComponent(title);

//		Collection<SchichtkalenderNotification> notifications = SchichtkalenderUI.getDataProvider().getNotifications();
//		SchichtkalenderEventBus.post(new NotificationsCountUpdatedEvent());
//
//		for (SchichtkalenderNotification notification : notifications) {
//			VerticalLayout notificationLayout = new VerticalLayout();
//			notificationLayout.addStyleName("notification-item");
//
//			Label titleLabel = new Label(notification.getFirstName() + " " + notification.getLastName() + " " + notification.getAction());
//			titleLabel.addStyleName("notification-title");
//
//			Label timeLabel = new Label(notification.getPrettyTime());
//			timeLabel.addStyleName("notification-time");
//
//			Label contentLabel = new Label(notification.getContent());
//			contentLabel.addStyleName("notification-content");
//
//			notificationLayout.addComponents(titleLabel, timeLabel, contentLabel);
//			notificationsLayout.addComponent(notificationLayout);
//		}

		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth("100%");
		Button showAll = new Button("View All Notifications", new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				Notification.show("Not implemented in this demo");
			}
		});
		showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		showAll.addStyleName(ValoTheme.BUTTON_SMALL);
		footer.addComponent(showAll);
		footer.setComponentAlignment(showAll, Alignment.TOP_CENTER);
		notificationsLayout.addComponent(footer);

		if (notificationsWindow == null) {
			notificationsWindow = new Window();
			notificationsWindow.setWidth(300.0f, Unit.PIXELS);
			notificationsWindow.addStyleName("notifications");
			notificationsWindow.setClosable(false);
			notificationsWindow.setResizable(false);
			notificationsWindow.setDraggable(false);
			notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
			notificationsWindow.setContent(notificationsLayout);
		}

		if (!notificationsWindow.isAttached()) {
			notificationsWindow.setPositionY(event.getClientY() - event.getRelativeY() + 40);
			getUI().addWindow(notificationsWindow);
			notificationsWindow.focus();
		} else {
			notificationsWindow.close();
		}
	}

	@Override
	public void enter(final ViewChangeEvent event) {
		notificationsButton.updateNotificationsCount(null);
	}

	@Override
	public void dashboardNameEdited(final String name) {
		titleLabel.setValue(name);
	}

	private void toggleMaximized(final Component panel, final boolean maximized) {
		for (Iterator<Component> it = root.iterator(); it.hasNext();) {
			it.next().setVisible(!maximized);
		}
		dashboardPanels.setVisible(true);

		for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
			Component c = it.next();
			c.setVisible(!maximized);
		}

		if (maximized) {
			panel.setVisible(true);
			panel.addStyleName("max");
		} else {
			panel.removeStyleName("max");
		}
	}

	public static final class NotificationsButton extends Button {
		private static final String STYLE_UNREAD = "unread";
		public static final String ID = "dashboard-notifications";

		public NotificationsButton() {
			setIcon(FontAwesome.BELL);
			setId(ID);
			addStyleName("notifications");
			addStyleName(ValoTheme.BUTTON_ICON_ONLY);
			SchichtkalenderEventBus.register(this);
		}

		@Subscribe
		public void updateNotificationsCount(final NotificationsCountUpdatedEvent event) {
//			setUnreadCount(SchichtkalenderUI.getDataProvider().getUnreadNotificationsCount());
		}

		public void setUnreadCount(final int count) {
			setCaption(String.valueOf(count));

			String description = "Notifications";
			if (count > 0) {
				addStyleName(STYLE_UNREAD);
				description += " (" + count + " unread)";
			} else {
				removeStyleName(STYLE_UNREAD);
			}
			setDescription(description);
		}
	}

}
