package secret_project.task;

import secret_project.exception.UsageException;

public interface TaskCommand {
	
	void runTask(TaskParameter taskParameter) throws UsageException; 
}
