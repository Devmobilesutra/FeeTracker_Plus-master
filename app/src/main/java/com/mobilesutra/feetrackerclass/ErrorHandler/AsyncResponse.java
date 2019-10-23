package com.mobilesutra.feetrackerclass.ErrorHandler;

public interface AsyncResponse<T> {

	 void processFinish(T output);
	 
	 void processFinishLog(T output);
	 
}
