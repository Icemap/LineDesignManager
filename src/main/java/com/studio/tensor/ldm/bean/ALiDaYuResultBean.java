package com.studio.tensor.ldm.bean;

public class ALiDaYuResultBean
{

    private AlibabaAliqinFcSmsNumSendResponseBean alibaba_aliqin_fc_sms_num_send_response;

    public AlibabaAliqinFcSmsNumSendResponseBean getAlibaba_aliqin_fc_sms_num_send_response()
    {
        return alibaba_aliqin_fc_sms_num_send_response;
    }

    public void setAlibaba_aliqin_fc_sms_num_send_response(AlibabaAliqinFcSmsNumSendResponseBean alibaba_aliqin_fc_sms_num_send_response)
    {
        this.alibaba_aliqin_fc_sms_num_send_response = alibaba_aliqin_fc_sms_num_send_response;
    }

    public static class AlibabaAliqinFcSmsNumSendResponseBean
    {
        /**
         * result : {"err_code":"0","model":"576508808490687873^0","msg":"OK","success":true}
         * request_id : eoo1r51y8r67
         */

        private ResultBean result;
        private String request_id;

        public ResultBean getResult()
        {
            return result;
        }

        public void setResult(ResultBean result)
        {
            this.result = result;
        }

        public String getRequest_id()
        {
            return request_id;
        }

        public void setRequest_id(String request_id)
        {
            this.request_id = request_id;
        }

        public static class ResultBean
        {
            /**
             * err_code : 0
             * model : 576508808490687873^0
             * msg : OK
             * success : true
             */

            private String err_code;
            private String model;
            private String msg;
            private boolean success;

            public String getErr_code()
            {
                return err_code;
            }

            public void setErr_code(String err_code)
            {
                this.err_code = err_code;
            }

            public String getModel()
            {
                return model;
            }

            public void setModel(String model)
            {
                this.model = model;
            }

            public String getMsg()
            {
                return msg;
            }

            public void setMsg(String msg)
            {
                this.msg = msg;
            }

            public boolean isSuccess()
            {
                return success;
            }

            public void setSuccess(boolean success)
            {
                this.success = success;
            }
        }
    }
}
