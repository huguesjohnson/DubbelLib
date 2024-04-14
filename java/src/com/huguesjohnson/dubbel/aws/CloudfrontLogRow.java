/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.aws;

//this is functionally a struct
public class CloudfrontLogRow{
	public String date; //really a date type in the log but this is easier to work with
	public String time;
	public String location;
	public Integer bytes; //note, not using primitive types because I want nulls, nulls rule
	public String request_ip;
	public String method;
	public String host;
	public String uri;
	public Integer status; //seriously, is there a better way to answer "does this field have a value?" than using null?
	public String referrer;
	public String user_agent;
	public String query_string;
	public String cookie; //for my usage this should always be null but including anyway
	public String result_type;
	public String request_id;
	public String host_header;
	public String request_protocol;
	public Integer request_bytes;
	public Float time_taken;
	public String xforwarded_for;
	public String ssl_protocol;
	public String ssl_cipher;
	public String response_result_type;
	public String http_version;
	public String fle_status;
	public Integer fle_encrypted_fields;
	public Integer c_port;
	public Float time_to_first_byte;
	public String x_edge_detailed_result_type;
	public String sc_content_type;
	public Integer sc_content_len;
	public Integer sc_range_start;
	public Integer sc_range_end;
}