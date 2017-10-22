package com.studio.tensor.ldm.utils;

import java.util.Map;

import com.google.gson.Gson;
import com.studio.tensor.ldm.bean.ALiDaYuResultBean;
import com.studio.tensor.ldm.bean.ALiDaYuResultBean.AlibabaAliqinFcSmsNumSendResponseBean;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsUtils
{
	//正式环境：http://gw.api.taobao.com/router/rest
	//沙箱环境：http://gw.api.tbsandbox.com/router/rest
	private static Boolean isRelease = true;
	private static String releaseUrl = "http://gw.api.taobao.com/router/rest";
	private static String debugUrl = "http://gw.api.tbsandbox.com/router/rest";
	private static String appKey = "23951870";//待更改
	private static String appSecret = "280b49675ec9c20c4990fd6896cbc1cc";//待更改
	public static AlibabaAliqinFcSmsNumSendResponseBean.ResultBean smsSend(String signName,
			Map<String, String> param,
			String phoneNums, String templateCode)
	{
		TaobaoClient client = new DefaultTaobaoClient(
				isRelease ? releaseUrl : debugUrl, appKey, appSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(signName);
		req.setSmsParamString(new Gson().toJson(param));
		req.setRecNum(phoneNums);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = new AlibabaAliqinFcSmsNumSendResponse();
		try
		{
			rsp = client.execute(req);
		} 
		catch (ApiException e)
		{
			e.printStackTrace();
		}
		
		return new Gson().fromJson(rsp.getBody(), ALiDaYuResultBean.class)
				.getAlibaba_aliqin_fc_sms_num_send_response()
				.getResult();
	}
}
