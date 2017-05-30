package pl.jdev.oanda_rest_client.main.task;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskManager {

	private final static Logger LOGGER = LogManager.getLogger(TaskManager.class);

	private final ScheduledExecutorService scheduler;

	private Map<Integer, ScheduledFuture<?>> scheduledTasks;

	public TaskManager(int threadPoolCount) {
		LOGGER.debug("Initiating Task Scheduler...");

		scheduler = Executors.newScheduledThreadPool(threadPoolCount);
		scheduledTasks = new HashMap<Integer, ScheduledFuture<?>>();

		LOGGER.debug("Task Scheduler initiated.");
	}

	public int run(Runnable task) {
		LOGGER.debug("Running {} task...", task.getClass().getSimpleName());

		Future<?> taskHandle = scheduler.submit(task);
		int taskId = taskHandle.hashCode();

		LOGGER.debug("{} scheduled. Returning id {}.", task.getClass().getSimpleName(), taskId);
		return taskId;
	}

	/**
	 * Schedules a Runnable task to be executed at a fixed interval rate.
	 * Interval is a SECONDS time unit.
	 * 
	 * @param task
	 *            Runnable to be scheduled.
	 * @param interval
	 *            Seconds interval.
	 * @return taskId HashCode value as ID.
	 */
	public int scheduleAtFixedRate(Runnable task, int interval) {
		LOGGER.debug("Scheduling {} task at {} seconds interval...", task.getClass().getSimpleName(), interval);

		ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(task, 0, interval, SECONDS);
		int taskId = taskHandle.hashCode();
		scheduledTasks.put(taskId, taskHandle);

		LOGGER.debug("{} scheduled. Returning id {}.", task.getClass().getSimpleName(), taskId);
		return taskId;
	}

	/**
	 * Schedules all Runnable tasks provided in the taskList to be executed at
	 * their specified fixed interval rates. Interval is a SECONDS time unit.
	 * 
	 * @param taskList
	 *            A list of Runnable tasks (keys) with their fixed interval
	 *            schedule rates (values).
	 * 
	 */
	public void scheduleAllAtFixedRates(Map<Runnable, Integer> taskList) {
		LOGGER.debug("Scheduling multiple tasks...");

		for (Runnable task : taskList.keySet()) {
			scheduleAtFixedRate(task, taskList.get(task));
		}

		LOGGER.debug("Finished scheduling tasks.");
	}

	/**
	 * Deschedule a Runnable task with the provided task ID number. Task will
	 * finish its execution if currently running.
	 * 
	 * @param taskId
	 *            A HashCode ID of the task intended to be stopped.
	 */
	public void stop(int taskId) {
		LOGGER.debug("Stopping task ID {} ...", taskId);

		if (scheduledTasks.get(taskId) == null) {
			LOGGER.warn("Could not find task ID {} in scheduled tasks list.");
		} else {
			scheduledTasks.get(taskId).cancel(false);
		}

		LOGGER.debug("{} stopped.", taskId);
	}

	/**
	 * Deschedule a Runnable task with the provided task ID number. Forcibly
	 * stopping the task will interrupt its execution if currently running.
	 * 
	 * @param taskId
	 *            A HashCode ID of the task intended to be forcibly stopped.
	 */
	public void forceStop(int taskId) {
		LOGGER.debug("Forcing stop of task ID {} ...", taskId);

		if (scheduledTasks.get(taskId) == null) {
			LOGGER.warn("Could not find task ID {} in scheduled tasks list.");
		} else {
			scheduledTasks.get(taskId).cancel(true);
		}

		LOGGER.debug("{} stopped.", taskId);
	}

	/**
	 * Deschedule all Runnable tasks currently scheduled. Tasks will finish
	 * their execution if currently running.
	 */
	public void stopAll() {
		LOGGER.debug("Stopping all tasks...");

		for (Integer taskId : scheduledTasks.keySet()) {
			stop(taskId);
		}

		LOGGER.debug("All tasks stopped.");
	}

	public boolean isTaskRunning(Class<? extends Runnable> task) {
		return false;
	}

}
