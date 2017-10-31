package com.example.key.com.weatherforcasting;


public class Weather {
    //Weather类的作用主要就是以下几个成员
    String cityname,pyname,stateDetail,tem1,tem2,temNow,windState,windDir,windPower,sd,time;

    public void addweather(String S) {
        //S指的是html源码的一行。
       if (S.startsWith("<city cityX")) {
           //附此处S的一个例子：
           //<city cityX="232" cityY="190.8" cityname="延庆" centername="延庆" fontColor="FFFFFF" pyName="yanqing" state1="0" state2="1" stateDetailed="晴转多云" tem1="15" tem2="3" temNow="11" windState="南风转北风小于3级" windDir="东北风" windPower="3级" humidity="33%" time="10:00" url="101010800"/>
           String[] SS = S.split(" ");
               if (SS.length >= 18) {
                   cityname = Getcon(SS[3]);
                   pyname = Getcon(SS[6]);
                   stateDetail = Getcon(SS[9]);
                   tem1 = Getcon(SS[10]);
                   tem2 = Getcon(SS[11]);
                   if(Integer.valueOf(tem1) < Integer.valueOf(tem2)){//交换顺序，tem2比较小
                       String T=tem2;
                       tem2=tem1;
                       tem1=T;
                   }
                   temNow = Getcon(SS[12]);
                   windState = Getcon(SS[13]);
                   windDir = Getcon(SS[14]);
                   windPower = Getcon(SS[15]);
                   sd = Getcon(SS[16]);
                   time = Getcon(SS[17]);
               }
        }else if (S.startsWith("<city quName")){
            //以下处理china传过来的数据。这是因为china传过来的html格式跟其他省市不一样，另外处理。
            //附此处S的一个例子：
            //<city quName="黑龙江" pyName="heilongjiang" cityname="哈尔滨" state1="1" state2="1" stateDetailed="多云" tem1="11" tem2="-2" windState="西南风转西风3-4级"/>
            String[] SS = S.split(" ");
            if (SS != null) {
                if (SS.length >= 10) {
                    cityname = Getcon(SS[1]);
                    pyname = Getcon(SS[2]);
                    stateDetail = Getcon(SS[6]);
                    tem1 = Getcon(SS[7]);
                    tem2 = Getcon(SS[8]);
                    if(Integer.valueOf(tem1) < Integer.valueOf(tem2)){//交换顺序，tem2比较小
                        String T=tem2;
                        tem2=tem1;
                        tem1=T;
                    }
                    temNow = Getcon(SS[8]);
                    windState = Getcon(SS[9]);
                    windDir ="暂无数据";
                    windPower = "";
                    sd = "暂无数据";
                    time = "未知";
                }
            }
        }
    }

    public String Getcon(String S){
        //这个函数参数形如A="B"(或者A="B"/>)，返回B。
        String[] SS=S.split("=");
        if(SS.length==2){
            if(SS[1].endsWith("/>")){
                return SS[1].substring(1,SS[1].length()-3);
            }else
            return SS[1].substring(1,SS[1].length()-1);
        }else{
            return "Failed";
        }
    }
}
