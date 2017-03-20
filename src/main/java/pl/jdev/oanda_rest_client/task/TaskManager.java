package pl.jdev.oanda_rest_client.task;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TaskManager {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	NetworkConnectionCheck netConnCheckTask = new NetworkConnectionCheck();

	public TaskManager() {
		scheduler.scheduleAtFixedRate(netConnCheckTask, 0, 30000, SECONDS);
	}

}
