package com.studio.tensor.ldm.test;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsTest
{
	//正式环境：http://gw.api.taobao.com/router/rest
	//沙箱环境：http://gw.api.tbsandbox.com/router/rest
	public static void main(String[] args) throws ApiException
	{
		TaobaoClient client = new DefaultTaobaoClient(
				"http://gw.api.taobao.com/router/rest", 
				"23951870", 
				"280b49675ec9c20c4990fd6896cbc1cc");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("王琦智");
		req.setSmsParamString("{\"code\":\"1234\"}");
		req.setRecNum("15626215416");
		req.setSmsTemplateCode("SMS_105720045");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	}
}
