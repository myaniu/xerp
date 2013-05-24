/*
*  @author Junyu
*  @description 基于jquery的long - pulling方式的数据推实现

webcomet：

1.创建一个新对象
var c = new webcomet(options);//options为一个参数对象
options 参数格式： (这些参数jquery的ajax参数意义相同)
{
url:null //访问的路径
,data:null //传入的数据
,dataType:"json" //从数据库获取数据后转换成的类型，默认"json"
,timeout:null //访问服务端后等待服务端的超时时间
,callback:null //成功后调用的回调函数
,target:null //一般用于从服务端加载一个页面到客户端的一个div中.此时这个target就是暂存div这个对象
,id:null//一般用于标识当前实例化的comet对象。对下面的webcometBlock有作用
,succeInterval:0 // 成功得到服务器结果后据下次自动访问服务器的间隔时间
,errorInterval:0 //访问服务器失败后据下次自动访问服务器的间隔时间
,startDelay :0 //调用Start启动方法后 延迟的访问服务器间隔时间
}
//注意：以上这些参数不是必须的。
url-这个参数一般需要设置
callback-这个参数是服务器数据推送成功之后所调用的回调函数。
     
2.启动
c.Start(); //或者 c.Start(callback);如果初始化时没有设置callback，这里启动时设置也可以
     
3.停止
c.Stop();
     
完整的示例：
var comet = new webcomet({url:"/views/index.cshtml"});
//启动
comet.Start(function(data,comet){
//data为服务器返回回来的数据，根据之前设置的dataType值,默认会转换为JSON
//comet 此时的comet对象。
alert("服务器推送数据成功！显示一个提示框");
});
     
注意：
1.如果想用此实现服务器数据推，那么需要服务器一个异步请求线程阻塞才可实现.
     

webcometBlock：
-webcomet块（算是一个webomet的集合类）

使用：
//将一个 webcometBlock挂载到 window下 ，方便其中的 webcomet开始和停止
window.customCometBlock = window.customCometBlock || new webcometBlock().Add(
new webcomet({
id:"1"
,url:"/mamanger/view.do"
,callback:function(data,comet){
alert("成功返回数据1为："+data+" comet id为："+comet.id);
}
})).Add(
new webcomet({
id:"2"
,url:"/mamanger/show.do"
,callback:function(){
alert("成功返回数据2为："+data+" comet id为："+comet.id);
}
})).Start();
//以上事例是新 new 二个 webcomet ，调用webcometBlock的Add方法将其添加到 block的列表中，然后调用Start()方法同时开始两个webcomet访问服务器
//后期可以通过 直接访问 customCometBlock.Stop(); 来停止所有的webcomet连接
//block.Contains(cometid); //验证指定id的comet是否存在于block集合中
//block.GetById(cometid); //在block集合中获取指定id的comet

*/
(function ($) {
    /*
    Comet
    */
    function webcomet(options) {
        this.version = "1.1"; //版本
        this.id = null; //对象标识
        this.url = null;
        this.timeout = null; //访问服务器超时时间
        this.data = null;
        this.dataType = null;
        this.global = null; //是否应用全局AJaxStart..
        this.target = null; //如果是load的话配置的target
        this.callback = null; //服务器成功推来新数据时的回调函数
        this.status = null; //运行状态. 0已停止,1运行中,2停止中
        this.succeInterval = null; //成功循环调用间隔时间
        this.errorInterval = null; //失败循环调用间隔时间
        this.startDelay = null; //启动延迟间隔时间

        this.Init = function (options) {
            if (!options) {
                options = {};
            }
            this.id = options.id;
            this.url = options.url;
            this.timeout = options.timeout || 60000; //60秒
            this.data = options.data || "";
            this.dataType = options.dataType || "json";
            this.global = options.global || false;
            this.target = options.target;
            this.callback = options.callback;
            this.status = 0;
            this.succeInterval = options.succeInterval || 10000; //10秒
            this.errorInterval = options.errorInterval || 10000; //10秒
            this.startDelay = options.startDelay || 0; //默认不延迟启动
            return this;
        };

        this.Start = function (callback) {
            var comet = this;
            if (callback) {
                comet.callback = callback;
            }
            if (1 == comet.status) {
                return comet;
            }
            if (2 == comet.status) {
                comet.status = 1;
            }
            else {
                comet.status = 1;
                setTimeout(function () {
                    comet.Process();
                }, comet.startDelay);
            }
            return comet;
        };

        this.Process = function () {
            var comet = this;
            if (2 == comet.status) {
                return comet.Stop();
            }
            $.ajax({
                type: "POST",
                cache: false,
                url: comet.url,
                data: comet.data,
                dataType: comet.dataType,
                global: comet.global,
                timeout: comet.timeout,
                success: function (data, textStatus) {
                    if ($.isFunction(comet.callback)) comet.callback(data, comet);
                },
                complete: function (XMLHttpRequest, textStatus) {
                    if (("success" == textStatus) || ("timeout" == textStatus)) {
                        setTimeout(function () {
                            comet.Process();
                        }, comet.succeInterval);
                    } else {
                        setTimeout(function () {
                            comet.Process();
                        }, comet.errorInterval);
                    }
                }
            });
            return comet;
        };

        this.Stop = function () {
            if (1 == this.status)
                this.status = 2; //stoping
            else
                this.status = 0; //stoped
            return this;
        };

        return this.Init(options);
    };
    if (!window.webcomet) window.webcomet = webcomet;

    /*
    CometBlock
    块，一个放置Comet的集合
    */
    function webcometBlock(options) {
        this.id = null;
        this.count = null;
        this.items = null;

        this.Init = function (options) {
            if (!options) {
                options = {};
            }
            this.id = options.id;
            this.count = options.count;
            if (this.count) {
                this.items = new Array(this.count);
            }
            else {
                this.items = new Array();
            }
            return this;
        };

        this.Add = function (comet) {
            this.items[this.items.length] = comet;
            return this;
        };

        //测试一个Comet是否存在其中。根据cometid
        this.Contains = function (cometId) {
            for (var key in this.items) {
                var citem = this.items[key];
                if (citem && citem.id == cometId) {
                    return true;
                }
            }
            return false;
        };

        this.GetById = function (cometId) {
            for (var key in this.items) {
                var citem = this.items[key];
                if (citem && citem.id == cometId) {
                    return citem;
                }
            }
            return null;
        };

        this.Start = function () {
            for (var key in this.items) {
                var citem = this.items[key];
                if (citem) {
                    citem.Start();
                }
            }
            return this;
        };

        this.Stop = function () {
            for (var key in this.items) {
                var citem = this.items[key];
                if (citem) {
                    citem.Stop();
                }
            }
            return this;
        };

        return this.Init(options);
    };
    if (!window.webcometBlock) window.webcometBlock = webcometBlock;

})(jQuery);
