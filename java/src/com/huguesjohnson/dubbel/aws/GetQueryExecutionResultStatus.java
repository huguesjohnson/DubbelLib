/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.aws;

public abstract class GetQueryExecutionResultStatus{
	public final static String STATUS_SUBMITTED="SUBMITTED";
	public final static String STATUS_RUNNING="RUNNING";
	public final static String STATUS_QUEUED="QUEUED";
	public final static String STATUS_SUCCEEDED="SUCCEEDED";
	public final static String STATUS_FAILED="FAILED";
	public final static String STATUS_CANCELLED="CANCELLED";
}