package com.kmeisl.schichtkalender;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.Subscribe;
import com.kmeisl.schichtkalender.dataprovider.UserProvider;
import com.kmeisl.schichtkalender.domain.User;
import com.kmeisl.schichtkalender.event.SchichtkalenderEventBus;
import com.kmeisl.schichtkalender.repository.UserRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
//import com.kmeisl.schichtkalender.data.DataProvider;
import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.BrowserResizeEvent;
import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.CloseOpenWindowsEvent;
import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.UserLoggedOutEvent;
import com.kmeisl.schichtkalender.event.SchichtkalenderEvent.UserLoginRequestedEvent;
import com.kmeisl.schichtkalender.view.LoginView;
import com.kmeisl.schichtkalender.view.MainView;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@Theme("schichtkalender")
@Title("QuickTickets Schichtkalender")
public class SchichtkalenderUI extends UI {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(SchichtkalenderUI.class);

	private final SchichtkalenderEventBus dashboardEventbus = new SchichtkalenderEventBus();

	@Autowired
	UserRepository repo;
	
	@Autowired
	UserProvider userProvider;

	@Override
	protected void init(final VaadinRequest request) {
		setLocale(Locale.US);

		SchichtkalenderEventBus.register(this);
		Responsive.makeResponsive(this);
		addStyleName(ValoTheme.UI_WITH_MENU);

		updateContent();

		// Some views need to be aware of browser resize events so a
		// BrowserResizeEvent gets fired to the event bus on every occasion.
		Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void browserWindowResized(final BrowserWindowResizeEvent event) {
				SchichtkalenderEventBus.post(new BrowserResizeEvent());
			}
		});
	}

	/**
	 * Updates the correct content for this UI based on the current user status.
	 * If the user is logged in with appropriate privileges, main view is shown.
	 * Otherwise login view is shown.
	 */
	private void updateContent() {
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
		if (user != null && user.isAdmin()) {
			// Authenticated user
			setContent(new MainView());
			removeStyleName("loginview");
			getNavigator().navigateTo(getNavigator().getState());
		} else {
			setContent(new LoginView());
			addStyleName("loginview");
		}
	}

	@Subscribe
	public void userLoginRequested(final UserLoginRequestedEvent event) {
		User user = userProvider.authenticate(event.getUserName(), event.getPassword());
		VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
		updateContent();
	}

	@Subscribe
	public void userLoggedOut(final UserLoggedOutEvent event) {
		// When the user logs out, current VaadinSession gets closed and the
		// page gets reloaded on the login screen. Do notice the this doesn't
		// invalidate the current HttpSession.
		VaadinSession.getCurrent().close();
		Page.getCurrent().reload();
	}

	@Subscribe
	public void closeOpenWindows(final CloseOpenWindowsEvent event) {
		for (Window window : getWindows()) {
			window.close();
		}
	}

	// /**
	// * @return An instance for accessing the (dummy) services layer.
	// */
	// public static DataProvider getDataProvider() {
	// return ((SchichtkalenderUI) getCurrent()).dataProvider;
	// }

	public static SchichtkalenderEventBus getSchichtkalenderEventbus() {
		return ((SchichtkalenderUI) getCurrent()).dashboardEventbus;
	}
}
